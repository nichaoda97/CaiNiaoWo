package com.cainiaowo.home.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.LogUtils
import com.cainiaowo.common.network.model.DataResult
import com.cainiaowo.common.network.model.onFailure
import com.cainiaowo.common.network.model.onSuccess
import com.cainiaowo.common.network.support.serveData
import com.cainiaowo.home.network.BannerListRsp
import com.cainiaowo.home.network.IHomeService
import com.cainiaowo.home.network.PageModuleListRsp
import com.cainiaowo.service.network.BaseCaiNiaoRsp
import com.cainiaowo.service.network.onBizError
import com.cainiaowo.service.network.onBizOK

class HomeRepo(private val service: IHomeService) : IHomeResource {

    private val _liveBannerList = MutableLiveData<BannerListRsp>()
    private val _livePageModuleList = MutableLiveData<PageModuleListRsp>()

    override val liveBannerList: LiveData<BannerListRsp> = _liveBannerList
    override val livePageModuleList: LiveData<PageModuleListRsp> = _livePageModuleList

    override suspend fun getBannerList() {
        service.getBannerList()
            .serveData()
            .onSuccess {
                onBizOK<BannerListRsp> { _, data, _ ->
                    _liveBannerList.value = data
                    LogUtils.i("获取Banner列表 BizOK $data")
                    return@onBizOK
                }.onBizError { code, message ->
                    LogUtils.w("获取Banner列表 BizError $code,$message")
                    return@onBizError
                }
            }.onFailure {
                LogUtils.e("获取Banner列表 接口异常 ${it.message}")
            }
    }

    override suspend fun getPageModuleList() {
        service.getPageModuleList()
            .serveData()
            .onSuccess {
                onBizOK<PageModuleListRsp> { _, data, _ ->
                    _livePageModuleList.value = data
                    LogUtils.i("获取首页模块分类 BizOK $data")
                    return@onBizOK
                }.onBizError { code, message ->
                    LogUtils.w("获取首页模块分类 BizError $code,$message")
                    return@onBizError
                }
            }.onFailure {
                LogUtils.e("获取首页模块分类 接口异常 ${it.message}")
            }
    }

    override suspend fun getPageModuleItems(moduleId: Int): DataResult<BaseCaiNiaoRsp> {
        return service.getPageModuleItems(moduleId).serveData()
    }
}