package com.cainiaowo.login.repo

import com.cainiaowo.login.network.ILoginService
import com.cainiaowo.login.network.LoginReqBody

/**
 * 登录模块相关的数据管理类
 */
class LoginRepo(private val service: ILoginService):ILoginResource {

    /**
     * 数据提供者,不具备感知状态,用suspend修饰,
     * 在调用该方法处处理
     */
    override suspend fun checkRegister(mobi: String) {
        service.checkRegister(mobi)
    }

    override suspend fun requestLogin(body: LoginReqBody) {
        service.login(body)
    }
}