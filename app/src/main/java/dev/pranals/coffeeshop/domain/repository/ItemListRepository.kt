package dev.pranals.coffeeshop.domain.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import dev.pranals.coffeeshop.domain.model.ItemModel

class ItemListRepository {
    val firebaseDatabase = FirebaseDatabase.getInstance()


    fun loadCategoryItems(categoryId: String): LiveData<MutableList<ItemModel>> {
        val listData = MutableLiveData<MutableList<ItemModel>>()
        val ref = firebaseDatabase.getReference("Items")
        val query: Query = ref.orderByChild("categoryId").equalTo(categoryId)

        Log.d("DashboardRepository", "Starting to load PopularItem from: $query")

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d(
                    "DashboardRepository",
                    "onDataChange: PopularItemModel Snapshot exists? ${snapshot.exists()} | Children >> $snapshot"
                )
                val list = mutableListOf<ItemModel>()
                for (childSnapshot in snapshot.children) {
                    val item = childSnapshot.getValue(ItemModel::class.java)
                    Log.d("DashboardRepository", "Loaded item: $item")
                    item?.let { list.add(it) }
                }
                Log.d("DashboardRepository", "Total popularItems loaded: ${list.size}")
                listData.postValue(list)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(
                    "DashboardRepository",
                    "Firebase Error: ${error.message} | Code: ${error.code} | Details: ${error.details}"
                )
            }
        })
        return listData
    }

}