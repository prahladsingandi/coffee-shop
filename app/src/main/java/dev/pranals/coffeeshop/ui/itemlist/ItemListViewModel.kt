package dev.pranals.coffeeshop.ui.itemlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.pranals.coffeeshop.domain.model.ItemModel
import dev.pranals.coffeeshop.domain.repository.ItemListRepository

class ItemListViewModel : ViewModel() {

    val repository = ItemListRepository()
    private val _items = MutableLiveData<List<ItemModel>>()
    val items: LiveData<List<ItemModel>> = _items

    fun loadCategoryItems(categoryId: String): LiveData<MutableList<ItemModel>> {
        return repository.loadCategoryItems(categoryId)
    }

}