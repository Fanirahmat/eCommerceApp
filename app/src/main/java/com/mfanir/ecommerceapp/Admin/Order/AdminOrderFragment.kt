package com.mfanir.ecommerceapp.Admin.Order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.mfanir.ecommerceapp.Model.Order

import com.mfanir.ecommerceapp.R
import io.paperdb.Paper

class AdminOrderFragment : Fragment() {

    private var mDb: DatabaseReference? = null
    private var rv: RecyclerView? = null
    private var adminOrderItemsAdapter: AdminOrderItemsAdapter? = null
    private var mOrder = ArrayList<Order>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_order, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mDb = FirebaseDatabase.getInstance().reference

        Paper.init(context)
        val phone = Paper.book().read("phone","1")

        rv = requireView().findViewById(R.id.rv_order)
        rv!!.setHasFixedSize(true)
        rv!!.layoutManager = LinearLayoutManager(context)
        mOrder = ArrayList()

        getData(phone)
    }

    private fun getData(phone: String?) {
        mDb!!.child("Orders").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                (mOrder).clear()
                for (snapshot in snapshot.children) {
                    val order:Order? = snapshot.getValue(Order::class.java)
                    if (order != null) {
                        (mOrder).add(order)
                    }
                }

                adminOrderItemsAdapter = context?.let {
                    AdminOrderItemsAdapter(mOrder, it)
                }
                rv!!.adapter = adminOrderItemsAdapter
            }
            override fun onCancelled(e: DatabaseError) {
                Toast.makeText(context, ""+e.message, Toast.LENGTH_LONG).show()
            }
        })
    }

}
