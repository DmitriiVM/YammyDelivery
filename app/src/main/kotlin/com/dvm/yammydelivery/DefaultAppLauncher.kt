package com.dvm.yammydelivery

import android.content.Context
import android.content.Intent
import com.dvm.utils.AppLauncher

class DefaultAppLauncher: AppLauncher {

    override fun getLauncherIntent(context: Context): Intent =
        Intent(context, MainActivity::class.java)
}