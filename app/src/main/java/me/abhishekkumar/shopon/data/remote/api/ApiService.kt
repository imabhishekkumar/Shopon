package me.abhishekkumar.shopon.data.remote.api

import me.abhishekkumar.shopon.model.ItemModel
import retrofit2.http.GET

interface ApiService {

    @GET("Shopon_Dummy_data/api/items.json")
    suspend fun getItems(): List<ItemModel>

}