package com.example.my_cook_book.db

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.example.my_cook_book.EditActivity
import com.example.my_cook_book.R

class MyAdapter(listMain: ArrayList<ListItem>, contextM: Context) : RecyclerView.Adapter<MyAdapter.MyHolder>() {
    var listArray = listMain
    var context = contextM
    class MyHolder(itemView: View, contextVH: Context) : RecyclerView.ViewHolder(itemView) {
        val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        val context = contextVH
        fun setData(item : ListItem){

            tvTitle.text =  item.title
            itemView.setOnClickListener {

                val intent = Intent(context, EditActivity::class.java).apply {

                    putExtra(MyConstants.TITLE_KEY, item.title)
                    putExtra(MyConstants.INGR_KEY, item.ingr)
                    putExtra(MyConstants.CONT_KEY, item.content)
                    putExtra(MyConstants.URI,item.uri)

                }
                context.startActivity(intent)

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyHolder(inflater.inflate(R.layout.rc_item, parent, false), context)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.setData(listArray.get(position))
    }

    override fun getItemCount(): Int {
        return listArray.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateAdapter(listItems:List<ListItem>){

        listArray.clear()
        listArray.addAll(listItems)
        notifyDataSetChanged()

    }
    fun removingItem(pos:Int, DbManager : MyDbManager){
        DbManager.removingItemFromDb(listArray[pos].id.toString())
        listArray.removeAt(pos)
        notifyItemRangeChanged(0, listArray.size)
        notifyItemRemoved(pos)
    }

}