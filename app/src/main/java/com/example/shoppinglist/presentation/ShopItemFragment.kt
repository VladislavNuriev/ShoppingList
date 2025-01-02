package com.example.shoppinglist.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.databinding.FragmentShopItemBinding
import com.example.shoppinglist.domain.ShopItem
import javax.inject.Inject

class ShopItemFragment : Fragment(){


    private var _binding: FragmentShopItemBinding? = null
    private val binding
        get() = _binding!!


    @Inject
    lateinit var viewmodelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewmodelFactory)[ShopItemViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as ShoppingListApplication).appComponent
    }

    private lateinit var onEditingFinished: OnEditingFinishedListener

    private var screenMode = MODE_UNKNOWN
    private var shopItemId = ShopItem.UNDEFINED_ID

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
        if (context is OnEditingFinishedListener){
            onEditingFinished = context
        } else {
            throw RuntimeException("OnEditingFinishedListener is not implemented in ${context.toString()}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //return inflater.inflate(R.layout.fragment_shop_item, container,false)
        _binding = FragmentShopItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        addTextChangeListeners()
        launchRightMode()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = args.getString(SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Param screen mode is undefined: $mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(SHOP_ITEM_ID)) {
                throw RuntimeException("Param item id is absent")
            }
            shopItemId = args.getInt(SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }
    }

    private fun launchRightMode() {
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun launchEditMode() {
        Log.d("ItemIdParam", "ShopItemFragment: launchEditMode(): shopItemId: $shopItemId")
        viewModel.getShopItem(shopItemId)
        viewModel.shopItem.observe(viewLifecycleOwner) {
            binding.editTextName.setText(it.name)
            binding.editTextCount.setText(it.count.toString())
        }
        binding.buttonSave.setOnClickListener {
            viewModel.editShopItem(
                binding.editTextName.text.toString(),
                binding.editTextCount.text.toString()
            )
        }
    }

    private fun launchAddMode() {
        binding.buttonSave.setOnClickListener {
            viewModel.addShopItem(
                binding.editTextName.text.toString(),
                binding.editTextCount.text.toString()
            )
        }
    }

    private fun observeViewModel() {
        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            onEditingFinished.onEditingFinish()
        }
        viewModel.errorInputName.observe(viewLifecycleOwner) {
            if (it) {
                binding.textInputName.error = "Insert item"
            }
        }
        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            if (it) {
                binding.textInputCount.error = "Must be > 0"
            }
        }
    }

    private fun addTextChangeListeners() {
        binding.editTextName.doOnTextChanged { text, start, before, count ->
            binding.textInputName.error = null
            viewModel.resetErrorInputName()
        }
        binding.editTextCount.doOnTextChanged { text, start, before, count ->
            binding.textInputCount.error = null
            viewModel.resetErrorInputCount()
        }
    }
    interface OnEditingFinishedListener{
        fun onEditingFinish()
    }

    companion object {
        private const val SCREEN_MODE = "extra_mode"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_UNKNOWN = ""

        fun newInstanceAddItem(): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEditMode(shopItemId: Int): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putInt(SHOP_ITEM_ID, shopItemId)
                }
            }
        }

    }
}