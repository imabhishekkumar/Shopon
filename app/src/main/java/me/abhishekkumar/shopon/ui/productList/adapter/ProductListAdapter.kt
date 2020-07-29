package me.abhishekkumar.shopon.ui.productList.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import me.abhishekkumar.shopon.R
import me.abhishekkumar.shopon.databinding.ProductRowBinding
import me.abhishekkumar.shopon.model.ItemModel

class ProductListAdapter(private val productsList: ArrayList<ItemModel>) :
    RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {

    fun updateProductList(newProductsList: List<ItemModel>) {
        productsList.clear()
        productsList.addAll(newProductsList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.product_row, parent, false)
        return ProductViewHolder(
            (view)
        )
    }

    override fun getItemCount() = productsList.size


    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        with(holder) {
            with(productsList[position]) {
                binding.productNameTV.text = title
                binding.productPriceTV.text = "$"+ price.toString()
                Glide.with(binding.productIV.context)
                    .load(image)
                    .into(binding.productIV)

                binding.productRow.setOnClickListener {
                    val valueBundle = Bundle().apply {
                        putSerializable("item",productsList[position])
                    }
                    it.findNavController().navigate(R.id.productDetailsFragment,valueBundle)
                }
            }
        }
    }

    class ProductViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        val binding = ProductRowBinding.bind(view)
    }
}