import kotlinx.serialization.Serializable

@Serializable
data class Article(
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?
)

@Serializable
data class NewsResponse(
    val status: String = "",
    val articles: List<Article> = emptyList(),
)