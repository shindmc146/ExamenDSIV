package com.examenii.reports.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.examenii.reports.api.Api.BASE_URL
import com.examenii.reports.domain.Report
import com.examenii.reports.utils.dbDateToNormalFormat
import com.examenii.reports.utils.typeToLabels

@Composable
fun ReportCard(
    report: Report,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 12.dp))
    {
        Column(modifier = Modifier.padding(16.dp)){

            if (!report.pictureURL.isNullOrBlank()){
                AsyncImage(
                    model = "${BASE_URL}images/${report.pictureURL}",
                    contentDescription = "Foto del reporte",
                    modifier = Modifier.fillMaxWidth().height(180.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Text(text = "Empresa: " + report.companyName)
            Text(text = "Pasajero: "+ report.passengerName)
            Text(text = "Tipo de reporte: " + typeToLabels[report.type])
            if (report.isUrgent){
                Text(text = "Urgente: Sí")
            }else {
                Text(text = "Urgente: No")
            }

            Text(text = "Asunto: "+report.subject)
            Text(text = "Fecha del viaje: "+dbDateToNormalFormat(report.tripDate))
            Text(text = "Descripción: " + report.description)
            Text(text = "Fecha de envío: " + dbDateToNormalFormat(report.createdAt))

            Row(modifier = Modifier.padding(top = 12.dp)) {
                Button(onClick = onEdit) {
                    Text(text = "Editar")
                }
                Button(onClick = onDelete) {
                    Text(text = "Eliminar", color = Color.Red)
                }
            }
        }
    }

}
