package com.dvm.notification

import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.dvm.database.api.NotificationRepository
import com.dvm.navigation.api.Navigator
import com.dvm.navigation.api.model.Destination
import com.dvm.preferences.api.DatastoreRepository
import com.dvm.utils.AppLauncher
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NotificationService : FirebaseMessagingService(), KoinComponent {

    private val datastore: DatastoreRepository by inject()
    private val notificationRepository: NotificationRepository by inject()
    private val navigator: Navigator by inject()
    private val appLauncher: AppLauncher by inject()
    private val serviceScope: CoroutineScope by inject()

    override fun onNewToken(token: String) {
        Log.d(NOTIFICATION_SERVICE, "Refreshed token: $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        serviceScope.launch {
            if (!datastore.isAuthorized()) return@launch

            val id = message.data[NOTIFICATION_ID].orEmpty()
            val title = message.data[NOTIFICATION_TITLE].orEmpty()
            val text = message.data[NOTIFICATION_TEXT].orEmpty()

            notificationRepository.insertNotification(
                title = title,
                text = text,
                seen = false
            )

            if (navigator.currentDestination == Destination.Notification) return@launch

            val notificationManager =
                NotificationManagerCompat.from(this@NotificationService)

            notificationManager.createNotificationChannel(
                NotificationChannelCompat
                    .Builder(CHANNEL_ID, NotificationManagerCompat.IMPORTANCE_HIGH)
                    .setName(CHANNEL_NAME)
                    .setVibrationEnabled(true)
                    .build()
            )

            val notification = NotificationCompat
                .Builder(this@NotificationService, CHANNEL_ID)
                .setSmallIcon(com.dvm.ui.R.drawable.icon_logo)
                .setContentTitle(title)
                .setContentText(text)
                .setAutoCancel(true)
                .setStyle(NotificationCompat.BigTextStyle().bigText(text))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(createPendingIntent(id.hashCode()))
                .build()

            notificationManager.notify(title.hashCode(), notification)
        }
    }

    private fun createPendingIntent(requestCode: Int): PendingIntent {
        val intent =
            appLauncher.getLauncherIntent(this)
                .putExtra(NOTIFICATION_EXTRA, true)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        return PendingIntent.getActivity(
            this,
            requestCode.hashCode(),
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    companion object {
        private const val NOTIFICATION_SERVICE = "NotificationService"
        private const val CHANNEL_NAME = "Yammy Delivery"
        private const val CHANNEL_ID = "yammy_delivery"
        private const val NOTIFICATION_ID = "id"
        private const val NOTIFICATION_TITLE = "title"
        private const val NOTIFICATION_TEXT = "text"
        const val NOTIFICATION_EXTRA = "notification"
    }
}