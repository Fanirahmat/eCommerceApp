package com.mfanir.ecommerceapp.Admin.Product

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.mfanir.ecommerceapp.Model.Product

import com.mfanir.ecommerceapp.R
import io.paperdb.Paper
import kotlinx.android.synthetic.main.fragment_product.*

class ProductFragment : Fragment() {

    private var mDb: DatabaseReference? = null
    private var rv: RecyclerView? = null
    private var productAdapter: ProductAdapter? = null
    private var mProduct = ArrayList<Product>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mDb = FirebaseDatabase.getInstance().reference

        Paper.init(context)
        val phone = Paper.book().read("phone","1")

        iv_addProduct.setOnClickListener {
            val i = Intent(context, SelectCategory::class.java)
            startActivity(i)
        }

        rv = requireView().findViewById(R.id.rv_productAdmin)
        rv!!.setHasFixedSize(true)
        rv!!.layoutManager = LinearLayoutManager(context)

        showProduct(phone)
    }

    private fun showProduct(phone: String?) {
        mDb!!.child("Products").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                mProduct.clear()
                for (data in snapshot.children) {
                    val product: Product? = data.getValue(Product::class.java)
                    val sellerID = product!!.getSeller()
                    if (product != null) {
                        if (sellerID == "+62"+phone) {
                            mProduct.add(product)
                        }
                    }

                }
                productAdapter = context?.let { ProductAdapter(mProduct, it) }
                rv!!.adapter = productAdapter
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }


}
