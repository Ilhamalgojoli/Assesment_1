package com.ilhamalgojali0081.assesment_1.ui.theme.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilhamalgojali0081.assesment_1.R
import com.ilhamalgojali0081.assesment_1.model.Product
import com.ilhamalgojali0081.assesment_1.ui.theme.poppins

@Composable
fun ListCard(product: Product) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        border = BorderStroke(1.dp, Color.Black),
        colors = CardDefaults.cardColors( containerColor = Color.White )
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = product.icon),
                contentDescription = product.name,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(100.dp)
            )
            Column(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 12.dp)
                    .weight(1f)
            ) {
                Text(
                    text = product.name,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Black
                )
                Text(
                    text = stringResource(R.string.stock) + product.quantity,
                    fontFamily = poppins,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = Color.Black
                )
                Text(
                    text = stringResource(R.string.tanggal_masuk) + product.stokInDate,
                    fontFamily = poppins,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = Color.Black
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 12.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = {  },
                        modifier = Modifier.padding(end = 4.dp),
                        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 8.dp)
                    ) {
                        Text(text = stringResource(R.string.edit))
                    }
                    Button(
                        onClick = {  },
                        modifier = Modifier.padding(start = 4.dp),
                        contentPadding = PaddingValues(vertical = 10.dp, horizontal = 10.dp)
                    ) {
                        Text(text = stringResource(R.string.delete))
                    }
                }
            }
        }
    }
}