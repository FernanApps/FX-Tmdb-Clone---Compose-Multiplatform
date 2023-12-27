package pe.fernan.kmp.tmdb.domain.model

enum class TimeWindows(val value: String) {
    DAY("day"),
    WEEK("week")
}
enum class MediaType(val value: String) {
    MOVIE("movie"),
    TV("tv"),
    /*
    TEST :(
    PERSON("person"),
    COLLECTION("collection"),
    KEYWORD("keyword"),
    COMPANY("company"),
    NETWORK("network"),

     */
}

enum class MovieListType(val value: String) {
    NOW_PLAYING("now_playing"),
    POPULAR("popular"),
    TOP_RATED("top_rated"),
    UPCOMING("upcoming")
}

enum class TVSeriesListType(val value: String) {
    AIRING_TODAY("airing_today"),
    ON_THE_AIR("on_the_air"),
    POPULAR("popular"),
    TOP_RATED("top_rated")
}