package com.example.my_cook_book

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class Info : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
    }

    fun onClickMenu(view: View) {
        var i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }
}