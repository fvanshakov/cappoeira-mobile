package ru.cappoeira.app.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import ru.cappoeira.app.network.models.AllSongsResponse
import ru.cappoeira.app.network.models.SongInfoByIdResponse
import ru.cappoeira.app.network.models.SongInfoBySearchTextResponse
import ru.cappoeira.app.network.models.SongTagsResponse

interface SongInfoApi {

    suspend fun getSongInfoById(id: String): NetworkResult<SongInfoByIdResponse>

    suspend fun getSongsInfosByTextSearch(
        textSearch: String,
        songType: String,
        page: Int,
        tags: String
    ): NetworkResult<SongInfoBySearchTextResponse>

    suspend fun getAllSongs(
        page: Int,
        songType: String,
        tags: String
    ): NetworkResult<AllSongsResponse>

    suspend fun getAllTags(
        filterType: String
    ): NetworkResult<SongTagsResponse>
}

open class SongInfoApiImpl(
    private val client: HttpClient = KtorClientProvider.client
): SongInfoApi {

    override suspend fun getSongInfoById(id: String): NetworkResult<SongInfoByIdResponse> {
        return safeApiCall {
            client.get("$URL/id/$id").body()
        }
    }

    override suspend fun getSongsInfosByTextSearch(
        textSearch: String,
        songType: String,
        page: Int,
        tags: String
    ): NetworkResult<SongInfoBySearchTextResponse> {
        return safeApiCall {
            client.get("$URL/searchText/$textSearch") {
                parameter("page", page.toString())
                parameter("songType", songType)
                parameter("tags", tags)
            }.body()
        }
    }

    override suspend fun getAllSongs(
        page: Int,
        songType: String,
        tags: String
    ): NetworkResult<AllSongsResponse> {
        return safeApiCall {
            client.get("$URL/allSongs") {
                parameter("page", page.toString())
                parameter("songType", songType)
                parameter("tags", tags)
            }.body()
        }
    }

    override suspend fun getAllTags(filterType: String): NetworkResult<SongTagsResponse> {
        return safeApiCall {
            client.get("$URL/tags") {
                parameter("filterType", filterType)
            }.body()
        }
    }

    companion object {
        private const val URL = "https://cappoeira.ru/api"
    }
}