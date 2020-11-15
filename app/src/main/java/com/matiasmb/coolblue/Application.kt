package com.matiasmb.coolblue

import android.app.Application
import com.google.gson.GsonBuilder
import com.matiasmb.coolblue.search.data.networking.ItemsApiClient
import com.matiasmb.coolblue.search.data.networking.ItemsApiService
import com.matiasmb.coolblue.search.data.networking.ItemsApiServiceImpl
import com.matiasmb.coolblue.search.domain.interactor.DomainInteractor
import com.matiasmb.coolblue.search.domain.interactor.DomainInteractorImpl
import com.matiasmb.coolblue.search.presentation.viewmodel.ItemsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalCoroutinesApi
class Application : Application() {

    private val appModule = module {
        single {
            Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
                .create(ItemsApiClient::class.java)
        }
        viewModel { ItemsViewModel(get()) }
        factory<DomainInteractor> { DomainInteractorImpl(get()) }
        factory<ItemsApiService> { ItemsApiServiceImpl(get()) }
    }

    override fun onCreate() {
        super.onCreate()
        stopKoin()
        startKoin {
            // use AndroidLogger as Koin Logger - default Level.INFO
            androidLogger()

            // use the Android context given there
            androidContext(this@Application)

            // load properties from assets/koin.properties file
            androidFileProperties()

            // module list
            modules(appModule)
        }
    }
}