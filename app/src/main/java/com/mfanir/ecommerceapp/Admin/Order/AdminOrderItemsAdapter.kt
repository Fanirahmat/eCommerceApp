package com.mfanir.ecommerceapp.Admin.Order

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mfanir.ecommerceapp.Model.Order
import com.mfanir.ecommerceapp.R

class AdminOrderItemsAdapter (
    private val mData: List<Order>,
    private val mContext: Context,
) : RecyclerView.Adapter<AdminOrderItemsAdapter.ViewHolder?>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminOrderItemsAdapter.ViewHolder {
        val view: View = LayoutInflater.from(mContext).inflate(R.layout.admin_order_items_layout, parent, false)
        return AdminOrderItemsAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: AdminOrderItemsAdapter.ViewHolder, position: Int) {
        val order: Order? = mData[position]

        holder.name.text = order!!.name
        holder.phone.text = "+62"+order!!.phone
        holder.date.text = order!!.date
        holder.time.text = order!!.time

        holder.btn_detail.setOnClickListener {
            val intent = Intent(mContext, UserOrderDetailActivity::class.java)
            intent.putExtra("phone", order.phone)
            mContext!!.startActivity(intent)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.tv_usernameOrder)
        var phone: TextView = itemView.findViewById(R.id.tv_phoneOrder)
        var time: TextView = itemView.findViewById(R.id.tv_timeOrder)
        var date: TextView = itemView.findViewById(R.id.tv_dateOrder)
        var btn_detail: Button = itemView.findViewById(R.id.btn_detailOrder)

    }

}