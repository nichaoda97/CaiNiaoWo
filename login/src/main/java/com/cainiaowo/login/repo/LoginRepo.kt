package com.cainiaowo.login.repo

import androidx.lifecycle.LiveData
import com.blankj.utilcode.util.LogUtils
import com.cainiaowo.common.livedata.SingleLiveData
import com.cainiaowo.common.network.model.onFailure
import com.cainiaowo.common.network.model.onSuccess
import com.cainiaowo.common.network.support.serveData
import com.cainiaowo.login.network.ILoginService
import com.cainiaowo.login.network.LoginReqBody
import com.cainiaowo.login.network.LoginRsp
import com.cainiaowo.login.network.RegisterRsp
import com.cainiaowo.service.network.onBizError
import com.cainiaowo.service.network.onBizOK

/**
 * 登录模块相关的数据管理类
 */
class LoginRepo(private val service: ILoginService) : ILoginResource {

    private val _registerRsp = SingleLiveData<RegisterRsp>()
    private val _loginRsp = SingleLiveData<LoginRsp>()

    override val registerRsp: LiveData<RegisterRsp> = _registerRsp
    override val loginRsp: LiveData<LoginRsp> = _loginRsp

    /**
     * 数据提供者,不具备感知状态,用suspend修饰,
     * 在调用该方法处处理
     */
    override suspend fun checkRegister(mobi: String) {
        service.checkRegister(mobi)
            .serveData()
            .onSuccess {
                // 接口响应成功时
                onBizError { code, message ->
                    LogUtils.w("是否注册 BizError $code,$message")
                }
                onBizOK<RegisterRsp> { code, data, message ->
                    _registerRsp.value = data
                    LogUtils.i("是否注册 BizOK $data")
                    return@onBizOK
                }
            }
            .onFailure {
                // 接口响应失败
                LogUtils.e("是否注册接口异常 ${it.message}")
            }
    }

    override suspend fun requestLogin(body: LoginReqBody) {
        service.login(body)
            .serveData()
            .onSuccess {
                // 接口响应成功时
                onBizError { code, message ->
                    LogUtils.w("登录接口 BizError $code,$message")
                }
                onBizOK<LoginRsp> { code, data, message ->
                    _loginRsp.value = data
                    LogUtils.i("登录接口 BizOK $data")
                    return@onBizOK
                }
            }
            .onFailure {
                // 接口响应失败
                LogUtils.e("登录接口异常 ${it.message}")
            }
    }
}