package io.ashkay.picklerick.data.api

import io.ashkay.picklerick.models.CharacterModelResponse
import retrofit2.http.GET

interface RickAndMortyApi {
    @GET("character")
    suspend fun getCharacters(pageId: Int): CharacterModelResponse
}