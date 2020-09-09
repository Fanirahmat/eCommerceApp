package com.mfanir.ecommerceapp.User.Setting

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.mfanir.ecommerceapp.Model.Users
import com.mfanir.ecommerceapp.R
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_update_profile.*

class UpdateProfileActivity : AppCompatActivity() {

    private lateinit var mDatabase: DatabaseReference
    private lateinit var storage: StorageReference
    private var imageUri: Uri? = null
    private lateinit var uploadTask: StorageTask<*>
    var myUri: String? = null
    var checker: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile)

        mDatabase = FirebaseDatabase.getInstance().reference
        storage = FirebaseStorage.getInstance().reference
        Paper.init(this)

        getData()

        iv_profile_update.setOnClickListener{
            CropImage.activity(imageUri)
                .setAspectRatio(1,1)
                .start(this);
        }

        btn_update_phone.setOnClickListener {
            Toast.makeText(this, "Belum tersedia", Toast.LENGTH_SHORT).show()
        }

        btn_update.setOnClickListener {
            checkData()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            var result = CropImage.getActivityResult(data)
            imageUri = result.uri

            iv_profile_update.setImageURI(imageUri)
            checker = true
        }
        else
        {
            Toast.makeText(this, "Error, Try again", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, UpdateProfileActivity::class.java))
            finish()
        }
    }

    private fun getData() {
        val phonePaper = Paper.book().read("phone", "1")
        if (phonePaper != null) {
            mDatabase.child("Users").child(phonePaper).addValueEventListener(object : ValueEventListener{
                override fun onDataChange(p0: DataSnapshot) {
                    val user: Users? = p0.getValue(Users::class.java)
                    et_name_update.setText(user!!.getName())
                    et_email_update.setText(user.getEmail())
                    et_password_update.setText(user.getPhone())
                    et_username_update.setText(user.getUsername())
                    et_address_update.setText(user.getAddress())
                    Picasso.get().load(user.getProfile()).into(iv_profile_update)
                }

                override fun onCancelled(p0: DatabaseError) {

                }
            })
        }
    }

    private fun checkData() {
        val name = et_name_update.text.toString()
        val email = et_email_update.text.toString()
        val password = et_password_update.text.toString()
        val username = et_username_update.text.toString()
        val address = et_address_update.text.toString()

        if (name.isEmpty()) {
            et_name_update.error = "name is empty"
            et_name_update.requestFocus()
        } else if (username.isEmpty()) {
            et_username_update.error = "username is empty"
            et_username_update.requestFocus()
        } else if (email.isEmpty()) {
            et_email_update.error = "email is empty"
            et_email_update.requestFocus()
        } else if (address.isEmpty()) {
            et_address_update.error = "address is empty"
            et_address_update.requestFocus()
        }else if (password.isEmpty()) {
            et_password_update.error = "password is empty"
            et_password_update.requestFocus()
        } else {
            saveDataWithImage(name, username, email, address, password)
        }

    }

    private fun saveDataWithImage(name: String, username: String, email: String, address: String, password: String) {
        var loadingBar = ProgressDialog(this)
        loadingBar.setTitle("Update Profile")
        loadingBar.setMessage("please wait, we are updating your account information")
        loadingBar.setCanceledOnTouchOutside(false)
        loadingBar.show()

        val phonePaper = Paper.book().read("phone", "1")
        if (imageUri != null && checker == true) {
            val fileRef = storage.child("User_profile_image").child(phonePaper + ".jpg")

            uploadTask = fileRef.putFile(imageUri!!)
            (uploadTask as UploadTask).continueWithTask(Continuation <UploadTask.TaskSnapshot, Task<Uri>>{ task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }

                }
                return@Continuation fileRef.downloadUrl
            }).addOnCompleteListener {task ->
                val dowloadUrl_result = task.result
                myUri = dowloadUrl_result.toString()

                val data = HashMap<String, Any>()
                data["profile"] = myUri!!
                data["address"] = address
                data["username"] = username
                data["name"] = name
                data["email"] = email
                data["password"] = password
                mDatabase.child("Users").child(phonePaper).updateChildren(data)

                loadingBar.dismiss()
                Toast.makeText(this, "successful", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, UpdateProfileActivity::class.java))
                finish()
            }
        } else {
            val data = HashMap<String, Any>()
            data["address"] = address
            data["username"] = username
            data["name"] = name
            data["email"] = email
            data["password"] = password
            mDatabase.child("Users").child(phonePaper).updateChildren(data)

            loadingBar.dismiss()
            Toast.makeText(this, "successful", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, UpdateProfileActivity::class.java))
            finish()
        }
    }
}
