package com.example.nurkowapolskaapp.app.functions

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nurkowapolskaapp.R

@Composable
fun FirstAid() {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp)) {
        FirstAidInfo()
    }
}

@Composable
fun FirstAidInfo() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Instrukcja Pierwszej Pomocy", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(30.dp))
        Text(text = "Jakaś przykładowa instrukcja pierwszej pomocy")
        ImageFirstAid(R.drawable.placeholder_image)
    }
}

@Composable
fun ImageFirstAid(painter: Int) {
    val imageModifier = Modifier.size(200.dp)
    Image(painterResource(painter), null, modifier = imageModifier)
}

@Preview
@Composable
fun FirstAidPreview() {
    FirstAidInfo()
}