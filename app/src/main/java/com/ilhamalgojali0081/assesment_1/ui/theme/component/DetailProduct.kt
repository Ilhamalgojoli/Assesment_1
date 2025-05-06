package com.ilhamalgojali0081.assesment_1.ui.theme.component

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilhamalgojali0081.assesment_1.R
import com.ilhamalgojali0081.assesment_1.model.Category
import com.ilhamalgojali0081.assesment_1.model.Product
import com.ilhamalgojali0081.assesment_1.relatiion.CategoryWithProduct
import com.ilhamalgojali0081.assesment_1.ui.theme.poppins

@Composable
fun DetailProduct(
    categoryWithProduct: CategoryWithProduct,
    onDismiss: () -> Unit,
    onTraction: () -> Unit,
    onEdit: (Int) -> Unit,
    onDelete: () -> Unit
) {
    val context = LocalContext.current

    AlertDialog(
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = categoryWithProduct.product.name,
                    fontWeight = FontWeight.Bold,
                    fontFamily = poppins,
                )
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Nama Produk : ${categoryWithProduct.product.name}")
                Text("Kategori : ${categoryWithProduct.category.name}")
                Text("Stok : ${categoryWithProduct.product.quantity} Kg/Unit")
                Text("Tanggal Masuk : ${categoryWithProduct.product.stokInDate}")
                Text("Harga : Rp ${categoryWithProduct.product.price}")

                Row(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
                ) {
                    Button(
                        onClick = {
                            onDelete()
                            onDismiss()
                        }
                    ) {
                        Text(text = stringResource(R.string.delete))
                    }
                    Button(
                        onClick = {
                            onEdit(categoryWithProduct.product.id.toInt())
                            onDismiss()
                        }
                    ) {
                        Text(text = stringResource(R.string.edit))
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                Toast.makeText(context, "Under Maintance", Toast.LENGTH_SHORT).show()

                onTraction()
            }) {
                Text(text = stringResource(R.string.transaction))
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDismiss()
            }) {
                Text(text = stringResource(R.string.back))
            }
        },
        onDismissRequest = { onDismiss() },
    )
}

@Preview(showBackground = true)
@Composable
fun DetailProductPrevieww(){
    val dummyCategory = CategoryWithProduct(
        category = Category(id = 1, name = "Electronics"),
        product = Product(id = 1, name = "Smartphone","4" , "5.0"," " ,categoryId = 1)
    )

    DetailProduct(
        onDismiss = { },
        onDelete =  { },
        onEdit = { },
        onTraction = { },
        categoryWithProduct = dummyCategory
    )
}