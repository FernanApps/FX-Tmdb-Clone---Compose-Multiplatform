package pe.fernan.kmp.tmdb.ui.navigation


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import io.ktor.util.decodeBase64String
import io.ktor.util.encodeBase64
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import pe.fernan.kmp.tmdb.domain.model.Result
import pe.fernan.kmp.tmdb.utils.toJson
import pe.fernan.kmp.tmdb.utils.toModel

@Serializable
data class NavigateRoute(
    @SerialName("parentKey") val parentKey: KeyRoute,
    @SerialName("routeKey") val routeKey: KeyRoute
) {
    override fun toString(): String {
        return "NavigateRoute(parentKey=$parentKey, routeKey=$routeKey)"
    }
}

@Serializable
data class KeyRoute(
    @SerialName("key") val key: String,
    @SerialName("title") val title: String
) {
    override fun toString(): String {
        return "KeyRoute(key='$key', title='$title')"
    }
}

@Serializable
data class KeyRoutes(
    @SerialName("main") val main: KeyRoute,
    @SerialName("routes") val routes: Set<KeyRoute>
) {
    override fun toString(): String {
        return "KeyRoutes(main=$main, routes=$routes)"
    }
}

sealed class Screen(
    private val baseRoute: String,
    val argKeys: List<String> = emptyList(),
    val title: String = "",
    val icon: ImageVector,
) {
    private val separator = "/"
    private val stringFormat = "%s"

    private val keysFormat = "{$stringFormat}"
    //val keysFormat: (String) -> String = { "{$it}" }


    val routeFormat: String
        get() = if (argKeys.isEmpty()) throw Exception("The format does not exist since there are no ArgKeys") else {
            val builder = argKeys.joinToString(
                prefix = separator,
                separator = separator
            ) { stringFormat }
            baseRoute + builder
        }

    val route: String
        get() = if (argKeys.isEmpty()) baseRoute else {
            val builder = argKeys.joinToString(
                prefix = separator,
                separator = separator
            ) {
                keysFormat.format(it)
                // "%s"
                //keysFormat(it)
            }
            baseRoute + builder
        }

    fun pass(vararg values: String) = routeFormat.format(*values)


    inline fun <reified T> passObject(object1: T) = pass(
        parser(object1)
    )

    inline fun <reified T> getObject(key: String, pathMap: Map<String, String>) =
        generate<T>(pathMap[key]!!)


    inline fun <reified T> parser(model: T): String =
        model.toJson().encodeBase64()

    inline fun <reified T> generate(text: String): T = text.decodeBase64String().toModel<T>()


    data object Splash : Screen("splash", title = "Tmdb Kmp", icon = Icons.Default.Home)

    data object Home : Screen("home", title = "", icon = Icons.Default.Home)
    data object Items : Screen(
        "items",
        argKeys = listOf(ITEMS_NAVIGATE_ROUTE_ARGUMENT_KEY),
        title = "",
        icon = Icons.Default.Home
    ) {
        fun getObject(pathMap: Map<String, String>): NavigateRoute = getObject(ITEMS_NAVIGATE_ROUTE_ARGUMENT_KEY, pathMap)

    }

    data object Details : Screen(
        "details",
        argKeys = listOf(DETAILS_RESULT_ARGUMENT_KEY),
        title = "",
        icon = Icons.Default.Home
    ) {
        fun getObject(pathMap: Map<String, String>): Result = getObject(DETAILS_RESULT_ARGUMENT_KEY, pathMap)

    }


}

private const val ITEMS_NAVIGATE_ROUTE_ARGUMENT_KEY = "ITEMS_NAVIGATE_ROUTE_ARGUMENT_KEY"
private const val DETAILS_RESULT_ARGUMENT_KEY = "DETAILS_RESULT_ARGUMENT_KEY"


fun main() {
    val text = "Hello, Kotlin!"
    val text64 = text.encodeBase64()
    println(text64)
    println(text64.decodeBase64String())

}


fun String.format(vararg args: Any): String {
    val format = this
    val stringBuilder = StringBuilder()

    var currentIndex = 0
    var i = 0
    while (i < format.length) {
        if (format[i] == '%') {
            if (i + 1 < format.length) {
                val marker = format[i + 1]

                val value = args[currentIndex++]
                val cadenaFormateada = when (marker) {
                    's' -> value.toString()
                    'd' -> value.toString().toInt()
                    'f' -> value.toString().toDouble()
                    else -> ""
                }

                stringBuilder.append(cadenaFormateada)
                i += 2
            } else {
                stringBuilder.append('%')
                i++
            }
        } else {
            stringBuilder.append(format[i])
            i++
        }
    }

    return stringBuilder.toString()
}