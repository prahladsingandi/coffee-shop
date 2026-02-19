package dev.pranals.coffeeshop.domain.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dev.pranals.coffeeshop.domain.model.BannerModel
import dev.pranals.coffeeshop.domain.model.CategoryModel
import dev.pranals.coffeeshop.domain.model.ItemModel

class DashboardRepository {
    // Explicitly using the URL since it's missing from google-services.json
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    fun loadBanner(): LiveData<MutableList<BannerModel>> {
        val listData = MutableLiveData<MutableList<BannerModel>>()
        val ref = firebaseDatabase.getReference("Banner")

        Log.d("DashboardRepository", "Starting to load banners from: ${ref.toString()}")

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d(
                    "DashboardRepository",
                    "onDataChange: Snapshot exists? ${snapshot.exists()} | Children count: ${snapshot.childrenCount}"
                )
                val list = mutableListOf<BannerModel>()
                for (childSnapshot in snapshot.children) {
                    val item = childSnapshot.getValue(BannerModel::class.java)
                    Log.d("DashboardRepository", "Loaded item: $item")
                    item?.let { list.add(it) }
                }
                Log.d("DashboardRepository", "Total banners loaded: ${list.size}")
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

    fun loadCategory(): LiveData<MutableList<CategoryModel>> {
        val listData = MutableLiveData<MutableList<CategoryModel>>()
        val ref = firebaseDatabase.getReference("Category")

        Log.d("DashboardRepository", "Starting to load Category from: ${ref.toString()}")

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d(
                    "DashboardRepository",
                    "onDataChange: Snapshot exists? ${snapshot.exists()} | Children count: ${snapshot.childrenCount}"
                )
                val list = mutableListOf<CategoryModel>()
                for (childSnapshot in snapshot.children) {
                    val item = childSnapshot.getValue(CategoryModel::class.java)
                    Log.d("DashboardRepository", "Loaded item: $item")
                    item?.let { list.add(it) }
                }
                Log.d("DashboardRepository", "Total Categories loaded: ${list.size}")
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

    fun loadPopularItems(): LiveData<MutableList<ItemModel>> {
        val listData = MutableLiveData<MutableList<ItemModel>>()
        val ref = firebaseDatabase.getReference("Popular")

        Log.d("DashboardRepository", "Starting to load PopularItem from: ${ref.toString()}")

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
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
