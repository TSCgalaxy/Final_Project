package com.example.finalproject.data

import kotlinx.coroutines.flow.Flow

class RepositoryClass ( val conversionDao: CharacterDao
): KotlinRepositoryInterface {
    override fun getAllCharacters(): Flow<List<CharacterEntity>>
            = conversionDao.getAllCharacters()
    override suspend fun insertCharacter(conversion: CharacterEntity)
            = conversionDao.addCharacter(conversion)
    override suspend fun insertItem(conversion: ItemEntity)
            = conversionDao.addItem(conversion)
    override suspend fun insertInventory(conversion: CharacterEntity, itemEntity: ItemEntity)
            = conversionDao.addtoInventory(conversion,itemEntity)

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