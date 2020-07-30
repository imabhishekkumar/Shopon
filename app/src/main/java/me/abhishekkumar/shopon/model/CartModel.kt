package me.abhishekkumar.shopon.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import me.abhishekkumar.shopon.model.CartModel.Companion.TABLE_NAME
import java.io.Serializable


@Entity(tableName = TABLE_NAME)
data class CartModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val item: ItemModel,
    val count: Int  = 0
) : Serializable {
    companion object {
        const val TABLE_NAME = "CART_TABLE"
    }
}