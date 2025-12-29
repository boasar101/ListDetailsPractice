package com.example.listdetailspractice.data.repository

import com.example.listdetailspractice.data.db.FavoriteRepoEntity
import com.example.listdetailspractice.data.db.FavoritesDao
import com.example.listdetailspractice.ui.model.RepoUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoritesRepository(
    private val dao: FavoritesDao
) {
    val favoritesFlow: Flow<List<RepoUi>> =
        dao.observeAll().map { list -> list.map { it.toUi() } }

    suspend fun toggle(repo: RepoUi) {
        if (dao.isFavorite(repo.id)) dao.deleteById(repo.id)
        else dao.upsert(repo.toEntity())
    }

    suspend fun isFavorite(id: Long): Boolean = dao.isFavorite(id)

    private fun FavoriteRepoEntity.toUi(): RepoUi = RepoUi(
        id = id,
        name = name,
        owner = owner,
        description = description ?: "No description",
        language = language,
        stars = stars,
        forks = forks,
        openIssues = openIssues,
        updatedAt = updatedAt,
        url = url,
        topics = emptyList()
    )

    private fun RepoUi.toEntity(): FavoriteRepoEntity = FavoriteRepoEntity(
        id = id,
        name = name,
        owner = owner,
        description = description,
        language = language,
        stars = stars,
        forks = forks,
        openIssues = openIssues,
        updatedAt = updatedAt,
        url = url
    )
}
