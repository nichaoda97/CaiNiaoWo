package com.cainiaowo.home.viewmodel

import com.cainiaowo.common.base.BaseViewModel
import com.cainiaowo.home.repo.IHomeResource

class HomeViewModel(private val resource: IHomeResource) : BaseViewModel() {

    val liveBannerList = resource.liveBannerList

    val livePageModuleList = resource.livePageModuleList

    fun getBannerList() = serveLaunch {
        resource.getBannerList()
    }

    fun getPageModuleList() = serveLaunch {
        resource.getPageModuleList()
    }

    suspend fun getPageModuleItems(moduleId: Int) =
        resource.getPageModuleItems(moduleId)
}