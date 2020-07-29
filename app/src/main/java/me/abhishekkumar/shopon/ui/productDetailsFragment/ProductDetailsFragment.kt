package me.abhishekkumar.shopon.ui.productDetailsFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_product_details.*
import me.abhishekkumar.shopon.databinding.FragmentProductDetailsBinding
import me.abhishekkumar.shopon.model.ItemModel

class ProductDetailsFragment : Fragment() {
    private val args : ProductDetailsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentProductDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val item= args.item!!
        val ctx = this.requireContext()
        with(item){
            itemNameTV.text = title
            itemDescTV.text = description
            Glide.with(ctx)
                .load(item.image)
                .into(itemIV)
        }
    }
}
