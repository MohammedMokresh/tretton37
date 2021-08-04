package com.mokresh.tretton37.api


import com.mokresh.tretton37.BuildConfig.BASE_URL
import com.mokresh.tretton37.model.QuestionsResponseBody
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.mokresh.tretton37.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ApiServices {

    @GET("api.php")
    suspend fun getQuestions(
        @Query("amount") amount: Int, @Query("type") type: String,
    ): Response<QuestionsResponseBody>

    companion object {
        fun create(): ApiServices {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val okHttpClient = OkHttpClient.Builder()
            okHttpClient.connectTimeout(40, TimeUnit.SECONDS).readTimeout(40, TimeUnit.SECONDS)
                .writeTimeout(40, TimeUnit.SECONDS)
            if (BuildConfig.DEBUG)
                okHttpClient.addInterceptor(logging)


            return Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient.build())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiServices::class.java)
        }
    }

}