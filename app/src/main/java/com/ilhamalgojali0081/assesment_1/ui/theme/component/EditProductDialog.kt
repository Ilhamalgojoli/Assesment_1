package com.ilhamalgojali0081.assesment_1.ui.theme.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
fun EditProductDialog(
    product: Product,
    onDissmis: () -> Unit,
    onEdit: (String) -> Unit
){
    var quantity by rememberSaveable { mutableStateOf(product.quantity) }
    var quanityError by rememberSaveable { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDissmis,
        title = {
            Text(
                text = stringResource(R.string.edit_fragment),
                fontFamily = poppins,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(modifier = Modifier.padding(8.dp)) {
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
        dismissButton = {
            TextButton(
                onClick = onDissmis
            ) {
                Text(
                    text = stringResource(R.string.cancel),
                    fontFamily = poppins,
                    fontWeight = FontWeight.SemiBold
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    quanityError = quantity == "" || quantity == "0"
                    if(!quanityError){
                        onEdit(quantity)
                    } else {
                        return@TextButton
                    }
                }
            ) {
                Text(
                    text = stringResource(R.string.confirm),
                    fontFamily = poppins,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    )
}