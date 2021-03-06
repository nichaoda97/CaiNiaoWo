package com.cainiaowo.login

import com.cainiaowo.common.base.BaseApplication
import org.koin.core.context.loadKoinModules

class LoginApplication : BaseApplication() {

    override fun initConfig() {
        super.initConfig()
        // 配置Koin
        loadKoinModules(moduleLogin)
    }

}