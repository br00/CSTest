package com.alessandroborelli.cstest.utils.extension


import com.alessandroborelli.cstest.utils.HttpCallFailureException
import com.alessandroborelli.cstest.utils.NoNetworkException
import com.alessandroborelli.cstest.utils.ServerUnreachableException
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


fun <T> Observable<T>.mapNetworkErrors(): Observable<T> =
    this.onErrorResumeNext {
            throwable: Throwable -> when (throwable) {
                is SocketTimeoutException -> Observable.error(NoNetworkException(throwable))
                is UnknownHostException -> Observable.error(ServerUnreachableException(throwable))
                is ConnectException -> Observable.error(ServerUnreachableException(throwable))
                is HttpException -> Observable.error(HttpCallFailureException(throwable))
                else -> Observable.error(throwable)
        }
    }


fun <T> Single<T>.mapNetworkErrors(): Single<T> =
    this.onErrorResumeNext {
            error -> when (error) {
                is SocketTimeoutException -> Single.error(NoNetworkException(error))
                is UnknownHostException -> Single.error(ServerUnreachableException(error))
                is ConnectException -> Single.error(ServerUnreachableException(error))
                is HttpException -> Single.error(HttpCallFailureException(error))
                else -> Single.error(error)
        }
    }
