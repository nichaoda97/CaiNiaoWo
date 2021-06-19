package com.cainiaowo.common.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * ViewModel抽象基类
 */
abstract class BaseViewModel : ViewModel() {

    private val jobs = mutableListOf<Job>()

    /**
     * 标记网络Loading状态
     */
    val isLoading = MutableLiveData<Boolean>()

    /**
     * 协程网络请求
     */
    fun serveLaunch(block: suspend CoroutineScope.() -> Unit) = viewModelScope.launch {
        isLoading.value = true
        block.invoke(this)
        isLoading.value = false
    }.addTo(jobs)

    override fun onCleared() {
        jobs.forEach { it.cancel() }
        super.onCleared()
    }
}

/**
 * 扩展函数,将ViewModel中的Job对象添加到一个list中
 */
private fun Job.addTo(list: MutableList<Job>) {
    list.add(this)
}