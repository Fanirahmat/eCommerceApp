package com.mfanir.ecommerceapp.User

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.mfanir.ecommerceapp.R
import com.mfanir.ecommerceapp.User.Cart.CartFragment
import com.mfanir.ecommerceapp.User.Category.CategoryFragment
import com.mfanir.ecommerceapp.User.Dashboard.DashboardFragment
import com.mfanir.ecommerceapp.User.Order.OrderFragment
import com.mfanir.ecommerceapp.User.Setting.SettingFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val cart = CartFragment()
        val category = CategoryFragment()
        val dashboard = DashboardFragment()
        val order = OrderFragment()
        val setting = SettingFragment()

        setFragment(dashboard)

        iv_cart.setOnClickListener {
            setFragment(cart)
        }

        iv_category.setOnClickListener {
            setFragment(category)
        }

        iv_dashboard.setOnClickListener {
            setFragment(dashboard)
        }

        iv_order.setOnClickListener {
            setFragment(order)
        }

        iv_setting.setOnClickListener {
            setFragment(setting)
        }
    }

    protected fun setFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.layout_frame, fragment)
        fragmentTransaction.commit()
    }

    private fun changeIcon(imageView: ImageView, int: Int){
        imageView.setImageResource(int)
    }
}
