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

            changeIcon(iv_product, R.drawable.ic_home_active)
            changeIcon(iv_adminOrder, R.drawable.ic_order)
            changeIcon(iv_adminSetting, R.drawable.ic_profil)
        }

        iv_adminOrder.setOnClickListener {
            setFragment(order)

            changeIcon(iv_product, R.drawable.ic_home)
            changeIcon(iv_adminOrder, R.drawable.ic_order_active)
            changeIcon(iv_adminSetting, R.drawable.ic_profil)
        }

        /*
        iv_adminSetting.setOnClickListener {
            setFragment(product)

            changeIcon(iv_product, R.drawable.ic_home)
            changeIcon(iv_adminOrder, R.drawable.ic_order)
            changeIcon(iv_adminSetting, R.drawable.ic_profil_active)
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
