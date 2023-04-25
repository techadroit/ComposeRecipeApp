//package com.core.network.di
//
//import com.core.network.BuildConfig
//import com.core.network.service_provider.DebugServiceProvider
//import com.core.network.service_provider.NetworkServiceProvider
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//
//@Module
//@InstallIn(SingletonComponent::class)
//class NetworkModule {
//
//    @Provides
//    fun getServiceProvider(): NetworkServiceProvider = if (BuildConfig.DEBUG) {
//        DebugServiceProvider()
//    } else {
//        throw IllegalStateException("Initialize Your Prod Services with production url.")
//    }
//}
