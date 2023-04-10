package com.example.composerecipeapp.core.network

import com.google.gson.Gson
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private var BASE_URL: String = "https://api.spoonacular.com/"
private val NUMBER_OF_RETRY = 5L
val TIMEOUT = 60L

class DebugServiceProvider(
    val baseurl: String = BASE_URL,
    val retry: Long = NUMBER_OF_RETRY,
    val timeOut: Long = TIMEOUT,
    val cache: Cache? = null
) : NetworkServiceProvider {

    private var retrofit: Retrofit

    init {
        val okHttpClient = createOkHttpClient()
        if (cache != null)
            okHttpClient.cache(cache)
        val gsonConverter = GsonConverterFactory.create(Gson())
        val client = okHttpClient.build()
        retrofit = createRetrofitInstance(baseurl, gsonConverter, client)
    }

    private fun createOkHttpClient(): OkHttpClient.Builder {

        val okHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(timeOut, TimeUnit.SECONDS)
            .writeTimeout(timeOut, TimeUnit.SECONDS)
            .readTimeout(timeOut, TimeUnit.SECONDS)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpClient.addInterceptor(interceptor)
        return okHttpClient
    }

    /**
     * create instance of retrofit
     */
    private fun createRetrofitInstance(
        url: String,
        gsonConverter: GsonConverterFactory,
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(gsonConverter)
            .client(client).build()
    }

    override fun <T> getService(service: Class<T>): T {
        return retrofit.create(service)
    }
}
