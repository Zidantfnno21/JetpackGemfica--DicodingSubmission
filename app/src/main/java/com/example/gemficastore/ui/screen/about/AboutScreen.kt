package com.example.gemficastore.ui.screen.about

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.gemficastore.R
import com.example.gemficastore.ui.theme.GemficaStoreTheme
import com.example.gemficastore.ui.theme.lato

@Composable
fun AboutScreen(
    modifier : Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center ,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2C2C2C))
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
        ) {
            AsyncImage(
                model = R.drawable.picture ,
                contentDescription = null,
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .size(260.dp)
                    .border(
                        width = 2.dp ,
                        color = Color(0xFF676767) ,
                        shape = RoundedCornerShape(size = 20.dp)
                    ),
                    contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.about_name) ,
                fontSize = 20.sp ,
                fontFamily = lato ,
                fontWeight = FontWeight(700) ,
                color = Color(0xFFE8E9EB) ,
            )
            Text(
                text = stringResource(R.string.about_email) ,
                fontSize = 16.sp ,
                fontFamily = lato ,
                fontWeight = FontWeight(400) ,
                color = Color(0xFFE8E9EB) ,
            )
        }

    }

}

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    GemficaStoreTheme {
        AboutScreen()
    }
}