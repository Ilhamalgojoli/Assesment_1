package com.ilhamalgojali0081.assesment_1.ui.theme.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ilhamalgojali0081.assesment_1.R
import com.ilhamalgojali0081.assesment_1.navigation.Screen
import com.ilhamalgojali0081.assesment_1.relatiion.CategoryWithProduct
import com.ilhamalgojali0081.assesment_1.ui.theme.Assesment_1Theme
import com.ilhamalgojali0081.assesment_1.ui.theme.component.DeleteDialog
import com.ilhamalgojali0081.assesment_1.ui.theme.component.DetailProduct
import com.ilhamalgojali0081.assesment_1.ui.theme.component.GridCard
import com.ilhamalgojali0081.assesment_1.ui.theme.component.ListCard
import com.ilhamalgojali0081.assesment_1.ui.theme.poppins
import com.ilhamalgojali0081.assesment_1.ui.theme.viewmodel.ProductRepository
import com.ilhamalgojali0081.assesment_1.util.SettingsDataStore
import com.ilhamalgojali0081.assesment_1.util.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    val dataStore = SettingsDataStore(LocalContext.current)
    val showList by dataStore.layoutFlow.collectAsState(true)

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
                ),
                actions = {
                    IconButton(
                        onClick = {
                            CoroutineScope(Dispatchers.IO).launch {
                                dataStore.saveLayout(!showList)
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(
                                if (showList)
                                    R.drawable.baseline_grid_view_24
                                else
                                    R.drawable.baseline_view_list_24
                            ),
                            contentDescription = stringResource(
                                if (showList)
                                    R.string.grid
                                else
                                    R.string.list
                            ),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.FormBaru.route)
                },
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "add")
            }
        }
    ) { innerPadding ->
        MainContent(
            modifier = Modifier
                .padding(innerPadding),
            showList, navController = navController
        )
    }
}

@Composable
fun MainContent(modifier: Modifier = Modifier, showList: Boolean, navController: NavHostController) {
    val context = LocalContext.current
    val factory = ViewModelFactory(context)

    val viewModal: ProductRepository = viewModel(factory = factory)
    val data by viewModal.data.collectAsState()

    val openDialog = remember { mutableStateOf(false) }
    val showDeleteDialog = remember { mutableStateOf(false) }
    val selectedItem = remember { mutableStateOf<CategoryWithProduct?>(null) }

    // Update state: menutup DetailProduct ketika DeleteDialog akan muncul
    LaunchedEffect(showDeleteDialog.value) {
        if (showDeleteDialog.value) {
            openDialog.value = false // Tutup DetailProduct
        }
    }

    if (data.isEmpty()) {
        Column(
            modifier = modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.empty),
                fontFamily = poppins,
                fontWeight = FontWeight.Normal
            )
        }
    } else {
        if (showList) {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 84.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(data) { item ->
                    ListCard(categoryWithProduct = item) {
                        selectedItem.value = item
                        openDialog.value = true
                    }
                    HorizontalDivider()
                }
            }
        } else {
            LazyVerticalStaggeredGrid(
                modifier = modifier.fillMaxSize(),
                columns = StaggeredGridCells.Fixed(2),
                verticalItemSpacing = 8.dp,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(8.dp, 8.dp, 8.dp, 84.dp)
            ) {
                items(data) { item ->
                    GridCard(categoryWithProduct = item) {
                        selectedItem.value = item
                        openDialog.value = true
                    }
                }
            }
        }

        if (openDialog.value && selectedItem.value != null) {
            DetailProduct(
                categoryWithProduct = selectedItem.value!!,
                onDismiss = { openDialog.value = false },
                onTraction = {},
                onEdit = { id ->
                    navController.navigate(Screen.FormUbah.withId(id.toLong()))
                    openDialog.value = false
                },
                onDelete = {
                    if (selectedItem.value != null) {
                        showDeleteDialog.value = true // Tampilkan DeleteDialog
                    }
                }
            )
        }

        if (showDeleteDialog.value) {
            DeleteDialog(
                onDismissRequest = { showDeleteDialog.value = false },
                onConfirmation = {
                    val id = selectedItem.value!!.product.id
                    viewModal.deleteProduct(id)
                    showDeleteDialog.value = false
                }
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Assesment_1Theme {
        MainScreen(rememberNavController())
    }
}