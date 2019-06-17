package com.alessandroborelli.cstest.presentation.common

import android.arch.lifecycle.ViewModel
import com.alessandroborelli.cstest.presentation.creditscore.CreditScoreViewModel
import com.alessandroborelli.cstest.presentation.di.component.DaggerViewModelInjector
import com.alessandroborelli.cstest.presentation.di.component.ViewModelInjector
import com.alessandroborelli.cstest.presentation.di.module.NetworkModule

/**
 * Created by aborelli on 2019-06-13.
 */

abstract class BaseViewModel: ViewModel() {
    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when(this) {
            is CreditScoreViewModel -> injector.inject(this)
        }
    }
}
