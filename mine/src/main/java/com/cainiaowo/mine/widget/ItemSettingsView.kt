package com.cainiaowo.mine.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.cainiaowo.mine.databinding.VItemSettingsBinding

/**
 * 自定义的设置item的组合控件
 */
class ItemSettingsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        // 第一步：关联布局v_layout_settings.xml
        VItemSettingsBinding.inflate(LayoutInflater.from(context),this,true)
    }

}