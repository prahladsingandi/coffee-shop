package dev.pranals.coffeeshop.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dev.pranals.coffeeshop.domain.model.BannerModel
import dev.pranals.coffeeshop.domain.model.CategoryModel
import dev.pranals.coffeeshop.domain.model.ItemModel
import dev.pranals.coffeeshop.domain.repository.DashboardRepository

class DashboardViewModel : ViewModel() {

    private val repository = DashboardRepository()

    // Keep this for compatibility if needed, but preferred to use the property above
    fun loadBanner(): LiveData<MutableList<BannerModel>> {
        return repository.loadBanner()
    }

    fun loadCategory(): LiveData<MutableList<CategoryModel>> {
        return repository.loadCategory()
    }

    fun loadPopularItems(): LiveData<MutableList<ItemModel>> {
        return repository.loadPopularItems()
    }

}
