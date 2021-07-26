package com.cainiaowo.home.adapter

import com.cainiaowo.common.webview.WebViewActivity
import com.cainiaowo.home.R
import com.cainiaowo.home.databinding.ItemJobCourseBinding
import com.cainiaowo.home.network.JobCourseListRsp
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder

/**
 * 就业班Adapter
 */
class JobCourseAdapter(jobCourseList: MutableList<JobCourseListRsp.JobCourseItem>) :
    BaseQuickAdapter<JobCourseListRsp.JobCourseItem, BaseDataBindingHolder<ItemJobCourseBinding>>(
        R.layout.item_job_course,
        jobCourseList
    ) {
    override fun convert(
        holder: BaseDataBindingHolder<ItemJobCourseBinding>,
        item: JobCourseListRsp.JobCourseItem
    ) {
        holder.itemView.setOnClickListener { view ->
            WebViewActivity.openUrl(view.context, item.course?.h5site ?: "https://m.cniao5.com/")
        }
        holder.dataBinding?.apply {
            url = item.course?.getDetailImgUrl()
            executePendingBindings()
        }
    }
}