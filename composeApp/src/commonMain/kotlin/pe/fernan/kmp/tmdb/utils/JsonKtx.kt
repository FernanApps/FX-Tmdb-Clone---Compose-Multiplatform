package pe.fernan.kmp.tmdb.utils

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json



object JsonKtx {

    val json = Json {
        encodeDefaults = true
        isLenient = true
        prettyPrint = false
        ignoreUnknownKeys = true
    }

    inline fun <reified T> fromJson(json: String): T {
        return this.json.decodeFromString(json)
    }


    inline fun <reified T> fromJsonOrNull(json: String): T? = try {
        this.json.decodeFromString(json)
    } catch (e: Exception) {
        null
    }

    inline fun <reified T> fromJsonNormal(json: String): T {
        val _X = Json {
            encodeDefaults = true
            isLenient = true
            prettyPrint = false
        }
        return _X.decodeFromString(json)
    }

    inline fun <reified T>toJsonPretty(value: T): String = Json { prettyPrint = true }.encodeToString(value)

    inline fun <reified T> toJson(value: T): String = this.json.encodeToString(value)

}

// Extension functions
inline fun <reified T> String.toModelNormal() = JsonKtx.fromJsonNormal<T>(this)
inline fun <reified T> String.toModel() = JsonKtx.fromJson<T>(this)
inline fun <reified T> String.toModelOrNull() = JsonKtx.fromJsonOrNull<T>(this)

inline fun <reified T> T.toJson(): String = JsonKtx.toJson(this)
inline fun <reified T> T.toJsonPretty(): String = JsonKtx.toJsonPretty(this)

