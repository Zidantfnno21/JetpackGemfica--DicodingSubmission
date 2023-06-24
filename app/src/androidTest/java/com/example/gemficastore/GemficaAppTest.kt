package com.example.gemficastore

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.gemficastore.model.FakeDataSource
import com.example.gemficastore.ui.navigation.Screen
import com.example.gemficastore.ui.theme.GemficaStoreTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GemficaAppTest {
    @get: Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        composeTestRule.setContent {
            GemficaStoreTheme {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                GemficaApp(navController = navController)
            }
        }
    }

    @Test
    fun checkBottomNavigationStartDestination() {
        navController.assertCurrentRouteName(Screen.Games.route)
    }

    @Test
    fun checkBottomNavigationWorks() {
        composeTestRule.onNodeWithStringId(R.string.menu_about).performClick()
        navController.assertCurrentRouteName(Screen.About.route)
        composeTestRule.onNodeWithStringId(R.string.menu_liked).performClick()
        navController.assertCurrentRouteName(Screen.Liked.route)
        composeTestRule.onNodeWithStringId(R.string.menu_games).performClick()
        navController.assertCurrentRouteName(Screen.Games.route)
    }

    @Test
    fun verifyEmptyGamesList() {
        composeTestRule.onNodeWithStringId(R.string.menu_liked).performClick()
        navController.assertCurrentRouteName(Screen.Liked.route)
        composeTestRule.onNodeWithText("Liked Games Empty :(").assertIsDisplayed()
    }

    @Test
    fun verifySuccessGamesList() {
        navController.assertCurrentRouteName(Screen.Games.route)
        composeTestRule.onNodeWithTag("gamesItem").assertIsDisplayed()
    }

    @Test
    fun search_successGamesListEmpty() {
        composeTestRule.onNodeWithTag("search").assertIsDisplayed()
        composeTestRule.onNodeWithTag("search").performClick().performTextInput("Dicoding")
        composeTestRule.onNodeWithTag("emptyState").assertIsDisplayed()
    }

    @Test
    fun search_successGamesListNotEmpty() {
        composeTestRule.onNodeWithTag("search").assertIsDisplayed()
        composeTestRule.onNodeWithTag("search").performClick().performTextInput("valorant")
        composeTestRule.onNodeWithTag("gamesItem").assertIsDisplayed()
    }

    @Test
    fun successNavigateToDetailScreen() {
        composeTestRule.onNodeWithTag("gamesItem").performScrollToIndex(8)
        composeTestRule.onNodeWithText(FakeDataSource.dummyGames[8].title).performClick()
        navController.assertCurrentRouteName(Screen.Detail.route)
        composeTestRule.onNodeWithText(FakeDataSource.dummyGames[8].title).assertIsDisplayed()
    }

    @Test
    fun successNavigateToWrongDetailScreen() {
        composeTestRule.onNodeWithTag("gamesItem").performScrollToIndex(5)
        composeTestRule.onNodeWithText(FakeDataSource.dummyGames[5].title).performClick()
        navController.assertCurrentRouteName(Screen.Detail.route)
        composeTestRule.onNodeWithText(FakeDataSource.dummyGames[6].title).assertDoesNotExist()
    }

    @Test
    fun onUnlikedPressedShouldShownInLikedScreen() {
        composeTestRule.onNodeWithTag("gamesItem").performScrollToIndex(5)
        composeTestRule.onNodeWithText(FakeDataSource.dummyGames[5].title).performClick()
        navController.assertCurrentRouteName(Screen.Detail.route)
        composeTestRule.onNodeWithTag("likeButton").performClick()
        composeTestRule.onNodeWithTag("backButton").performClick()
        navController.assertCurrentRouteName(Screen.Games.route)
        composeTestRule.onNodeWithStringId(R.string.menu_liked).performClick()
        navController.assertCurrentRouteName(Screen.Liked.route)
        composeTestRule.onNodeWithText(FakeDataSource.dummyGames[5].title).assertIsDisplayed()
        composeTestRule.onNodeWithTag("likeIconButton").performClick()
    }

    @Test
    fun onLikedPressedShouldRemovedFromLikedScreen() {
        composeTestRule.onNodeWithTag("gamesItem").performScrollToIndex(5)
        composeTestRule.onNodeWithText(FakeDataSource.dummyGames[6].title).performClick()
        navController.assertCurrentRouteName(Screen.Detail.route)
        composeTestRule.onNodeWithTag("likeButton").performClick()
        composeTestRule.onNodeWithTag("backButton").performClick()
        navController.assertCurrentRouteName(Screen.Games.route)
        composeTestRule.onNodeWithStringId(R.string.menu_liked).performClick()
        navController.assertCurrentRouteName(Screen.Liked.route)
        composeTestRule.onNodeWithText(FakeDataSource.dummyGames[6].title).assertIsDisplayed()
        composeTestRule.onNodeWithTag("likeIconButton").performClick()
        composeTestRule.onNodeWithText("Liked Games Empty :(").assertIsDisplayed()
    }
}