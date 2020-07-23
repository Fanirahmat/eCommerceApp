package com.mfanir.ecommerceapp.Admin.Order

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.mfanir.ecommerceapp.Model.Cart
import com.mfanir.ecommerceapp.R
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_user_order_detail.*

class UserOrderDetailActivity : AppCompatActivity() {

    private var ref: DatabaseReference? = null
    private var phoneID: String? = ""
    private var rv: RecyclerView? = null
    private var mCart = ArrayList<Cart>()
    private var detailOrderAdapter: DetailOrderAdapter? = null
    private var phoneSeller: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_order_detail)

        Paper.init(this)
        phoneSeller = Paper.book().read("phone","1")

        phoneID = intent.getStringExtra("phone")
        ref = FirebaseDatabase.getInstance().reference
        mCart = ArrayList()

        rv = findViewById(R.id.rv_productList)
        rv!!.setHasFixedSize(true)
        rv!!.layoutManager = LinearLayoutManager(this)
        getData(phoneID)

    }

    private fun getData(phoneID: String?) {
        ref!!.child("Cart List").child("Admin View")
            .child(phoneID!!).child("Products").addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    mCart.clear()
                    for (data in snapshot.children) {
                        val cart: Cart? = data.getValue(Cart::class.java)
                        val seller = data.child("seller").value.toString()
                        if (cart != null) {
                            if (seller == "+62"+phoneSeller) {
                                (mCart).add(cart)
                            }

                        }
                    }

                    if (mCart.isNotEmpty()) {
                        detailOrderAdapter = DetailOrderAdapter(mCart, this@UserOrderDetailActivity) {
                            val intent = Intent(this@UserOrderDetailActivity, OverviewUserOrderActivity::class.java)
                                .putExtra("data", it)
                                .putExtra("phone", phoneID)
                            startActivity(intent)
                        }
                        rv!!.adapter = detailOrderAdapter
                    }
                }
                override fun onCancelled(error: DatabaseError) {

                }
            })
    }


}
