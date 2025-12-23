package com.example.listdetailspractice.ui.mock

import com.example.listdetailspractice.ui.model.RepoUi

object RepoMock {
    val items: List<RepoUi> = List(30) { idx ->
        RepoUi(
            id = idx.toLong(),
            name = "awesome-repo-$idx",
            owner = if (idx % 2 == 0) "octocat" else "androiddev",
            description = "وصف الريبو رقم $idx. نص طويل شوية عشان يبان في التفاصيل.",
            language = listOf("Kotlin", "Java", "Swift", "Go").getOrNull(idx % 4),
            stars = 1200 + idx * 37,
            forks = 100 + idx * 5,
            openIssues = idx % 12,
            updatedAt = "2025-12-${(idx % 28) + 1}",
            url = "https://github.com/sample/awesome-repo-$idx",
            topics = listOf("compose", "mvvm", "navigation", "android")
                .shuffled()
                .take((idx % 4) + 1)
        )
    }
}
