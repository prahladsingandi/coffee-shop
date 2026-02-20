package dev.pranals.coffeeshop.ui.dashboard.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.pranals.coffeeshop.databinding.ViewHolderPopularItemBinding
import dev.pranals.coffeeshop.domain.model.ItemModel
import dev.pranals.coffeeshop.ui.itemdetails.DetailsActivity
import java.util.zip.Inflater

class PopularItemAdapter
    (val list: MutableList<ItemModel>, val context: Context) :
    RecyclerView.Adapter<PopularItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        return ViewHolder(
            ViewHolderPopularItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        val item = list[position]
        holder.binding.apply {
            Glide.with(context).load(item.picUrl[0]).into(ivItem)
            tvTitle.text = item.title
            tvSubtitle.text = item.extra
            tvPrice.text = "$ ${item.price}"

            root.setOnClickListener {
                context.startActivity(Intent(context, DetailsActivity::class.java).apply {
                    putExtra("item", item)
                })
            }

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(val binding: ViewHolderPopularItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}