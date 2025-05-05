package com.ilhamalgojali0081.assesment_1.ui.theme.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilhamalgojali0081.assesment_1.database.CategoryDao
import com.ilhamalgojali0081.assesment_1.database.ProductDao
import com.ilhamalgojali0081.assesment_1.model.Category
import com.ilhamalgojali0081.assesment_1.model.Product
import com.ilhamalgojali0081.assesment_1.relatiion.CategoryWithProduct
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ProductRepository(
    private val productDao: ProductDao,
    private val categoryDao: CategoryDao
):ViewModel() {

    private val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    private val _categoryList = MutableStateFlow<List<Category>>(emptyList())
    val categoryList: Flow<List<Category>> get() = _categoryList

    init {
        viewModelScope.launch {
            val categories = categoryDao.getAllCategory().first()
            if (categories.isEmpty()){
                categoryDao.insertCategory(Category(name = "Bahan Pokok"))
                categoryDao.insertCategory(Category(name = "Barang Electronic"))
                categoryDao.insertCategory(Category(name = "Perkakas Rumah"))
                categoryDao.insertCategory(Category(name = "Sayuran"))
                categoryDao.insertCategory(Category(name = "Buah buahan"))
                categoryDao.insertCategory(Category(name = "Rempah & Bumbu"))
            }

            _categoryList.value = categoryDao.getAllCategory().first()
        }
    }

    val data: StateFlow<List<CategoryWithProduct>> = productDao.getAllProductWithCategory().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

    suspend fun getProductById(id: Long): Product?{
        return productDao.getProductById(id)
    }

    fun insertProduct(
        name: String,
        stock: String,
        price: String,
        category: Category
    ) {
        val product = Product(
            name = name,
            quantity = stock,
            price = price,
            stokInDate = formatter.format(Date()),
            categoryId = category.id
        )
        viewModelScope.launch(Dispatchers.IO) {
            productDao.insert(product)
        }
    }

    fun updateProduct(
        id: Long,
        name: String,
        stock: String,
        price: String,
        category: Category
    ){
        val product = Product(
            id = id,
            name = name,
            quantity = stock,
            price = price,
            stokInDate = formatter.format(Date()),
            categoryId = category.id
        )

        viewModelScope.launch(Dispatchers.IO) {
            productDao.update(product)
        }
    }

    fun deleteProduct(id:Long) {
        viewModelScope.launch(Dispatchers.IO) {
            productDao.deleteProductById(id = id)
        }
    }
}