package ru.cappoeira.app.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import ru.cappoeira.app.network.models.AllSongsResponse
import ru.cappoeira.app.network.models.SongInfoByIdResponse
import ru.cappoeira.app.network.models.SongInfoBySearchTextResponse

interface SongInfoApi {

    suspend fun getSongInfoById(id: String): NetworkResult<SongInfoByIdResponse>

    suspend fun getSongsInfosByTextSearch(
        textSearch: String,
        songType: String,
        page: Int
    ): NetworkResult<SongInfoBySearchTextResponse>

    suspend fun getAllSongs(
        page: Int,
        songType: String
    ): NetworkResult<AllSongsResponse>
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
        page: Int
    ): NetworkResult<SongInfoBySearchTextResponse> {
        return safeApiCall {
            client.get("$URL/searchText/$textSearch") {
                parameter("page", page.toString())
                parameter("songType", songType)
            }.body()
        }
    }

    override suspend fun getAllSongs(
        page: Int,
        songType: String
    ): NetworkResult<AllSongsResponse> {
        return safeApiCall {
            client.get("$URL/allSongs") {
                parameter("page", page.toString())
                parameter("songType", songType)
            }.body()
        }
    }

    companion object {
        private const val URL = "https://cappoeira.ru/api"
    }
}