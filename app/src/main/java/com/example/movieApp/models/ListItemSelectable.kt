package com.example.movieApp.models

data class ListItemSelectable<T>(
    val reference: T,
    val isSelected: Boolean = false
)