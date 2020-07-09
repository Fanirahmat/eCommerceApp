package com.mfanir.ecommerceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.mfanir.ecommerceapp.Admin.AdminHomeActivity
import com.mfanir.ecommerceapp.Auth.LoginActivity
import com.mfanir.ecommerceapp.Auth.SignUpActivity
import com.mfanir.ecommerceapp.User.HomeActivity
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var firebaseUser: FirebaseUser? = null

    override fun onStart() {
        super.onStart()
        firebaseUser = FirebaseAuth.getInstance().currentUser
        if (firebaseUser != null) {
            Paper.init(this)
            var paper = Paper.book().read("login", "1")
            if (paper != null && paper == "1") {
                startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                finish()
            } else if (paper != null && paper == "2") {
                startActivity(Intent(this@MainActivity, AdminHomeActivity::class.java))
                finish()
            }

        }


    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        primary_btn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        second_btn.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}
