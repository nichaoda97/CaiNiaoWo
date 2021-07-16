package com.cainiaowo.study.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cainiaowo.common.base.BaseFragment
import com.cainiaowo.study.R
import com.cainiaowo.study.databinding.FragmentStudyBinding
import com.cainiaowo.study.databinding.FragmentStudyDetailBinding
import com.google.android.material.tabs.TabLayoutMediator

/**
 * 学习中心
 */
class StudyFragment : BaseFragment<FragmentStudyBinding>() {

    override fun getLayoutRes() = R.layout.fragment_study

    private val fragments = mapOf<Int, Fragment>(
        0 to StudyListFragment(),
        1 to BoughtCourseFragment()
    )

    override fun initView(view: View, savedInstanceState: Bundle?) {
        super.initView(view, savedInstanceState)
        mBinding.apply {
            // ViewPager2适配器
            viewPager2.adapter = StudyViewPager2Adapter(this@StudyFragment, fragments)

            viewPager2.isUserInputEnabled = false

            TabLayoutMediator(tlStudy, viewPager2) { tab, position ->
                when (position) {
                    0 -> tab.apply {
                        text = "最近学习"
                    }
                    1 -> tab.apply {
                        text = "我的课程"
                    }
                }
            }.attach()
        }
    }

}

/**
 * 学习中心下面部分的ViewPager2适配器
 */
class StudyViewPager2Adapter(
    fragment: Fragment,
    private val fragments: Map<Int, Fragment>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int): Fragment =
        fragments[position] ?: error("请确保fragments数据源和viewPager2的index匹配设置")
}


/**
 * 最近学习
 */
class StudyListFragment : BaseFragment<FragmentStudyDetailBinding>() {

    override fun getLayoutRes() = R.layout.fragment_study_detail

}

/**
 * 我的课程
 */
class BoughtCourseFragment : BaseFragment<FragmentStudyDetailBinding>() {

    override fun getLayoutRes() = R.layout.fragment_study_detail

}