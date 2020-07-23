package com.mfanir.ecommerceapp.Admin.Order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.*
import com.mfanir.ecommerceapp.Model.Cart
import com.mfanir.ecommerceapp.Model.Order
import com.mfanir.ecommerceapp.R
import kotlinx.android.synthetic.main.activity_overview_user_order.*

class OverviewUserOrderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview_user_order)

        val ref = FirebaseDatabase.getInstance().reference
        val data = intent.getParcelableExtra<Cart>("data")
        val phone = intent.getStringExtra("phone")

        showIdentityOrder(phone, ref)

        btn_confirm.setOnClickListener {
            val map = HashMap<String, Any>()
            map["shippingState"] = "shiped"
            ref.child("Orders").child(phone).child("items").child(data.pid!!).updateChildren(map).addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    Toast.makeText(this, "Order berhasil dikonfirmasi", Toast.LENGTH_SHORT).show()
                    finish()
                }
                else
                {
                    Toast.makeText(this, "error : " + task.exception, Toast.LENGTH_SHORT).show()
                }
            }
        }


        updateOrderState(ref, phone)


    }

    private fun updateOrderState(ref: DatabaseReference, phone: String)
    {
        ref.child("Orders").child(phone).child("items").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data in snapshot.children) {
                    val shippingState = data.child("shippingState").value.toString()
                    if (shippingState != "pending")
                    {
                        val map = HashMap<String, Any>()
                        map["orderState"] = "shiped"
                        ref.child("Orders").child(phone).updateChildren(map).addOnCompleteListener {
                            task -> if (task.isSuccessful)
                            {
                                //task success
                            }
                        }
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun showIdentityOrder(phone: String, ref: DatabaseReference) {
        ref.child("Orders").child(phone).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val order: Order? = snapshot.getValue(Order::class.java)
                if (order != null) {
                    tv_name.text = order.name
                    tv_phoneOrder.text = order.phone
                    tv_address.text = order.address
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}
