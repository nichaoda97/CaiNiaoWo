package com.cainiaowo.common.network.model

sealed class DataResult<out R> {
    /**
     * 成功状态的时候
     */
    data class Success<out T>(val data: T) : DataResult<T>()

    /**
     * 错误、失败状态的时候
     */
    data class Error(val exception: Exception) : DataResult<Nothing>()

    /**
     * 加载数据中
     */
    object Loading : DataResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }
}

/**
 * 返回结果是Success且data非null
 */
val DataResult<*>.succeed
    get() = this is DataResult.Success && data != null