package com.examenii.reports.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.examenii.reports.api.Api
import com.examenii.reports.components.Alert
import com.examenii.reports.components.ConfirmAlert
import com.examenii.reports.domain.Report
import com.examenii.reports.utils.errorReader
import kotlinx.coroutines.launch

@Composable
fun ReportScreen(){
    var reports by remember { mutableStateOf<List<Report>>(emptyList()) }
    var message by remember { mutableStateOf("") }
    var reload by remember { mutableStateOf(0) }

    var creatingReport by remember { mutableStateOf(false) }
    var reportForEdit by remember { mutableStateOf<Report?>(null) }
    var reportForDelete by remember { mutableStateOf<Report?>(null) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(reload) {
        try {
            reports = Api.service.getReports()
        }catch (e: Exception){
            message = "Error al obtener los reportes ${e.message}"
        }
    }

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Row(modifier = Modifier.padding(top = 12.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Reportes",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Button(onClick = {creatingReport=true}) {
                Text(text = "Hacer Reporte")
            }
            Alert(message)

            if (reports.isEmpty()){
                Text(text = "No hay reportes aún")
            }

            for (r in reports){
                ReportCard(
                    report = r,
                    onEdit = {reportForEdit = r},
                    onDelete = {reportForDelete = r})
            }



            if (creatingReport){
                ReportForm(
                    report = null,
                    close = {creatingReport = false},
                    save = {
                        creatingReport = false
                        message = "Se envió el reporte correctamente"
                        reload += 1
                    }
                )
            }
            if (reportForEdit != null) {
                ReportForm(
                    report = reportForEdit,
                    close = {reportForEdit = null},
                    save = {
                        reportForEdit = null
                        message = "Se actualizó el reporte correctamente"
                        reload += 1
                    }
                )
            }

            val reportToDelete = reportForDelete;
            if (reportToDelete != null) {
                ConfirmAlert(
                    message= "¿Está seguro que desea borrar el reporte?",
                    onConfirm = {
                        scope.launch {
                            try {
                                val response = Api.service.deleteReport(reportToDelete.id)
                                if (response.isSuccessful){
                                    message = "Se eliminó el reporte correctamente"
                                    reload += 1
                                }else {
                                    message = errorReader(response)
                                }
                            }catch (e: Exception){
                                message = "Error al borrar el curso: ${e.message}"
                            }
                        }
                    },
                    onCancel = {reportForDelete = null}
                )
            }
        }
    }
}