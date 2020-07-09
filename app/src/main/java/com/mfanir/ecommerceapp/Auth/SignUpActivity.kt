package com.mfanir.ecommerceapp.Auth

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.mfanir.ecommerceapp.R
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    //private lateinit var ref: DatabaseReference
    private var loadingBar: ProgressDialog? = null
    private var statusUser: Boolean = true
    //lateinit var countryCodePicker: CountryCodePicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        //spinner.adapter = Adapter(this, android.R.layout.simple_spinner_dropdown_item, )
        //countryCodePicker = findViewById(R.id.codePicker)
        //ref = FirebaseDatabase.getInstance().reference
        loadingBar = ProgressDialog(this)
        mAuth = FirebaseAuth.getInstance()

        tv_admin_register.setOnClickListener {
            tv_admin_register.visibility = View.INVISIBLE
            tv_user_register.visibility = View.VISIBLE
            statusUser = false
        }

        tv_user_register.setOnClickListener {
            tv_admin_register.visibility = View.VISIBLE
            tv_user_register.visibility = View.INVISIBLE
            statusUser = true
        }

        btn_register.setOnClickListener {
            createAccount()
        }

    }

    private fun createAccount() {
        val name = et_name_register.text.toString()
        val email = et_email_register.text.toString()
        val phone = et_phone_register.text.toString()
        val username = et_username_register.text.toString()
        val password = et_password_register.text.toString()

        if (name.isEmpty()) {
            et_name_register.error = "silahkan isi nama"
            et_name_register.requestFocus()
        } else if (username.isEmpty()) {
           et_username_register.error = "silahkan isi username"
            et_username_register.requestFocus()
        } else if (phone.isEmpty()) {
            et_phone_register.error = "silahkan isi telepon"
            et_phone_register.requestFocus()
        } else if (email.isEmpty()) {
            et_email_register.error = "silahkan isi email"
            et_email_register.requestFocus()
        } else if (password.isEmpty()) {
            et_password_register.error = "silahkan isi password"
            et_password_register.requestFocus()
        } else {
            loadingBar!!.setTitle("Create Account")
            loadingBar!!.setMessage("Please wait, we are checking the credentials.")
            loadingBar!!.setCanceledOnTouchOutside(false)
            loadingBar!!.show()

            loadingBar!!.dismiss()
            val intent = Intent(applicationContext, VerifyPhoneActivity::class.java)
            intent.putExtra("phone", phone)
            intent.putExtra("name", name)
            intent.putExtra("email", email)
            intent.putExtra("username", username)
            intent.putExtra("password", password)
            if (statusUser == true)
            {
                intent.putExtra("status", "user")
            }
            else
            {
                intent.putExtra("status", "seller")
            }

            startActivity(intent)
            }
        }
    }

