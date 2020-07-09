package com.mfanir.ecommerceapp.User.Dashboard


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

class ProductItemsAdapter(
    mData: List<Product>,
    mContext:Context
) : RecyclerView.Adapter<ProductItemsAdapter.ViewHolder?>() {

    private val mContext:Context
    private val mData: List<Product>

    init {
        this.mContext = mContext
        this.mData = mData
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ProductItemsAdapter.ViewHolder {
        val view: View = LayoutInflater.from(mContext).inflate(R.layout.product_items_layout, viewGroup, false)
        return ProductItemsAdapter.ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: ProductItemsAdapter.ViewHolder, position: Int) {
        val product: Product? = mData[position]

        holder.pname.text = product!!.getPname()
        Picasso.get().load(product.getImage()).into(holder.image)
        holder.desc.text = product!!.getDescription()
        holder.price.text = "Rp." + product!!.getPrice() + ",00"

        holder.itemView.setOnClickListener {
            val intent = Intent(mContext, ProductDetailActivity::class.java)
            intent.putExtra("pid", product.getPid()).putExtra("seller", product.getSeller())
            mContext!!.startActivity(intent)
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pname: TextView =itemView.findViewById(R.id.product_name_item)
        var price: TextView = itemView.findViewById(R.id.product_price_item)
        var image: ImageView = itemView.findViewById(R.id.product_image_item)
        var desc: TextView = itemView.findViewById(R.id.product_desc_item)




    }


}