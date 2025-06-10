package com.ilhamalgojali0081.assesment_1.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ilhamalgojali0081.assesment_1.database.ProductDb
import com.ilhamalgojali0081.assesment_1.ui.theme.viewmodel.ProductViewModel

class ViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val db = ProductDb.getInstance(context)
        val daoCategory = db.category
        val daoProduct = db.product
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            return ProductViewModel(daoProduct, daoCategory) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
