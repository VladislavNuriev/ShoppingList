package com.example.shoppinglist.domain

interface ShopListRepository {
    fun addShopItem(shopItem: ShopItem)
    fun getShopItem(shopItemId: Int): ShopItem
    fun editeShopItem(shopItem: ShopItem)
    fun getShopList(): List<ShopItem>
    fun deleteShopItem(id: Int)
}