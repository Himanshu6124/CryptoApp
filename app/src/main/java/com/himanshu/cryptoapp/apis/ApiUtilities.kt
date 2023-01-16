package com.himanshu.cryptoapp.apis

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiUtilities {

    val BASE_URL : String = "https://api.coinmarketcap.com/"

    fun getInstance() : Retrofit
    {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


}