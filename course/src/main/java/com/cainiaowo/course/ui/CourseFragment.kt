package com.cainiaowo.course.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.cainiaowo.common.base.BaseFragment
import com.cainiaowo.course.R
import com.cainiaowo.course.databinding.FragmentCourseBinding
import com.cainiaowo.course.viewmodel.CourseViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * 课程
 */
class CourseFragment : BaseFragment<FragmentCourseBinding>() {

    private val viewModel: CourseViewModel by viewModel()

    override fun getLayoutRes() = R.layout.fragment_course

    override fun initView(view: View, savedInstanceState: Bundle?) {
        super.initView(view, savedInstanceState)
        mBinding.apply {
            vm = viewModel
        }
    }

    override fun initData() {
        super.initData()
        lifecycleScope.launchWhenCreated {
            viewModel.getCourseList().collectLatest { pagingData ->
                viewModel.adapter.submitData(pagingData)
            }
        }
    }
}