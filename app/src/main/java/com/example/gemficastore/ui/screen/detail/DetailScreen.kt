package com.example.gemficastore.ui.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.gemficastore.R
import com.example.gemficastore.di.Injection
import com.example.gemficastore.ui.common.UiState
import com.example.gemficastore.ui.components.ButtonDetailOutlined
import com.example.gemficastore.ui.screen.ViewModelFactory
import com.example.gemficastore.ui.theme.GemficaStoreTheme
import com.example.gemficastore.ui.theme.lato

@Composable
fun DetailScreen(
    gamesId: Long,
    navigateBack: ()-> Unit,
    viewModel : DetailViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    onPlayButtonClicked: (String) -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let {uiState->
        when(uiState) {
            is UiState.Loading -> {
                viewModel.getDetailGames(gamesId)
            }
            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    id = data.id,
                    imageUrl = data.imageUrl ,
                    title = data.title ,
                    description = data.description ,
                    price = data.price ,
                    genre = data.genre ,
                    externalLink = data.externalLink ,
                    favorite = data.favorite,
                    navigateBack = navigateBack,
                    onLikedButtonClicked = { id, state ->
                        viewModel.updatesGames(id ,state)
                    },
                    onPlayButtonClicked = onPlayButtonClicked,
                )
            }
            is UiState.Error -> {

            }
        }
    }
}

@Composable
fun DetailContent(
    id: Long,
    imageUrl: String,
    title: String,
    description: String,
    price: String,
    genre: List<String>,
    externalLink: String,
    favorite: Boolean,
    navigateBack : () -> Unit,
    onLikedButtonClicked: (id: Long, state: Boolean) -> Unit,
    onPlayButtonClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2C2C2C))
    ){
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            AsyncImage(
                model = imageUrl  ,
                contentDescription = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(258.dp)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontFamily = lato,
                    fontWeight = FontWeight(700),
                    color = Color(0xFFE8E9EB),
                )
                Text(
                    text = price,
                    fontSize = 16.sp,
                    fontFamily = lato,
                    fontWeight = FontWeight(400),
                    color = Color(0xFFE8E9EB),
                )
            }
            Text(
                text = stringResource(R.string.detail_content_genre) ,
                fontSize = 12.sp ,
                fontFamily = lato ,
                color = Color(0xFFE8E9EB) ,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp)
            ) {
                for (text in genre){
                    Text(
                        text = "#${text} " ,
                        fontSize = 12.sp ,
                        fontFamily = lato ,
                        color = Color(0xFF236BFE) ,
                        maxLines = 1 ,
                        overflow = TextOverflow.Visible ,
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                ButtonDetailOutlined(
                    onClick = { onPlayButtonClicked(externalLink) },
                    text = stringResource(R.string.button_play),
                    modifier = Modifier
                        .weight(4f)
                )
                Spacer(modifier = Modifier.size(4.dp))
                IconButton(
                    onClick = {
                        onLikedButtonClicked(id, favorite)
                    },
                    modifier = Modifier
                        .height(55.dp)
                        .weight(1f)
                        .testTag("likeButton")
                ) {
                    Icon(
                        imageVector = if(!favorite) Icons.Default.FavoriteBorder else Icons.Default.Favorite,
                        contentDescription = null ,
                        tint = if(!favorite) Color(0xFF236BFE) else Color(0xFFEF3F38),
                        modifier = Modifier
                            .size(42.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = description,
                fontSize = 16.sp,
                fontFamily = lato,
                color = Color(0xFFE8E9EB),
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp)
                .background(Color(0xFF2C2C2C))
        ) {
            Row {
                IconButton(
                    onClick = navigateBack,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent)
                        .padding(vertical = 8.dp)
                        .padding(start = 10.dp)
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                        .testTag("backButton")
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack ,
                        contentDescription = "backButton",
                        tint = Color(0xFFE8E9EB),
                        modifier = Modifier
                            .size(32.dp)
                    )
                }
                Text(
                    text = stringResource(R.string.label_detail_screen) ,
                    fontFamily = lato ,
                    fontSize = 20.sp ,
                    fontWeight = FontWeight.Bold ,
                    color = Color(0xFFE8E9EB) ,
                    modifier = Modifier
                        .weight(6f)
                        .align(Alignment.CenterVertically)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    GemficaStoreTheme {
        DetailContent(
            id = 10 ,
            imageUrl = "https://cdn.akamai.steamstatic.com/steam/apps/550/header.jpg?t=1675801903" ,
            title = "Game keren" ,
            description = "game kren bggt lah" ,
            price = "50" ,
            genre = listOf("horror", "apa yaaa") ,
            externalLink = "" ,
            favorite = true ,
            navigateBack = { /*TODO*/ } ,
            onLikedButtonClicked = { _, _ ->} ,
            onPlayButtonClicked = { /*TODO*/ })
    }
}