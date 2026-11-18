package com.examenii.reports.api

import com.examenii.reports.domain.Report
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("api/reports")
    suspend fun getReports(): List<Report>

    @POST("api/reports")
    suspend fun registerReport(@Body report: Report): Response<Unit>

    @PUT("api/reports/{id}")
    suspend fun updateReport(@Path("id") id: Int, @Body report: Report): Response<Unit>

    @DELETE("api/reports/{id}")
    suspend fun deleteReport(@Path("id") id: Int): Response<Unit>
}