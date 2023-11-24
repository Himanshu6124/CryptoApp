package com.himanshu.cryptoapp.models

data class Status(val errorMessage: String = "",
                  val elapsed: String = "",
                  var creditCount: Int = 0,
                  var errorCode: String = "",
                  var timestamp: String = "")

fun main()
{
    var status = Status()

    with(status) {
        this.errorCode = "35"
        this.timestamp = "3wr"
        this.creditCount = 343
    }



    println(status.errorCode)
    println(status.timestamp)

}