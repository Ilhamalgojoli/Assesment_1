package com.ilhamalgojali0081.assesment_1.navigation

sealed class Screen(val route: String){
    data object Home: Screen("MainScreen")
    data object About: Screen("AboutScreen")
}