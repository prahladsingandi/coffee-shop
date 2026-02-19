package dev.pranals.coffeeshop.ui.itemlist

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dev.pranals.coffeeshop.R
import dev.pranals.coffeeshop.databinding.ActivityItemListBinding
import dev.pranals.coffeeshop.ui.itemlist.adapter.CategoryItemAdapter

class ItemListActivity : AppCompatActivity() {
    private var categoryId: Int = -1
    private var viewModel: ItemListViewModel = ItemListViewModel()
    private lateinit var binding: ActivityItemListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityItemListBinding.inflate(layoutInflater)
        setContentView(binding.root)
//         binding.progressBar.visibility = VISIBLE
        getBundleData()
        binding.toolbar.btnBack.setOnClickListener { finish() }
    }

    private fun getBundleData() {
        val bundle = intent.extras
        bundle?.let {
            val title = it.getString("title")
            categoryId = it.getInt("id")
            binding.toolbar.tvTitle.text = title
            viewModel.loadCategoryItems(categoryId.toString()).observe(this) { items ->
                binding.listView.layoutManager = GridLayoutManager(this, 2)
                binding.listView.adapter = CategoryItemAdapter(items, this)
                binding.progressBar.visibility = android.view.View.GONE
            }
        }
    }
}