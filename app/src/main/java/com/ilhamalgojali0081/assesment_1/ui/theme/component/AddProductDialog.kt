package com.ilhamalgojali0081.assesment_1.ui.theme.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ilhamalgojali0081.assesment_1.R
import com.ilhamalgojali0081.assesment_1.model.Product
import com.ilhamalgojali0081.assesment_1.ui.theme.poppins


@Composable
fun AddProductDialog(
    onDismiss: () -> Unit,
    onAdd: (Product) -> Unit,
) {
    var name by rememberSaveable { mutableStateOf("") }
    var nameError by rememberSaveable { mutableStateOf(false) }

    var quantity by rememberSaveable { mutableStateOf("") }
    var quanityError by rememberSaveable { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(R.string.add_product),
                fontFamily = poppins,
                fontWeight = FontWeight.Bold,
            ) },
        text = {
            Column(modifier = Modifier.padding(8.dp)) {
                OutlinedTextField(
                    value = name ,
                    onValueChange = {
                        name = it
                        nameError = it.isEmpty()
                    },
                    trailingIcon = { IconPicker(nameError) },
                    supportingText = { ErrorHint(nameError) },
                    singleLine = true,
                    label = {
                        Text(
                            text = stringResource(R.string.name),
                            fontFamily = poppins,
                            fontWeight = FontWeight.SemiBold
                        )
                    },
                    isError = nameError,
                    modifier = Modifier
                        .fillMaxWidth(),
                )
                OutlinedTextField(
                    value = quantity ,
                    onValueChange = {
                        quantity = it
                        quanityError = it.isEmpty()
                    },
                    singleLine = true,
                    trailingIcon = { IconPicker(quanityError) },
                    supportingText = { ErrorHint(quanityError) },
                    label = {
                        Text(
                            text = stringResource(R.string.stock),
                            fontFamily = poppins,
                            fontWeight = FontWeight.SemiBold
                        )
                    },
                    isError = quanityError,
                    modifier = Modifier
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done

                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {}
            ) {
                Text(
                    text = stringResource(R.string.confirm),
                    fontFamily = poppins,
                    fontWeight = FontWeight.SemiBold
                )
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(
                    text = stringResource(R.string.cancel),
                    fontFamily = poppins,
                    fontWeight = FontWeight.SemiBold
                )
            }
        },
    )
}