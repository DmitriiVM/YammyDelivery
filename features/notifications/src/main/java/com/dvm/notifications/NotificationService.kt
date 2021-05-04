package com.dvm.notifications

import android.app.PendingIntent
import android.content.Intent
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.dvm.db.db_api.data.NotificationRepository
import com.dvm.db.db_api.data.models.Notification
import com.dvm.navigation.Navigator
import com.dvm.navigation.api.model.Destination
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
    lateinit var notificationRepository: NotificationRepository

    @Inject
    lateinit var scope: CoroutineScope

    @Inject
    lateinit var appLauncher: AppLauncher

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val title = message.data["title"].orEmpty()
        val text = message.data["text"].orEmpty()

        scope.launch {
            notificationRepository.insertNotification(
                Notification(
                    title = title,
                    text = text,
                    seen = false
                )
            )
        }

        if (navigator.currentDestination == Destination.Notification) return

        val notificationManager = NotificationManagerCompat.from(this)

        notificationManager.createNotificationChannel(
            NotificationChannelCompat
                .Builder(CHANNEL_ID, NotificationManagerCompat.IMPORTANCE_HIGH)
                .setName(CHANNEL_NAME)
                .setVibrationEnabled(true)
                .build()
        )

        val notification = NotificationCompat
            .Builder(this, CHANNEL_ID)
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
                    this,
                    title.hashCode(),
                    appLauncher.getLauncherIntent(this)
                        .putExtra(NOTIFICATION_EXTRA, true)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP),
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                )
            )
            .build()

        notificationManager.notify(title.hashCode(), notification)
    }

    companion object {
        private const val CHANNEL_NAME = "Yammy Delivery"
        private const val CHANNEL_ID = "yammy_delivery"
        const val NOTIFICATION_EXTRA = "notification"
    }
}