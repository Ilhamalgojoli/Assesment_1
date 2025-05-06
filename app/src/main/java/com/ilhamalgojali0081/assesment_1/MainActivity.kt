package com.ilhamalgojali0081.assesment_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ilhamalgojali0081.assesment_1.navigation.SetupNavGraph
import com.ilhamalgojali0081.assesment_1.ui.theme.Assesment_1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assesment_1Theme {
                    SetupNavGraph()
                }
            }
        }
    }


