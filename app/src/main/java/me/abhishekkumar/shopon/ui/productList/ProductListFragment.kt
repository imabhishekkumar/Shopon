package me.abhishekkumar.shopon.ui.productList

import android.os.Bundle
import android.os.Parcelable
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
    private var selectedTab: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
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
        observeViewModel()
        productRV.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = productListAdapter
        }



        productListChipGroup.setOnCheckedChangeListener { _, id ->

            when (id) {
                R.id.chipAll -> {
                    selectedTab = 0
                    viewModel.setSelectedTab(selectedTab!!)
                    viewModel.getAllItem()
                }
                R.id.chipElectronics -> {
                    selectedTab = 1
                    viewModel.setSelectedTab(selectedTab!!)
                    viewModel.getElectronicItems()
                }
                R.id.chipMenClothing -> {
                    selectedTab = 2
                    viewModel.setSelectedTab(selectedTab!!)
                    viewModel.getMenClothingItems()
                }
                R.id.chipWomenClothing -> {
                    selectedTab = 3
                    viewModel.setSelectedTab(selectedTab!!)
                    viewModel.getWomenClothingItems()
                }
                R.id.chipJewelery -> {
                    selectedTab = 4
                    viewModel.setSelectedTab(selectedTab!!)
                    viewModel.getJeweleryItems()
                }
            }
        }
    }

    private fun observeViewModel() {
        viewModel.getItemList.observe(viewLifecycleOwner, androidx.lifecycle.Observer { items ->
            items?.let {
                productListAdapter.updateProductList(items)
                productShimmer.visibility = View.GONE
                productRV.visibility = View.VISIBLE
                productShimmer.stopShimmer()
            }
        })

        viewModel.getIsDataLoading()
            .observe(viewLifecycleOwner, androidx.lifecycle.Observer { items ->
                items?.let {
                    if (it) {
                        productShimmer.visibility = View.VISIBLE
                        productRV.visibility = View.GONE
                        productShimmer.startShimmer()
                    } else {
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