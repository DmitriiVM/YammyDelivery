package com.dvm.auth.auth_impl.restore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

internal class PasswordRestoreViewModel: ViewModel()

internal class PasswordRestoreViewModelFactory @Inject constructor() : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PasswordRestoreViewModel::class.java)) {
            return PasswordRestoreViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}