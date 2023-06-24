@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.gemficastore.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gemficastore.R

@Composable
fun SearchView(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier : Modifier = Modifier
) {
    TextField(
        value = query ,
        onValueChange = onQueryChange ,
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = "Search Container",
                Modifier.size(32.dp),
                Color(0xFFFFFFFF)
            )
        } ,
        singleLine = true ,
        shape = RoundedCornerShape(size = 15.dp) ,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color(0xFF515151),
            disabledIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            textColor = Color.White,
        ) ,
        placeholder = {
                      Text(stringResource(R.string.search_hint),
                      color = Color(0xFF999999))
        } ,
        modifier = modifier
            .width(387.dp)
            .height(55.dp)
            .size(16.dp)
    )
}

@Preview
@Composable
fun SearchViewPreview() {
    SearchView(query = "aku" , onQueryChange = {})
}