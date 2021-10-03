package com.dvm.utils

import android.content.Context
import android.content.Intent

interface AppLauncher {
    fun getLauncherIntent(context: Context): Intent
}