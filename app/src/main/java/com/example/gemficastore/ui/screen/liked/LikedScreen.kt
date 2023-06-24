package com.example.gemficastore.ui.screen.liked

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gemficastore.R
import com.example.gemficastore.di.Injection
import com.example.gemficastore.model.Games
import com.example.gemficastore.ui.common.UiState
import com.example.gemficastore.ui.components.EmptyState
import com.example.gemficastore.ui.screen.ViewModelFactory
import com.example.gemficastore.ui.screen.games.GamesList
import com.example.gemficastore.ui.theme.GemficaStoreTheme

@Composable
fun LikedScreen(
    navigateToDetail: (Long) -> Unit ,
    viewModel : LikedViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    onPlayButtonClicked: (String) -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let {uiState ->  
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getLikedGames()
            }
            is UiState.Success -> {
                LikedContent(
                    listGames = uiState.data ,
                    navigateToDetail = navigateToDetail ,
                    onLikedIconClicked = { id, newValue ->
                        viewModel.updateGames(id , newValue)
                    },
                    onPlayButtonClicked = onPlayButtonClicked
                )
            }
            is UiState.Error -> {

            }
        }
    }
}

@Composable
fun LikedContent(
    listGames : List<Games>,
    navigateToDetail: (Long) -> Unit ,
    onLikedIconClicked: (id: Long, newValue: Boolean) -> Unit,
    onPlayButtonClicked: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .background(Color(0xFF2C2C2C))
            .fillMaxSize()
    ) {
        if (listGames.isNotEmpty()) {
            GamesList(
                listGames = listGames ,
                onLikedIconClicked = onLikedIconClicked ,
                onPlayButtonClicked = onPlayButtonClicked ,
                navigateToDetail = navigateToDetail,
                Modifier.testTag("gamesItem")
            )
        }else{
            EmptyState(
                contentText = stringResource(R.string.liked_screen_empty_state)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LikedScreenPreview() {
    GemficaStoreTheme {

    }
}