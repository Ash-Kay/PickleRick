package io.ashkay.picklerick.data

import io.ashkay.picklerick.data.api.RickAndMortyApi
import io.ashkay.picklerick.domain.Repository
import io.ashkay.picklerick.models.CharacterModelResponse
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val apiService: RickAndMortyApi) : Repository {

    override suspend fun getCharacters(page: Int, name: String?): CharacterModelResponse {
        return apiService.getCharacters(page, name)
    }
}