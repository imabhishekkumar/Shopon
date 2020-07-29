package me.abhishekkumar.shopon.ui.productList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_product_list.*
import me.abhishekkumar.shopon.databinding.FragmentProductListBinding
import me.abhishekkumar.shopon.ui.productList.adapter.ProductListAdapter
import me.abhishekkumar.shopon.ui.productList.viewmodel.ItemViewModel
import me.abhishekkumar.shopon.utils.InternetConnectionUtils

@AndroidEntryPoint
class ProductListFragment : Fragment() {

    private val viewModel: ItemViewModel by viewModels()
    private val productListAdapter =
        ProductListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentProductListBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val internetConnectionUtils  = InternetConnectionUtils()
        if(internetConnectionUtils.isInternetAvailable(this.requireContext())){
            viewModel.getItemsAndStore()
        }
        productRV.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = productListAdapter
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.getItems.observe(viewLifecycleOwner, androidx.lifecycle.Observer  { items ->
            items?.let {
                productListAdapter.updateProductList(items)
            }
        })
    }
}