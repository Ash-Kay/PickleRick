package io.ashkay.picklerick.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import io.ashkay.picklerick.models.CharacterEntity

@Database(
    entities = [CharacterEntity::class],
    version = 1
)
abstract class CharacterDatabase : RoomDatabase() {

    internal abstract val characterDao: CharacterDao

    companion object {
        const val DATABASE_NAME = "currency_exchange_entries_db"
    }
}