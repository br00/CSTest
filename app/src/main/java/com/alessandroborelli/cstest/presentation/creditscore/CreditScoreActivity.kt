package com.alessandroborelli.cstest.presentation.creditscore

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.alessandroborelli.cstest.R
import com.alessandroborelli.cstest.databinding.ActivityCreditScoreBinding

class CreditScoreActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityCreditScoreBinding
    private lateinit var mViewModel: CreditScoreViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_credit_score)

        mViewModel = ViewModelProviders.of(this).get(CreditScoreViewModel::class.java)
        mBinding.viewModel = mViewModel
        if (null == savedInstanceState) {
            mBinding.viewModel?.retrieveCreditScore()
        }

    }
}