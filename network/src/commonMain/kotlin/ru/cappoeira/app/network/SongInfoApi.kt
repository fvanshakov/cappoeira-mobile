package ru.cappoeira.app.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import ru.cappoeira.app.network.models.SongInfoByIdResponse
import ru.cappoeira.app.network.models.SongInfoBySearchTextResponse

interface SongInfoApi {

    suspend fun getSongInfoById(id: String): NetworkResult<SongInfoByIdResponse>

    suspend fun getSongsInfosByTextSearch(textSearch: String, page: Int): NetworkResult<SongInfoBySearchTextResponse>
}

class SongInfoApiImpl(
    private val client: HttpClient = KtorClientProvider.client
): SongInfoApi {

    override suspend fun getSongInfoById(id: String): NetworkResult<SongInfoByIdResponse> {
        return safeApiCall {
            client.get("$URL/id/$id").body()
        }
    }

    override suspend fun getSongsInfosByTextSearch(textSearch: String,  page: Int): NetworkResult<SongInfoBySearchTextResponse> {
        return safeApiCall {
            client.get("$URL/searchText/$textSearch") {
                parameter("page", page.toString())
            }.body()
        }
    }

    companion object {
        private const val URL = "https://cappoeira.ru/api"
    }
}