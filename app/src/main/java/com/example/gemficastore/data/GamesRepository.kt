package com.example.gemficastore.data

import com.example.gemficastore.model.FakeDataSource
import com.example.gemficastore.model.Games
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class GamesRepository {
    private val gemficaGames = mutableListOf<Games>()

    init {
        if (gemficaGames.isEmpty()){
            gemficaGames.addAll(FakeDataSource.dummyGames)
        }
    }

    fun getGamesById(id: Long): Flow<Games> {
        return flowOf(gemficaGames.first{
            it.id == id
        })
    }

    fun searchGames(query: String) = flow {
        val data = gemficaGames.filter {
            it.title.contains(query, ignoreCase = true)
        }
        emit(data)
    }

    fun getFavoriteGame(): Flow<List<Games>> {
        return flowOf(gemficaGames.filter {
            it.favorite
        })
    }

    fun updateGame(gamesId: Long, newValue: Boolean): Flow<Boolean> {
        val index = gemficaGames.indexOfFirst { it.id == gamesId }
        val result = if (index >= 0) {
            val likedGames = gemficaGames[index]
            gemficaGames[index] = likedGames.copy(favorite = newValue)
            true
        } else {
            false
        }
        return flowOf(result)
    }


    companion object{
        @Volatile
        private var instance: GamesRepository? = null

        fun getInstance(): GamesRepository =
            instance ?: synchronized(this) {
                GamesRepository().apply {
                    instance = this
                }
            }
    }
}