package com.example.miniprojektliste.Database.Navigation

sealed class Screen (val route: String){
    object HomeScreen : Screen("home_screen")
    object AddScreen : Screen("add_screen")

    object MainScreen : Screen("main_screen")
    object DetailScreen : Screen("detail_screen")
}