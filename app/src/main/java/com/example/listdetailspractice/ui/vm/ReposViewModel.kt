package com.example.listdetailspractice.ui.vm

import androidx.lifecycle.ViewModel
import com.example.listdetailspractice.ui.mock.RepoMock
import com.example.listdetailspractice.ui.model.RepoUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ReposViewModel : ViewModel() {
    private val _repos = MutableStateFlow(RepoMock.items)
    val repos: StateFlow<List<RepoUi>> = _repos

    fun getById(id: Long): RepoUi? = _repos.value.firstOrNull { it.id == id }
}
