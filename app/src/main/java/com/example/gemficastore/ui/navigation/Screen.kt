package com.example.gemficastore.ui.navigation

sealed class Screen(val route: String) {
    object Games : Screen("games")
    object Liked : Screen("liked")
    object About : Screen("about")
    object Detail : Screen("games/{gamesId}") {
        fun createRoute(gamesId: Long) = "games/$gamesId"
    }
}
