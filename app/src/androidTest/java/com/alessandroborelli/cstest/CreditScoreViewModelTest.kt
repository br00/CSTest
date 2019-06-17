package com.alessandroborelli.cstest

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import com.alessandroborelli.cstest.data.entities.CreditReportInfo
import com.alessandroborelli.cstest.data.entities.CreditScore
import com.alessandroborelli.cstest.presentation.creditscore.CreditScoreViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.rules.TestRule
import org.junit.Rule



/**
 * Created by aborelli on 2019-06-16.
 */
@RunWith(AndroidJUnit4::class)
class CreditScoreViewModelTest {

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var mViewModel: CreditScoreViewModel


    @Before
    fun setUp() {
        mViewModel = CreditScoreViewModel()
    }


    @Test
    fun updateViewModel() {
        // Mock CreditScore data
        val creditReportInfo = CreditReportInfo(326, 700, 0)
        val creditScore = CreditScore(creditReportInfo)
        mViewModel.onRetrieveCreditScoreSuccess(creditScore)

        // Check that the viewmodel is updated as expected
        assertEquals(mViewModel.score.value, "326")
        assertEquals(mViewModel.maxScore.value, "700")
        assertEquals(mViewModel.loadingVisibility.value, View.GONE)
        assertEquals(mViewModel.mainLayoutVisibility.value, View.VISIBLE)
    }


}
