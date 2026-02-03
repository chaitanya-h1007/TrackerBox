package com.example.compoepractice.viewmodel
//viewModel for User Interaction for Authentication
data class AuthState(
    val email: String = "",
    val otpInput: String = "",
    val message: String? = null,
    val sessionStartTime: Long? = null
)
