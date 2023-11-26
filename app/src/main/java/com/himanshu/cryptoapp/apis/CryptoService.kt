package com.himanshu.cryptoapp.apis

import com.himanshu.cryptoapp.models.MarketModel
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface CryptoService {
    @GET("data-api/v3/cryptocurrency/listing?start=1&limit=500")
    suspend fun getMarketData(): Response<MarketModel>

    companion object {
        private const val BASE_URL: String = "https://api.coinmarketcap.com/"
        fun getInstance(): CryptoService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CryptoService::class.java)
        }
    }
}