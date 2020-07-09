package com.mfanir.ecommerceapp.Auth

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.mfanir.ecommerceapp.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var ref: DatabaseReference
    private var loadingBar: ProgressDialog? = null
    private var statusUser: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        ref = FirebaseDatabase.getInstance().reference
        loadingBar = ProgressDialog(this)
        mAuth = FirebaseAuth.getInstance()

        tv_admin_link.setOnClickListener {
            tv_admin_link.visibility = View.INVISIBLE
            tv_user_link.visibility = View.VISIBLE
            statusUser = false

        }
         tv_user_link.setOnClickListener {
             tv_admin_link.visibility = View.VISIBLE
             tv_user_link.visibility = View.INVISIBLE
             statusUser = true


         }

        btn_login.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val phone = et_phone_login.text.toString()

        if (phone.isEmpty()) {
            et_phone_login.error = "monggo"
            et_phone_login.requestFocus()
        } else {
            if (statusUser == true) {
                //login user
                ref.child("Sellers").child(phone).addValueEventListener(object : ValueEventListener{
                    override fun onDataChange(dataSeller: DataSnapshot) {
                        if (dataSeller.exists()) {
                            loadingBar!!.dismiss()
                            Toast.makeText(baseContext, "Anda sudah terdaftar sebagai seller",
                                Toast.LENGTH_SHORT).show()
                        }
                        else
                        {
                            ref.child("Users").child(phone).addValueEventListener(object : ValueEventListener{
                                override fun onDataChange(dataUser: DataSnapshot) {
                                    if (!dataUser.exists()) {
                                        Toast.makeText(baseContext, "Anda belum terdaftar sebagai user, silahkan daftar dahulu",
                                            Toast.LENGTH_SHORT).show()
                                    }
                                    else
                                    {
                                        loadingBar!!.dismiss()
                                        val intent = Intent(applicationContext, VerifyPhoneActivity::class.java)
                                        intent.putExtra("phone", phone)
                                        intent.putExtra("status", "user")
                                        startActivity(intent)
                                    }
                                }

                                override fun onCancelled(p0: DatabaseError) {

                                }
                            })

                        }
                    }
                    override fun onCancelled(p0: DatabaseError) { }
                })
            } else {
                //login seller
                ref.child("Users").child(phone).addValueEventListener(object : ValueEventListener{
                    override fun onDataChange(dataUser: DataSnapshot) {
                        if (dataUser.exists()) {
                            loadingBar!!.dismiss()
                            Toast.makeText(baseContext, "Anda sudah terdaftar sebagai user",
                                Toast.LENGTH_SHORT).show()
                        }
                        else
                        {
                            ref.child("Sellers").child(phone).addValueEventListener(object : ValueEventListener{
                                override fun onDataChange(dataSeller: DataSnapshot) {
                                    if (!dataSeller.exists()) {
                                        Toast.makeText(baseContext, "Anda belum terdaftar sebagai seller, silahkan daftar dahulu",
                                            Toast.LENGTH_SHORT).show()
                                    }
                                    else
                                    {
                                        loadingBar!!.dismiss()
                                        val intent = Intent(applicationContext, VerifyPhoneActivity::class.java)
                                        intent.putExtra("phone", phone)
                                        intent.putExtra("status", "seller")
                                        startActivity(intent)
                                    }
                                }

                                override fun onCancelled(p0: DatabaseError) {

                                }
                            })

                        }
                    }
                    override fun onCancelled(p0: DatabaseError) { }
                })
            }



        }
    }


}
