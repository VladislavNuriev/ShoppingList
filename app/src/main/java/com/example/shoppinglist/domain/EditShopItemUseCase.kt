package com.example.shoppinglist.domain

class EditShopItemUseCase(private val shopListRepository: ShopListRepository)   {
    suspend fun editeShopItem(shopItem: ShopItem){
        shopListRepository.editShopItem(shopItem)
    }
}