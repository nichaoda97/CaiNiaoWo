package com.cainiaowo.course.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.blankj.utilcode.util.ToastUtils
import com.cainiaowo.common.base.BaseFragment
import com.cainiaowo.course.R
import com.cainiaowo.course.databinding.FragmentCourseBinding
import com.cainiaowo.course.paging.CourseFooterAdapter
import com.cainiaowo.course.paging.CoursePagingAdapter
import com.cainiaowo.course.viewmodel.CourseViewModel
import com.google.android.material.tabs.TabLayout
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.enums.PopupPosition
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * 课程
 */
class CourseFragment : BaseFragment<FragmentCourseBinding>() {

    private val viewModel: CourseViewModel by viewModel()

    override fun getLayoutRes() = R.layout.fragment_course

    private val adapter = CoursePagingAdapter()

    override fun initView(view: View, savedInstanceState: Bundle?) {
        super.initView(view, savedInstanceState)
        mBinding.apply {
            vm = viewModel
            rvCourse.adapter = adapter.withLoadStateFooter(CourseFooterAdapter {
                adapter.retry()
            })
        }
        addListener()
    }

    override fun initData() {
        super.initData()
        viewModel.apply {
            // 获取课程类别
            getCourseCategory()
            liveCourseCategory.observe { courseCategoryList ->
                // 将所有课程类别添加到TabLayout中
                mBinding.apply {
                    // 先移除上一次添加的tab
                    tlCourseCategory.removeAllTabs()
                    // "全部"手动添加
                    tlCourseCategory.addTab(
                        tlCourseCategory.newTab().apply { text = "全部" },
                        true
                    )
                    // 添加从接口获取的tab
                    courseCategoryList?.forEach { courseCategoryItem ->
                        tlCourseCategory.addTab(
                            tlCourseCategory.newTab().apply { text = courseCategoryItem.title },
                            false
                        )
                    }
                }
            }
        }
    }

    private fun addListener() {
        // TabLayout选择Tab
        mBinding.tlCourseCategory.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val courseCategoryList = viewModel.liveCourseCategory.value ?: return
                val index = tab?.position ?: 0
                // 选中的tab对应去请求课程列表的code值,需要减去"全部"类别
                val code = if (index > 0) courseCategoryList[index - 1].code ?: "all" else "all"
                lifecycleScope.launchWhenCreated {
                    viewModel.getCourseList(code = code).collectLatest { pagingData ->
                        adapter.submitData(pagingData)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        // PagingAdapter监听Load状态
        adapter.addLoadStateListener { loadState ->
            when (loadState.refresh) {
                is LoadState.NotLoading -> {
                    mBinding.progressBar.visibility = View.GONE
                    mBinding.rvCourse.visibility = View.VISIBLE
                }
                is LoadState.Loading -> {
                    mBinding.progressBar.visibility = View.VISIBLE
                    mBinding.rvCourse.visibility = View.GONE
                }
                is LoadState.Error -> {
                    val state = loadState.refresh as LoadState.Error
                    mBinding.progressBar.visibility = View.GONE
                    ToastUtils.showShort("Load Error: ${state.error.message}")
                }
            }
        }

        // 筛选类型、难度、价格、排序,使用XPopup
        mBinding.apply {
            courseCategorySelector.setOnClickListener {
                courseCategoryPopup.show()
            }
            courseLevelSelector.setOnClickListener {
                courseLevelPopup.show()
            }
            coursePriceSelector.setOnClickListener {
                coursePricePopup.show()
            }
            courseSortSelector.setOnClickListener {
                courseSortPopup.show()
            }
        }
    }

    private var item = -1
    private var lastCategoryPosition = 0
    private var lastLevelPosition = 0
    private var lastPricePosition = 0
    private var lastSortPosition = 0

    private val courseCategoryPopup by lazy {
        XPopup.Builder(context)
            .hasShadowBg(false)
            .popupPosition(PopupPosition.Bottom)
            .atView(mBinding.courseCategorySelector)
            .asAttachList(arrayOf("全部类型", "商业实战", "专项好课"), null) { position, text ->
                if (lastCategoryPosition == position) return@asAttachList
                lastCategoryPosition = position

                mBinding.courseCategorySelector.text = text
                item = when (position) {
                    0 -> -1
                    1 -> 3
                    2 -> 1
                    else -> -1
                }
                lifecycleScope.launchWhenCreated {
                    viewModel.getCourseList(courseType = item).collectLatest { pagingData ->
                        adapter.submitData(pagingData)
                    }
                }
            }
    }

    private val courseLevelPopup by lazy {
        XPopup.Builder(context)
            .hasShadowBg(false)
            .popupPosition(PopupPosition.Bottom)
            .atView(mBinding.courseLevelSelector)
            .asAttachList(arrayOf("全部难度", "初级", "中级", "高级"), null) { position, text ->
                if (lastLevelPosition == position) return@asAttachList
                lastLevelPosition = position

                mBinding.courseLevelSelector.text = text
                item = when (position) {
                    0 -> -1
                    else -> position
                }
                lifecycleScope.launchWhenCreated {
                    viewModel.getCourseList(difficulty = item).collectLatest { pagingData ->
                        adapter.submitData(pagingData)
                    }
                }
            }
    }

    private val coursePricePopup by lazy {
        XPopup.Builder(context)
            .hasShadowBg(false)
            .popupPosition(PopupPosition.Bottom)
            .atView(mBinding.coursePriceSelector)
            .asAttachList(arrayOf("全部价格", "免费", "付费"), null) { position, text ->
                if (lastPricePosition == position) return@asAttachList
                lastPricePosition = position

                mBinding.coursePriceSelector.text = text
                item = when (position) {
                    0 -> -1
                    1 -> 1
                    2 -> 0
                    else -> -1
                }
                lifecycleScope.launchWhenCreated {
                    viewModel.getCourseList(isFree = item).collectLatest { pagingData ->
                        adapter.submitData(pagingData)
                    }
                }
            }
    }

    private val courseSortPopup by lazy {
        XPopup.Builder(context)
            .hasShadowBg(false)
            .popupPosition(PopupPosition.Bottom)
            .atView(mBinding.courseSortSelector)
            .asAttachList(arrayOf("默认排序", "评价最高", "学习最多"), null) { position, text ->
                if (lastSortPosition == position) return@asAttachList
                lastSortPosition = position

                mBinding.courseSortSelector.text = text
                item = when (position) {
                    0 -> -1
                    else -> position
                }
                lifecycleScope.launchWhenCreated {
                    viewModel.getCourseList(q = item).collectLatest { pagingData ->
                        adapter.submitData(pagingData)
                    }
                }
            }
    }
}