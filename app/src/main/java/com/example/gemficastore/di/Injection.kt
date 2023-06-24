package com.example.gemficastore.di

import com.example.gemficastore.data.GamesRepository

object Injection {
    fun provideRepository(): GamesRepository {
        return GamesRepository.getInstance()
    }
}