package me.abhishekkumar.shopon.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import me.abhishekkumar.shopon.model.ItemModel.Companion.TABLE_NAME
import java.io.Serializable


@Entity(tableName = TABLE_NAME)
data class ItemModel(
    @PrimaryKey
    val id: Int? = 0,
    val category: String? = null,
    val description: String? = null,
    val image: String? = null,
    val stock: Int? = 0,
    val discount: Int? = 0,
    val price: Double? = 0.0,
    val title: String? = null,
    val variant: List<String>? = null
) : Serializable {
    companion object {
        const val TABLE_NAME = "ITEM_TABLE"
    }


}