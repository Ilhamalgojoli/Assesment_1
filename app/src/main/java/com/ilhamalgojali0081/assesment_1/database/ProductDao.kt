package com.ilhamalgojali0081.assesment_1.database

import androidx.room.Insert
import androidx.room.Query
import com.ilhamalgojali0081.assesment_1.model.Category
import com.ilhamalgojali0081.assesment_1.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductDao {
    @Insert
    suspend fun insert(product: Product)

    @Insert
    suspend fun insert(vararg category: Category)

    @Query("SELECT * FROM product ORDER BY name ASC")
    fun getAllProduct(): Flow<List<Product>>

    @Query("SELECT * FROM product WHERE id = :id")
    suspend fun getProductById(id:Long): Product?

    @Query("DELETE FROM product WHERE id = :id")
    suspend fun deleteProductById(id:Long)
}