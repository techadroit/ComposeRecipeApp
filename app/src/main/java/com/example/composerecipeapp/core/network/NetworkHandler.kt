package com.example.composerecipeapp.core.network

import com.example.composerecipeapp.BuildConfig
import com.example.composerecipeapp.core.network.api_service.RecipeApi
import com.google.gson.Gson
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkHandler {

    private var BASE_URL: String = "https://api.spoonacular.com/"
    private lateinit var retrofit: Retrofit
    val NUMBER_OF_RETRY = 5L
    val TIMEOUT = 60L

    fun init(cache: Cache? = null) {
        val okHttpClient = createOkHttpClient()
        if (cache != null)
            okHttpClient.cache(cache)
        val gsonConverter = GsonConverterFactory.create(Gson())
        val client = okHttpClient.build()
        retrofit = createRetrofitInstance(BASE_URL, gsonConverter, client)
    }

    private fun createOkHttpClient(): OkHttpClient.Builder {
        val okHttpClient = OkHttpClient().newBuilder().connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS).readTimeout(TIMEOUT, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            okHttpClient.addInterceptor(interceptor)
        }
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

    fun getRecipeService(): RecipeApi {
        return retrofit.create(RecipeApi::class.java)
    }

    fun getRetrofitInstance(): Retrofit {
        return retrofit
    }
}
