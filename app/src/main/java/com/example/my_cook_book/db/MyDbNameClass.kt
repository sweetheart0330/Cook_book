package com.example.my_cook_book.db

import android.provider.BaseColumns
object MyDbNameClass : BaseColumns{
    const val TABLE_NAME = "my_table"
    const val COLUMN_NAME_TITLE = "title"
    const val COLUMN_NAME_CONTENT = "content"
    const val COLUMN_NAME_INGREDIENTS = "ingredients"
    const val COLUMN_NAME_IMAGE_URI = "uri"

    const val DATABASE_VERSION = 3
    const val DATABASE_NAME = "FeedReader.db"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY, $COLUMN_NAME_TITLE TEXT,$COLUMN_NAME_INGREDIENTS TEXT, " +
            "$COLUMN_NAME_CONTENT TEXT, $COLUMN_NAME_IMAGE_URI TEXT"
    const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"

}