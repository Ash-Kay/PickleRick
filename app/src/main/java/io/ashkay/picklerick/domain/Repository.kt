package io.ashkay.picklerick.domain

import io.ashkay.picklerick.models.CharacterModelResponse

interface Repository {
    suspend fun getCharacters(page: Int, name: String?): CharacterModelResponse
}