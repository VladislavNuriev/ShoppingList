package com.example.shoppinglist.domain

import javax.inject.Inject

class EditShopItemUseCase @Inject constructor (private val shopListRepository: ShopListRepository)   {
    suspend fun editeShopItem(shopItem: ShopItem){
        shopListRepository.editShopItem(shopItem)
    }
}