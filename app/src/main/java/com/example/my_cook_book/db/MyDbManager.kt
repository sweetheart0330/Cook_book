package com.example.my_cook_book.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class MyDbManager(val context: Context) {
    val myDbHelper = MyDbHelper(context)
    var db: SQLiteDatabase? = null

    fun openDb(){
       db = myDbHelper.writableDatabase
    }
    fun insertToDb(title: String, content: String, ingredients: String, uri: String){
        val values = ContentValues().apply {
            put(MyDbNameClass.COLUMN_NAME_TITLE, title)
            put(MyDbNameClass.COLUMN_NAME_CONTENT, content)
            put(MyDbNameClass.COLUMN_NAME_INGREDIENTS, ingredients)
            put(MyDbNameClass.COLUMN_NAME_IMAGE_URI, uri)

        }
        db?.insert(MyDbNameClass.TABLE_NAME, null, values)
    }

    fun removingItemFromDb(id: String){
        val selection = BaseColumns._ID + "=$id"
        db?.delete(MyDbNameClass.TABLE_NAME, selection, null)
    }
    @SuppressLint("Range")
    fun readDbData() : ArrayList <ListItem> {
        val dataList = ArrayList<ListItem>()
        val cursor = db?.query(
            MyDbNameClass.TABLE_NAME, null, null, null,
            null, null, null
        )
        // with(cursor)
        //Если не заработает поменять на this?.moveToNext()!!
        while (cursor?.moveToNext()!!) {
            val dataText = cursor.getString(cursor.getColumnIndex(MyDbNameClass.COLUMN_NAME_TITLE))
            val dataIngr = cursor.getString(cursor.getColumnIndex(MyDbNameClass.COLUMN_NAME_INGREDIENTS))
            val dataCont = cursor.getString(cursor.getColumnIndex(MyDbNameClass.COLUMN_NAME_CONTENT))
            val dataUri = cursor.getString(cursor.getColumnIndex(MyDbNameClass.COLUMN_NAME_IMAGE_URI))
            val dataID = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))
            var item = ListItem()
            item.title = dataText
            item.ingr = dataIngr
            item.content = dataCont
            item.uri = dataUri
            item.id = dataID
            dataList.add(item)
        }
    //}
        cursor!!.close()
        return dataList
    }

    fun closeDb() {
        db?.close()
    }
}