package com.cainiaowo.mine.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.alibaba.android.arouter.launcher.ARouter
import com.cainiaowo.common.base.BaseFragment
import com.cainiaowo.mine.R
import com.cainiaowo.mine.databinding.FragmentMineBinding
import com.cainiaowo.service.repo.CaiNiaoDbHelper
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * 我的
 */
class MineFragment : BaseFragment<FragmentMineBinding>() {

    private val viewModel: MineViewModel by viewModel()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_mine
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {

        mBinding.apply {
            vm = viewModel

            // UI登出操作
            btnLogoutMine.setOnClickListener {
                CaiNiaoDbHelper.deleteUserInfo(requireContext())
                ARouter.getInstance().build("/login/login").navigation()
            }

            // 使用navigation跳转到UserInfoFragment
            ivUserIconMine.setOnClickListener {
                val userInfo = viewModel.liveUserInfo.value
                if (userInfo == null) {
                    ARouter.getInstance().build("/login/login").navigation()
                } else {
                    val action = MineFragmentDirections
                        .actionMineFragmentToUserInfoFragment(userInfo)
                    findNavController().navigate(action)
                }
            }
        }
    }

    override fun initData() {
        super.initData()
        CaiNiaoDbHelper.getLiveUserInfo(requireContext()).observe {
            viewModel.getUserInfo(it?.token)
        }
    }
}