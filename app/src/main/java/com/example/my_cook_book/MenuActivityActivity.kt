package com.example.my_cook_book

import android.content.ClipData.Item
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.my_cook_book.db.MyAdapter
import com.example.my_cook_book.db.MyDbManager
import org.w3c.dom.Text

class MenuActivityActivity : AppCompatActivity() {
    val myDbManager = MyDbManager(this)
    val myAdapter = MyAdapter(ArrayList(), this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_activity)
        init()
    }

    fun onClickNew(view: View) {
        var i = Intent(this, EditActivity::class.java)
        startActivity(i)
    }

    override fun onResume() {
        super.onResume()
        myDbManager.openDb()
        fillAdapter()
    }

    override fun onDestroy() {
        super.onDestroy()
        myDbManager.closeDb()
    }

    fun init(){
        //может не зааработать
        val rcView = findViewById<RecyclerView>(R.id.rcView)
        val layoutmanager =  LinearLayoutManager(this)
        rcView.layoutManager = layoutmanager
        val swapHelper = getSwapMg()
        swapHelper.attachToRecyclerView(rcView)
        rcView.adapter = myAdapter
    }

    fun fillAdapter() {
        val list = myDbManager.readDbData()
        val tvNoElem  = findViewById<TextView>(R.id.tvNoElements2)
        myAdapter.updateAdapter(myDbManager.readDbData())
        if (list.size  > 0) {
            tvNoElem.visibility = View.GONE
        } else {
            tvNoElem.visibility = View.VISIBLE
        }
    }

    private fun getSwapMg() : ItemTouchHelper {
        return ItemTouchHelper(object:ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                myAdapter.removingItem(viewHolder.adapterPosition, myDbManager)
            }
        })
    }

}