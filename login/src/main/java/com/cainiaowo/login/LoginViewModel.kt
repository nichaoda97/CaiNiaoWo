package com.cainiaowo.login

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    /**
     * 账号,密码的observable对象
     */
    val obMobile = ObservableField<String>()
    val obPassword = ObservableField<String>()

    fun goLogin() {

    }
}