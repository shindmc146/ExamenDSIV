package com.examenii.reports.domain

data class Report(
    val id: Int,
    val companyName: String,
    val passengerName: String,
    val type: String,
    val isUrgent: Boolean,
    val subject: String,
    val pictureURL: String,
    val tripDate: String,
    val description: String,
    val createdAt: String
)
