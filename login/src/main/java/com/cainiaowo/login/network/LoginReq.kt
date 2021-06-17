package com.cainiaowo.login.network

import androidx.annotation.Keep

@Keep
data class LoginReqBody(
    val mobi: String,
    val password: String
)