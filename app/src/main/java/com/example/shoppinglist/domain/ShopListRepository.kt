package com.example.shoppinglist.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {
    suspend fun addShopItem(shopItem: ShopItem)
    suspend fun getShopItem(shopItemId: Int): ShopItem
    suspend fun editShopItem(shopItem: ShopItem)
    fun getShopList(): LiveData<List<ShopItem>>
    suspend fun deleteShopItem(shopItem: ShopItem)
}