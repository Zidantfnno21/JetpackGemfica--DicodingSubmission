package com.example.gemficastore.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gemficastore.R
import com.example.gemficastore.ui.theme.GemficaStoreTheme
import com.example.gemficastore.ui.theme.lato

@Composable
fun EmptyState(
    contentText: String,
    modifier : Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF2C2C2C)) ,
        contentAlignment = Alignment.Center
    ){
        Column(
            Modifier
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_help_24) ,
                contentDescription = null ,
                tint = Color.Unspecified ,
                modifier = Modifier
                    .size(150.dp)
            )
            Spacer(
                modifier = Modifier.size(2.dp)
            )
            Text(
                text = contentText ,
                textAlign = TextAlign.Center ,
                fontFamily = lato ,
                fontSize = 16.sp ,
                fontWeight = FontWeight.W500,
                color = Color(0xFF236BFE)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyStatePreview() {
    GemficaStoreTheme {
        EmptyState(contentText = "Empty Data???")
    }
}