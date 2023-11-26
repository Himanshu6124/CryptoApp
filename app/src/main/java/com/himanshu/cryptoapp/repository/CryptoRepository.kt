package com.himanshu.cryptoapp.repository

import com.himanshu.cryptoapp.apis.CryptoService

class CryptoRepository(private val service : CryptoService) {

    suspend fun getMarketData() = service.getMarketData()

}