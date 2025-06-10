package com.ilhamalgojali0081.assesment_1.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ilhamalgojali0081.assesment_1.ui.theme.screen.FormUbah
import com.ilhamalgojali0081.assesment_1.ui.theme.screen.KEY_PRODUCT_ID
import com.ilhamalgojali0081.assesment_1.ui.theme.screen.MainScreen

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()){
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            MainScreen(navController)
        }
        composable(route = Screen.FormBaru.route) {
            FormUbah(navController)
        }
        composable(
            route = Screen.FormUbah.route,
            arguments = listOf(
                navArgument(KEY_PRODUCT_ID){
                    type = NavType.LongType
                }
            )
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getLong(KEY_PRODUCT_ID) ?: 0L
            FormUbah(navController, id)
        }
    }
}