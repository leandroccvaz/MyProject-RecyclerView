package com.example.myapplication_test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.myapplication_test.data.Item
import com.example.myapplication_test.ui.compose.ItemList

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val list = listOf (
                Item("Title 1", description = "Description 1"),
                Item("Title 2", description = "Description 2"),
                Item("Title 3", description = "Description 3")
            )

            ItemList(list)
        }
    }
}
