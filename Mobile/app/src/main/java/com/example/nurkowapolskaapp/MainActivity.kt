package com.example.nurkowapolskaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.nurkowapolskaapp.ui.theme.NurkowaPolskaAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NurkowaPolskaAppTheme() {
                HelloWorld()
            }
        }
    }
}

@Composable
fun HelloWorld() {
    Text(text = "Hello World")
}

@Preview
@Composable
fun HelloWorldPreview() {
    HelloWorld()
}