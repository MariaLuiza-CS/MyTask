package com.example.mytask.repository

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.BaseColumns
import android.view.contentcapture.DataShareWriteAdapter
import com.example.mytask.R
import com.example.mytask.datasource.DataBaseDefinitions
import com.example.mytask.datasource.DataBaseHelper
import com.example.mytask.model.Task

class TaskRepository(context: Context) {

    private val dbHelper = DataBaseHelper(context)

    fun save(task: Task): Int {

        val db = dbHelper.writableDatabase

        val values = ContentValues()
        values.put(DataBaseDefinitions.Task.Columns.TITLE, task.title)
        values.put(DataBaseDefinitions.Task.Columns.DESCRIPTION, task.description)
        values.put(DataBaseDefinitions.Task.Columns.PRIORITY_FIRST, task.priority_fist)
        values.put(DataBaseDefinitions.Task.Columns.PRIORITY_LATER, task.priority_later)
        values.put(DataBaseDefinitions.Task.Columns.PRIORITY_DELEGATE, task.priority_delegate)
        values.put(DataBaseDefinitions.Task.Columns.PRIORITY_ELIMINATE, task.priority_eliminate)

        val newRowId = db.insert(DataBaseDefinitions.Task.TABLE_NAME, null, values)

        return newRowId.toInt()
    }


    fun delete(id: Int): Int {
        val db = dbHelper.writableDatabase

        val projection = arrayOf(
            DataBaseDefinitions.Task.Columns.ID,
            DataBaseDefinitions.Task.Columns.TITLE,
            DataBaseDefinitions.Task.Columns.DESCRIPTION,
            DataBaseDefinitions.Task.Columns.PRIORITY_FIRST,
            DataBaseDefinitions.Task.Columns.PRIORITY_LATER,
            DataBaseDefinitions.Task.Columns.PRIORITY_DELEGATE,
            DataBaseDefinitions.Task.Columns.PRIORITY_ELIMINATE
        )
        val selection = "${DataBaseDefinitions.Task.Columns.ID} = ?"
        val selectionArgs = arrayOf(id.toString())

        val deletedRows = db.delete(
            DataBaseDefinitions.Task.TABLE_NAME,
            selection,
            selectionArgs
        )
        return deletedRows
    }

    fun update(task: Task): Int {

        val db = dbHelper.writableDatabase

        val selection = "${DataBaseDefinitions.Task.Columns.ID} LIKE ?"
        val selectionArgs = arrayOf(task.id.toString())

        val values = ContentValues()
        values.put(DataBaseDefinitions.Task.Columns.TITLE, task.title)
        values.put(DataBaseDefinitions.Task.Columns.DESCRIPTION, task.description)
        values.put(DataBaseDefinitions.Task.Columns.PRIORITY_FIRST, task.priority_fist)
        values.put(DataBaseDefinitions.Task.Columns.PRIORITY_LATER, task.priority_later)
        values.put(DataBaseDefinitions.Task.Columns.PRIORITY_DELEGATE, task.priority_delegate)
        values.put(DataBaseDefinitions.Task.Columns.PRIORITY_ELIMINATE, task.priority_eliminate)

        val count = db.update(
            DataBaseDefinitions.Task.TABLE_NAME,
            values,
            selection,
            selectionArgs
        )

        return count
    }

    fun getTask(id: Int): Task {
        val db = dbHelper.readableDatabase

        val projection = arrayOf(
            DataBaseDefinitions.Task.Columns.ID,
            DataBaseDefinitions.Task.Columns.TITLE,
            DataBaseDefinitions.Task.Columns.DESCRIPTION,
            DataBaseDefinitions.Task.Columns.PRIORITY_FIRST,
            DataBaseDefinitions.Task.Columns.PRIORITY_LATER,
            DataBaseDefinitions.Task.Columns.PRIORITY_DELEGATE,
            DataBaseDefinitions.Task.Columns.PRIORITY_ELIMINATE
        )
        val selection = "${DataBaseDefinitions.Task.Columns.ID} = ?"
        val selectionArgs = arrayOf(id.toString())

        val cursor = db.query(
            DataBaseDefinitions.Task.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        val itemId = Task()

        if (cursor != null) {
            cursor.moveToNext()
            itemId.id = cursor.getInt(cursor.getColumnIndex(DataBaseDefinitions.Task.Columns.ID))
            itemId.title =
                cursor.getString(cursor.getColumnIndex(DataBaseDefinitions.Task.Columns.TITLE))
            itemId.description =
                cursor.getString(cursor.getColumnIndex(DataBaseDefinitions.Task.Columns.DESCRIPTION))
            itemId.priority_fist =
                cursor.getInt(cursor.getColumnIndex(DataBaseDefinitions.Task.Columns.PRIORITY_FIRST)) == 1
            itemId.priority_later =
                cursor.getInt(cursor.getColumnIndex(DataBaseDefinitions.Task.Columns.PRIORITY_LATER)) == 1
            itemId.priority_delegate =
                cursor.getInt(cursor.getColumnIndex(DataBaseDefinitions.Task.Columns.PRIORITY_DELEGATE)) == 1
            itemId.priority_eliminate =
                cursor.getInt(cursor.getColumnIndex(DataBaseDefinitions.Task.Columns.PRIORITY_ELIMINATE)) == 1
        }
        return itemId
    }

    fun getTasks(): ArrayList<Task> {
        val db = dbHelper.readableDatabase

        val projection = arrayOf(
            DataBaseDefinitions.Task.Columns.ID,
            DataBaseDefinitions.Task.Columns.TITLE,
            DataBaseDefinitions.Task.Columns.DESCRIPTION
        )

        val sortOrder = "${DataBaseDefinitions.Task.Columns.ID} DESC"

        val cursor = db.query(
            DataBaseDefinitions.Task.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            sortOrder
        )

        val itemIds = ArrayList<Task>()

        if (cursor != null) {
            with(cursor) {
                while (moveToNext()) {
                    val task = Task(
                        id = cursor.getInt(getColumnIndex(DataBaseDefinitions.Task.Columns.ID)),
                        title = cursor.getString(getColumnIndex(DataBaseDefinitions.Task.Columns.TITLE)),
                        description = cursor.getString(getColumnIndex(DataBaseDefinitions.Task.Columns.DESCRIPTION))
                    )
                    itemIds.add(task)
                }
            }
        }
        return itemIds
    }

}
