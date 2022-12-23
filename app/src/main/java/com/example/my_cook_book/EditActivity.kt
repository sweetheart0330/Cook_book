package com.example.my_cook_book

import android.app.Activity
import android.content.Intent
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.my_cook_book.db.MyConstants
import com.example.my_cook_book.db.MyDbManager

class EditActivity : AppCompatActivity() {
    var tempImageUri = "empty"
    val imageRequestCode = 10
    val myDbManager = MyDbManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_activity)
        getMyIntents()
    }

    override fun onResume() {
        super.onResume()
        myDbManager.openDb()
    }

    override fun onDestroy() {
        super.onDestroy()
        myDbManager.closeDb()
    }
    fun onClickSave(view: View) {
        //Получаем данные и конвертируем их в String для проверки на пустоту
        val editTitle = findViewById<EditText>(R.id.editTitle).text.toString()
        val editIngredients = findViewById<EditText>(R.id.editIngredients).text.toString()
        val editContent = findViewById<EditText>(R.id.editContent).text.toString()
       // val uri = findViewById<EditText>(R.id.imMyImage).text.toString()

        if (editTitle != "" && editIngredients != "" && editContent != "") {
            myDbManager.insertToDb(editTitle, editIngredients, editContent, tempImageUri)
            finish()
        }

    }

    fun onClickAddImage(view: View) {
        val mainImageLayout = findViewById<ConstraintLayout>(R.id.myImageLayout)
        val fbAddImage = findViewById<ImageButton>(R.id.fbAddImage)
        mainImageLayout.visibility = View.VISIBLE
        fbAddImage.visibility = View.GONE
    }

    fun onClickDeleteImage(view: View) {
        val mainImageLayout = findViewById<ConstraintLayout>(R.id.myImageLayout)
        val fbAddImage = findViewById<ImageButton>(R.id.fbAddImage)
        mainImageLayout.visibility = View.GONE
        fbAddImage.visibility = View.VISIBLE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val inMyImage = findViewById<ImageView>(R.id.imMyImage)
        if (resultCode == Activity.RESULT_OK &&  requestCode == imageRequestCode) {

            inMyImage.setImageURI(data?.data)
            tempImageUri = data?.data.toString()

        }
    }
    fun onClickEditImage(view: View) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        startActivityForResult(intent,imageRequestCode)
    }
    fun onClickMenu(view: View) {
        var i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }

    fun getMyIntents(){
        val editTitle = findViewById<EditText>(R.id.editTitle)
        val editIngredients = findViewById<EditText>(R.id.editIngredients)
        val editContent = findViewById<EditText>(R.id.editContent)
        val mainImageLayout = findViewById<ConstraintLayout>(R.id.myImageLayout)
        val fbAddImage = findViewById<ImageButton>(R.id.fbAddImage)
        val imMyImage = findViewById<ImageView>(R.id.imMyImage)
        val imButtonDeleteImage = findViewById<ImageButton>(R.id.imButtonDeleteImage)
        val imButtonEditImage = findViewById<ImageButton>(R.id.imButtonEditImage)
        val i = intent

        if (i != null) {

            if (i.getStringExtra(MyConstants.TITLE_KEY)  != null) {
                editTitle.setText(i.getStringExtra(MyConstants.TITLE_KEY))
                editIngredients.setText(i.getStringExtra(MyConstants.CONT_KEY))
                editContent.setText(i.getStringExtra(MyConstants.INGR_KEY))
                if (i.getStringExtra(MyConstants.URI) != "empty") {

                    mainImageLayout.visibility = View.VISIBLE
                    imMyImage.setImageURI(Uri.parse(i.getStringExtra(MyConstants.URI)))
                    imButtonDeleteImage.visibility = View.GONE
                    imButtonEditImage.visibility = View.GONE

                }
            }

        }

    }
}