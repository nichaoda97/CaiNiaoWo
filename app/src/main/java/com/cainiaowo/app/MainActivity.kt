package com.cainiaowo.app

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cainiaowo.app.databinding.ActivityMainBinding
import com.cainiaowo.common.base.BaseActivity
import com.cainiaowo.common.widget.BnvVp2Mediator
import com.cainiaowo.course.ui.CourseFragment
import com.cainiaowo.home.ui.HomeFragment
import com.cainiaowo.mine.ui.MineContainerFragment
import com.cainiaowo.study.ui.StudyFragment

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getLayoutRes() = R.layout.activity_main

    private val fragments = mapOf<Int, Fragment>(
        INDEX_HOME to HomeFragment(),
        INDEX_COURSE to CourseFragment(),
        INDEX_STUDY to StudyFragment(),
        INDEX_MINE to MineContainerFragment(),
    )

    override fun initView() {
        super.initView()
        mBinding.apply {
            // ViewPager2适配器
            vp2Main.adapter = MainViewPager2Adapter(this@MainActivity, fragments)
            vp2Main.offscreenPageLimit = 3
            BnvVp2Mediator(bnvMain, vp2Main) { _, viewPager2 ->
                // ViewPager2不滑动
                viewPager2.isUserInputEnabled = false
            }.attach()
        }
    }


    companion object {
        const val INDEX_HOME = 0    // HomeFragment对应的索引位置
        const val INDEX_COURSE = 1  // CourseFragment对应的索引位置
        const val INDEX_STUDY = 2   // StudyFragment对应的索引位置
        const val INDEX_MINE = 3    // MineFragment对应的索引位置
    }
}

/**
 * App主体部分的ViewPager2适配器
 */
class MainViewPager2Adapter(
    fragmentActivity: FragmentActivity,
    private val fragments: Map<Int, Fragment>
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments[position] ?: error("请确保fragments数据源和viewPager2的index匹配设置")
    }

}