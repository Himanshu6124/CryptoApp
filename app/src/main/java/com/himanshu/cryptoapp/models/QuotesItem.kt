package com.himanshu.cryptoapp.models

data class QuotesItem(val marketCap: Double ,
                      val volumeH: Double ,
                      val marketCapByTotalSupply: Double ,
                      val lastUpdated: String ,
                      val percentChange1h: Double,
                      val percentChange24h: Double ,
                      val percentChange7d: Double ,
                      val percentChange30d: Double ,
                      val percentChange60d: Double ,
                      val percentChange90d: Double ,
                      val price: Double ,
                      val ytdPriceChangePercentage: Double ,
                      val name: String,
                      val dominance: Double,
                      val fullyDilluttedMarketCap: Double ,
                      val turnover: Double
)
