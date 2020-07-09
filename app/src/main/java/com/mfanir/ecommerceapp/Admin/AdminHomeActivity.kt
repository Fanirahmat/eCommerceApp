package com.mfanir.ecommerceapp.Admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mfanir.ecommerceapp.Admin.Order.AdminOrderFragment
import com.mfanir.ecommerceapp.Admin.Product.ProductFragment
import com.mfanir.ecommerceapp.R
import com.mfanir.ecommerceapp.User.Order.OrderFragment
import kotlinx.android.synthetic.main.activity_admin_home.*

class AdminHomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home)
        //Toast.makeText(this, "seller", Toast.LENGTH_SHORT).show()

        val product = ProductFragment()
        val order = AdminOrderFragment()

        setFragment(product)

        iv_product.setOnClickListener {
            setFragment(product)
        }

        iv_adminOrder.setOnClickListener {
            setFragment(order)
        }

        /*
        iv_adminSetting.setOnClickListener {
            setFragment(product)
        }

         */

    }

    protected fun setFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.layout_frame_admin, fragment)
        fragmentTransaction.commit()
    }

    private fun changeIcon(imageView: ImageView, int: Int){
        imageView.setImageResource(int)
    }
}
