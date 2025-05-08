package com.ilhamalgojali0081.assesment_1.ui.theme.component

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilhamalgojali0081.assesment_1.R
import com.ilhamalgojali0081.assesment_1.model.Category
import com.ilhamalgojali0081.assesment_1.model.Product
import com.ilhamalgojali0081.assesment_1.relatiion.CategoryWithProduct
import com.ilhamalgojali0081.assesment_1.ui.theme.Assesment_1Theme
import com.ilhamalgojali0081.assesment_1.ui.theme.poppins

@Composable
fun GridCard(categoryWithProduct: CategoryWithProduct, onClick: () -> Unit){
    val initialProduct = categoryWithProduct.product.name.firstOrNull()?.uppercaseChar() ?: '-'

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable{ onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, DividerDefaults.color)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = initialProduct.toString(),
                fontSize = 64.sp,
                fontFamily = poppins,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = categoryWithProduct.product.name,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                fontFamily = poppins,
                maxLines = 1
            )
            Text(
                text = stringResource(R.string.category_name)
                        + " " + categoryWithProduct.category.name,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Normal,
                fontFamily = poppins,
                maxLines = 1
            )
            Text(
                text = stringResource(R.string.stock)
                        + " " + categoryWithProduct.product.quantity,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Normal,
                fontFamily = poppins,
                maxLines = 1
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun GridPreview(){
    Assesment_1Theme {
        val dummyCategory = CategoryWithProduct(
            category = Category(id = 1, name = "Electronics"),
            product = Product(id = 1, name = "Smartphone","4"
                ,"5.0"," " , categoryId = 1)
        )
        GridCard (
            categoryWithProduct = dummyCategory,
            onClick = { }
        )
    }
}