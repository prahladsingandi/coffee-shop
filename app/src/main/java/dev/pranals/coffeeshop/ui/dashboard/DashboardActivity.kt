package dev.pranals.coffeeshop.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import dev.pranals.coffeeshop.R
import dev.pranals.coffeeshop.databinding.ActivityDashboardBinding
import dev.pranals.coffeeshop.domain.model.CategoryModel
import dev.pranals.coffeeshop.domain.model.PopularItemModel
import dev.pranals.coffeeshop.ui.dashboard.adapter.PopularItemAdapter

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var viewModel: DashboardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("DashboardActivity", "onCreate called")
        enableEdgeToEdge()
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[DashboardViewModel::class.java]

        initBanner()
        initCategory()
        initPopularItems()

    }

    private fun initCategory() {
        Log.d("DashboardActivity", "initBanner called")
        binding.progressBarCategory.visibility = VISIBLE
        viewModel.loadCategory().observeForever { categories ->
            Log.d(
                "DashboardActivity",
                "banners observer triggered. Size: ${categories?.size ?: "null"}"
            )
            if (!categories.isNullOrEmpty()) {
                Log.d("DashboardActivity", "Loading image from URL: ${categories[0].title}")
                setupChips(categories)
            } else {
                Log.d("DashboardActivity", "No banners found in the database list")
            }
            binding.progressBarCategory.visibility = GONE
        }
        viewModel.loadCategory()
    }

    private fun initPopularItems() {
        Log.d("DashboardActivity", "initBanner called")
        binding.progressBarCategory.visibility = VISIBLE
        viewModel.loadPopularItems().observeForever { popularItems ->
            Log.d(
                "DashboardActivity",
                "banners observer triggered. Size: ${popularItems?.size ?: "null"}"
            )
            if (!popularItems.isNullOrEmpty()) {
                Log.d("DashboardActivity", "Loading image from URL: ${popularItems[0].title}")
                setupPopularItems(popularItems)
            } else {
                Log.d("DashboardActivity", "No banners found in the database list")
            }
            binding.progressBarPopular.visibility = GONE
        }
    }

    private fun setupPopularItems(popularItems: MutableList<PopularItemModel>) {
        binding.rvPopularItems.apply {
            layoutManager = GridLayoutManager(this@DashboardActivity, 2)
            adapter = PopularItemAdapter(popularItems, this@DashboardActivity)
        }
    }

    private fun setupChips(categories: List<CategoryModel>) {
        binding.chipGroupCategory.removeAllViews()

        categories.forEach { category ->

            val chip = Chip(this, null, R.style.MyModernChip).apply {
                text = category.title
                isCheckable = true
                isClickable = true
                setPadding(32, 22, 32, 22)
            }

            binding.chipGroupCategory.addView(chip)
        }
    }

    private fun initBanner() {
        Log.d("DashboardActivity", "initBanner called")
        binding.progressBar.visibility = VISIBLE
        viewModel.loadBanner().observe(this) { banners ->
            Log.d(
                "DashboardActivity",
                "banners observer triggered. Size: ${banners?.size ?: "null"}"
            )
            if (!banners.isNullOrEmpty()) {
                Log.d("DashboardActivity", "Loading image from URL: ${banners[0].url}")
                Glide.with(this@DashboardActivity)
                    .load(banners[0].url)
                    .into(binding.banner)
            } else {
                Log.d("DashboardActivity", "No banners found in the database list")
            }
            binding.progressBar.visibility = GONE
        }
        viewModel.loadBanner()
    }
}
