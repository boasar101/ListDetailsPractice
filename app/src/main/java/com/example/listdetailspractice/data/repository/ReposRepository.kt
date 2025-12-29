package com.example.listdetailspractice.data.repository

import com.example.listdetailspractice.data.api.NetworkModule
import com.example.listdetailspractice.ui.model.RepoUi

class ReposRepository {

    suspend fun loadRepos(owner: String): List<RepoUi> {
        val dtos = NetworkModule.githubApi.getRepos(owner = owner)

        return dtos.map {
            RepoUi(
                id = it.id,
                name = it.name,
                owner = owner,
                description = it.description ?: "No description",
                language = it.language,
                stars = it.stargazersCount,
                forks = it.forksCount,
                openIssues = it.openIssuesCount,
                updatedAt = it.updatedAt,
                url = it.htmlUrl,
                topics = it.topics ?: emptyList()
            )
        }
    }
}
