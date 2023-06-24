package com.example.gemficastore.ui.screen.games

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gemficastore.R
import com.example.gemficastore.di.Injection
import com.example.gemficastore.model.Games
import com.example.gemficastore.ui.common.UiState
import com.example.gemficastore.ui.components.EmptyState
import com.example.gemficastore.ui.components.GameItem
import com.example.gemficastore.ui.components.SearchView
import com.example.gemficastore.ui.screen.ViewModelFactory
import com.example.gemficastore.ui.theme.GemficaStoreTheme

@Composable
fun GamesScreen(
    viewModel : GamesViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ) ,
    navigateToDetail: (Long) -> Unit ,
    onPlayButtonClicked: (String) -> Unit,
) {
    val query by viewModel.query
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let {uiState ->
        when(uiState) {
            is UiState.Loading -> {
                viewModel.search(query)
            }
            is UiState.Success -> {
                GamesContent(
                    query = query,
                    onQueryChange = viewModel::search,
                    listGames = uiState.data,
                    onLikedIconClicked = {id, newValue->
                        viewModel.updatesGames(id, newValue)
                    },
                    navigateToDetail = navigateToDetail,
                    onPlayButtonClicked = onPlayButtonClicked
                )
            }
            is UiState.Error -> {
            }
        }
    }
}

@Composable
fun GamesContent(
    query: String,
    onQueryChange: (String) -> Unit,
    listGames: List<Games>,
    onLikedIconClicked: (id: Long, newValue: Boolean) -> Unit,
    onPlayButtonClicked: (String) -> Unit,
    navigateToDetail: (Long) -> Unit,
) {
    Column(
        modifier = Modifier
            .background(Color(0xFF2C2C2C))
            .fillMaxSize()
    ) {
        SearchView(
            query = query ,
            onQueryChange = onQueryChange,
            modifier = Modifier
                .padding(start = 20.dp , end = 20.dp , top = 20.dp , bottom = 8.dp)
                .fillMaxWidth()
                .testTag("search")
        )
        if (listGames.isNotEmpty()) {
            GamesList(
                listGames = listGames,
                onLikedIconClicked = onLikedIconClicked ,
                navigateToDetail = navigateToDetail,
                onPlayButtonClicked = onPlayButtonClicked,
                modifier = Modifier
                    .testTag("gamesItem")
            )
        }else{
            EmptyState(
                contentText = stringResource(R.string.empty_state_data),
                Modifier.testTag("emptyState")
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GamesList(
    listGames: List<Games> ,
    onLikedIconClicked: (id: Long, newValue : Boolean) -> Unit ,
    onPlayButtonClicked: (String) -> Unit,
    navigateToDetail: (Long) -> Unit ,
    modifier: Modifier = Modifier ,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 390.dp),
        contentPadding = PaddingValues(20.dp) ,
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier
    ){
        items(listGames, key = {it.id}) {data->
            GameItem(
                id = data.id ,
                imageUrl =  data.imageUrl,
                title = data.title ,
                description = data.description ,
                favorite = data.favorite ,
                onLikedButtonClicked = onLikedIconClicked ,
                onPlayButtonClicked = { onPlayButtonClicked(data.externalLink)  } ,
                modifier = Modifier
                    .animateItemPlacement(tween(durationMillis = 200))
                    .clickable { navigateToDetail(data.id) },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GamesScreenPreview() {
    GemficaStoreTheme {
        GamesContent(
            query = "" ,
            onQueryChange = {} ,
            listGames = emptyList() ,
            onLikedIconClicked = { _, _ ->  } ,
            navigateToDetail = {},
            onPlayButtonClicked = {}
        )
    }
}