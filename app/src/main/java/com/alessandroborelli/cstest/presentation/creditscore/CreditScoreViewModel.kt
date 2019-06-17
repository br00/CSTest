package com.alessandroborelli.cstest.presentation.creditscore

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import android.view.View
import com.alessandroborelli.cstest.data.api.CreditScoreApi
import com.alessandroborelli.cstest.data.entities.CreditScore
import com.alessandroborelli.cstest.presentation.common.BaseViewModel
import com.alessandroborelli.cstest.utils.ScoreUtils
import com.alessandroborelli.cstest.utils.extension.mapNetworkErrors
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by aborelli on 2019-06-13.
 */

class CreditScoreViewModel: BaseViewModel() {

    @Inject
    lateinit var creditScoreApi: CreditScoreApi

    private lateinit var subscription: Disposable

    val score = MutableLiveData<String>()
    val maxScore = MutableLiveData<String>()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val scoreProgress: MutableLiveData<Int> = MutableLiveData()
    val mainLayoutVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorVisibility: MutableLiveData<Int> = MutableLiveData()


    public fun retrieveCreditScore() {
        subscription = creditScoreApi
            .getCreditReport()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveCreditScoreStart() }
            .doOnTerminate { onRetrieveCreditScoreFinish() }
            .mapNetworkErrors()
            .subscribe(
                { result -> onRetrieveCreditScoreSuccess(result) },
                { error -> onRetrieveCreditScoreError(error) }
            )
    }

    public fun onRetrieveCreditScoreError(error: Throwable?) {
        errorVisibility.value = View.VISIBLE
        mainLayoutVisibility.value = View.GONE
        Log.d("CreditScoreViewModel", "Error" +error.toString())
    }

    public fun onRetrieveCreditScoreSuccess(result: CreditScore?) {
        Log.d("CreditScoreViewModel", "Score: " +result?.creditReportInfo?.score)
        val score = result?.creditReportInfo?.score
        val maxScore = result?.creditReportInfo?.maxScoreValue
        this.score.value = score.toString()
        this.maxScore.value = maxScore.toString()
        if (null != score && null != maxScore) {
            scoreProgress.value = ScoreUtils.getPercentage(score, maxScore)
        }
        if (this.score.value.isNullOrEmpty() || this.maxScore.value.isNullOrEmpty()) {
            errorVisibility.value = View.VISIBLE
            mainLayoutVisibility.value = View.GONE
        }
        loadingVisibility.value = View.GONE
        mainLayoutVisibility.value = View.VISIBLE
    }

    private fun onRetrieveCreditScoreFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveCreditScoreStart() {
        errorVisibility.value = View.GONE
        mainLayoutVisibility.value = View.GONE
        loadingVisibility.value = View.VISIBLE
        scoreProgress.value = 0
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

}