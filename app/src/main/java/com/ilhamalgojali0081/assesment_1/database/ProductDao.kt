package com.ilhamalgojali0081.assesment_1.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ilhamalgojali0081.assesment_1.model.Product
import com.ilhamalgojali0081.assesment_1.relatiion.CategoryWithProduct
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Insert
    suspend fun insert(product: Product)

    @Update
    suspend fun update(product: Product)

    @Query("SELECT * FROM product ORDER BY name ASC")
    fun getAllProductWithCategory(): Flow<List<CategoryWithProduct>>

    @Query("SELECT * FROM product WHERE id = :id")
    suspend fun getProductById(id:Long): Product?

    @Query("DELETE FROM product WHERE id = :id")
    suspend fun deleteProductById(id:Long)
}