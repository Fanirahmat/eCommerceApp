package com.mfanir.ecommerceapp.User.Dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.mfanir.ecommerceapp.Model.Product

import com.mfanir.ecommerceapp.R
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.util.ArrayList


class DashboardFragment : Fragment() {

    private lateinit var mDatabase: DatabaseReference
    private var mProduct: List<Product>? = null
    private var productItemsAdapter: ProductItemsAdapter? = null
    private var rv: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_dashboard, container, false)
        mDatabase = FirebaseDatabase.getInstance().getReference("Products")

        rv = view.findViewById(R.id.rv_product)
        rv!!.setHasFixedSize(true)
        rv!!.layoutManager = LinearLayoutManager(context)
        mProduct = ArrayList()
        getData()

        return view
    }


    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(data: DataSnapshot) {
                (mProduct as ArrayList<Product>).clear()
                for (snapshot in data.children) {
                    val product: Product? = snapshot.getValue(Product::class.java)

                    if (product != null) {
                        (mProduct as ArrayList<Product>).add(product)
                    }
                }
                productItemsAdapter =
                    context?.let { ProductItemsAdapter(mProduct as ArrayList<Product>, it) }
                rv!!.adapter = productItemsAdapter
            }

            override fun onCancelled(e: DatabaseError) {
                Toast.makeText(context, ""+e.message, Toast.LENGTH_LONG).show()
            }
        })
    }

}
