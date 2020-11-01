import com.example.nod.common.EMPTY_STRING
import com.example.nod.data.local.models.News
import com.example.nod.data.remote.models.Article

/**
 * Function to convert the [Article] model containing remote news information data
 * into a [News] model that can be stored in the local cache.
 */
fun Article.toNews(): News {

    return News(
        author = author ?: EMPTY_STRING,
        contentUrl = url ?: EMPTY_STRING,
        description = description ?: EMPTY_STRING,
        imageUrl = urlToImage ?: EMPTY_STRING,
        publishedAt = publishedAt ?: EMPTY_STRING,
        sourceName = source?.name ?: EMPTY_STRING,
        title = title ?: EMPTY_STRING
    )
}