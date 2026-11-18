package com.examenii.reports.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.examenii.reports.api.Api
import com.examenii.reports.components.Alert
import com.examenii.reports.components.PickDate
import com.examenii.reports.components.Select
import com.examenii.reports.domain.Report
import com.examenii.reports.utils.errorReader
import kotlinx.coroutines.launch
import java.util.Dictionary

@Composable
fun ReportForm(
    report: Report?,
    save: () -> Unit,
    close: () -> Unit
){
    var name by remember { mutableStateOf(report?.passengerName ?: "") }
    var companyName by remember { mutableStateOf(report?.companyName ?: "") }
    var type by remember { mutableStateOf(report?.type ?: "") }
    var isUrgent by remember { mutableStateOf(report?.isUrgent ?: false) }
    var subject by remember { mutableStateOf(report?.subject ?: "") }
    var tripDate by remember { mutableStateOf(report?.tripDate ?: "") }
    var description by remember { mutableStateOf(report?.description ?: "") }
    var error by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    val companyNames = listOf("Tracasa", "Buses Caribeños", "HEBA", "TRACOPA", "SACSA", "LUMACA", "MEPE", "Tica Bus")
    val types = listOf("Queja","Sugerencia" , "Objeto perdido")
    val typeLabels = hashMapOf(
        "Queja" to "COMPLAINT",
        "Sugerencia" to "SUGGESTION",
        "Objeto perdido" to "LOSTITEM"
    )



    AlertDialog(onDismissRequest = close,
        title = {Text(text = if (report != null) "Nuevo reporte" else "Editar reporte")},
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                OutlinedTextField(
                    value = name,
                    onValueChange = {text -> name = text},
                    label = {Text(text = "Nombre")},
                    maxLines = 1
                )
                Select(
                    title = "Empresa",
                    text = companyName,
                    options = companyNames,
                    onClick = {pos -> companyName = companyNames[pos]}
                )
                Select(
                    title = "Tipo de reporte",
                    text = type,
                    options = types,
                    onClick = {pos -> type = types[pos]}
                )

                OutlinedTextField(
                    value = subject,
                    onValueChange = {text -> subject = text},
                    label = {Text(text = "Asunto")},
                    maxLines = 1
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = {text -> description = text},
                    label = {Text(text = "Descripción")},
                    maxLines = 1
                )
                PickDate(
                    title = "Fecha del viaje",
                    tripDate,
                    {text -> tripDate = text}
                )
                if (error != ""){
                    Text(text = error, color = Color.Red, modifier = Modifier.padding(bottom = 30.dp))
                }
            }

        },
        confirmButton = {
            val typeCode = typeLabels[type]
            Button(onClick = {
                val data = Report(
                    id = report?.id ?: 0,
                    companyName = companyName,
                    passengerName = name,
                    type = typeCode ?: "",
                    isUrgent = true,
                    subject = subject,
                    pictureURL = "test",
                    tripDate = tripDate,
                    description = description,
                    createdAt = ""
                )
                scope.launch {
                    try {
                        var response = if (report == null) {
                            Api.service.registerReport(data)
                        }else {
                            Api.service.updateReport(data.id, data)
                        }
                        if (response.isSuccessful){
                            save();
                        }else {
                            error = errorReader(response);
                        }
                    }catch (e: Exception){
                        error = "Error al guardar el reporte ${e.message}"
                    }
                }
            }
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            Button(onClick = close) {
                Text("Cancelar")
            }
        }
    )

}

