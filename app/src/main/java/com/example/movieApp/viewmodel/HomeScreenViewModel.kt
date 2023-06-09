package com.example.movieApp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieApp.io.repository.WorldRepository
import com.example.movieApp.models.World
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel(private val worldRepository: WorldRepository): ViewModel() {
    private val _worldList = MutableStateFlow(listOf<World>())
    val worldList: StateFlow<List<World>> = _worldList.asStateFlow()

    /*private val _movieList = MutableStateFlow(listOf<Movie>())
    val movieList: StateFlow<List<Movie>> =_movieList.asStateFlow()*/

    init {
        viewModelScope.launch {
            worldRepository.getAll().collect {worlds ->
                _worldList.value = worlds
            }
        }
    }

    suspend fun toggleFave(id: String) {
        val world: World = worldRepository.getById(id)
        world.isFavorite = !world.isFavorite
        worldRepository.update(world)
    }
}