package com.mfanir.ecommerceapp.Admin.Product

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.PermissionRequest
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.mfanir.ecommerceapp.Model.Product
import com.mfanir.ecommerceapp.R
import com.squareup.picasso.Picasso
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_product_admin_detail.*
import java.util.*

class ProductAdminDetailActivity : AppCompatActivity() {

    private var statusAdd:Boolean = false
    private lateinit var filePath: Uri
    private var statusImg: String? = ""
    private lateinit var mFirebaseDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_admin_detail)

        val data = intent.getParcelableExtra<Product>("data")
        mFirebaseDatabase = FirebaseDatabase.getInstance().reference
        Paper.init(this)


        if (data != null) {
            Glide.with(this)
                .load(data.image)
                .apply(RequestOptions.circleCropTransform())
                .into(product_img2)
            product_name2.setText(data.pname)
            product_desc2.setText(data.description)
            product_price2.setText(data.price)
            categoryName2.text = data.category
        }

        product_img2.setOnClickListener {
            ImagePicker.with(this)
                .galleryOnly()
                .compress(1024)
                .start()
        }

        editProduct_btn.setOnClickListener {
            val et_pname = product_name2.text.toString()
            val et_desc = product_desc2.text.toString()
            val et_price = product_price2.text.toString()

            if (et_pname == "") {
                product_name2.error = "Nama product kosong"
                product_name2.requestFocus()
            } else if (et_desc == "") {
                product_desc2.error = "Deskripsi product kosong"
                product_desc2.requestFocus()
            } else if (et_price == "") {
                product_price2.error = "Harga product kosong"
                product_price2.requestFocus()
            } else {
                saveUpdate(et_pname, et_desc, et_price, filePath)
            }
        }

    }

    private fun saveUpdate(etPname: String, etDesc: String, etPrice: String, filePath: Uri) {
        val progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Uploading new photo...")
        progressDialog.show()

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/User_product_images/$filename");

        ref.putFile(filePath).addOnSuccessListener {
            progressDialog.dismiss()

            ref.downloadUrl.addOnSuccessListener {
                val phone = Paper.book().read("phone","1")
                val map = HashMap<String, Any>()
                map["pname"] = etPname
                map["description"] = etDesc
                map["price"] = etPrice
                map["image"] = it.toString()
                map["search"] = etPname.toLowerCase(Locale.ROOT)
                mFirebaseDatabase.child("Products").child(phone).updateChildren(map).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Updated" , Toast.LENGTH_SHORT).show()
                        startActivity(Intent())
                    }
                }
            }
        }.addOnFailureListener { e ->
            progressDialog.dismiss()
            Toast.makeText(this, "Failed" + e.message, Toast.LENGTH_SHORT).show()
        }.addOnProgressListener { taskSnapshot ->
            val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot
                .totalByteCount
            progressDialog.setMessage("Uploaded " + progress.toInt() + "%")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            statusAdd = true
            filePath = data?.data!!

            Glide.with(this)
                .load(filePath)
                .apply(RequestOptions.circleCropTransform())
                .into(product_img2)


        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }


}
