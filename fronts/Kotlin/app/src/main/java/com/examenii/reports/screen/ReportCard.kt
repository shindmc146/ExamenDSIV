package com.examenii.reports.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.examenii.reports.domain.Report

@Composable
fun ReportCard(
    report: Report,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth().padding(top = 12.dp))
    {
        Text(text = report.companyName)
        Text(text = report.passengerName)
        Text(text = report.type)
        Text(text = report.isUrgent.toString())
        Text(text = report.subject)
        Text(text = report.tripDate)
        Text(text = report.description)
        Text(text = report.createdAt)

        Row() {
            Button(onClick = onEdit) {
                Text(text = "Editar")
            }
            Button(onClick = onDelete) {
                Text(text = "Eliminar", color = Color.Red)
            }
        }
    }

}
