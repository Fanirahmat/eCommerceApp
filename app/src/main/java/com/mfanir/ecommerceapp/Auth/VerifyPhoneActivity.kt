package com.mfanir.ecommerceapp.Auth

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.*
import com.mfanir.ecommerceapp.Admin.AdminHomeActivity
import com.mfanir.ecommerceapp.R
import com.mfanir.ecommerceapp.User.HomeActivity
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_verify_phone.*
import java.util.concurrent.TimeUnit


class VerifyPhoneActivity : AppCompatActivity() {

    private lateinit var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var ref: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    var verificationId = ""
    var resendToken = ""
    private var phone: String? = ""
    private var status: String? = ""
    private var loadingBar: ProgressDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_phone)

        Paper.init(this)

        loadingBar = ProgressDialog(this)
        mAuth = FirebaseAuth.getInstance()
        ref = FirebaseDatabase.getInstance().reference
        status = intent.getStringExtra("status")
        phone = intent.getStringExtra("phone")
        sendVerification(phone)

        btn_verify.setOnClickListener {
            val otp  = et_otp.text.toString()
            if (otp.isEmpty()) {
                et_otp.error = "otp?"
                et_otp.requestFocus()
            } else {
                verify(otp)
            }
        }

    }

    private fun verificationCallbacks() {
        mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                val code = credential.smsCode
                if (code != null)
                {
                    et_otp.setText(code)
                }
                else {
                    Toast.makeText(applicationContext, "code : $credential", Toast.LENGTH_LONG).show()
                }

            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(baseContext, e.message, Toast.LENGTH_LONG).show()
            }

            override fun onCodeSent(
                verification: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                super.onCodeSent(verification, token)
                verificationId = verification
                resendToken = token.toString()
            }

        }
    }


    private fun verify(codeByUser: String) {
        loadingBar!!.setTitle("Login Account")
        loadingBar!!.setMessage("Please wait...")
        loadingBar!!.setCanceledOnTouchOutside(false)
        loadingBar!!.show()
        val credential = PhoneAuthProvider.getCredential(verificationId, codeByUser)
        signIn(credential)
    }

    private fun sendVerification(phone: String?) {
        verificationCallbacks()
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            "+62$phone",        // Phone number to verify
            60,                 // Timeout duration
            TimeUnit.SECONDS,   // Unit of timeout
            TaskExecutors.MAIN_THREAD,               // Activity (for callback binding)
            mCallbacks)        // OnVerificationStateChangedCallbacks
    }

    private fun signIn(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    inputData()
                } else {
                    Toast.makeText(baseContext, "error : " + task.exception, Toast.LENGTH_LONG).show()
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        Toast.makeText(this, "The verification code entered was invalid", Toast.LENGTH_LONG).show()
                    }
                }
            }

    }

    private fun inputData() {
        val name = intent.getStringExtra("name")
        val email = intent.getStringExtra("email")
        val phone = intent.getStringExtra("phone")
        val username = intent.getStringExtra("username")
        val password = intent.getStringExtra("password")

        if (status == "user") {
            ref.child("Users").child(phone!!).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    if (p0.exists()) {
                        Toast.makeText(applicationContext, "Welcome User", Toast.LENGTH_LONG).show()
                        Paper.book().write("login", "1")
                        Paper.book().write("phone", phone)
                        val i = Intent(applicationContext, HomeActivity::class.java)
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(i)
                    } else {
                        val data = HashMap<String, Any>()
                        data["name"] = name!!
                        data["email"] = email!!
                        data["phone"] = phone
                        data["username"] = username!!
                        data["password"] = password!!
                        data["profile"] = ""
                        ref.child("Users").child(phone).setValue(data)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    loadingBar!!.dismiss()
                                    Toast.makeText(applicationContext, "Welcome User", Toast.LENGTH_LONG).show()
                                    Paper.book().write("login", "1")
                                    Paper.book().write("phone", phone)
                                    val i = Intent(applicationContext, HomeActivity::class.java)
                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                    startActivity(i)
                                } else {
                                    loadingBar!!.dismiss()
                                    Toast.makeText(applicationContext, "Gagal menambahkan data ke database", Toast.LENGTH_LONG).show()
                                }
                            }
                    }
                }

                override fun onCancelled(p0: DatabaseError) {

                }
            })
        }
        else
        {
            ref.child("Sellers").child(phone!!).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    if (p0.exists()) {
                        Toast.makeText(applicationContext, "Welcome Seller", Toast.LENGTH_LONG).show()
                        Paper.book().write("login", "2")
                        Paper.book().write("phone", phone)
                        val i = Intent(applicationContext, AdminHomeActivity::class.java)
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(i)
                    } else {
                        val data = HashMap<String, Any>()
                        data["name"] = name!!
                        data["email"] = email!!
                        data["phone"] = phone
                        data["username"] = username!!
                        data["password"] = password!!
                        data["profile"] = "https://firebasestorage.googleapis.com/v0/b/ecommerce-bd81e.appspot.com/o/Asset_image%2F043-member%20card.png?alt=media&token=05ef6a57-d673-47b5-82d0-2b1f92a9169f"
                        ref.child("Sellers").child(phone).setValue(data)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    loadingBar!!.dismiss()
                                    Toast.makeText(applicationContext, "Welcome Seller", Toast.LENGTH_LONG).show()
                                    Paper.book().write("login", "2")
                                    Paper.book().write("phone", phone)
                                    val i = Intent(applicationContext, AdminHomeActivity::class.java)
                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                    startActivity(i)
                                } else {
                                    loadingBar!!.dismiss()
                                    Toast.makeText(applicationContext, "Gagal menambahkan data ke database", Toast.LENGTH_LONG).show()
                                }
                            }
                    }
                }

                override fun onCancelled(p0: DatabaseError) {

                }
            })
        }


    }


}
