package com.mfanir.ecommerceapp.User.Dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    private var categoryStatus: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mDatabase = FirebaseDatabase.getInstance().getReference("Products")

        //search = view.findViewById(R.id.searchProductET)

        rv = requireView().findViewById(R.id.rv_product)
        rv!!.setHasFixedSize(true)
        rv!!.layoutManager = LinearLayoutManager(context)
        mProduct = ArrayList()

        /*
        search!!.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                showSearchData(s.toString().toLowerCase())
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

         */
        all.setOnClickListener {
            categoryStatus = ""
            getData()
        }

        book.setOnClickListener {
            categoryStatus = "Books"
            getDataByCategory(categoryStatus!!)
            Toast.makeText(context,  categoryStatus, Toast.LENGTH_SHORT).show()
        }

        kitchens.setOnClickListener {
            categoryStatus = "Kitchens"
            getDataByCategory(categoryStatus!!)
            Toast.makeText(context, categoryStatus, Toast.LENGTH_SHORT).show()
        }

        electronics.setOnClickListener {
            getDataByCategory(categoryStatus!!)
            categoryStatus = "Electronics"
            Toast.makeText(context,  categoryStatus, Toast.LENGTH_SHORT).show()
        }

        fashion_kids.setOnClickListener {
            getDataByCategory(categoryStatus!!)
            categoryStatus = "Fashion kids"
            Toast.makeText(context,  categoryStatus, Toast.LENGTH_SHORT).show()
        }

        fashion_muslim.setOnClickListener {
            categoryStatus = "Fashion muslim"
            getDataByCategory(categoryStatus!!)
            Toast.makeText(context, categoryStatus, Toast.LENGTH_SHORT).show()
        }

        fashion_man.setOnClickListener {
            categoryStatus = "Fashion man"
            getDataByCategory(categoryStatus!!)
            Toast.makeText(context, categoryStatus, Toast.LENGTH_SHORT).show()
        }

        fashion_woman.setOnClickListener {
            categoryStatus = "Fashion woman"
            getDataByCategory(categoryStatus!!)
            Toast.makeText(context,  categoryStatus, Toast.LENGTH_SHORT).show()
        }

        film_and_music.setOnClickListener {
            categoryStatus = "Film and music"
            getDataByCategory(categoryStatus!!)
            Toast.makeText(context, categoryStatus, Toast.LENGTH_SHORT).show()
        }

        gaming.setOnClickListener {
            categoryStatus = "Gaming"
            getDataByCategory(categoryStatus!!)
            Toast.makeText(context,  categoryStatus, Toast.LENGTH_SHORT).show()
        }

        hp_and_tablet.setOnClickListener {
            categoryStatus = "Handphone and tablet"
            getDataByCategory(categoryStatus!!)
            Toast.makeText(context,  categoryStatus, Toast.LENGTH_SHORT).show()
        }

        healthy.setOnClickListener {
            categoryStatus = "Healthy"
            getDataByCategory(categoryStatus!!)
            Toast.makeText(context,  categoryStatus, Toast.LENGTH_SHORT).show()
        }

        foods.setOnClickListener {
            categoryStatus = "Foods"
            getDataByCategory(categoryStatus!!)
            Toast.makeText(context,  categoryStatus, Toast.LENGTH_SHORT).show()
        }


        getData()

    }

    private fun getDataByCategory(categoryStatus: String) {
        //val query = mDatabase.orderByChild("search").startAt(categoryStatus)
        mDatabase.orderByChild("search").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(data: DataSnapshot) {
                (mProduct as ArrayList<Product>).clear()
                for (snapshot in data.children) {
                    val product: Product? = snapshot.getValue(Product::class.java)
                    val category = snapshot.child("category").value.toString()
                    if (product != null) {
                        if (category == categoryStatus) {
                            (mProduct as ArrayList<Product>).add(product)
                        }
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

    private fun getData() {
        mDatabase.orderByChild("search").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(data: DataSnapshot) {
                (mProduct as ArrayList<Product>).clear()
                if (categoryStatus == "") {
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
            }

            override fun onCancelled(e: DatabaseError) {
                Toast.makeText(context, ""+e.message, Toast.LENGTH_LONG).show()
            }
        })
    }






}
