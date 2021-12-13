package com.confession.app.ui.qa

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun Question(
    content: String
) {
    Text(
        text = content,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp
    )
}