package net.adiwilaga.githubuserfinder.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object APIBuilder {

        const val BASEURL="https://api.github.com/"

        val retrofitBuilder: Retrofit.Builder by lazy{
            Retrofit.Builder()
                .client(okhttp3)
                .addConverterFactory(
                    GsonConverterFactory.create()
                )
                .baseUrl(BASEURL)

        }

        val apiServices: API by lazy{

            retrofitBuilder.build().create(API::class.java)
        }


        val okhttp3: OkHttpClient by lazy {
            val httpClient = OkHttpClient().newBuilder()
            val TIMEOUT=3000L
            httpClient.connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
            httpClient.readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
            httpClient.writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)

            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            httpClient.interceptors().add(logging)


            httpClient.build()
        }



}