package me.abhishekkumar.shopon.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.abhishekkumar.shopon.model.CartModel
import me.abhishekkumar.shopon.model.ItemModel

@Dao
interface ShoponDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(items: List<ItemModel>)
    @Query("SELECT * FROM ${ItemModel.TABLE_NAME}")
    fun allItems(): List<ItemModel>

    @Query("SELECT * FROM ${ItemModel.TABLE_NAME} WHERE category=='electronics'")
    fun allElectronicItems(): List<ItemModel>

    @Query("SELECT * FROM ${ItemModel.TABLE_NAME} WHERE category=='men clothing'")
    fun allMenClothingItems(): List<ItemModel>

    @Query("SELECT * FROM ${ItemModel.TABLE_NAME} WHERE category=='jewelery'")
    fun allJeweleryItems(): List<ItemModel>

    @Query("SELECT * FROM ${ItemModel.TABLE_NAME} WHERE category=='women clothing'")
    fun allWomenClothingItems(): List<ItemModel>

    @Query("DELETE FROM ${ItemModel.TABLE_NAME}")
    fun deleteAllItems()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItemToCart(item: CartModel): Long

    @Query("SELECT * FROM ${CartModel.TABLE_NAME}")
    fun allCartItems(): List<CartModel>

    @Query("DELETE FROM ${CartModel.TABLE_NAME}")
    fun deleteAllCartItems()

    @Query("SELECT * FROM ${CartModel.TABLE_NAME} WHERE item = :item")
    fun getSpecificCartItem(item: ItemModel) : CartModel

    @Query("UPDATE ${CartModel.TABLE_NAME} SET count = :count WHERE item = :item")
    fun updateCartItem(item: ItemModel, count: Int): Int

    @Query("DELETE FROM ${CartModel.TABLE_NAME} WHERE item = :item")
    fun deleteSpecificItemFromCart(item: ItemModel): Int
}