package com.ilhamalgojali0081.assesment_1.ui.theme.screen

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ilhamalgojali0081.assesment_1.R
import com.ilhamalgojali0081.assesment_1.model.Category
import com.ilhamalgojali0081.assesment_1.ui.theme.Assesment_1Theme
import com.ilhamalgojali0081.assesment_1.ui.theme.component.ErrorHint
import com.ilhamalgojali0081.assesment_1.ui.theme.component.IconPicker
import com.ilhamalgojali0081.assesment_1.ui.theme.poppins
import com.ilhamalgojali0081.assesment_1.ui.theme.viewmodel.ProductRepository
import com.ilhamalgojali0081.assesment_1.util.ViewModelFactory

const val KEY_PRODUCT_ID = "product_id"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormUbah(
    navController: NavHostController,
    id: Long? = null
) {
    val context = LocalContext.current
    val factory = ViewModelFactory(context)
    val viewModel: ProductRepository = viewModel(factory = factory)
    val categories by viewModel.categoryList.collectAsState(initial = emptyList())

    var name by remember { mutableStateOf("") }
    var stockInput by remember { mutableStateOf("") }
    var priceInput by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<Category?>(null) }

    LaunchedEffect(Unit) {
        if (id == null) return@LaunchedEffect
        val data = viewModel.getProductById(id) ?: return@LaunchedEffect
        name = data.name
        stockInput = data.quantity
        priceInput = data.price
        selectedCategory = categories.find {
            it.id == data.categoryId
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
                title = {
                    if(id == null)
                        Text(
                            text = stringResource(R.string.add_product),
                            fontFamily = poppins,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    else
                        Text(
                            stringResource(R.string.edit_product),
                            fontFamily = poppins,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                },
                actions = {
                    IconButton(onClick = {
                        if (name == "" || stockInput == "" || priceInput == ""
                            || selectedCategory == null) {
                            Toast.makeText(context, R.string.sanity_check_input,
                                Toast.LENGTH_SHORT).show()
                            return@IconButton
                        }

                        if (id == null) {
                            viewModel.insertProduct(
                                name,
                                stockInput,
                                priceInput,
                                selectedCategory!!
                            )
                        } else {
                            viewModel.updateProduct(
                                id,
                                name,
                                stockInput,
                                priceInput,
                                selectedCategory!!
                            )
                        }
                        navController.navigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = stringResource(R.string.confirm))
                    }
                }
            )
        }
    ) { padding ->
        FormContent(
            modifier = Modifier.padding(padding),
            name = name,
            onChangeName = { name = it },
            stockInput = stockInput,
            onChangeStockInput = { stockInput = it },
            priceInput = priceInput,
            onChangePriceInput = { priceInput = it },
            categories = categories,
            selectedCategory = selectedCategory,
            onSelectedCategory = { selectedCategory = it }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormContent(
    modifier: Modifier = Modifier,
    name: String,
    onChangeName: (String) -> Unit,
    stockInput: String,
    onChangeStockInput: (String) -> Unit,
    priceInput: String,
    onChangePriceInput: (String) -> Unit,
    categories: List<Category>,
    selectedCategory: Category?,
    onSelectedCategory: (Category) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var nameError by remember { mutableStateOf(false) }
    var priceError by remember { mutableStateOf(false) }
    var stockError by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.padding(16.dp)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { onChangeName(it) },
            label = {
                Text(
                    text = stringResource(R.string.product_name),
                    fontFamily = poppins
                )
            },
            trailingIcon = {
                IconPicker(nameError)
            },
            supportingText = {
                ErrorHint(nameError)
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = nameError
        )

        OutlinedTextField(
            value = stockInput,
            onValueChange = { onChangeStockInput(it) },
            label = {
                Text(
                    text = stringResource(R.string.stock_amount),
                    fontFamily = poppins
                )
            },
            trailingIcon = {
                IconPicker(stockError)
            },
            supportingText = {
                ErrorHint(stockError)
            },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = stockError
        )

        OutlinedTextField(
            value = priceInput,
            onValueChange = { onChangePriceInput(it) },
            label = {
                Text(
                    text = stringResource(R.string.price_input),
                    fontFamily = poppins
                )
            },
            trailingIcon = {
                IconPicker(priceError)
            },
            supportingText = {
                ErrorHint(priceError)
            },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = priceError
        )

        if (categories.isNotEmpty()) {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                OutlinedTextField(
                    readOnly = true,
                    value = selectedCategory?.name ?: stringResource(R.string.chose_category),
                    onValueChange = {},
                    label = {
                        Text(
                            text = stringResource(R.string.category),
                            fontFamily = poppins
                        )
                    },
                    trailingIcon = {
                        Icon(
                            imageVector =
                            if (expanded)
                                Icons.Default.KeyboardArrowUp
                            else
                                Icons.Default.KeyboardArrowDown,
                            contentDescription = stringResource(R.string.chose_category)
                        )
                    },
                    modifier = Modifier.fillMaxWidth().menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    categories.forEach { category ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    category.name,
                                    fontFamily = poppins
                                )
                            },
                            onClick = {
                                onSelectedCategory(category)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(showBackground = true)
@Composable
fun AboutScreenPreview(){
    Assesment_1Theme {
        FormUbah(rememberNavController())
    }
}
