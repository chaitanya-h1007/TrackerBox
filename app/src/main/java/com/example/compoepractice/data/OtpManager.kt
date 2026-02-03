package com.example.compoepractice.data

// This Holds the Code,ExpirationTime and the LoginAttempts done Invalid
data class OtpData(
    val otpCode : String,
    val expiryTime : Long,
    var invalidLoginAttempts : Int = 0
)


class OtpManager{
    // Need A Local Storage To store the email and Otp
    // I Think to map the email -> Otp
    // Using the mutable Map (HashMap)
    private val otpStorage = mutableMapOf<String, OtpData>();

    //We need two method to Generate OTP
    fun generateOTP(email : String) : String{
        //generate random 6 digit code for now using random
        val otpData = (10000..999999).random().toString()
        //set expiry time for this code
        val expiryTime = System.currentTimeMillis() + 60_000
        //mapping the email -> otpData
        otpStorage[email] = OtpData(otpCode = otpData, expiryTime = expiryTime)
        return otpData;
    }


    //Validate OTP
    fun validateOTP(email : String, userInputOTP : String) : OtpResult{
        val otpData = otpStorage[email] ?: return OtpResult.Invalid

        if(System.currentTimeMillis() > otpData.expiryTime){
            otpStorage.remove(email)
            return OtpResult.Expired;
        }

        if(otpData.otpCode == userInputOTP){
            otpStorage.remove(email)
            return OtpResult.Success
        }else {
            otpData.invalidLoginAttempts++
            return OtpResult.AttemptsExceeded
        }

    }

    sealed class OtpResult {
        object Success : OtpResult()
        object Invalid : OtpResult()
        object Expired : OtpResult()
        object AttemptsExceeded : OtpResult()
    }
}