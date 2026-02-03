package com.example.compoepractice.analytics

object AnalyticsLogger {

    fun init() {
        Timber.plant(Timber.DebugTree())
    }

    fun logOtpGenerated() = Timber.d("OTP Generated")
    fun logOtpSuccess() = Timber.d("OTP Success")
    fun logOtpFailure() = Timber.d("OTP Failure")
    fun logLogout() = Timber.d("User Logged Out")
}
