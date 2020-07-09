package com.mfanir.ecommerceapp.Admin.Product

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mfanir.ecommerceapp.R
import kotlinx.android.synthetic.main.activity_select_category.*

class SelectCategory : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_category)

        books.setOnClickListener {
            val i = Intent(this, AddProductActivity::class.java)
            i.putExtra("category", "Books")
            startActivity(i)
        }

        kitchens.setOnClickListener {
            val i = Intent(this, AddProductActivity::class.java)
            i.putExtra("category", "Kitchens")
            startActivity(i)
        }

        electronics.setOnClickListener {
            val i = Intent(this, AddProductActivity::class.java)
            i.putExtra("category", "Electronics")
            startActivity(i)
        }

        fashion_kids.setOnClickListener {
            val i = Intent(this, AddProductActivity::class.java)
            i.putExtra("category", "Fashion kids")
            startActivity(i)
        }

        fashion_muslim.setOnClickListener {
            val i = Intent(this, AddProductActivity::class.java)
            i.putExtra("category", "Fashion muslim")
            startActivity(i)
        }

        fashion_man.setOnClickListener {
            val i = Intent(this, AddProductActivity::class.java)
            i.putExtra("category", "Fashion man")
            startActivity(i)
        }

        fashion_woman.setOnClickListener {
            val i = Intent(this, AddProductActivity::class.java)
            i.putExtra("category", "Fashion woman")
            startActivity(i)
        }

        film_and_music.setOnClickListener {
            val i = Intent(this, AddProductActivity::class.java)
            i.putExtra("category", "Film and music")
            startActivity(i)
        }

        gaming.setOnClickListener {
            val i = Intent(this, AddProductActivity::class.java)
            i.putExtra("category", "Gaming")
            startActivity(i)
        }

        hp_and_tablet.setOnClickListener {
            val i = Intent(this, AddProductActivity::class.java)
            i.putExtra("category", "Handphone and tablet")
            startActivity(i)
        }

        healthy.setOnClickListener {
            val i = Intent(this, AddProductActivity::class.java)
            i.putExtra("category", "Healthy")
            startActivity(i)
        }

        foods.setOnClickListener {
            val i = Intent(this, AddProductActivity::class.java)
            i.putExtra("category", "Foods")
            startActivity(i)
        }
    }
}
