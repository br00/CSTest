package com.alessandroborelli.cstest.data.api

import com.alessandroborelli.cstest.data.entities.CreditScore
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers

/**
 * Created by aborelli on 2019-06-13.
 */

interface CreditScoreApi {

    @GET("/prod/mockcredit/values")
    @Headers("Content-Type: application/json;charset=utf-8")
    fun getCreditReport (
        // no parameters here
    ): Observable<CreditScore>

}