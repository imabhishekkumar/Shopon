package me.abhishekkumar.shopon.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.abhishekkumar.shopon.model.ItemModel
import me.abhishekkumar.shopon.data.repositiory.ShoponRepo
import me.abhishekkumar.shopon.model.CartModel

class ShoponViewModel @ViewModelInject constructor(private val shoponRepo: ShoponRepo) :
    ViewModel() {

    val getItemList: MutableLiveData<List<ItemModel>> = shoponRepo.itemList()
    fun getAllItem(): MutableLiveData<List<ItemModel>> = shoponRepo.getAllItems()
    fun getElectronicItems(): MutableLiveData<List<ItemModel>> = shoponRepo.getElectronicItemsFromDB()
    fun getMenClothingItems(): MutableLiveData<List<ItemModel>> = shoponRepo.getMenClothingItemsFromDB()
    fun getWomenClothingItems(): MutableLiveData<List<ItemModel>> = shoponRepo.getWomenClothingItemsFromDB()
    fun getJeweleryItems(): MutableLiveData<List<ItemModel>> = shoponRepo.getJeweleryItemsFromDB()


    val getCartItems: MutableLiveData<List<CartModel>> = shoponRepo.getCartItems()

    fun setSelectedTab(id: Int){
        shoponRepo.setSelectedTab(id)
    }
    fun getItemsAndStore() {
        shoponRepo.getItemsAndStore()
    }

    fun addToCart(item: CartModel): Long {
        return shoponRepo.addItemToCart(item)
    }

    fun updateCartItem(item: ItemModel, count: Int): Int {
        return shoponRepo.updateSpecificCartItem(item, count)
    }

    fun getSpecificCartItem(item: ItemModel): MutableLiveData<CartModel> {
        return shoponRepo.getSpecificCartItem(item)
    }

    fun deleteSpecificCartItem(item: ItemModel) {
        shoponRepo.deleteSpecificCartItem(item)
    }

    fun cancelJobs() {
        shoponRepo.cancelJobs()
    }

    fun deleteAllCartItems() {
        shoponRepo.deleteAllItemFromCart()
    }

    fun getIsDataLoading(): MutableLiveData<Boolean> {
        return shoponRepo.isDataLoading()
    }
}