package com.example.mytask.datasource

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class DataBaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(CREATE_DATABASE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "task.db"
        private const val DATABASE_VERSION = 1000

        private const val CREATE_DATABASE_TABLE =
            "CREATE TABLE ${DataBaseDefinitions.Task.TABLE_NAME} (" +
                    "${DataBaseDefinitions.Task.Columns.ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${DataBaseDefinitions.Task.Columns.TITLE} TEXT," +
                    "${DataBaseDefinitions.Task.Columns.DESCRIPTION} TEXT," +
                    "${DataBaseDefinitions.Task.Columns.PRIORITY_FIRST} INTEGER," +
                    "${DataBaseDefinitions.Task.Columns.PRIORITY_LATER} INTEGER," +
                    "${DataBaseDefinitions.Task.Columns.PRIORITY_DELEGATE} INTEGER," +
                    "${DataBaseDefinitions.Task.Columns.PRIORITY_ELIMINATE} INTEGER);"

        private const val SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS ${DataBaseDefinitions.Task.TABLE_NAME}"

    }

}