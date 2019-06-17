package com.alessandroborelli.cstest.presentation.di.module

import com.alessandroborelli.cstest.base.App
import com.alessandroborelli.cstest.base.Constants
import com.alessandroborelli.cstest.data.api.CreditScoreApi
import com.alessandroborelli.cstest.utils.hasNetwork
import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by aborelli on 2019-06-13.
 */

@Module
object NetworkModule {

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideCreditScoreApi(retrofit: Retrofit): CreditScoreApi {
        return retrofit.create(CreditScoreApi::class.java)
    }

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRetrofitInterface(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }

    // Class for logging network calls
    private val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    private val client : OkHttpClient = OkHttpClient.Builder().apply {
        val cache = okhttp3.Cache(App.sApplicationContext.cacheDir, 10 * 1024 * 1024)  // 10 MB
        this
            .addInterceptor { chain ->
                // Offline
                var request = chain.request()
                if (!hasNetwork(App.sApplicationContext)!!) {
                    val maxStale = 60 * 60 * 24 * 30 // Offline cache available for 30 days
                    request = request.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                        .removeHeader("Pragma")
                        .build()
                }
                chain.proceed(request)
            }
            .addNetworkInterceptor { chain ->
                // Online
                val originalResponse = chain.proceed(chain.request())
                // read from cache for 3 secs
                // this value could be bigger like 30 secs but not for PUT requests
                // some operations are too fast and it's easy end up in an inconsistent state
                val maxAge = 3
                originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=$maxAge")
                    .build()
            }
            .cache(cache)
            .addInterceptor(interceptor)
    }.build()

}