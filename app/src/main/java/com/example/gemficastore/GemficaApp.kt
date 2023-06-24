@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.gemficastore

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gemficastore.R.*
import com.example.gemficastore.ui.navigation.NavigationItem
import com.example.gemficastore.ui.navigation.Screen
import com.example.gemficastore.ui.screen.about.AboutScreen
import com.example.gemficastore.ui.screen.detail.DetailScreen
import com.example.gemficastore.ui.screen.games.GamesScreen
import com.example.gemficastore.ui.screen.liked.LikedScreen
import com.example.gemficastore.ui.theme.GemficaStoreTheme
import com.example.gemficastore.ui.theme.black


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GemficaApp(
    modifier : Modifier = Modifier,
    navController : NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.Detail.route) {
                BottomBar(navController)
            }
        },
        modifier = modifier
    ) {innerPadding->
        NavHost(
            navController = navController ,
            startDestination = Screen.Games.route ,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Games.route) {
                val context = LocalContext.current
                GamesScreen(
                    navigateToDetail = { gamesId ->
                        navController.navigate(Screen.Detail.createRoute(gamesId))
                    },
                    onPlayButtonClicked = {externalLink->
                        playGame(context, externalLink)
                    }

                )
            }
            composable(
                Screen.Detail.route,
                arguments = listOf(
                    navArgument("gamesId") { type= NavType.LongType},
                )
            ) {
                val context = LocalContext.current
                val id = it.arguments?.getLong("gamesId") ?: -1L
                DetailScreen(
                    gamesId = id ,
                    navigateBack = {
                        navController.navigateUp()
                    },
                    onPlayButtonClicked = {externalLink->
                        playGame(context, externalLink)
                    }
                )
            }
            composable(Screen.Liked.route) {
                val context = LocalContext.current
                LikedScreen(
                    navigateToDetail = {gamesId->
                        navController.navigate(Screen.Detail.createRoute(gamesId))
                    },
                    onPlayButtonClicked = {externalLink->
                        playGame(context, externalLink)
                    }
                )
            }
            composable(Screen.About.route) {
                AboutScreen()
            }
        }
    }
}

private fun playGame(
    context : Context,
    externalLink: String
) {
    val webpage: Uri = Uri.parse(externalLink)
    val intent = Intent(Intent.ACTION_VIEW, webpage)

    context.startActivity(
        intent
    )
}

@Composable
private fun BottomBar(
    navController: NavHostController ,
    modifier : Modifier = Modifier
) {
    NavigationBar(
        containerColor = black ,
        modifier = modifier
            .drawBehind {
                val borderSize = 4.dp.toPx()
                drawLine(
                    Color(0xFF676767),
                    Offset(size.height, size.width),
                    Offset(size.width, 0f),
                    borderSize
                )
            }
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItem = listOf(
            NavigationItem(
                title = stringResource(string.menu_games) ,
                icon = Icons.Default.List ,
                screen = Screen.Games
            ),
            NavigationItem(
                title = stringResource(string.menu_liked),
                icon = Icons.Default.FavoriteBorder,
                screen = Screen.Liked
            ),
            NavigationItem(
                title = stringResource(string.menu_about),
                icon = Icons.Default.AccountCircle,
                screen = Screen.About
            )
        )
        navigationItem.map {item->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon ,
                        contentDescription = item.title
                    )
                    modifier.size(32.dp)
                },
                label = {
                    Text(text = item.title)
                },
                alwaysShowLabel = false,
                selected = currentRoute == item.screen.route ,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color(0xFF676767) ,
                    selectedIconColor = Color.White ,
                    unselectedIconColor = Color.Gray,
                    selectedTextColor = Color.White
                ),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    GemficaStoreTheme {
        GemficaApp()
    }
}