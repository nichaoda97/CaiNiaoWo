package com.cainiaowo.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.blankj.utilcode.util.LogUtils

/**
 * Fragment抽象基类
 */
abstract class BaseFragment<FragmentDataBinding : ViewDataBinding> : Fragment() {

    protected lateinit var mBinding: FragmentDataBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.lifecycleOwner = viewLifecycleOwner
        initView(view, savedInstanceState)
        initConfig()
        initData()
    }

    @LayoutRes
    abstract fun getLayoutRes(): Int

    /**
     * 初始化view
     */
    open fun initView(view: View, savedInstanceState: Bundle?) {
        LogUtils.d("${this.javaClass.simpleName} 初始化 initView")
    }

    /**
     * 初始化配置
     */
    open fun initConfig() {
        LogUtils.d("${this.javaClass.simpleName} 初始化 initConfig")
    }

    /**
     * 初始化数据
     */
    open fun initData() {
        LogUtils.d("${this.javaClass.simpleName} 初始化 initData")
    }

    override fun onDestroy() {
        super.onDestroy()
        if (this::mBinding.isInitialized) {
            mBinding.unbind()
        }
    }

    /**
     * 扩展用于LiveData便捷写法的observe函数
     * [block] 响应change变化的逻辑块
     */
    protected inline fun <T : Any> LiveData<T>.observe(crossinline block: (T?) -> Unit) {
        this.observe(viewLifecycleOwner) { data ->
            block.invoke(data)
        }
    }
}