package com.ilhamalgojali0081.assesment_1.ui.theme.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import com.ilhamalgojali0081.assesment_1.ui.theme.component.ListCard
import com.ilhamalgojali0081.assesment_1.ui.theme.poppins

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val data = rememberSaveable { mutableStateListOf<Product>() }

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
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {}
            ) {

            }
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
                    ListCard(product = product)
                        }
                }
            }
        }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Assesment_1Theme {
        MainScreen()
    }
}