package io.ashkay.picklerick.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ashkay.picklerick.BuildConfig
import io.ashkay.picklerick.data.RepositoryImpl
import io.ashkay.picklerick.data.api.RickAndMortyApi
import io.ashkay.picklerick.domain.Repository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    fun providesRickAndMortyApi(okHttpClient: OkHttpClient): RickAndMortyApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RickAndMortyApi::class.java)
    }

    @Provides
    fun createNetworkClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG)
            logging.level = HttpLoggingInterceptor.Level.BODY
        else
            logging.level = HttpLoggingInterceptor.Level.NONE

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    fun providesRepository(apiService: RickAndMortyApi): Repository {
        return RepositoryImpl(apiService)
    }
}