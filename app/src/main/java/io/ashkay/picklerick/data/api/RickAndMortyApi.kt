package io.ashkay.picklerick.data.api

import io.ashkay.picklerick.models.CharacterModelResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyApi {
    @GET("character")
    suspend fun getCharacters(
        @Query("page") pageId: Int,
        @Query("name") name: String?,
    ): CharacterModelResponse
}