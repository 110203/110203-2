package com.example.test2

fun cusFormatDateTime(date: String, time: String): String {
    val dateSplit = date.split("-")
    var year = dateSplit[0].toInt()
    var month = dateSplit[1].toInt()
    var day = dateSplit[2].toInt()
    val timeSplit = time.split(":")
    var hour = timeSplit[0].toInt() + 8
    val min = timeSplit[1].toInt()

    if (hour > 23) {
        day += 1
        hour -= 24
        if ((month < 8 && month % 2 != 0) || (month > 7 && month % 2 == 0)) {
            if (day > 31) {
                day -= 31
                month += 1
            }
        } else if ((month < 8 && month % 2 == 0) || (month > 7 && month % 2 != 0)) {
            if (day > 30) {
                day -= 30
                month += 1
            }
        }
        if (month > 12) {
            month -= 12
            year += 1
        }
    }
    return if (hour < 10 && min < 10) {
        "$year/$month/$day 0$hour:0$min"
    } else if (hour < 10 && min >= 10) {
        "$year/$month/$day 0$hour:$min"
    } else if (hour > 10 && min < 10) {
        "$year/$month/$day $hour:0$min"
    } else {
        "$year/$month/$day $hour:$min"
    }
}