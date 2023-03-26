package com.example.movieApp.navigation

const val DETAIL_ARGUMENT_KEY = "id"

sealed class Screen(val route: String) {
    object Home: Screen(route = "home")
    object Detail: Screen(route = "detail/{$DETAIL_ARGUMENT_KEY}") {
        fun passId(id: String): String {
            return this.route.replace(oldValue = "{$DETAIL_ARGUMENT_KEY}", newValue = id)
        }
    }
    object Favorite: Screen(route = "favorite")
}