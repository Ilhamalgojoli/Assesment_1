package com.ilhamalgojali0081.assesment_1.navigation

import com.ilhamalgojali0081.assesment_1.ui.theme.screen.KEY_PRODUCT_ID

sealed class Screen(val route: String){
    data object Home: Screen("MainScreen")
    data object FormBaru: Screen("FormUbah")
    data object FormUbah: Screen("FormUbah/{$KEY_PRODUCT_ID}")
        fun withId(id: Long) = "FormUbah/$id"
}