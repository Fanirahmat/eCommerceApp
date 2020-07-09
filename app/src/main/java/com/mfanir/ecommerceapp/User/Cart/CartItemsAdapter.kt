package com.mfanir.ecommerceapp.User.Cart

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.mfanir.ecommerceapp.Model.Cart
import com.mfanir.ecommerceapp.R
import com.mfanir.ecommerceapp.User.Dashboard.ProductDetailActivity
import com.squareup.picasso.Picasso
import io.paperdb.Paper

class CartItemsAdapter (
    private val mData: List<Cart>,
    private val mContext:Context,
) : RecyclerView.Adapter<CartItemsAdapter.ViewHolder?>() {



    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CartItemsAdapter.ViewHolder {
        val view: View = LayoutInflater.from(mContext).inflate(R.layout.cart_items_layout, viewGroup, false)
        return CartItemsAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: CartItemsAdapter.ViewHolder, position: Int) {
        val cart: Cart? = mData[position]

        if (cart!!.pname?.startsWith("Total")!! && cart!!.quantity == "" && cart!!.image == "" &&
                cart!!.pid == "" && cart!!.date == "" && cart!!.time == "")
        {
            holder.pname.text = cart!!.pname
            holder.price.text = "IDR " + cart!!.price

            holder.quantity.visibility = View.INVISIBLE
            holder.cardView.visibility = View.INVISIBLE
            holder.btn_del.visibility = View.INVISIBLE
            holder.btn_edit.visibility = View.INVISIBLE

        } else {
            holder.pname.text = cart!!.pname
            holder.price.text = "IDR " + cart!!.price
            holder.quantity.text = "X " + cart!!.quantity
            Picasso.get().load(cart!!.image).into(holder.image)

            holder.btn_del.setOnClickListener {
                val phone = Paper.book().read("phone","1")
                val mDb = FirebaseDatabase.getInstance().getReference("Cart List")
                mDb.child("User View")
                    .child(phone!!.toString())
                    .child("Products")
                    .child(cart.pid.toString())
                    .removeValue()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            mDb.child("Admin View")
                                .child(phone!!.toString())
                                .child("Products")
                                .child(cart.pid.toString())
                                .removeValue().addOnCompleteListener {  task ->
                                    if (task.isSuccessful) {
                                        Toast.makeText(mContext, "item removed", Toast.LENGTH_SHORT).show()
                                    }
                                }
                        }
                    }

            }

            holder.btn_edit.setOnClickListener {
                val intent = Intent(mContext, ProductDetailActivity::class.java )
                intent.putExtra("pid", cart.pid.toString())
                mContext!!.startActivity(intent)
            }
        }

    }



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pname: TextView =itemView.findViewById(R.id.tv_dateOrder)
        var price: TextView = itemView.findViewById(R.id.tv_price_cart)
        var image: ImageView = itemView.findViewById(R.id.iv_image_cart)
        var quantity: TextView = itemView.findViewById(R.id.tv_quantity_cart)
        var btn_del: ImageView = itemView.findViewById(R.id.btn_delete_cart)
        var btn_edit: ImageView = itemView.findViewById(R.id.btn_edit_cart)
        var cardView: CardView = itemView.findViewById(R.id.cardView)

    }

}