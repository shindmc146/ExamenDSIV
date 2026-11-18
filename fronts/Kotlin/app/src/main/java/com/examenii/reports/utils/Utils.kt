package com.examenii.reports.utils

import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

fun errorReader(response: Response<Unit>): String{
    val body = response.errorBody();
    if (body === null) {
        return "Error ${response.code()}"
    }
    return body.string();
}
fun milisecondsToText(miliseconds: Long) : String{
    val format = SimpleDateFormat("yyyy-MM-dd")
    format.timeZone = TimeZone.getTimeZone("UTC-6")
    return format.format(Date(miliseconds))

}