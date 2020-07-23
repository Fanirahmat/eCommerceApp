package com.mfanir.ecommerceapp.Admin.Order

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mfanir.ecommerceapp.Model.Cart
import com.mfanir.ecommerceapp.R
import com.squareup.picasso.Picasso

class DetailOrderAdapter(
    private val mData: List<Cart>,
    private val mContext: Context,
    private val listener: (Cart) -> Unit
) : RecyclerView.Adapter<DetailOrderAdapter.ViewHolder?>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailOrderAdapter.ViewHolder {
        val view: View = LayoutInflater.from(mContext).inflate(R.layout.detail_order_items_layout, parent, false)
        return DetailOrderAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: DetailOrderAdapter.ViewHolder, position: Int) {
        holder.bindItem(mData[position], listener, mContext)

    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var pname: TextView =itemView.findViewById(R.id.tv_productName_cartAdmin)
        var price: TextView = itemView.findViewById(R.id.tv_price_cartAdmin)
        var image: ImageView = itemView.findViewById(R.id.iv_image_cartAdmin)
        var quantity: TextView = itemView.findViewById(R.id.tv_quantity_cartAdmin)
        var cardView: CardView = itemView.findViewById(R.id.cardViewAdmin)

        fun bindItem(cart: Cart, listener: (Cart) -> Unit, mContext: Context) {
            pname.text = cart.pname
            price.text = cart.price
            quantity.text = "X "+cart.quantity
            Picasso.get().load(cart.image).into(image)

            itemView.setOnClickListener{
                listener(cart)
            }
        }
    }


}