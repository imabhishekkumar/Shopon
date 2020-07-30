package me.abhishekkumar.shopon.ui.cartFragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_cart.*
import me.abhishekkumar.shopon.R
import me.abhishekkumar.shopon.ui.cartFragment.adapter.CartListAdapter
import me.abhishekkumar.shopon.ui.viewmodel.ShoponViewModel


@AndroidEntryPoint
class CartFragment : Fragment() {
    private val viewModel: ShoponViewModel by viewModels()
    private val cartListAdapter =
        CartListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_cart, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cartRV.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = cartListAdapter
        }
        observeViewModel()
        orderFAB.setOnClickListener{
            Toast.makeText(context,"Your order has been placed", Toast.LENGTH_SHORT).show()
            viewModel.deleteAllCartItems()

        }

    }
    private fun observeViewModel() {
        viewModel.getCartItems.observe(viewLifecycleOwner, androidx.lifecycle.Observer { items ->
            items?.let {
                cartListAdapter.updateProductList(items)
            }
        })
    }
    override fun onDestroy() {
        super.onDestroy()
        viewModel.cancelJobs()
    }
}