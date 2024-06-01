package com.example.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ActivityShopItemBinding

class ShopItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShopItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityShopItemBinding.inflate(layoutInflater)
        val view: View = binding.getRoot()
        setContentView(view)

        setContentView(R.layout.activity_shop_item)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.buttonSave.setOnClickListener {
            Log.d("ShopItemActivity", "buttonSave.setOnClickListener: ${it.toString()}")
            val intent = MainActivity.newIntent(this@ShopItemActivity)
            startActivity(intent)
            finish()
        }
    }



    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, ShopItemActivity::class.java)
        }
    }
}