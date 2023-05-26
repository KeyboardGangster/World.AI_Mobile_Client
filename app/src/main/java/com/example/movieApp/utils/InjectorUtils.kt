package com.example.movieApp.utils

import android.content.Context
import com.example.movieApp.io.db.MovieDatabase
import com.example.movieApp.io.repository.MovieRepository
import com.example.movieApp.viewmodel.AddMovieScreenViewModel
import com.example.movieApp.viewmodel.AddMovieScreenViewModelFactory
import com.example.movieApp.viewmodel.DetailScreenViewModelFactory
import com.example.movieApp.viewmodel.FavoriteScreenViewModelFactory
import com.example.movieApp.viewmodel.HomeScreenViewModelFactory

object InjectorUtils {
    private fun getMovieRepository(context: Context): MovieRepository {
        return MovieRepository(MovieDatabase.getDatabase(context).movieDao())
    }

    fun provideHomeScreenViewModelFactory(context: Context): HomeScreenViewModelFactory {
        val repository = getMovieRepository(context)
        return HomeScreenViewModelFactory(repository)
    }

    fun provideFavoriteScreenViewModelFactory(context: Context): FavoriteScreenViewModelFactory {
        val repository = getMovieRepository(context)
        return FavoriteScreenViewModelFactory(repository)
    }

    fun provideDetailScreenViewModelFactory(context: Context): DetailScreenViewModelFactory {
        val repository = getMovieRepository(context)
        return DetailScreenViewModelFactory(repository)
    }

    fun provideAddMovieScreenViewModelFactory(context: Context): AddMovieScreenViewModelFactory {
        val repository = getMovieRepository(context)
        return AddMovieScreenViewModelFactory(repository)
    }
}