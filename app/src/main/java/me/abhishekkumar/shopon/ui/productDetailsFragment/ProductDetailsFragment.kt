package me.abhishekkumar.shopon.ui.productDetailsFragment

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_product_details.*
import me.abhishekkumar.shopon.R
import me.abhishekkumar.shopon.model.CartModel
import me.abhishekkumar.shopon.ui.viewmodel.ShoponViewModel


@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {

    private val viewModel: ShoponViewModel by viewModels()
    private val args: ProductDetailsFragmentArgs by navArgs()
    private var selectVariant = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = layoutInflater.inflate(R.layout.fragment_product_details, container, false)
        setHasOptionsMenu(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val item = args.item!!
        val ctx = this.requireContext()
        with(item) {
            itemNameTV.text = title
            itemDescTV.text = description
            Glide.with(ctx)
                .load(item.image)
                .into(itemIV)
            if (variant!!.isNotEmpty()) {
                val adapter = ArrayAdapter(
                    ctx,
                    android.R.layout.simple_spinner_item, variant.toMutableList()
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }else{
                spinner.visibility = View.GONE
            }

            val cart = viewModel.getSpecificCartItem(this)

            cart.observe((viewLifecycleOwner), androidx.lifecycle.Observer { items ->
                println("Observer called")
                if (items == null || items.count == 0) {
                    addedToCartLayout.visibility = View.GONE
                    addToCartBtn.visibility = View.VISIBLE
                } else {
                    addedToCartLayout.visibility = View.VISIBLE
                    addToCartBtn.visibility = View.GONE
                    noOfProductSelectedTV.text = items.count.toString()
                }
            })
            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    selectVariant = variant[position]

                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
            addToCartBtn.setOnClickListener {
                if (cart.value == null || cart.value!!.count == 0) {
                    println("Inside this")
                    val cartItem = CartModel(item = item, count = 1, variant =selectVariant)
                    viewModel.addToCart(cartItem)
                    viewModel.getSpecificCartItem(item)
                }
            }

            addOneMoreBtn.setOnClickListener {
                if (cart.value!!.count < stock!!) {
                    val count = cart.value!!.count + 1
                    viewModel.updateCartItem(item, count)
                    viewModel.getSpecificCartItem(item)
                } else {
                    Toast.makeText(context, "No more stock", Toast.LENGTH_SHORT).show()
                }
            }
            removeOneMoreBtn.setOnClickListener {

                if (cart.value!!.count > 0) {
                    val count = cart.value!!.count - 1
                    viewModel.updateCartItem(item, count)
                    viewModel.getSpecificCartItem(item)
                }
                if (cart.value!!.count == 1) {
                    viewModel.deleteSpecificCartItem(item)
                }
            }
        }
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
