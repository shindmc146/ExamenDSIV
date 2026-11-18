package com.examenii.reports.components

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.material3.DatePickerDialog;
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.examenii.reports.utils.milisecondsToText

@Composable
fun Select(title: String, text: String, options: List<String>,onClick: (Int) -> Unit){
    var open by remember { mutableStateOf(false) };
    Text(text= title, modifier = Modifier.padding(top = 12.dp,bottom = 4.dp ))

    Box{
        OutlinedButton(onClick = {open = true}, modifier = Modifier.fillMaxWidth()) {
            Text(text)
        }
        DropdownMenu(expanded = open, onDismissRequest = {open = false}) {
            for (pos in options.indices){
                DropdownMenuItem(
                    text = {Text(text=options[pos])},
                    onClick = {onClick(pos)
                    open = false}

                )
            }
        }
    }
}

@Composable
fun ConfirmAlert(message: String, onConfirm: () -> Unit, onCancel: () -> Unit){
    AlertDialog(
        onDismissRequest = onCancel,
        title = {Text(text = "¿Está seguro?")},
        text = {Text(text = message)},
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Sí, borrar", color = Color.Red)
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text("Cancelar")
            }
        }
    )
}
@Composable
fun PickDate(title: String, date: String, onConfirm: (String) -> Unit){
    var open by remember { mutableStateOf(false)}

    Text(text = title)

    OutlinedButton(onClick = {open = true}) {
        Text(text = if (date == "") "Seleccionar fecha" else date)
    }

    if (open){
        val state = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = {open=false},
            confirmButton = {
                TextButton(onClick = {
                    val miliseconds = state.selectedDateMillis
                    if (miliseconds != null){
                        onConfirm(milisecondsToText(miliseconds))
                    }
                    open = false
                }) {
                    Text(text = "Aceptar")
                }
            },
            dismissButton = {
                TextButton(onClick = {open = false}) {
                    Text(text = "Cancelar")
                }
            }
        ){
            DatePicker(state = state)
        }
    }
}

@Composable
fun Alert(text: String){
    if (text != ""){
        Text(
            text=text,
            fontWeight = FontWeight.Bold
        )
    }
}
