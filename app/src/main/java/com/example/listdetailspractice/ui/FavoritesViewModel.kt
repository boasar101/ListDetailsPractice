package com.example.listdetailspractice.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.listdetailspractice.data.repository.FavoritesRepository
import com.example.listdetailspractice.ui.model.RepoUi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val repo: FavoritesRepository
) : ViewModel() {

    val favorites: StateFlow<List<RepoUi>> =
        repo.favoritesFlow.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun toggleFavorite(item: RepoUi) {
        viewModelScope.launch { repo.toggle(item) }
    }
}

class FavoritesViewModelFactory(
    private val repo: FavoritesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return FavoritesViewModel(repo) as T
    }
}
