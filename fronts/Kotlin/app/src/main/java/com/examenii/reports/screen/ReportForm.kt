package com.examenii.reports.screen

import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.examenii.reports.api.Api
import com.examenii.reports.api.ApiService
import com.examenii.reports.components.Alert
import com.examenii.reports.components.ImagePicker
import com.examenii.reports.components.PickDate
import com.examenii.reports.components.Select
import com.examenii.reports.domain.Report
import com.examenii.reports.utils.companyNames
import com.examenii.reports.utils.errorImageReader
import com.examenii.reports.utils.errorReader
import com.examenii.reports.utils.typeToLabels
import com.examenii.reports.utils.types
import com.examenii.reports.utils.validations.validReport
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.http.Multipart
import java.io.InputStream

@Composable
fun ReportForm(
    report: Report?,
    save: () -> Unit,
    close: () -> Unit
) {
    var name by remember { mutableStateOf(report?.passengerName ?: "") }
    var companyName by remember { mutableStateOf(report?.companyName ?: "") }
    var type by remember { mutableStateOf(if (report != null) typeToLabels[report.type] else "") }
    var isUrgent by remember { mutableStateOf(report?.isUrgent ?: false) }
    var subject by remember { mutableStateOf(report?.subject ?: "") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var tripDate by remember { mutableStateOf(report?.tripDate ?: "") }
    var description by remember { mutableStateOf(report?.description ?: "") }
    var error by remember { mutableStateOf("") }

    var uploadedFileName by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current





    AlertDialog(
        onDismissRequest = close,
        title = { Text(text = if (report == null) "Nuevo reporte" else "Editar reporte") },
        text = {

            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                if (error != "") {
                    Text(
                        text = error,
                        color = Color.Red,
                        modifier = Modifier.padding(bottom = 30.dp)
                    )
                }

                OutlinedTextField(
                    value = name,
                    onValueChange = { text -> name = text },
                    label = { Text(text = "Nombre") },
                    maxLines = 1
                )
                Select(
                    title = "Empresa",
                    text = companyName,
                    options = companyNames,
                    onClick = { pos -> companyName = companyNames[pos] }
                )
                Select(
                    title = "Tipo de reporte",
                    text = type,
                    options = types,
                    onClick = { pos -> type = types[pos] }
                )

                OutlinedTextField(
                    value = subject,
                    onValueChange = { text -> subject = text },
                    label = { Text(text = "Asunto") },
                    maxLines = 1
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Urgente")
                    Checkbox(checked = isUrgent, onCheckedChange = {isChecked -> isUrgent = isChecked})
                }

                PickDate(
                    title = "Fecha del viaje",
                    tripDate,
                    { text -> tripDate = text }
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { text -> description = text },
                    label = { Text(text = "Descripción") },
                    minLines = 3
                )


                ImagePicker(
                    title = "Foto relacionada",
                    onImageSelected = { uri ->
                        imageUri = uri
                        uploadedFileName = null
                    }
                )
                if (imageUri != null) {
                    Text(text = "Imagen seleccionada ✓")
                }

            }

        },
        confirmButton = {

            Button(onClick = {
                error = validReport(name, companyName, type ?: "", subject, description, tripDate, uploadedFileName ?: "");
                if (error != ""){
                    return@Button
                }

                var fileName : String? = if (report != null) report.pictureURL else uploadedFileName;

                val uri = imageUri;
                scope.launch {
                    if ((uri != null && fileName == null) || (uri != null && report != null)) {
                        val bytes = withContext(Dispatchers.IO) {
                            context.contentResolver.openInputStream(uri)
                                ?.use { stream -> stream.readBytes() }
                        }
                        val type = context.contentResolver.getType(uri)
                        if (bytes != null && type != null) {
                            val extension =
                                MimeTypeMap.getSingleton().getExtensionFromMimeType(type)
                            val body = bytes.toRequestBody(type.toMediaTypeOrNull())
                            val part =
                                MultipartBody.Part.createFormData("file", "photo.$extension", body)

                            try {
                                val response = Api.service.uploadImage(part);
                                if (!response.isSuccessful) {
                                    error = errorImageReader(response);
                                    return@launch;
                                }
                                fileName = response.body()?.fileName;
                                println("AAAAAAAAAAA $fileName")
                                uploadedFileName = fileName
                            } catch (e: Exception) {
                                error = "Error al guardar la imagen ${e.message}"
                            }
                        }
                    }
                    val validType =
                        if (type == "Queja") {
                            "COMPLAINT"
                        } else if (type == "Sugerencia") {
                            "SUGGESTION"
                        } else if (type == "Objeto perdido") {
                            "LOSTITEM"
                        } else {
                            ""
                        }
                    val data = Report(
                        id = report?.id ?: 0,
                        companyName = companyName,
                        passengerName = name,
                        type = validType,
                        isUrgent = isUrgent,
                        subject = subject,
                        pictureURL = fileName,
                        tripDate = tripDate,
                        description = description,
                        createdAt = ""
                    )
                    try {
                        error = "";
                        val response = if (report == null) {
                            Api.service.registerReport(data)
                        } else {
                            Api.service.updateReport(data.id, data)
                        }
                        if (response.isSuccessful) {
                            imageUri = null
                            uploadedFileName = null

                            save();
                        } else {
                            error = errorReader(response);
                        }
                    } catch (e: Exception) {
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

