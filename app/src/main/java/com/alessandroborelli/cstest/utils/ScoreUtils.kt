package com.alessandroborelli.cstest.utils

/**
 * Created by aborelli on 2019-06-16.
 */
object ScoreUtils {
    fun getPercentage(score: Int, maxScore: Int): Int {
        val r = maxScore.toFloat().div(score)
        return (100/r).toInt()
    }
}