package com.ilhamalgojali0081.assesment_1.ui.theme.component

import android.content.res.Configuration
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ilhamalgojali0081.assesment_1.R
import com.ilhamalgojali0081.assesment_1.ui.theme.Assesment_1Theme

@Composable
fun DisplayAlertDialog(
    onDismissRequest : () -> Unit,
    onConfirmation : () -> Unit
) {
    AlertDialog(
        text = { Text( text = stringResource(R.string.delete_product) ) },
        confirmButton = {
            TextButton(onClick = { onConfirmation()} ) {
                Text( text = stringResource(R.string.delete) )
            }
        },
        dismissButton = {
            TextButton(onClick = { onConfirmation() }) {
                Text( text = stringResource(R.string.cancel) )
            }
        },
        onDismissRequest = { onDismissRequest() }
    )
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DialogAlertPreview(){
    Assesment_1Theme  {
        DisplayAlertDialog(
            onDismissRequest = {  },
            onConfirmation = { }
        )
    }
}