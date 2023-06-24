package com.example.gemficastore.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gemficastore.data.GamesRepository
import com.example.gemficastore.model.Games
import com.example.gemficastore.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class DetailViewModel(private val gamesRepository : GamesRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<Games>> = MutableStateFlow(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun getDetailGames(id: Long) = viewModelScope.launch {
        gamesRepository.getGamesById(id)
            .catch {
                _uiState.value = UiState.Error(it.message.toString())
            }
            .collect{
                _uiState.value = UiState.Success(it)
            }
    }

    fun updatesGames(id: Long, newValue : Boolean) = viewModelScope.launch {
        gamesRepository.updateGame(id, !newValue)
            .collect{isUpdated->
                if(isUpdated) getDetailGames(id)
            }
    }
}