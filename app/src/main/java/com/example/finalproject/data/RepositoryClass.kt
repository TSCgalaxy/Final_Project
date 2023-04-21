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
    override suspend fun insertInventory(conversion: InventoryEntity)
            = conversionDao.addToInventory(conversion)
    override fun getAllItem(): Flow<List<ItemEntity>>
            = conversionDao.getAllItem()
    override fun getAllInventory(conversion: CharacterEntity)
            = conversionDao.getAllFromInventory()
    override suspend fun removeCharacter(player: CharacterEntity)
            = conversionDao.removeCharacter(player)
    override suspend fun removeItem(itemEntity: ItemEntity)
            = conversionDao.removeItem(itemEntity)
    override suspend fun removeInventory(inventoryEntity: InventoryEntity)
            = conversionDao.removeInventory(inventoryEntity)
    override fun updateNPC(player: CharacterEntity)
            = conversionDao.updateNPC(player)
    override fun updateInvetory(inventoryEntity: InventoryEntity)
            = conversionDao.updateInventory(inventoryEntity)
    override fun getCharacter(userId: Int): Flow<CharacterEntity>?
            = conversionDao.NPCbyID(userId)

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