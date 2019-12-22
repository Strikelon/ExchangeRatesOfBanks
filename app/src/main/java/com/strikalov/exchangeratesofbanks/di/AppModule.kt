package com.strikalov.exchangeratesofbanks.di

import android.content.Context
import com.strikalov.exchangeratesofbanks.di.provider.ApiProvider
import com.strikalov.exchangeratesofbanks.di.provider.OkHttpClientProvider
import com.strikalov.exchangeratesofbanks.model.server.Api
import com.strikalov.exchangeratesofbanks.model.BanksDataRepository
import com.strikalov.exchangeratesofbanks.system.AppSchedulers
import com.strikalov.exchangeratesofbanks.system.SchedulersProvider
import okhttp3.OkHttpClient
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import toothpick.config.Module

class AppModule(context: Context) : Module() {

    init {
        bind(Context::class.java).toInstance(context)

        val cicerone = Cicerone.create()
        bind(Router::class.java).toInstance(cicerone.router)
        bind(NavigatorHolder::class.java).toInstance(cicerone.navigatorHolder)

        bind(OkHttpClient::class.java).toProvider(OkHttpClientProvider::class.java)
            .providesSingletonInScope()

        bind(Api::class.java).toProvider(ApiProvider::class.java)
            .providesSingletonInScope()

        bind(SchedulersProvider::class.java).toInstance(AppSchedulers())

        bind(BanksDataRepository::class.java).singletonInScope()
    }

}