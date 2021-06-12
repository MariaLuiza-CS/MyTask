package com.example.mytask.datasource

class DataBaseDefinitions {
    object Task {
        const val TABLE_NAME = "tbl_task"

        object Columns {
            const val ID = "id"
            const val TITLE = "title"
            const val DESCRIPTION = "description"
            const val PRIORITY_FIRST = "priority_fist"
            const val PRIORITY_LATER = "priority_later"
            const val PRIORITY_DELEGATE = "priority_delegate"
            const val PRIORITY_ELIMINATE = "priority_eliminate"
        }
    }
}