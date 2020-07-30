package me.abhishekkumar.shopon.ui.cartFragment.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import me.abhishekkumar.shopon.R
import me.abhishekkumar.shopon.model.CartModel
import org.w3c.dom.Text
import kotlin.math.roundToInt

class CartListAdapter(private val productsList: ArrayList<CartModel>) :
    RecyclerView.Adapter<CartListAdapter.CartViewHolder>() {

    fun updateProductList(newProductsList: List<CartModel>) {
        productsList.clear()
        productsList.addAll(newProductsList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.cart_row, parent, false)
        return CartViewHolder(
            (view)
        )
    }

    override fun getItemCount() = productsList.size


    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {

        with(productsList[position]) {
            holder.productNameCartTV.text = item.title
            var discountedPrice = (item.price!! - (item.price * item.discount!! / 100))
            discountedPrice = (discountedPrice * 100).roundToInt() / 100.0
            holder.productDiscountPriceCartTV.text = "$$discountedPrice"

            holder.productPriceCartTV.apply {
                paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                text = "$${item.price}"
            }
            holder.productNumberCartTV.text = "$count pc(s)"
            Glide.with(holder.productCartIV.context)
                .load(item.image)
                .into(holder.productCartIV)

        }

    }

    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productNameCartTV: TextView = view.findViewById(R.id.productNameCartTV)
        val productCartIV: ImageView = view.findViewById(R.id.productCartIV)
        val productNumberCartTV: TextView = view.findViewById(R.id.productNumberCartTV)
        val productPriceCartTV: TextView = view.findViewById(R.id.productPriceCartTV)
        val productDiscountPriceCartTV: TextView =
            view.findViewById(R.id.productDiscountPriceCartTV)

    }
}