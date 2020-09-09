package com.mfanir.ecommerceapp.Admin.Product

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mfanir.ecommerceapp.Model.Product
import com.mfanir.ecommerceapp.R
import com.squareup.picasso.Picasso

class ProductAdapter (
    private var mData : List<Product>,
    private var listener : (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ViewHolder?>   () {

    lateinit var contextAdapter : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ViewHolder {
        contextAdapter = parent.context
        val view = LayoutInflater.from(contextAdapter).inflate(R.layout.product_admin_items_layout, parent, false )
        return ProductAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductAdapter.ViewHolder, position: Int) {
        holder.bindItem(mData[position], listener, contextAdapter)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pname: TextView =itemView.findViewById(R.id.tv_productName_admin)
        var price: TextView = itemView.findViewById(R.id.tv_priceAdmin)
        var image: ImageView = itemView.findViewById(R.id.iv_poductAdmin)
        var category: TextView = itemView.findViewById(R.id.tv_productCategory_admin)

        fun bindItem(product: Product, listener: (Product) -> Unit, context: Context) {
            pname.text = product.pname
            price.text = product.price
            category.text = product.category
            Picasso.get().load(product.image).into(image)

            itemView.setOnClickListener {
                listener(product)
            }
        }
    }

}