package com.example.shoppinglist.data

import com.example.shoppinglist.domain.ShopItem
import javax.inject.Inject

class ShopListMapper @Inject constructor() {
    fun mapEntityToDbModel(shopItem: ShopItem) = ShopItemDbModel(shopItem.id,
        shopItem.name,
        shopItem.count,
        shopItem.enabled)

    fun mapDbModelToEntity(shopItemDbModel: ShopItemDbModel) = ShopItem(
        shopItemDbModel.name,
        shopItemDbModel.count,
        shopItemDbModel.enabled,
        shopItemDbModel.id
    )

    fun mapListDbModelToListEntity(list: List<ShopItemDbModel>) = list.map{
        mapDbModelToEntity(it)
    }
}