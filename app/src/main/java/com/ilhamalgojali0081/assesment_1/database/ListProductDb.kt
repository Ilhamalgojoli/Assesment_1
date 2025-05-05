package com.ilhamalgojali0081.assesment_1.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ilhamalgojali0081.assesment_1.model.Category
import com.ilhamalgojali0081.assesment_1.model.Product

@Database(entities = [Category::class, Product::class], version = 1)
abstract class ListProductDb: RoomDatabase() {
    abstract val category: CategoryDao
    abstract val product: ProductDao

    companion object {
        @Volatile
        private var INSTANCE : ListProductDb? = null

        fun getInstance(context: Context): ListProductDb{
            synchronized(this){
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ListProductDb::class.java,
                        "list_product.db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}