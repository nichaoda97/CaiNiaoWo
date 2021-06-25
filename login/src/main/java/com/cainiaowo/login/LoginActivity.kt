package com.cainiaowo.login

import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ToastUtils
import com.cainiaowo.common.base.BaseActivity
import com.cainiaowo.common.ktx.context
import com.cainiaowo.login.databinding.ActivityLoginBinding
import com.cainiaowo.login.network.RegisterRsp
import com.cainiaowo.service.repo.CaiNiaoDbHelper
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * 登录界面
 */
@Route(path = "/login/login")
class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    private val viewModel: LoginViewModel by viewModel()

    override fun getLayoutRes() = R.layout.activity_login

    override fun initView() {
        super.initView()
        mBinding.apply {
            vm = viewModel
            // 点击事件
            toolbarLogin.setNavigationOnClickListener { finish() }
            tvRegisterLogin.setOnClickListener { ToastUtils.showShort("点击了注册按钮,后端没有对应接口,只显示UI") }
        }
    }

    override fun initConfig() {
        super.initConfig()
        viewModel.apply {

            liveRegisterRsp.observe {
                if (it?.is_register == RegisterRsp.FLAG_REGISTERED) {
                    repoLogin()
                }
            }
            liveLoginRsp.observe {
                ToastUtils.showShort("登陆结果 " + it.toString())
                it?.let {
                    CaiNiaoDbHelper.insertUserInfo(context, it)
                }
                // 关闭activity
                finish()
            }
        }
    }
}