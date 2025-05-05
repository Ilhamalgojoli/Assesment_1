package com.ilhamalgojali0081.assesment_1.ui.theme.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilhamalgojali0081.assesment_1.database.ProductDao
import com.ilhamalgojali0081.assesment_1.model.Product
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class MainViewModel(
    productDao: ProductDao
):ViewModel() {

    val data: StateFlow<List<Product>> = productDao.getAllProduct().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

}