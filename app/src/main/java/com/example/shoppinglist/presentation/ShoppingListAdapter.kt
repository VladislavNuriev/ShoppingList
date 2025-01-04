package com.example.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem

class ShoppingListAdapter :
    ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallback()) {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    companion object {
        const val VIEW_TYPE_ENABLED = 100
        const val VIEW_TYPE_DISABLED = 101
        const val MAX_POOL_SIZE = 15
    }

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ShopItemViewHolder {
        // Create a new view, which defines the UI of the list item
        val layout = when (viewType) {
            VIEW_TYPE_ENABLED -> R.layout.item_shop_enabled
            VIEW_TYPE_DISABLED -> R.layout.item_shop_disabled
            else -> throw RuntimeException("Unknown view type: $viewType")
        }
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(layout, viewGroup, false)
        return ShopItemViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(shopItemViewHolder: ShopItemViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val shopItem = getItem(position)
        with(shopItemViewHolder) {
            textViewName.text = shopItem.name
            textViewCount.text = shopItem.count.toString()
            view.setOnLongClickListener {
                onShopItemLongClickListener?.invoke(shopItem)
                true
            }
            view.setOnClickListener { onShopItemClickListener?.invoke(shopItem) }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).enabled)
            VIEW_TYPE_ENABLED
        else VIEW_TYPE_DISABLED
    }
}
