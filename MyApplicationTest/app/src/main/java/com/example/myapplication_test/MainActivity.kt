package com.example.myapplication_test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import com.example.myapplication_test.presentation.composable.ItemListScreen

// Just launches the screen — all the real logic lives in the ViewModel
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                ItemListScreen()
            }
        }
    }
}
