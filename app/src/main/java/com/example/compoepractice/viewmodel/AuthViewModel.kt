package com.example.compoepractice.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.compoepractice.data.OtpManager
import com.example.compoepractice.analytics.AnalyticsLogger

class AuthViewModel(
    private val otpManager: OtpManager,
    private val logger: AnalyticsLogger
) : ViewModel() {

    var state by mutableStateOf(AuthState())
        private set

    fun updateEmail(email: String) {
        state = state.copy(email = email)
    }

    fun updateOtp(otp: String) {
        state = state.copy(otpInput = otp)
    }

    fun sendOtp() {
        val otp = otpManager.generateOTP(state.email)
        logger.logOtpGenerated()
        state = state.copy(message = "OTP sent: $otp") // show locally
    }

    fun validateOtp(onSuccess: () -> Unit) {
        when (otpManager.validateOTP(state.email, state.otpInput)) {
            OtpManager.OtpResult.Success -> {
                logger.logOtpSuccess()
                state = state.copy(sessionStartTime = System.currentTimeMillis())
                onSuccess()
            }
            OtpManager.OtpResult.Invalid -> {
                logger.logOtpFailure()
                state = state.copy(message = "Invalid OTP")
            }
            OtpManager.OtpResult.Expired -> state = state.copy(message = "OTP Expired")
            OtpManager.OtpResult.AttemptsExceeded -> state = state.copy(message = "Attempts Exceeded")
        }
    }

    fun logout(onLogout: () -> Unit) {
        logger.logLogout()
        state = AuthState()
        onLogout()
    }
}
