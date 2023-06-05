package com.example.movieApp.utils

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.work.WorkManager
import com.example.movieApp.io.ExternalStorageIO
import com.example.movieApp.io.db.MovieDatabase
import com.example.movieApp.io.db.WorldDatabase
import com.example.movieApp.io.repository.MovieRepository
import com.example.movieApp.io.repository.WorldRepository
import com.example.movieApp.viewmodel.AddMovieScreenViewModel
import com.example.movieApp.viewmodel.AddMovieScreenViewModelFactory
import com.example.movieApp.viewmodel.DetailScreenViewModelFactory
import com.example.movieApp.viewmodel.FavoriteScreenViewModelFactory
import com.example.movieApp.viewmodel.HomeScreenViewModelFactory

object InjectorUtils {
    private fun getWorldRepository(context: Context): WorldRepository {
        return WorldRepository(WorldDatabase.getDatabase(context).worldDao(), ExternalStorageIO(context))
    }

    private fun getMovieRepository(context: Context): MovieRepository {
        return MovieRepository(MovieDatabase.getDatabase(context).movieDao())
    }

    fun provideHomeScreenViewModelFactory(context: Context): HomeScreenViewModelFactory {
        val repository = getWorldRepository(context)
        return HomeScreenViewModelFactory(repository)
    }

    fun provideFavoriteScreenViewModelFactory(context: Context): FavoriteScreenViewModelFactory {
        val repository = getWorldRepository(context)
        return FavoriteScreenViewModelFactory(repository)
    }

    fun provideDetailScreenViewModelFactory(context: Context): DetailScreenViewModelFactory {
        val repository = getWorldRepository(context)
        return DetailScreenViewModelFactory(repository)
    }

    fun provideAddMovieScreenViewModelFactory(context: Context): AddMovieScreenViewModelFactory {
        val repository = getWorldRepository(context)
        val workManager = WorkManager.getInstance(context)
        return AddMovieScreenViewModelFactory(repository, workManager)
    }
}