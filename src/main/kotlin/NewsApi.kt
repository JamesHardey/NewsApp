import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class NewsApi {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    private val apiKey = "3b37cf06066c4f979c782234a2578487"

    suspend fun getNews(category: String): List<Article> {

        val source = if(category.equals("Top Headlines")){
            ""
        }else category

        val response: NewsResponse = client.get("https://newsapi.org/v2/top-headlines") {
            parameter("country", "us")
            parameter("apiKey", apiKey)
            if(source.isNotEmpty()) parameter("category", source.lowercase())
        }.body()

//        return NewsResponse().articles
        return response.articles
    }
}