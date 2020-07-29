package me.abhishekkumar.shopon.data.repositiory

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import me.abhishekkumar.shopon.data.local.ItemDao
import me.abhishekkumar.shopon.data.local.ItemDatabase
import me.abhishekkumar.shopon.data.remote.api.RetrofitBuilder
import me.abhishekkumar.shopon.model.ItemModel
import javax.inject.Inject

class ItemRepo @Inject constructor(application: Application) {
    private val itemDao: ItemDao
    private var itemLiveDataList : MutableLiveData<List<ItemModel>>
    init {
        val itemDatabase = ItemDatabase.getInstance(application)
        itemDao = itemDatabase?.itemDao()!!
        itemLiveDataList = MutableLiveData()
    }

    var jobGetItemsAndStore: CompletableJob? = null
    var jobGetItemsFromDB: CompletableJob? = null

    fun getItemsAndStore() {
        jobGetItemsAndStore = Job()
        jobGetItemsAndStore?.let { theJob ->
            CoroutineScope(IO + theJob).launch {
                val items = RetrofitBuilder.apiService.getItems()
                itemDao.insertItems(items)
                withContext(Main){
                    itemLiveDataList.value = items
                }
                theJob.complete()
            }


        }
    }

    fun getItemsFromDB(): MutableLiveData<List<ItemModel>> {
        jobGetItemsFromDB = Job()
                jobGetItemsFromDB?.let { theJob ->
                    CoroutineScope(IO + theJob).launch {
                        val items = itemDao.allItems()
                        withContext(Main) {
                            itemLiveDataList.value = items
                            theJob.complete()
                        }
                    }
                }
           return itemLiveDataList
    }

    fun cancelJobs() {
        jobGetItemsAndStore?.cancel()
        jobGetItemsFromDB?.cancel()
    }

}
