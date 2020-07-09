package com.mfanir.ecommerceapp.User.Setting

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.mfanir.ecommerceapp.Model.Users

import com.mfanir.ecommerceapp.R
import com.squareup.picasso.Picasso
import io.paperdb.Paper
import kotlinx.android.synthetic.main.fragment_setting.*

class SettingFragment : Fragment() {

    private lateinit var mDatabase: DatabaseReference
    var firebaseUser: FirebaseUser? = null
    var tv_update: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_setting, container, false)

        mDatabase = FirebaseDatabase.getInstance().reference
        firebaseUser = FirebaseAuth.getInstance().currentUser


        Paper.init(context)
        val paper = Paper.book().read("login", "1")
        val phonePaper = Paper.book().read("phone", "1")
        if (paper != null && paper == "1" && phonePaper != null) {
            mDatabase.child("Users").child(phonePaper).addValueEventListener(object : ValueEventListener{
                override fun onDataChange(p0: DataSnapshot) {
                    val user:Users? = p0.getValue(Users::class.java)
                    tv_name.text = user!!.getName()
                    tv_phoneOrder.text = "+62"+user!!.getPhone()
                    Picasso.get().load(user.getProfile()).into(iv_profile)
                }

                override fun onCancelled(p0: DatabaseError) {

                }
            })
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tv_updateProfile.setOnClickListener {
            val i = Intent(activity, UpdateProfileActivity::class.java)
            startActivity(i)
        }
    }

}
