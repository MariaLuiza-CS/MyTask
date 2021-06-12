package com.example.mytask.model

data class Task(
    var id: Int = 0,
    var title: String = "",
    var description: String = "",
    var priority_fist: Boolean = false,
    var priority_later: Boolean = false,
    var priority_delegate: Boolean = false,
    var priority_eliminate: Boolean = false
)