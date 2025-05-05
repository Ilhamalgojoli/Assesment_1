package com.ilhamalgojali0081.assesment_1.relatiion

import androidx.room.Embedded
import androidx.room.Relation
import com.ilhamalgojali0081.assesment_1.model.Category
import com.ilhamalgojali0081.assesment_1.model.Product

data class CategoryWithProduct(
    @Embedded val product: Product,
    @Relation(
        parentColumn = "Category",
        entityColumn = "Product"
    )
    val category: Category
)