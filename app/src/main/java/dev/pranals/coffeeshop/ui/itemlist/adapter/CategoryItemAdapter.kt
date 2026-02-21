package dev.pranals.coffeeshop.ui.itemlist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.pranals.coffeeshop.databinding.ViewHolderItemBinding
import dev.pranals.coffeeshop.domain.model.ItemModel

class CategoryItemAdapter
    (val list: MutableList<ItemModel>, val context: Context) :
    RecyclerView.Adapter<CategoryItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        return ViewHolder(
            ViewHolderItemBinding.inflate(
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

        if (position == list.size) {
            holder.binding.root.visibility = View.INVISIBLE
            holder.binding.root.isEnabled = false

            return
        }

        val item = list[position]
        holder.binding.apply {
            Glide.with(context).load(item.picUrl[0]).into(ivItem)
            tvTitle.text = item.title
            tvSubtitle.text = item.extra
            tvPrice.text = "$ ${item.price}"
        }
    }

    override fun getItemCount(): Int {
        return list.size + 1
    }

    class ViewHolder(val binding: ViewHolderItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}