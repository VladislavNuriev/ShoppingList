package com.example.shoppinglist.domain

class EditShopItemUseCase(private val shopListRepository: ShopListRepository)   {
    fun editeShopItem(shopItem: ShopItem){
        shopListRepository.editShopItem(shopItem)
    }
}