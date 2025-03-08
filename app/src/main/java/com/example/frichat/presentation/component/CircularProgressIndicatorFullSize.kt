package com.example.frichat.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.frichat.ui.theme.AppTheme

@Composable
fun CircularProgressIndicatorFullSize(modifier: Modifier = Modifier) {
    val color = if (isSystemInDarkTheme()) Color(255, 255, 255, 50)
                else Color(0, 0, 0, 50)
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = color),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
    }
}

@Preview(showBackground = true)
@Composable
fun CircularProgressIndicatorFullSizePreview() {
    AppTheme {
        CircularProgressIndicatorFullSize()
    }
}