package com.dvm.notification

import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.dvm.database.api.NotificationRepository
import com.dvm.database.api.models.Notification
import com.dvm.navigation.api.Navigator
import com.dvm.navigation.api.model.Destination
import com.dvm.preferences.api.DatastoreRepository
import com.dvm.utils.AppLauncher
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NotificationService : FirebaseMessagingService() {

    @Inject
    lateinit var navigator: Navigator
    @Inject
    lateinit var serviceScope: CoroutineScope
    @Inject
    lateinit var appLauncher: AppLauncher
    @Inject
    lateinit var notificationRepository: NotificationRepository
    @Inject
    lateinit var datastore: DatastoreRepository

    override fun onNewToken(token: String) {
        Log.d(NOTIFICATION_SERVICE, "Refreshed token: $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        serviceScope.launch {
            if (!datastore.isAuthorized()) return@launch

            val title = message.data[NOTIFICATION_TITLE].orEmpty()
            val text = message.data[NOTIFICATION_TEXT].orEmpty()

            notificationRepository.insertNotification(
                Notification(
                    title = title,
                    text = text,
                    seen = false
                )
            )

            if (navigator.currentDestination == Destination.Notification) return@launch

            val notificationManager = NotificationManagerCompat.from(this@NotificationService)

            notificationManager.createNotificationChannel(
                NotificationChannelCompat
                    .Builder(CHANNEL_ID, NotificationManagerCompat.IMPORTANCE_HIGH)
                    .setName(CHANNEL_NAME)
                    .setVibrationEnabled(true)
                    .build()
            )

            val notification = NotificationCompat
                .Builder(this@NotificationService, CHANNEL_ID)
                .setSmallIcon(R.drawable.icon_logo)
                .setContentTitle(title)
                .setContentText(text)
                .setStyle(
                    NotificationCompat
                        .BigTextStyle()
                        .bigText(text)
                )
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(
                    PendingIntent.getActivity(
                        this@NotificationService,
                        title.hashCode(),
                        appLauncher.getLauncherIntent(this@NotificationService)
                            .putExtra(NOTIFICATION_EXTRA, true)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP),
                        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                    )
                )
                .build()

            notificationManager.notify(title.hashCode(), notification)
        }
    }

    companion object {
        private const val NOTIFICATION_SERVICE = "NotificationService"
        private const val CHANNEL_NAME = "Yammy Delivery"
        private const val CHANNEL_ID = "yammy_delivery"
        private const val NOTIFICATION_TITLE = "title"
        private const val NOTIFICATION_TEXT = "text"
        const val NOTIFICATION_EXTRA = "notification"
    }
}