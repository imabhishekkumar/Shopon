package me.abhishekkumar.shopon.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import me.abhishekkumar.shopon.model.ItemModel


class ItemConverter {
    @TypeConverter
    fun itemModelToString(itemModel: ItemModel?): String? = Gson().toJson(itemModel)

    @TypeConverter
    fun stringToItemModel(string: String?): ItemModel? =
        Gson().fromJson(string, ItemModel::class.java)

    @TypeConverter
    fun listToJson(value: List<String>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()
}