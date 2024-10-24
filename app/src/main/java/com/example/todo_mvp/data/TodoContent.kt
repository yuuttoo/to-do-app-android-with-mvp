package com.example.todo_mvp.data

data class TodoContent(
    val id: Int,
    val title: String,
    var isCompleted: Boolean = false
)
