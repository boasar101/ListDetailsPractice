package com.example.listdetailspractice

import android.content.Context
import com.example.listdetailspractice.data.db.AppDatabase
import com.example.listdetailspractice.data.repository.FavoritesRepository

class AppContainer(context: Context) {
    private val db: AppDatabase = AppDatabase.create(context)
    val favoritesRepository: FavoritesRepository = FavoritesRepository(db.favoritesDao())
}
