package com.dvm.auth.auth_impl.launcher

import android.content.Context
import androidx.fragment.app.Fragment
import com.dvm.auth.R
import com.dvm.auth.auth_impl.di.AuthComponentHolder

internal class AuthNavHostFragment: Fragment(R.layout.fragment_auth_nav_host) {
    override fun onAttach(context: Context) {
        super.onAttach(context)
        AuthComponentHolder.init()
    }
}