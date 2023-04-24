package com.example.finalproject.data

import kotlinx.coroutines.flow.Flow

class RepositoryClass (private val conversionDao: CharacterDao
): KotlinRepositoryInterface {
    override fun getAllCharacters(): Flow<List<CharacterEntity>>
            = conversionDao.getAllCharacters()
    override suspend fun insertCharacter(conversion: CharacterEntity)
            = conversionDao.addCharacter(conversion)

    override suspend fun insertItem(item: ItemEntity) {
        conversionDao.addItem(item)
    }

    override suspend fun deleteItem(item: ItemEntity) {
        conversionDao.deleteItem(item)
    }

    override fun getAllItems(): Flow<List<ItemEntity>> {
        return conversionDao.getAllItems()
    }
    companion object {
        private var repository: KotlinRepositoryInterface? = null
        fun getRepository(conversionDatabase: CharacterDB):
                KotlinRepositoryInterface {
            if (repository == null) {
                repository = RepositoryClass(conversionDatabase.characterDAO())
            }
            return repository!!
        }
    }
}