package com.himanshu.cryptoapp.models

data class Status(val errorMessage: String = "",
                  val elapsed: String = "",
                  var creditCount: Int = 0,
                  var errorCode: String = "",
                  var timestamp: String = "")