package com.example.my_cook_book

import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.example.my_cook_book.db.MyDbManager
import android.widget.TextView
import org.w3c.dom.Text
import java.util.*

class MainActivity : AppCompatActivity() {
    val myDbManager = MyDbManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        myDbManager.openDb()
    }

    fun onClickAdd(view: View) {
        var i = Intent(this, EditActivity::class.java)
        startActivity(i)
    }
    fun onClickShow(view: View) {
        var i = Intent(this, MenuActivityActivity::class.java)
        startActivity(i)
    }

    fun onClickHelp(view: View) {
        var i = Intent(this, Info::class.java)
        startActivity(i)
    }

    fun OnClickExit(view: View) {

        finishAffinity()

    }
    var i = 0;
    fun onClickChangeLang(view: View) {
        if (i % 2 == 1) {
            val locale = Locale("en")
            changeLocale(locale)
        } else {
            val locale = Locale("ru")
            changeLocale(locale)
        }
        i++
    }

    private fun changeLocale(locale: Locale) {
        Locale.setDefault(locale)
        val configuration = Configuration()
        configuration.setLocale(locale)
        baseContext.resources
            .updateConfiguration(
                configuration,
                baseContext
                    .resources
                    .displayMetrics
            )
        val addRecipe = findViewById<Button>(R.id.addReciepe)
        addRecipe.setText(R.string.AddReciepeButton)
        val seeReciepe = findViewById<Button>(R.id.showReciepe)
        seeReciepe.setText(R.string.ShowRecipesButton)
        val helpplz = findViewById<Button>(R.id.HelpPLZ)
        helpplz.setText(R.string.Helpplz)
        val exit = findViewById<Button>(R.id.deadButton)
        exit.setText(R.string.Exit)
        val lang = findViewById<Button>(R.id.Language)
        lang.setText(R.string.LanguageButton)
    }

    override fun onDestroy() {
          super.onDestroy()
          myDbManager.closeDb()
    }
}