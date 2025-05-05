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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ilhamalgojali0081.assesment_1.model.Category
import com.ilhamalgojali0081.assesment_1.ui.theme.Assesment_1Theme
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

    var name by remember { mutableStateOf("") }
    var stockInput by remember { mutableStateOf("") }
    var priceInput by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<Category?>(null) }

    val categories by viewModel.categoryList.collectAsState(initial = emptyList())

    // Load data jika id ada (mode edit)
    LaunchedEffect(id, categories) {
        if (id != null && categories.isNotEmpty()) {
            val product = viewModel.getProductById(id)
            product?.let {
                name = it.name
                stockInput = it.quantity
                priceInput = it.price
                selectedCategory = categories.find { cat -> cat.id == it.categoryId }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = if (id == null) "Tambah Produk" else "Edit Produk")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (name.isBlank() || stockInput.isBlank() || priceInput.isBlank() || selectedCategory == null) {
                            Toast.makeText(context, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show()
                            return@IconButton
                        }

                        if (id == null) {
                            viewModel.insertProduct(name, stockInput, priceInput, selectedCategory!!)
                        } else {
                            viewModel.updateProduct(id, name, stockInput, priceInput, selectedCategory!!)
                        }
                        navController.navigateUp()
                    }) {
                        Icon(Icons.Default.Check, contentDescription = "Simpan")
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

    Column(modifier = modifier.padding(16.dp)) {
        OutlinedTextField(
            value = name,
            onValueChange = onChangeName,
            label = { Text("Nama Produk") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = stockInput,
            onValueChange = onChangeStockInput,
            label = { Text("Jumlah Stok") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        OutlinedTextField(
            value = priceInput,
            onValueChange = onChangePriceInput,
            label = { Text("Harga") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
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
                    value = selectedCategory?.name ?: "Pilih Kategori",
                    onValueChange = {},
                    label = { Text("Kategori") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    categories.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category.name) },
                            onClick = {
                                onSelectedCategory(category)
                                expanded = false
                            }
                        )
                    }
                }
            }
        } else {
            Text("Memuat kategori...", modifier = Modifier.padding(top = 16.dp))
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
