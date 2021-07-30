package com.cainiaowo.common.ktx

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.core.app.ComponentActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner

/**
 * Activity扩展
 */
// region 扩展函数
/**
 * Activity中使用DataBinding时setContentView的简化
 * [layoutId] 布局文件资源id
 * @return 返回Binding对象实例
 */
fun <T : ViewDataBinding> Activity.bindView(@LayoutRes layoutId: Int): T {
    return DataBindingUtil.setContentView<T>(this, layoutId)
}

/**
 * Activity中使用DataBinding时bind的简化
 * [root] The root View of the inflated binding layout.
 * @return 返回Binding对象实例,可null
 */
fun <T : ViewDataBinding> Activity.bindView(root: View): T? {
    return DataBindingUtil.bind<T>(root)
}

/**
 * 软键盘的隐藏
 * [view] 事件控件View
 */
fun Activity.dismissKeyBoard(view: View) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager?
    imm?.hideSoftInputFromWindow(view.windowToken, 0)
}

// endregion

// region 扩展属性
/**
 * 扩展lifeCycleOwner字段,便于和Fragment之间使用lifeCycleOwner保持一致性
 */
val ComponentActivity.viewLifecycleOwner: LifecycleOwner
    get() = this

/**
 * Activity的扩展字段,便于和Fragment中使用liveData之类的时候保持一致性
 */
val Activity.context: Context
    get() = this
// endregion