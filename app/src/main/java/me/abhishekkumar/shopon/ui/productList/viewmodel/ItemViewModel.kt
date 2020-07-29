package me.abhishekkumar.shopon.ui.productList.viewmodel
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.abhishekkumar.shopon.model.ItemModel
import me.abhishekkumar.shopon.data.repositiory.ItemRepo

class ItemViewModel @ViewModelInject constructor( private val itemRepo: ItemRepo) : ViewModel() {

    val getItems: MutableLiveData<List<ItemModel>> = itemRepo.getItemsFromDB()

    fun getItemsAndStore(){
        itemRepo.getItemsAndStore()
    }

    fun cancelJobs() {
        itemRepo.cancelJobs()
    }
}