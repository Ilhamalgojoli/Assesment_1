package com.ilhamalgojali0081.assesment_1.database

import androidx.room.Query
import com.ilhamalgojali0081.assesment_1.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryDao {
    @Query("SELECT * FROM category ORDER BY id")
    fun getAllProduct(): Flow<List<Category>>
}