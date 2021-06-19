package com.cainiaowo.common.network.model

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

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
 * 返回结果是Success且data非null,此data表示接口响应的数据,并不是业务中具体某个值的数据(不是code,data,message中的data)
 */
val DataResult<*>.succeed
    get() = this is DataResult.Success && data != null


@OptIn(ExperimentalContracts::class)
inline fun <R> DataResult<R>.onSuccess(
    action: R.() -> Unit
): DataResult<R> {
    // 实验特性,需要加注解,并且需要添加compiler argument '-Xopt-in=kotlin.RequiresOptIn'
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    if (succeed) action.invoke((this as DataResult.Success).data)
    return this
}

/**
 * 这是网络请求出现错误的时候的回调(比如说网络异常等问题或者404等),不是业务错误
 */
@OptIn(ExperimentalContracts::class)
inline fun <R> DataResult<R>.onFailure(
    action: (exception: Throwable) -> Unit
): DataResult<R> {
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    if (this is DataResult.Error) action.invoke(exception)
    return this
}