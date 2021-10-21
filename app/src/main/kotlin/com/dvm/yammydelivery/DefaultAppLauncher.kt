package com.dvm.yammydelivery

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import com.dvm.utils.AppLauncher

class DefaultAppLauncher: AppLauncher {

    override fun getLauncherIntent(context: Context): Intent {
        val activityManager =
            context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val isAppRunning = activityManager.appTasks.isNotEmpty()

        return if (isAppRunning) {
            Intent(context, MainActivity::class.java)
        } else {
            Intent(
                Intent.ACTION_VIEW,
                NOTIFICATION_URI.toUri(),
                context,
                MainActivity::class.java
            )
        }
    }
}