package com.ilhamalgojali0081.assesment_1.ui.theme.component

import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ilhamalgojali0081.assesment_1.R
import com.ilhamalgojali0081.assesment_1.model.Category
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

    var selectedDate by rememberSaveable { mutableStateOf<Long?>(null) }
    var dateError by rememberSaveable { mutableStateOf(false) }
    var isShowModal by rememberSaveable { mutableStateOf(false) }

    val categoryProduct = listOf(
        Category(stringResource(R.string.laptop), R.drawable.laptop),
        Category(stringResource(R.string.phone), R.drawable.phone)
    )

    var selectedCategories by remember { mutableStateOf(categoryProduct[0]) }

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
            OutlinedTextField(
                value = selectedDate?.let { convertMillisToDate(it) } ?: "",
                onValueChange = {  },
                label = { Text(
                    text = stringResource(R.string.date),
                    fontFamily = poppins,
                    fontWeight = FontWeight.SemiBold
                ) },
                placeholder = { Text(text = stringResource(R.string.placeholder)) },
                trailingIcon = {
                    Icon(Icons.Default.DateRange, contentDescription = "Selected one")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .pointerInput(selectedDate){
                        awaitEachGesture {
                            awaitFirstDown(pass = PointerEventPass.Initial)
                            val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                            if (upEvent != null){
                                isShowModal = true
                            }
                        }
                    }
            )
            if (isShowModal){
                DatePickerModal(
                    onDateSelected = { selectedDate = it },
                    onDismis =  { isShowModal = false  }
                )
            }
            Row(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .border(2.dp, Color.Gray, RoundedCornerShape(4.dp))
            ){
                categoryProduct.forEach{ product ->
                    CategoryOption(
                        label = product.name,
                        isSelected = selectedCategories == product,
                        modifier = Modifier
                            .selectable(
                                selected = selectedCategories == product,
                                onClick = { selectedCategories = product },
                                role = Role.RadioButton
                            )
                            .weight(1f)
                            .padding(16.dp)
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    nameError = (name == "")
                    quanityError = (quantity == "" || quantity == "0")
                    dateError = selectedDate == null
                    if (nameError || quanityError || dateError){
                        return@Button
                    } else {
                        val newProduct = Product(
                            id = System.currentTimeMillis().toString(),
                            name = name,
                            quantity = quantity,
                            stokInDate = selectedDate?.let { convertMillisToDate(it) } ?: "",
                            icon = selectedCategories.image
                        )
                        onAdd(newProduct)
                        onDismiss()
                    }
                }
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
