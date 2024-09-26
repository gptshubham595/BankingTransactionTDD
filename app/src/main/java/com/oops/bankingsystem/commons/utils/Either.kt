package com.oops.bankingsystem.commons.utils

sealed class Either {
    data class Success<T>(val data: T) : Either()
    data class Failed(val message: String) : Either()
}
