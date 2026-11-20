package com.examenii.reports.api

import com.examenii.reports.domain.ImageResponse
import com.examenii.reports.domain.Report
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @GET("reports")
    suspend fun getReports(): List<Report>

    @POST("reports")
    suspend fun registerReport(@Body report: Report): Response<Unit>

    @PUT("reports/{id}")
    suspend fun updateReport(@Path("id") id: Int, @Body report: Report): Response<Unit>

    @DELETE("reports/{id}")
    suspend fun deleteReport(@Path("id") id: Int): Response<Unit>

    @Multipart
    @POST("images")
    suspend fun uploadImage(@Part file: MultipartBody.Part) : Response<ImageResponse>

}