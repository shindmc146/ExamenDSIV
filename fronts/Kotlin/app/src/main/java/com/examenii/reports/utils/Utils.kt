package com.examenii.reports.utils

import com.google.gson.Gson
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone
import kotlin.jvm.java

data class ErrorResponse(val error: String)
fun errorReader(response: Response<Unit>): String{
    val errorString = response.errorBody()?.string()

    return try {
        val errorResponse = Gson().fromJson(errorString, ErrorResponse::class.java)
        errorResponse.error;
    }catch (e: Exception){
        return "Error parseando el error ${e.message}"
    }


}
fun milisecondsToText(miliseconds: Long) : String{
    val format = SimpleDateFormat("dd-MM-yyyy")
    format.timeZone = TimeZone.getTimeZone("UTC-6")
    return format.format(Date(miliseconds))

}
val companyNames = listOf("Tracasa", "Buses Caribeños", "HEBA", "TRACOPA", "SACSA", "LUMACA", "MEPE", "Tica Bus")
val types = listOf("Queja","Sugerencia","Objeto perdido")
val typeToLabels = hashMapOf(
    "COMPLAINT" to "Queja",
    "SUGGESTION" to "Sugerencia",
    "LOSTITEM" to "Objeto perdido"
)
