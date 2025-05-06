package com.ilhamalgojali0081.assesment_1.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ilhamalgojali0081.assesment_1.model.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Insert
    suspend fun insertCategory(category: Category)

    @Query("SELECT * FROM category ORDER BY id ASC")
    fun getAllCategory(): Flow<List<Category>>

}