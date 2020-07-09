package com.mfanir.ecommerceapp.Admin.Product

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.mfanir.ecommerceapp.R
import kotlinx.android.synthetic.main.fragment_product.*

class ProductFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        iv_addProduct.setOnClickListener {
            val i = Intent(context, SelectCategory::class.java)
            startActivity(i)
        }
    }


}
