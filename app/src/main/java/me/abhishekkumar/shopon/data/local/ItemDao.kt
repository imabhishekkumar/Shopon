package me.abhishekkumar.shopon.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.abhishekkumar.shopon.model.ItemModel

@Dao
interface ItemDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(items: List<ItemModel>)

    @Query("SELECT * FROM ${ItemModel.TABLE_NAME}")
    fun allItems(): List<ItemModel>

    @Query("DELETE FROM ${ItemModel.TABLE_NAME}")
    fun deleteAllItems()

}