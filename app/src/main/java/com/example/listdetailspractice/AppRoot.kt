package com.example.listdetailspractice

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.listdetailspractice.ui.screens.PlaceholderScreen
import com.example.listdetailspractice.ui.screens.RepoDetailsScreen
import com.example.listdetailspractice.ui.screens.ReposListScreen
import com.example.listdetailspractice.ui.vm.ReposUiState
import com.example.listdetailspractice.ui.vm.ReposViewModel

sealed class BottomDest(val route: String, val label: String) {
    data object Repos : BottomDest("repos_list", "Repos")
    data object Favorites : BottomDest("favorites", "Fav")
    data object Settings : BottomDest("settings", "Settings")
}

@Composable
fun AppRoot() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar {
                val currentRoute =
                    navController.currentBackStackEntryAsState().value?.destination?.route

                listOf(BottomDest.Repos, BottomDest.Favorites, BottomDest.Settings).forEach { dest ->
                    val icon = when (dest) {
                        BottomDest.Repos -> Icons.AutoMirrored.Filled.List
                        BottomDest.Favorites -> Icons.Default.Star
                        BottomDest.Settings -> Icons.Default.Settings
                    }

                    NavigationBarItem(
                        selected = currentRoute == dest.route,
                        onClick = {
                            navController.navigate(dest.route) {
                                popUpTo(BottomDest.Repos.route) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(icon, contentDescription = dest.label) },
                        label = { Text(dest.label) }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = BottomDest.Repos.route,
            modifier = Modifier.padding(padding)
        ) {

            composable(BottomDest.Repos.route) {
                val vm: ReposViewModel = viewModel()

                // غيّر owner هنا لأي username انت عايزه
                LaunchedEffect(Unit) { vm.load(owner = "google") }

                ReposListScreen(
                    state = vm.state.collectAsState().value,
                    onRetry = { vm.load(owner = "google") },
                    onRepoClick = { id -> navController.navigate("repo_details/$id") }
                )
            }

            composable(
                "repo_details/{id}",
                arguments = listOf(navArgument("id") { type = NavType.LongType })
            ) { entry ->
                val vm: ReposViewModel = viewModel()
                val id = entry.arguments?.getLong("id") ?: -1L

                RepoDetailsScreen(
                    repo = vm.getById(id),
                    onBack = { navController.popBackStack() }
                )
            }

            composable("favorites") { PlaceholderScreen("Favorites") }
            composable("settings") { PlaceholderScreen("Settings") }
        }
    }
}
