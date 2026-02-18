package dev.pranals.coffeeshop.domain.model

data class PopularItemModel(
    val description: String = "",
    val extra: String = "",
    val picUrl: String = "",
    val price: Double = 0.0,
    val rating: Double = 0.0,
    val title: String = "",

)