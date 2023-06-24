package com.example.gemficastore.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gemficastore.data.GamesRepository
import com.example.gemficastore.ui.screen.detail.DetailViewModel
import com.example.gemficastore.ui.screen.games.GamesViewModel
import com.example.gemficastore.ui.screen.liked.LikedViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repository : GamesRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass : Class<T>) : T {
        if (modelClass.isAssignableFrom(GamesViewModel::class.java)){
            return GamesViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(LikedViewModel::class.java)){
            return LikedViewModel(repository) as  T
        }else if (modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return DetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}