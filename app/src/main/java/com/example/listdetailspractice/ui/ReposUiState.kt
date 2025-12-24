package com.example.listdetailspractice.ui.vm

import com.example.listdetailspractice.ui.model.RepoUi

sealed interface ReposUiState {
    data object Loading : ReposUiState
    data class Content(val items: List<RepoUi>) : ReposUiState
    data class Error(val message: String) : ReposUiState
}
