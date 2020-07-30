package me.abhishekkumar.shopon.ui.productList

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_product_list.*
import me.abhishekkumar.shopon.R
import me.abhishekkumar.shopon.ui.productList.adapter.ProductListAdapter
import me.abhishekkumar.shopon.ui.viewmodel.ShoponViewModel
import me.abhishekkumar.shopon.utils.InternetConnectionUtils

@AndroidEntryPoint
class ProductListFragment : Fragment() {

    private val viewModel: ShoponViewModel by viewModels()
    private val productListAdapter =
        ProductListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = layoutInflater.inflate(R.layout.fragment_product_list, container, false)
        setHasOptionsMenu(true)
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val internetConnectionUtils = InternetConnectionUtils()
        if (internetConnectionUtils.isInternetAvailable(this.requireContext())) {
            viewModel.getItemsAndStore()
        }
        productRV.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = productListAdapter
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.getItems.observe(viewLifecycleOwner, androidx.lifecycle.Observer { items ->
            items?.let {
                productListAdapter.updateProductList(items)
                productShimmer.visibility = View.GONE
                productRV.visibility = View.VISIBLE
                productShimmer.stopShimmer()
            }
        })

        viewModel.getIsDataLoading().observe(viewLifecycleOwner, androidx.lifecycle.Observer { items ->
            items?.let {
                if(it){
                    productShimmer.visibility = View.VISIBLE
                    productRV.visibility = View.GONE
                    productShimmer.startShimmer()
                }else{
                    productShimmer.visibility = View.GONE
                    productRV.visibility = View.VISIBLE
                    productShimmer.stopShimmer()
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cancelJobs()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.cart) {
            findNavController().navigate(R.id.cartFragment)
            return true
        }
        return NavigationUI.onNavDestinationSelected(
            item,
            requireView().findNavController()
        )
                || super.onOptionsItemSelected(item)
    }
}