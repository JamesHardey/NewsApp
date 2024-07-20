@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import java.util.concurrent.ConcurrentHashMap

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onCategorySelected: (String) -> Unit,
    onArticleSelected: (Article) -> Unit,
    selectedCategory: String
) {
    val newsApi = remember { NewsApi() }
    var articles by remember { mutableStateOf<List<Article>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()
    var drawerOpen by remember { mutableStateOf(false) }

    LaunchedEffect(selectedCategory) {
        coroutineScope.launch {
            articles = newsApi.getNews(selectedCategory)
        }
    }

    val categories = listOf(
        Category("Top Headlines", Icons.Default.Article),
        Category("Business", Icons.Default.Business),
        Category("Technology", Icons.Default.Computer),
        Category("Entertainment", Icons.Default.Movie),
        Category("Sports", Icons.Default.Sports),
        Category("Science", Icons.Default.Science),
        Category("Health", Icons.Default.HealthAndSafety)
    )

    MaterialTheme {
        Row {
            if (drawerOpen) {
                Box(
                    Modifier.width(250.dp).fillMaxHeight().background(MaterialTheme.colorScheme.surface)
                ) {
                    Column {
                        Text(
                            "Categories",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(16.dp)
                        )
                        categories.forEach { category ->
                            ListItem(
                                headlineText = { Text(category.name) },
                                leadingContent = { Icon(category.icon, contentDescription = null) },
                                modifier = Modifier.clickable {
                                    onCategorySelected(category.name)
                                    drawerOpen = false
                                }
                            )

                        }
                    }
                }
            }

            Column(Modifier.weight(1f)) {
                TopAppBar(
                    title = { Text(selectedCategory) },
                    navigationIcon = {
                        IconButton(onClick = { drawerOpen = !drawerOpen }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    items(articles, key = {it.title!!}) { article ->
                        if(!article.title.equals("[Removed]")){
                            ArticleItem(article, onArticleSelected)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ArticleItem(
    article: Article,
    onArticleSelected: (Article) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onArticleSelected(article) },
        shape = RoundedCornerShape(12.dp),
    ) {
        Row(modifier = Modifier.height(200.dp)) {
            article.urlToImage?.let { imageUrl ->
                CachedAsyncImage(
                    url = imageUrl,
                    contentDescription = "Article image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(300.dp)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp))
                )
            }

//            if(article.urlToImage == null){
//                Box(modifier = Modifier
//                    .width(300.dp)
//                    .fillMaxHeight()
//                    .clip(RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp))
//                    .background(Color.DarkGray)
//                )
//            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            ) {
                Text(
                    text = article.title ?: "",
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = article.description ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}


object ImageCache {
    private val cache = ConcurrentHashMap<String, ImageBitmap>()

    fun get(url: String): ImageBitmap? = cache[url]
    fun put(url: String, bitmap: ImageBitmap) {
        cache[url] = bitmap
    }
}

@Composable
fun CachedAsyncImage(
    url: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
) {
    var imageState by remember { mutableStateOf<ImageBitmap?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(url) {
        isLoading = true
        imageState = ImageCache.get(url) ?: withContext(Dispatchers.IO) {
            try {
                loadImageBitmap(url).also { ImageCache.put(url, it) }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
        isLoading = false
    }

    Box(modifier = modifier) {
        if (isLoading || imageState == null) {
            ShimmerAnimation()
        } else {
            Image(
                painter = remember(imageState) { BitmapPainter(imageState!!) },
                contentDescription = contentDescription,
                contentScale = contentScale,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}




@Composable
fun ShimmerAnimation() {
    val transition = rememberInfiniteTransition()
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val brush = Brush.linearGradient(
        colors = listOf(
            Color.LightGray.copy(alpha = 0.6f),
            Color.LightGray.copy(alpha = 0.2f),
            Color.LightGray.copy(alpha = 0.6f),
        ),
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, y = translateAnim.value)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush)
    )
}

data class Category(
    val name: String,
    val icon: ImageVector
)


fun loadImageBitmap(url: String): androidx.compose.ui.graphics.ImageBitmap =
    URL(url).openStream().buffered().use(::loadImageBitmap)
