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
    private var mContext : Context
) : RecyclerView.Adapter<ProductAdapter.ViewHolder?>   () {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.product_admin_items_layout, parent, false )
        return ProductAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductAdapter.ViewHolder, position: Int) {
        val product: Product? = mData[position]

        holder.pname.text = product!!.getPname()
        holder.price.text = product.getPrice()
        holder.category.text = product.getCategory()
        Picasso.get().load(product.getImage()).into(holder.image)

        holder.itemView.setOnClickListener{
            val intent = Intent(mContext, ProductAdminDetailActivity::class.java)
            mContext.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return mData.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pname: TextView =itemView.findViewById(R.id.tv_productName_admin)
        var price: TextView = itemView.findViewById(R.id.tv_priceAdmin)
        var image: ImageView = itemView.findViewById(R.id.iv_poductAdmin)
        var category: TextView = itemView.findViewById(R.id.tv_productCategory_admin)
    }

}