package com.example.ip_search_app.di

import com.example.ip_search_app.data.api.IpSearchService
import com.example.ip_search_app.data.api.UserIpService
import com.example.ip_search_app.utils.Constants.Companion.IP_IFY_END_POINT
import com.example.ip_search_app.utils.Constants.Companion.IP_INFO_END_POINT
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun searchBaseUrl() = IP_INFO_END_POINT
    @Provides
    fun getUserIpBaseUrl() = IP_IFY_END_POINT

    @Provides
    fun logging() = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)
    @Provides
    fun okHttpClient() = OkHttpClient.Builder()
        .addInterceptor(logging())
        .build()

    @Provides
    @Named("search")
    @Singleton
    fun provideSearchRetrofit(): IpSearchService =
        Retrofit.Builder()
            .baseUrl(searchBaseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient())
            .build()
            .create(IpSearchService::class.java)

    @Provides
    @Named("userIp")
    @Singleton
    fun provideUserIpRetrofit(): UserIpService =
        Retrofit.Builder()
            .baseUrl(getUserIpBaseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient())
            .build()
            .create(UserIpService::class.java)
}