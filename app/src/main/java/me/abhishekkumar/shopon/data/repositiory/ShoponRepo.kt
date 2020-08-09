package me.abhishekkumar.shopon.data.repositiory

import android.app.Application
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import me.abhishekkumar.shopon.data.local.ShoponDao
import me.abhishekkumar.shopon.data.local.ShoponDatabase
import me.abhishekkumar.shopon.data.remote.api.RetrofitBuilder
import me.abhishekkumar.shopon.model.CartModel
import me.abhishekkumar.shopon.model.ItemModel
import javax.inject.Inject

class ShoponRepo @Inject constructor(application: Application) {
    private val shoponDao: ShoponDao
    private var itemLiveDataList: MutableLiveData<List<ItemModel>>
    private var cartItemLiveDataList: MutableLiveData<List<CartModel>>
    private var specificCartItem: MutableLiveData<CartModel>
    private var isDataLoading: MutableLiveData<Boolean>
    private var selectedType: Int

    init {
        val shoponDatabase = ShoponDatabase.getInstance(application)
        shoponDao = shoponDatabase?.shoponDao()!!
        itemLiveDataList = MutableLiveData()
        cartItemLiveDataList = MutableLiveData()
        specificCartItem = MutableLiveData()
        isDataLoading = MutableLiveData()
        selectedType = 0
    }

    private var jobGetAllItem: CompletableJob? = null
    private var jobGetItemsAndStore: CompletableJob? = null
    private var jobGetElectronicItemsFromDB: CompletableJob? = null
    private var jobGetJeweleryItemsFromDB: CompletableJob? = null
    private var jobGetMenClothingItemsFromDB: CompletableJob? = null
    private var jobGetWomenClothingItemsFromDB: CompletableJob? = null
    private var jobGetCartItems: CompletableJob? = null
    private var jobPutItemInCart: CompletableJob? = null
    private var jobGetSpecificCartItem: CompletableJob? = null
    private var jobUpdateCartItem: CompletableJob? = null
    private var jobDeleteSpecificCartItem: CompletableJob? = null
    private var jobDeleteAllCartItems: CompletableJob? = null

    fun getItemsAndStore() {
        jobGetItemsAndStore = Job()
        jobGetItemsAndStore?.let { theJob ->
            isDataLoading.value = true
            CoroutineScope(IO + theJob).launch {
                val items = RetrofitBuilder.apiService.getItems()
                shoponDao.insertItems(items)
                withContext(Main) {
                    getAllItems()
                }
                theJob.complete()
            }
        }
    }

    fun itemList(): MutableLiveData<List<ItemModel>> {
        when (selectedType) {
            0 -> return getAllItems()
            1 -> return getElectronicItemsFromDB()
            2 -> return getMenClothingItemsFromDB()
            3 -> return getWomenClothingItemsFromDB()
            4 -> return getJeweleryItemsFromDB()
        }
        return itemLiveDataList
    }

    fun setSelectedTab(id: Int){
        selectedType = id
    }

    fun getAllItems(): MutableLiveData<List<ItemModel>> {
        jobGetAllItem = Job()
        jobGetAllItem?.let { theJob ->
            isDataLoading.value = true
            CoroutineScope(IO + theJob).launch {
                val items = shoponDao.allItems()
                withContext(Main) {
                    isDataLoading.value = false
                    itemLiveDataList.value = items
                    print("All item size")
                    println(items.size.toString())
                    theJob.complete()
                }
            }
        }
        return itemLiveDataList
    }


    fun getElectronicItemsFromDB(): MutableLiveData<List<ItemModel>> {
        jobGetElectronicItemsFromDB = Job()
        jobGetElectronicItemsFromDB?.let { theJob ->
            isDataLoading.value = true
            CoroutineScope(IO + theJob).launch {
                val items = shoponDao.allElectronicItems()
                withContext(Main) {
                    isDataLoading.value = false
                    itemLiveDataList.value = items
                    print("Electronic item size")
                    println(items.size.toString())
                    theJob.complete()
                }
            }
        }
        return itemLiveDataList
    }

    fun getJeweleryItemsFromDB(): MutableLiveData<List<ItemModel>> {
        jobGetJeweleryItemsFromDB = Job()
        jobGetJeweleryItemsFromDB?.let { theJob ->
            isDataLoading.value = true
            CoroutineScope(IO + theJob).launch {
                val items = shoponDao.allJeweleryItems()
                withContext(Main) {
                    isDataLoading.value = false
                    itemLiveDataList.value = items
                    theJob.complete()
                }
            }
        }
        return itemLiveDataList
    }

