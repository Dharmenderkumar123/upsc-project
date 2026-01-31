package com.cmt.services.helper


import com.cmt.services.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class APIClient {
    private var retrofit: Retrofit? = null

    fun getInstance(bearer: String? = null): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.API)
                .addConverterFactory(GsonConverterFactory.create())
                .client(provideOkHttpClient(bearer ?: ""))
                .build()
        }


        return retrofit ?: Retrofit.Builder()
            .baseUrl(BuildConfig.API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    fun getInstanceLive(bearer: String? = null): Retrofit {
           return Retrofit.Builder()
                .baseUrl("https://theprideias.com/webapi/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(provideOkHttpClient(bearer ?: ""))
                .build()
    }



    fun vimeoInstance(bearer: String? = null): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl("https://player.vimeo.com/video/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(provideOkHttpClient(bearer ?: ""))
                .build()
        }
        return retrofit ?: Retrofit.Builder()
            .baseUrl(BuildConfig.API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }


    private fun provideOkHttpClient(bearer: String? = null): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.HTTP_LOG) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE)
        }
        val okhttpClientBuilder = OkHttpClient.Builder().apply {
            addInterceptor(
                Interceptor { chain ->
                    val builder = chain.request().newBuilder()
                    if (bearer != null && bearer.isNotEmpty()) {
                        builder.header("Authorization", "Bearer $bearer")
                    }
                    return@Interceptor chain.proceed(builder.build())
                }
            )
        }
        val timeOutSec = 45
        okhttpClientBuilder.connectTimeout(timeOutSec.toLong(), TimeUnit.SECONDS)
        okhttpClientBuilder.readTimeout(timeOutSec.toLong(), TimeUnit.SECONDS)
        okhttpClientBuilder.writeTimeout(timeOutSec.toLong(), TimeUnit.SECONDS)
        okhttpClientBuilder.addInterceptor(loggingInterceptor)

        okhttpClientBuilder.addInterceptor(BasicAuthInterceptor("admin", "1234"))

        return okhttpClientBuilder.build()
    }

}