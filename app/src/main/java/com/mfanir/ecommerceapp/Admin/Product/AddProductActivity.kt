package com.mfanir.ecommerceapp.Admin.Product

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.mfanir.ecommerceapp.Admin.AdminHomeActivity
import com.mfanir.ecommerceapp.R
import kotlinx.android.synthetic.main.activity_add_product.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class AddProductActivity : AppCompatActivity() {

    private var category: String? = ""
    private var galleryPick = 1
    private var imageUri: Uri? = null
    private var saveCurrentDate: String? = ""
    private var saveCurrentTime: String? = ""
    private var randomKey: String? = ""
    private lateinit var refStore: StorageReference
    private lateinit var refDb: DatabaseReference

    var price: String? = ""
    var desc: String? = ""
    var nameProduct: String? = ""

    var loadingBar: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        loadingBar = ProgressDialog(this)
        refStore = FirebaseStorage.getInstance().reference.child("User_product_images")
        refDb = FirebaseDatabase.getInstance().reference

        category = intent.getStringExtra("category")
        categoryName.text = category

        product_img.setOnClickListener {
            openGallery()
        }

        btn_upload_product.setOnClickListener {
            validateProductData()
        }

    }

    private fun openGallery() {
        val gallery = Intent()
        gallery.setAction(Intent.ACTION_GET_CONTENT)
        gallery.setType("image/*")
        startActivity(gallery)
        startActivityForResult(gallery, galleryPick)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == galleryPick && resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.data
            product_img.setImageURI(imageUri)
        }
    }

    private fun validateProductData() {
        desc = product_desc.text.toString()
        price = product_price.text.toString()
        nameProduct = product_name.text.toString()

        if (imageUri == null) {
            Toast.makeText(this, "Gambar kosong", Toast.LENGTH_SHORT ).show()
        } else if (nameProduct!!.isEmpty()) {
            product_name.error = "silahkan isi nama produk"
            product_name.requestFocus()
        } else if (desc!!.isEmpty()) {
            product_desc.error = "silahkan isi deskripsi"
            product_desc.requestFocus()
        } else if (price!!.isEmpty()) {
            product_price.error = "silahkan beri harga"
            product_price.requestFocus()
        } else {
            loadingBar!!.setTitle("Upload Product")
            loadingBar!!.setMessage("Please wait...")
            loadingBar!!.setCanceledOnTouchOutside(false)
            loadingBar!!.show()
            storeProductImage()
        }
    }

    private fun storeProductImage() {
        val calendar = Calendar.getInstance()

        val currentDate = SimpleDateFormat("MMM dd, yyyy")
        saveCurrentDate = currentDate.format(calendar.time)

        val currentTime = SimpleDateFormat(" HH:mm:ss")
        saveCurrentTime = currentTime.format(calendar.time)

        randomKey = saveCurrentDate + saveCurrentTime

        var filePath = refStore.child(imageUri!!.lastPathSegment.toString() + randomKey + ".jpg")
        val uploadTask = filePath.putFile(imageUri!!)
        uploadTask.addOnFailureListener { e ->
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
        }.addOnSuccessListener {
            val uriTask = uploadTask.continueWithTask(Continuation <UploadTask.TaskSnapshot, Task<Uri>>{ task ->
                if (!task.isSuccessful) {
                    loadingBar!!.dismiss()
                    task.exception?.let {
                        throw it
                    }
                }
                return@Continuation filePath.downloadUrl
            }).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUrl = task.result
                    val url = downloadUrl.toString()

                    saveDataProductToDB(url)
                }
            }
        }
    }

    private fun saveDataProductToDB(url: String) {
        val map = HashMap<String, Any>()
        map["pid"] = randomKey!!
        map["date"] = saveCurrentDate!!
        map["time"] = saveCurrentTime!!
        map["description"] = desc!!
        map["image"] = url
        map["category"] = category!!
        map["price"] = price!!
        map["pname"] = nameProduct!!
        map["seller"] = FirebaseAuth.getInstance().currentUser!!.phoneNumber!!
        refDb.child("Products").child(randomKey!!).updateChildren(map)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    loadingBar!!.dismiss()
                    Toast.makeText(this, "Product is added succesfully...", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, AdminHomeActivity::class.java))
                    finish()

                }
                else
                {
                    loadingBar!!.dismiss()
                    Toast.makeText(this, task.exception.toString(), Toast.LENGTH_LONG).show()
                }
            }
    }


}
