package com.alessandroborelli.cstest.presentation.di.component

import com.alessandroborelli.cstest.presentation.creditscore.CreditScoreViewModel
import com.alessandroborelli.cstest.presentation.di.module.NetworkModule
import dagger.Component
import javax.inject.Singleton

/**
 *
 * Component providing inject() methods for presenters.
 *
 */
@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {

    fun inject(creditScoreViewModel: CreditScoreViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector
        fun networkModule(networkModule: NetworkModule): Builder
    }
}