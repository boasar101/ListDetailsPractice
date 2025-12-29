package com.example.listdetailspractice.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listdetailspractice.data.repository.ReposRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReposViewModel(
    private val repository: ReposRepository = ReposRepository()
) : ViewModel() {

    private val _state = MutableStateFlow<ReposUiState>(ReposUiState.Loading)
    val state: StateFlow<ReposUiState> = _state

    fun load(owner: String) {
        viewModelScope.launch {
            _state.value = ReposUiState.Loading
            try {
                val repos = repository.loadRepos(owner)
                _state.value = ReposUiState.Content(repos)
            } catch (e: Exception) {
                _state.value = ReposUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun getById(id: Long) =
        (state.value as? ReposUiState.Content)
            ?.items
            ?.firstOrNull { it.id == id }
}
