package com.alessandroborelli.cstest.base

class Constants {
    companion object {
        // Not the best way but it's useful to change the server url and mock the api. It would be better using BuildConfig.BASE_URL
        var BASE_URL: String = "https://5lfoiyb0b3.execute-api.us-west-2.amazonaws.com/"
    }
}