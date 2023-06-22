package com.example.movieApp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieApp.io.repository.WorldRepository
import com.example.movieApp.models.World
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailScreenViewModel(private val worldRepository: WorldRepository): ViewModel() {
    private val _worldList = MutableStateFlow(listOf<World>())
    private val worldList: StateFlow<List<World>> =_worldList.asStateFlow()

    init {
        viewModelScope.launch {
            worldRepository.getAll().collect { worldList ->
                if (!worldList.isNullOrEmpty())
                    _worldList.value = worldList
            }
        }
    }

    suspend fun toggleFave(id: String) {
        val world: World = worldRepository.getById(id)
        world.isFavorite = !world.isFavorite
        worldRepository.update(world)
    }

    fun getWorldFromId(id: String): World {
        return worldList.value.first {
            it.id == id
        }
    }

    suspend fun removeWorld(world: World) {
        worldRepository.delete(world)
    }
}