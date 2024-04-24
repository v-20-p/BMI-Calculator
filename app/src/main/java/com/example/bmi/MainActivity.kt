package com.example.bmi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

import com.example.bmi.ui.theme.BMITheme




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {


            BMITheme {

                Nav()

            }
        }
    }
}
@Composable
fun Nav(){
    val navController = rememberNavController()
    NavHost(navController = navController,startDestination = home.route){
        composable(home.route) {
            HomeScreen(navController = navController)
        }
        composable(second.route+"/{bmiNO}",arguments = listOf(navArgument("bmiNO") { type = NavType.FloatType })) {
            backStackEntry ->
            val bmiNO = backStackEntry.arguments?.getFloat("bmiNO") ?: 18.0f
            SecondScreen(navController = navController, bmiNO = bmiNO )

        }
    }
}








