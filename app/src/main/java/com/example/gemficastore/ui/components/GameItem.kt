package com.example.gemficastore.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.gemficastore.R
import com.example.gemficastore.ui.theme.GemficaStoreTheme
import com.example.gemficastore.ui.theme.lato

@Composable
fun GameItem(
    id : Long ,
    imageUrl : String ,
    title : String ,
    description : String ,
    favorite : Boolean ,
    onLikedButtonClicked : (id : Long , newValue : Boolean) -> Unit ,
    onPlayButtonClicked: ()-> Unit,
    modifier : Modifier = Modifier ,
) {
    Box(
        modifier = modifier
            .offset(x = 0.dp , y = 0.dp)
            .border(
                width = 2.dp ,
                color = Color(0xFF676767) ,
                shape = RoundedCornerShape(size = 20.dp)
            )
            .fillMaxWidth()
            .height(305.dp)
            .background(color = Color(0xFF2C2C2C) , shape = RoundedCornerShape(size = 20.dp))
    ) {
        Column(
            modifier = modifier
                .background(Color.Transparent)
        ) {
            AsyncImage(
                model = imageUrl ,
                contentDescription = title ,
                contentScale = ContentScale.Crop ,
                modifier = Modifier
                    .offset(x = 0.dp , y = 0.dp)
                    .fillMaxWidth()
                    .height(130.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 20.dp ,
                            topEnd = 20.dp ,
                            bottomStart = 15.dp ,
                            bottomEnd = 15.dp
                        )
                    )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Column(
                modifier = modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 8.dp , bottom = 8.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = title ,
                        fontSize = 20.sp ,
                        fontFamily = lato ,
                        maxLines = 1 ,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight(700) ,
                        color = Color(0xFFE8E9EB) ,
                    )
                    IconButton(
                        onClick = {
                            onLikedButtonClicked(id, favorite)
                        },
                        modifier = Modifier
                            .padding(end= 8.dp)
                            .size(30.dp)
                            .testTag("likeIconButton")
                    ) {
                        Icon(
                            imageVector = if(favorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = null ,
                            tint = if(!favorite) Color(0xFF236BFE) else Color(0xFFEF3F38),
                            modifier = Modifier
                                .size(32.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = description,
                    fontSize = 16.sp,
                    fontFamily = lato,
                    color = Color(0xFFE8E9EB),
                    lineHeight = 17.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(10.dp))
                ButtonCard(
                    text = stringResource(R.string.button_play),
                    onClick = onPlayButtonClicked,
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameItemPreview() {
    GemficaStoreTheme {
        GameItem(
            1 ,
            "https://images.contentstack.io/v3/assets/bltb6530b271fddd0b1/blt500bad270ce6c5ad/63e6e95f9b18a175e2431347/Val_Banner_PatchNotes_6_03_16x9.jpg" ,
            "Valorant" ,
            "Valorantadsfasdfasdfasdfasdf fdasfd fsdafasdf asdfasdf contohny akdfak;lsl;df lkasfkl;aslkj;df kdslflajf kdasjfkads aksdfjkjafaskldfj aksdjfkasdjf" ,
            false ,
            onLikedButtonClicked = { _, _ ->  },
            onPlayButtonClicked = {}
        )
    }
}