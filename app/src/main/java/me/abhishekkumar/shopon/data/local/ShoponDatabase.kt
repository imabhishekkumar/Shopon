package me.abhishekkumar.shopon.data.local

import android.content.Context
import androidx.room.*
import me.abhishekkumar.shopon.model.CartModel
import me.abhishekkumar.shopon.model.ItemModel

@Database(entities = [ItemModel::class, CartModel::class], version = 1, exportSchema = false)
@TypeConverters(ItemConverter::class)
abstract class ShoponDatabase : RoomDatabase() {
    abstract fun shoponDao(): ShoponDao

    companion object {
        private val LOCK = ShoponDatabase::class
        private val DATABASE_NAME = "SHOPON DATABASE"
        private var sInstance: ShoponDatabase? = null

        fun getInstance(context: Context): ShoponDatabase? {
            if (sInstance == null) {
                synchronized(LOCK) {
                    sInstance = Room.databaseBuilder(
                        context.applicationContext,
                        ShoponDatabase::class.java, DATABASE_NAME
                    )
                        .build()
                }
            }
            return sInstance
        }
    }
}