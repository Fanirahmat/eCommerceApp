package com.mfanir.ecommerceapp.User.Cart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.*
import com.mfanir.ecommerceapp.Model.Users
import com.mfanir.ecommerceapp.R
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_confirm_final_order.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class ConfirmFinalOrderActivity : AppCompatActivity() {

    private var saveCurrentDate: String? = ""
    private var saveCurrentTime: String? = ""
    private lateinit var mDb: DatabaseReference
    private var phone: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_final_order)

        mDb = FirebaseDatabase.getInstance().reference

        Paper.init(this)
        phone = Paper.book().read("phone","1")

        tv_price.text = "IDR "+intent.getStringExtra("total")

        getAddress(phone)

        btn_purchase.setOnClickListener {
            val calendar = Calendar.getInstance()

            val currentDate = SimpleDateFormat("MM dd, yyyy")
            saveCurrentDate = currentDate.format(calendar.time)

            val currentTime = SimpleDateFormat(" HH:mm:ss")
            saveCurrentTime = currentTime.format(calendar.time)

            val map = HashMap<String, Any>()
            map["address"] = tv_address.text
            map["name"] = tv_name.text
            map["phone"] = phone.toString()
            map["state"] = "pending"
            map["date"] = saveCurrentDate!!
            map["time"] = saveCurrentTime!!
            //map["totalAmount"] = intent.getStringExtra("total")
            mDb.child("Orders").child(phone.toString()).updateChildren(map)
                .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Berhasil menambahkan pesanan", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }

    private fun getAddress(phone: String?) {
        mDb.child("Users").child(phone!!).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(data: DataSnapshot) {
                val user: Users? = data.getValue(Users::class.java)
                if (user != null) {
                    tv_name.text = user.getName()
                    tv_phoneOrder.text = "+62"+user.getPhone()
                    tv_address.text = user.getAddress()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }


}
