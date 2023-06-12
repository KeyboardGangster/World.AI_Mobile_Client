package com.example.movieApp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieApp.io.repository.WorldRepository
import com.example.movieApp.models.World
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoriteScreenViewModel(private val worldRepository: WorldRepository): ViewModel() {
    private val _worldListFaves = MutableStateFlow(listOf<World>())
    val worldListFaves: StateFlow<List<World>> = _worldListFaves.asStateFlow()

    init {
        viewModelScope.launch {
            worldRepository.getAllFavorites().collect { worldList ->
                _worldListFaves.value = worldList
            }
        }
    }

    suspend fun toggleFave(id: String) {
        val world: World = worldRepository.getById(id)
        world.isFavorite = !world.isFavorite
        worldRepository.update(world)
    }
}