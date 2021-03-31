package com.dvm.auth.auth_impl.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class RegisterViewModel: ViewModel()

internal class RegisterViewModelFactory @Inject constructor() : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}