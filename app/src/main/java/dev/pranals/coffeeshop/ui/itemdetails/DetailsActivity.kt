package dev.pranals.coffeeshop.ui.itemdetails

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.project1762.Helper.ManagementCart
import com.google.android.material.snackbar.Snackbar
import dev.pranals.coffeeshop.R
import dev.pranals.coffeeshop.databinding.ActivityDetailsBinding
import dev.pranals.coffeeshop.domain.model.ItemModel

private const val TAG = "DetailsActivityTag"

class DetailsActivity : AppCompatActivity() {

    private lateinit var item: ItemModel
    private lateinit var binding: ActivityDetailsBinding
    private lateinit var managementCart: ManagementCart


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        managementCart = ManagementCart(this)

        item = intent.getSerializableExtra("item") as ItemModel
        Log.d(TAG, "onCreate: item >> $item")

        initSizeList()
        clickListeners()
    }

    private fun initSizeList() {
        binding.apply {
            smallBtn.setOnClickListener {
                smallBtn.setBackgroundResource(R.drawable.brown_round_corner_bg)
                mediumBtn.setBackgroundColor(0)
                largeBtn.setBackgroundColor(0)
            }

            mediumBtn.setOnClickListener {
                mediumBtn.setBackgroundResource(R.drawable.brown_round_corner_bg)
                smallBtn.setBackgroundColor(0)
                largeBtn.setBackgroundColor(0)
            }

            largeBtn.setOnClickListener {
                largeBtn.setBackgroundResource(R.drawable.brown_round_corner_bg)
                mediumBtn.setBackgroundColor(0)
                smallBtn.setBackgroundColor(0)
            }

        }
    }

    private fun clickListeners() {
        binding.apply {

            Glide.with(this@DetailsActivity).load(item.picUrl[0]).into(ivImage)
            tvTitle.text = item.title
            tvDescription.text = item.description
            tvPrice.text = "${item.price * 50}"
            tvRating.text = item.rating.toString()

            btnBack.setOnClickListener {
                finish()
            }
            btnAddToCart.setOnClickListener {
                item.numberInCart = Integer.valueOf(tvNumQty.text.toString())
                managementCart.insertItem(item)
            }
            ivFavorite.setOnClickListener {

            }
            llSize.setOnClickListener {

            }
            tvDeQty.setOnClickListener {
                if (item.numberInCart == 1) {
                    Snackbar.make(
                        binding.root,
                        "You can't reduce more",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                --item.numberInCart;
                tvNumQty.text = item.numberInCart.toString()
            }
            tvInQty.setOnClickListener {
                ++item.numberInCart;
                tvNumQty.text = item.numberInCart.toString()

            }

        }
    }
}