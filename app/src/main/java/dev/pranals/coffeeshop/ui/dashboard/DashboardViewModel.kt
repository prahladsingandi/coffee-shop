package dev.pranals.coffeeshop.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dev.pranals.coffeeshop.domain.model.BannerModel
import dev.pranals.coffeeshop.domain.model.CategoryModel
import dev.pranals.coffeeshop.domain.model.PopularItemModel
import dev.pranals.coffeeshop.domain.repository.DashboardRepository

class DashboardViewModel : ViewModel() {

    private val repository = DashboardRepository()

    // Cache the LiveData to avoid creating multiple listeners in the repository
    private val _banners = repository.loadBanner()
    val banners: LiveData<MutableList<BannerModel>> = _banners
//    val categories: LiveData<MutableList<CategoryModel>> = repository.loadCategory()


    // Keep this for compatibility if needed, but preferred to use the property above
    fun loadBanner(): LiveData<MutableList<BannerModel>> {
        return _banners
    }

    fun loadCategory(): LiveData<MutableList<CategoryModel>> {
        return repository.loadCategory()
    }

    fun loadPopularItems(): LiveData<MutableList<PopularItemModel>> {
        return repository.loadPopularItems()
    }

}
