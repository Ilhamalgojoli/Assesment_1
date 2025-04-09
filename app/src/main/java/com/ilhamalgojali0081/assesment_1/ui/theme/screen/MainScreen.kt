package com.ilhamalgojali0081.assesment_1.ui.theme.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilhamalgojali0081.assesment_1.R
import com.ilhamalgojali0081.assesment_1.model.Product
import com.ilhamalgojali0081.assesment_1.ui.theme.Assesment_1Theme
import com.ilhamalgojali0081.assesment_1.ui.theme.component.AddProductDialog
import com.ilhamalgojali0081.assesment_1.ui.theme.component.EditProductDialog
import com.ilhamalgojali0081.assesment_1.ui.theme.component.ListCard
import com.ilhamalgojali0081.assesment_1.ui.theme.poppins

val productListSaver: Saver<MutableList<Product>, *> = listSaver(
    save = { list -> list.toList() },
    restore = { savedList -> savedList.toMutableList() }
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val data = rememberSaveable(saver = productListSaver){ mutableStateListOf<Product>() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.MainMenu),
                        fontFamily = poppins,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        MainContent(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            data = data
        )
    }
}

@Composable
fun MainContent(modifier: Modifier = Modifier, data: MutableList<Product>) {

    var showDialogAddProduct by remember { mutableStateOf(false) }
    var showDialogEditProduct by remember { mutableStateOf(false) }
    var selectedProduct by remember { mutableStateOf<Product?>(null) }

    Box(
        modifier = modifier
            .padding(6.dp)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(data) { product ->
                    ListCard(
                        product = product,
                        onEdit = {
                            selectedProduct = product
                            showDialogEditProduct = true
                        },
                        onDelete = { selectProduct -> data.remove(selectProduct) }
                    )
                }
            }
        }

        if (data.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.empty),
                    fontFamily = poppins,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }
        }

        FloatingActionButton(
            onClick = { showDialogAddProduct = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "add")
        }

        if (showDialogAddProduct) {
            AddProductDialog(
                onDismiss = { showDialogAddProduct = false },
                onAdd = { newProduct -> data.add(newProduct) },
            )
        }
        if (selectedProduct != null && showDialogEditProduct){
            selectedProduct?.let { product ->
                EditProductDialog(
                    product = product,
                    onDissmis = {
                        selectedProduct = null
                        showDialogEditProduct = false
                    },
                    onEdit = { updateQuantity ->
                        val index = data.indexOfFirst { it.id == selectedProduct!!.id }
                        if (index != -1){
                            data[index] = selectedProduct?.copy(quantity = updateQuantity) ?: product
                        }
                        showDialogEditProduct = false
                        selectedProduct = null
                    }
                )
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Assesment_1Theme {
        MainScreen()
    }
}