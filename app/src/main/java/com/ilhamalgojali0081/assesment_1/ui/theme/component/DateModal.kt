package com.ilhamalgojali0081.assesment_1.ui.theme.component

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.ilhamalgojali0081.assesment_1.R
import com.ilhamalgojali0081.assesment_1.ui.theme.poppins
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismis: () -> Unit){

    val datePicker = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismis,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePicker.selectedDateMillis)
                onDismis()
            }) {
                Text(
                    text = stringResource(R.string.confirm),
                    fontFamily = poppins,
                    fontWeight = FontWeight.SemiBold
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismis) {
                Text(
                    text = stringResource(R.string.cancel),
                    fontFamily = poppins,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    ) {
        DatePicker( state = datePicker )
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}