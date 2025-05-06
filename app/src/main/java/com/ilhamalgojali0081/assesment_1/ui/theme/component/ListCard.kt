package com.ilhamalgojali0081.assesment_1.ui.theme.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilhamalgojali0081.assesment_1.model.Category
import com.ilhamalgojali0081.assesment_1.model.Product
import com.ilhamalgojali0081.assesment_1.relatiion.CategoryWithProduct
import com.ilhamalgojali0081.assesment_1.ui.theme.poppins

@Composable
fun ListCard(categoryWithProduct: CategoryWithProduct,onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable{ onClick() }
            .padding(8.dp)
            .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(4.dp)),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text(
                text = categoryWithProduct.product.name,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                fontFamily = poppins,
                maxLines = 1
            )
            Text(
                text = "Category : ${ categoryWithProduct.category.name }",
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Normal,
                fontFamily = poppins,
                maxLines = 1
                )
            Text(
                text = "Stock : ${ categoryWithProduct.product.quantity }",
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Normal,
                fontFamily = poppins,
                maxLines = 1
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListCardPreview(){
    val dummyCategory = CategoryWithProduct(
        category = Category(id = 1, name = "Electronics"),
        product = Product(id = 1, name = "Smartphone","4" , "5.0"," " ,categoryId = 1)
    )
    ListCard(
        categoryWithProduct = dummyCategory,
        onClick = { }
    )
}