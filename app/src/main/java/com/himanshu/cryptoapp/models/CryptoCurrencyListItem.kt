package com.himanshu.cryptoapp.models

import java.io.Serializable

data class CryptoCurrencyListItem(
                                  val symbol: String = "",
                                  val selfReportedCirculatingSupply: Double ,
                                  val totalSupply: Double = 0.0,
                                  val cmcRank: Double ,
                                  val isActive: Int = 0,
                                  val circulatingSupply: Double = 0.0,
                                  val dateAdded: String = "",
                                  val tags: List<String>?,
                                  val quotes: List<QuotesItem>?,
                                  val lastUpdated: String = "",
                                  val isAudited: Boolean ,
                                  val name: String = "",
                                  val marketPairCount: Double ,
                                  val id: Int = 0,
                                  val maxSupply: Double = 0.0,
                                  val slug: String = ""
) : Serializable{
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun toString(): String {
        return super.toString()
    }
}