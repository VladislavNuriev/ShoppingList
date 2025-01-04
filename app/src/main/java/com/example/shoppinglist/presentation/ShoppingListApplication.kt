package com.example.shoppinglist.presentation

import android.app.Application
import com.example.shoppinglist.di.DaggerApplicationComponent

class ShoppingListApplication : Application() {

    val appComponent by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

}