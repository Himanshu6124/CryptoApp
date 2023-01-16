package com.himanshu.cryptoapp.models

data class Status(val errorMessage: String = "",
                  val elapsed: String = "",
                  val creditCount: Int = 0,
                  val errorCode: String = "",
                  val timestamp: String = "")