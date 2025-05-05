package com.ilhamalgojali0081.assesment_1.ui.theme.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilhamalgojali0081.assesment_1.database.CategoryDao
import com.ilhamalgojali0081.assesment_1.model.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CategoryViewModal(
    categoryDao: CategoryDao
): ViewModel() {
    private val _categoryList = MutableStateFlow<List<Category>>(emptyList())
    val categoryList: Flow<List<Category>> get() = _categoryList

    init {
        viewModelScope.launch {
            val categories = categoryDao.getAllCategory().first()
            if (categories.isEmpty()){
                categoryDao.insertCategory(Category(name = "Bahan Pokok"))
                categoryDao.insertCategory(Category(name = "Minuman"))
                categoryDao.insertCategory(Category(name = "Sayuran"))
                categoryDao.insertCategory(Category(name = "Buah buahan"))
                categoryDao.insertCategory(Category(name = "Rempah & Bumbu"))
            }

            _categoryList.value = categoryDao.getAllCategory().first()
        }
    }
}