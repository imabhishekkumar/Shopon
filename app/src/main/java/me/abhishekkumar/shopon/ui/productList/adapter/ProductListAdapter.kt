package me.abhishekkumar.shopon.ui.productList.adapter

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import me.abhishekkumar.shopon.R
import me.abhishekkumar.shopon.model.ItemModel
import kotlin.math.roundToInt

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

        with(productsList[position]) {
            holder.productNameTV.text = title
            var discountedPrice = (price!! - (price * discount!! / 100))
            discountedPrice = (discountedPrice * 100).roundToInt() / 100.0
            holder.productDiscountPriceTV.text = "$$discountedPrice"

            holder.productPriceTV.apply {
                paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                text = "$$price"
            }
            Glide.with(holder.productIV.context)
                .load(image)
                .into(holder.productIV)
            if (stock!! < 10) {
                val stockWarningTxt = "Hurry only $stock left in stock"
                holder.productStockWarningTV.text = stockWarningTxt
            }
            holder.productRow.setOnClickListener {
                val valueBundle = Bundle().apply {
                    putSerializable("item", productsList[position])
                }
                it.findNavController().navigate(R.id.productDetailsFragment, valueBundle)
            }
        }

    }

    class ProductViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        val productNameTV: TextView = view.findViewById(R.id.productNameTV)
        val productIV: ImageView = view.findViewById(R.id.productIV)
        val productStockWarningTV: TextView = view.findViewById(R.id.productStockWarningTV)
        val productPriceTV: TextView = view.findViewById(R.id.productPriceTV)
        val productDiscountPriceTV: TextView =
            view.findViewById(R.id.productDiscountPriceTV)
        val productRow: CardView = view.findViewById(R.id.productRow)
    }
}