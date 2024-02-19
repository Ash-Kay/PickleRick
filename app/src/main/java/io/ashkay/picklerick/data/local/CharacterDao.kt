package io.ashkay.picklerick.data.local;

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.ashkay.picklerick.models.CharacterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {
    @Query("SELECT * FROM character")
    fun getCurrencyExchangeRates(): Flow<List<CharacterEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertCharacters(currencyExchangeEntity: List<CharacterEntity>)

    @Query("DELETE FROM character")
    suspend fun clearAllEntries()
}