    fun getMenClothingItemsFromDB(): MutableLiveData<List<ItemModel>> {
        jobGetMenClothingItemsFromDB = Job()
        jobGetMenClothingItemsFromDB?.let { theJob ->
            isDataLoading.value = true
            CoroutineScope(IO + theJob).launch {
                val items = shoponDao.allMenClothingItems()
                withContext(Main) {
                    isDataLoading.value = false
                    itemLiveDataList.value = items
                    theJob.complete()
                }
            }
        }
        return itemLiveDataList
    }

    fun getWomenClothingItemsFromDB(): MutableLiveData<List<ItemModel>> {
        jobGetWomenClothingItemsFromDB = Job()
        jobGetWomenClothingItemsFromDB?.let { theJob ->
            isDataLoading.value = true
            CoroutineScope(IO + theJob).launch {
                val items = shoponDao.allWomenClothingItems()
                withContext(Main) {
                    isDataLoading.value = false
                    itemLiveDataList.value = items
                    theJob.complete()
                }
            }
        }
        return itemLiveDataList
    }

    fun getCartItems(): MutableLiveData<List<CartModel>> {
        jobGetCartItems = Job()
        jobGetCartItems?.let { theJob ->
            CoroutineScope(IO + theJob).launch {
                val items = shoponDao.allCartItems()
                withContext(Main) {
                    cartItemLiveDataList.value = items
                    theJob.complete()
                }
            }
        }
        return cartItemLiveDataList
    }

    fun getSpecificCartItem(item: ItemModel): MutableLiveData<CartModel> {
        jobGetSpecificCartItem = Job()
        jobGetSpecificCartItem?.let { theJob ->
            CoroutineScope(IO + theJob).launch {
                val cartItem = shoponDao.getSpecificCartItem(item)
                withContext(Main) {
                    specificCartItem.value = cartItem
                    theJob.complete()
                }
            }
        }
        return specificCartItem
    }

    fun updateSpecificCartItem(item: ItemModel, count: Int): Int {
        var id: Int = 0
        jobUpdateCartItem = Job()
        jobUpdateCartItem?.let { theJob ->
            CoroutineScope(IO + theJob).launch {
                shoponDao.updateCartItem(item, count)
                withContext(Main) {
                    cartItemLiveDataList = getCartItems()
                    specificCartItem = getSpecificCartItem(item)
                    theJob.complete()
                }
            }
        }
        return id
    }

    fun deleteSpecificCartItem(item: ItemModel) {
        jobDeleteSpecificCartItem = Job()
        jobDeleteSpecificCartItem?.let { theJob ->
            CoroutineScope(IO + theJob).launch {
                shoponDao.deleteSpecificItemFromCart(item)
                withContext(Main) {
                    cartItemLiveDataList = getCartItems()
                    specificCartItem = getSpecificCartItem(item)
                    theJob.complete()
                }
            }
        }
    }

    fun deleteAllItemFromCart() {
        jobDeleteAllCartItems = Job()
        jobDeleteAllCartItems?.let { theJob ->
            CoroutineScope(IO + theJob).launch {
                shoponDao.deleteAllCartItems()
                withContext(Main) {
                    cartItemLiveDataList = getCartItems()
                    theJob.complete()
                }
            }
        }
    }

    fun addItemToCart(item: CartModel): Long {
        var id: Long = 0
        jobPutItemInCart = Job()
        jobPutItemInCart?.let { theJob ->
            CoroutineScope(IO + theJob).launch {
                id = shoponDao.insertItemToCart(item)
                withContext(Main) {
                    cartItemLiveDataList = getCartItems()
                    theJob.complete()
                }
            }
        }
        return id
    }

    fun isDataLoading(): MutableLiveData<Boolean> {
        return isDataLoading
    }

    fun cancelJobs() {
        jobGetAllItem?.cancel()
        jobGetItemsAndStore?.cancel()
        jobGetElectronicItemsFromDB?.cancel()
        jobGetMenClothingItemsFromDB?.cancel()
        jobGetWomenClothingItemsFromDB?.cancel()
        jobGetJeweleryItemsFromDB?.cancel()
        jobGetCartItems?.cancel()
        jobPutItemInCart?.cancel()
        jobGetSpecificCartItem?.cancel()
        jobUpdateCartItem?.cancel()
        jobDeleteSpecificCartItem?.cancel()
        jobDeleteAllCartItems?.cancel()
    }

}
