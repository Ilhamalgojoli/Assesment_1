package com.ilhamalgojali0081.assesment_1.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ilhamalgojali0081.assesment_1.model.Category
import com.ilhamalgojali0081.assesment_1.model.Product

@Database(entities = [Category::class, Product::class], version = 1)
abstract class ProductDb: RoomDatabase() {
    abstract val category: CategoryDao
    abstract val product: ProductDao

    companion object {
        @Volatile
        private var INSTANCE : ProductDb? = null

        fun getInstance(context: Context): ProductDb{
            synchronized(this){
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ProductDb::class.java,
                        "product.db"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}