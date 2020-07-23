package com.mfanir.ecommerceapp.User.Dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mfanir.ecommerceapp.Model.Product
import com.mfanir.ecommerceapp.R
import com.mfanir.ecommerceapp.User.HomeActivity
import com.squareup.picasso.Picasso
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_product_detail.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class ProductDetailActivity : AppCompatActivity() {

    private var saveCurrentDate: String? = ""
    private var saveCurrentTime: String? = ""
    private var pid: String? = ""
    private var seller: String? = ""
    private var map: HashMap<String, Any>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        Paper.init(this)
        val phonePaper = Paper.book().read("phone", "1")
        map = HashMap<String, Any>()

        seller = intent.getStringExtra("seller")
        pid = intent.getStringExtra("pid")
        if (pid != null) {
            getDetail(pid)
        }

        checkOrderState(phonePaper)

        cart_btn.setOnClickListener {
            addingIntoCartList(phonePaper)
        }
    }

    private fun checkOrderState(phone: String) {
        val ref = FirebaseDatabase.getInstance().reference
        ref.child("Orders").child(phone).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists())
                {
                    val orderState = snapshot.child("orderState").value.toString()
                    //val name = snapshot.child("name").value.toString()
                    if (orderState.equals("shiped"))
                    {
                        tv_msg.visibility = View.INVISIBLE
                        cart_btn.visibility = View.VISIBLE
                        number_btn.visibility = View.VISIBLE
                    }
                    else if (orderState.equals("pending"))
                    {
                        tv_msg.text = "Tidak dapat menambahkan pesanan apabila semua pesanan sebelumnya belum terkirim"
                        tv_msg.visibility = View.VISIBLE
                        cart_btn.visibility = View.INVISIBLE
                        number_btn.visibility = View.INVISIBLE
                    }
                }
                else
                {
                    tv_msg.visibility = View.INVISIBLE
                    cart_btn.visibility = View.VISIBLE
                    number_btn.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })


    }

    private fun addingIntoCartList(phonePaper: String) {
        val calendar = Calendar.getInstance()

        val currentDate = SimpleDateFormat("MM dd, yyyy")
        saveCurrentDate = currentDate.format(calendar.time)

        val currentTime = SimpleDateFormat(" HH:mm:ss")
        saveCurrentTime = currentTime.format(calendar.time)

        val ref = FirebaseDatabase.getInstance().reference.child("Cart List")
        val subtotal = number_btn.number.toInt() * tv_price_cart.text.toString().toInt()

        map!!["pid"] = pid!!
        map!!["pname"] = tv_product_name.text.toString()
        map!!["price"] = subtotal.toString()
        map!!["date"] = saveCurrentDate!!
        map!!["time"] = saveCurrentTime!!
        map!!["quantity"] = number_btn.number
        map!!["discount"] = ""
        map!!["seller"] = seller!!

        ref.child("User View").child(phonePaper).child("Products").child(pid!!)
            .updateChildren(map!!)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    ref.child("Admin View").child(phonePaper).child("Products").child(pid!!)
                        .updateChildren(map!!)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                addOrder(pid!!, phonePaper)
                            }
                            else
                            {
                                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                            }
                        }
                }
                else {
                    Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                }
            }

    }

    private fun addOrder(pid: String, phonePaper: String) {
        val data = HashMap<String, Any>()
        data["pid"] = pid
        data["seller"] = seller!!
        data["shippingState"] = "pending"
        val ref = FirebaseDatabase.getInstance().reference
        ref.child("Orders").child(phonePaper).child("items").child(pid).updateChildren(data)
            .addOnCompleteListener {
                Toast.makeText(this, "Added to cart list", Toast.LENGTH_SHORT).show()
                finish()
                //startActivity(Intent(this, HomeActivity::class.java))
            }
    }

    private fun getDetail(pid: String?) {
        val ref = FirebaseDatabase.getInstance().reference.child("Products")

        ref.child(pid!!).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val product: Product? = snapshot.getValue(Product::class.java)
                Picasso.get().load(product!!.getImage()).into(iv_product)
                tv_product_name.text = product!!.getPname()
                tv_product_desc.text = product!!.getDescription()
                tv_price_cart.text = product!!.getPrice()

                map!!["image"] = product!!.getImage().toString()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

}
