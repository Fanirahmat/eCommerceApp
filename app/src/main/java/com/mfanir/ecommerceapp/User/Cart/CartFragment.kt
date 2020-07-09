package com.mfanir.ecommerceapp.User.Cart

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.mfanir.ecommerceapp.Model.Cart

import com.mfanir.ecommerceapp.R
import io.paperdb.Paper
import kotlinx.android.synthetic.main.fragment_cart.*


class CartFragment : Fragment() {

    private var mDb: DatabaseReference? = null
    private var rv: RecyclerView? = null
    private var cartItemsAdapter: CartItemsAdapter? =null
    private var mCart = ArrayList<Cart>()
    private var total:Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mDb = FirebaseDatabase.getInstance().reference
        mCart = ArrayList()

        Paper.init(context)
        val phone = Paper.book().read("phone","1")

        checkOrderState(phone)

        rv = requireView().findViewById(R.id.rv_cart)
        rv!!.setHasFixedSize(true)
        rv!!.layoutManager = LinearLayoutManager(context)

        getData(phone)

        btn_next.setOnClickListener {
            if (total != 0) {
                val intent = Intent(context, ConfirmFinalOrderActivity::class.java)
                intent.putExtra("total", total.toString())
                startActivity(intent)
            }
        }

    }

    private fun checkOrderState(phone: String) {

        mDb!!.child("Orders").child(phone).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists())
                {
                    val shippingState = snapshot.child("state").value.toString()
                    //val name = snapshot.child("name").value.toString()
                    if (shippingState.equals("shipping"))
                    {
                        msg.text = "Pesananmu sedang dikirim"
                        msg.visibility = View.VISIBLE
                        btn_next.visibility = View.GONE
                        rv_cart.visibility = View.INVISIBLE
                    }
                    else if (shippingState.equals("pending"))
                    {
                        msg.text = "Pesananmu menunggu konfirmasi penjual"
                        msg.visibility = View.VISIBLE
                        btn_next.visibility = View.GONE
                        rv_cart.visibility = View.INVISIBLE
                    }
                }
                else
                {
                    msg.visibility = View.GONE
                    btn_next.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })


    }

    private fun getData(phone: String?) {
        mDb!!.child("Cart List").child("User View").child(phone.toString()).child("Products").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(data: DataSnapshot) {
                (mCart as ArrayList<Cart>).clear()
                for (snapshot in data.children) {
                    val cart: Cart? = snapshot.getValue(Cart::class.java)
                    if (cart != null) {
                        (mCart).add(cart)
                    }
                }

                for (a in mCart.indices) {
                    total += mCart[a].price!!.toInt()
                }
                mCart.add(Cart("","", "", "Total Bayar", total.toString(),"",""))


                cartItemsAdapter = context?.let { CartItemsAdapter(mCart, it) }
                rv!!.adapter = cartItemsAdapter
            }

            override fun onCancelled(e: DatabaseError) {
                Toast.makeText(context, ""+e.message, Toast.LENGTH_LONG).show()
            }
        })
    }

}
