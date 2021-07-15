package com.cainiaowo.tablayoutviewpager2demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private val fragments = mapOf<Int, Fragment>(
        0 to OneFragment(),
        1 to TwoFragment(),
        2 to ThreeFragment()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ViewPager2
        val viewPager2 = findViewById<ViewPager2>(R.id.viewPager2)
        viewPager2.adapter = FragmentAdapter(this@MainActivity, fragments)

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        // TabLayout关联ViewPager2
        TabLayoutMediator(
            tabLayout, viewPager2
        ) { tab, position ->
            when (position) {
                0 -> tab.apply {
                    text = "One"
                }
                1 -> tab.apply {
                    text = "Two"
                }
                2 -> tab.apply {
                    text = "Three"
                }
            }
        }.attach()
    }
}

class FragmentAdapter(
    fragmentActivity: FragmentActivity,
    private val fragments: Map<Int, Fragment>
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments[position] ?: error("请确保fragments数据源和viewPager2的index匹配设置")
    }
}