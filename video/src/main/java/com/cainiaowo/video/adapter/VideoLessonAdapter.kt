package com.cainiaowo.video.adapter

import androidx.databinding.ObservableBoolean
import com.cainiaowo.video.R
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class VideoLessonAdapter(
    items: MutableList<Item>,
    private val callback: (Item) -> Unit
) : BaseMultiItemQuickAdapter<Item, BaseViewHolder>(items) {

    init {
        addItemType(Item.TYPE_TITLE, R.layout.item_course_title)
        addItemType(Item.TYPE_LESSON, R.layout.item_lesson)
    }

    override fun convert(holder: BaseViewHolder, item: Item) {
        when (holder.itemViewType) {
            Item.TYPE_TITLE -> {
                holder.setText(R.id.title, item.title ?: "Title Null")
            }
            Item.TYPE_LESSON -> {
                holder.itemView.setOnClickListener {
                    callback.invoke(item)
                }
                holder.setText(R.id.tv_title_item_play, item.title ?: "Lesson Name Null")
                holder.setTextColor(
                    R.id.tv_title_item_play,
                    context.resources.getColor(R.color.colorSecondaryText)
                )
                holder.setVisible(R.id.tv_try_study, item.tryPlay)
            }
            else -> error("不支持的ViewHolder类型")
        }
    }
}

/**
 * 单个Item可以分为  标题类型  和  具体的单节课  类型
 */
data class Item(
    val viewType: Int,  // 用于区分Item类型,TYPE_TITLE和TYPE_LESSON
    val title: String?, // TYPE_TITLE时表示章节名称;TYPE_LESSON表示单节课的名称
    val key: String? = null,   // 单节课才会有的key,用于获取播放地址
    val tryPlay: Boolean = false    // 单节课是否能够试看
) : MultiItemEntity {
    var isPlaying = ObservableBoolean(false)    // 当前的选中状态,用于更改UI

    companion object {
        const val TYPE_TITLE = 0
        const val TYPE_LESSON = 1
    }

    override val itemType = viewType
}