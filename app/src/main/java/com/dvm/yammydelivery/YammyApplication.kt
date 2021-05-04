package com.dvm.yammydelivery

import android.app.Application
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
internal class YammyApplication: Application(){

    override fun onCreate() {
        super.onCreate()
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            Log.d("mmm", "YammyApplication :  onCreate --  ${it.result}")
        }
    }
}