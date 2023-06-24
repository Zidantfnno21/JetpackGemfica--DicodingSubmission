package com.example.gemficastore.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gemficastore.ui.theme.GemficaStoreTheme
import com.example.gemficastore.ui.theme.lato

@Composable
fun ButtonCard(
    text : String ,
    modifier : Modifier = Modifier ,
    enabled : Boolean = true ,
    onClick : () -> Unit ,
) {
    OutlinedButton(
        onClick = onClick ,
        enabled = enabled ,
        shape = RoundedCornerShape(15.dp) ,
        border = BorderStroke(2.dp , color = Color(0xFF236BFE)) ,
        modifier = modifier
            .fillMaxWidth()
            .height(42.dp)
    ) {
        Text(
            text = text ,
            color = Color(0xFF236BFE) ,
            fontFamily = lato ,
            fontSize = 14.sp ,
            fontWeight = FontWeight(700) ,
            textAlign = TextAlign.Center ,
            modifier = modifier
                .align(Alignment.CenterVertically)
        )
    }
}

@Preview
@Composable
fun ButtonCardPreview() {
    GemficaStoreTheme {
        ButtonCard(
            text = "- Hapus Favorite" ,
            onClick = {})
    }
}

