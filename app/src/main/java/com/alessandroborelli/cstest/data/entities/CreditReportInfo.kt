package com.alessandroborelli.cstest.data.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by aborelli on 2019-06-13.
 *
 * I'm populating the model with only the values that I will use in the test.
 * In real scenario we should match the model received from the api.
 */

@Parcelize
data class CreditReportInfo (
    var score: Int,
    val maxScoreValue: Int,
    val minScoreValue: Int
): Parcelable