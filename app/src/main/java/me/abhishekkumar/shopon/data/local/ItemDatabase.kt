package me.abhishekkumar.shopon.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import me.abhishekkumar.shopon.model.ItemModel

@Database(entities = [ItemModel::class], version = 1, exportSchema = false)
abstract class ItemDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao

    companion object {
        private val LOCK = ItemDatabase::class
        private val DATABASE_NAME = "SHOPON DATABASE"
        private var sInstance: ItemDatabase? = null

        fun getInstance(context: Context): ItemDatabase? {
            if (sInstance == null) {
                synchronized(LOCK) {
                    sInstance = Room.databaseBuilder(
                        context.applicationContext,
                        ItemDatabase::class.java, DATABASE_NAME
                    )
                        .build()
                }
            }
            return sInstance
        }
    }
}