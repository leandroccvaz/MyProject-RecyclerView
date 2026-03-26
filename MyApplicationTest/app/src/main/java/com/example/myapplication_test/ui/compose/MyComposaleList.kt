package com.example.myapplication_test.ui.compose
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication_test.data.Item
@Composable
fun ItemList(items: List<Item>) {
    val context = LocalContext.current
    LazyColumn(
        Modifier.padding(vertical = 16.dp)
    ) {
        items(items) { myItem ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        Toast.makeText(
                            context,
                            "You've just clicked here: ${myItem.title}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = myItem.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = myItem.description,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            HorizontalDivider()
        }
    }
}
@Preview
@Composable
fun MyPreviewScreen() {
    val items: List<Item> = listOf(
        Item(title = "Title", description = "Description")
    )
    ItemList(items)
}