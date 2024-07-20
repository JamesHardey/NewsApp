import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@Composable
@Preview
fun App() {
    var currentScreen by remember { mutableStateOf("main") }
    var selectedCategory by remember { mutableStateOf("Top Headlines") }
    var selectedArticle by remember { mutableStateOf<Article?>(null) }

    MaterialTheme {
        when (currentScreen) {
            "main" -> MainScreen(
                onCategorySelected = { category ->
                    selectedCategory = category
                },
                onArticleSelected = { article ->
                    selectedArticle = article
                    currentScreen = "detail"
                },
                selectedCategory = selectedCategory
            )
            "detail" -> DetailScreen(
                article = selectedArticle,
                onBackPressed = {
                    currentScreen = "main"
                }
            )
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "News App") {
        App()
    }
}