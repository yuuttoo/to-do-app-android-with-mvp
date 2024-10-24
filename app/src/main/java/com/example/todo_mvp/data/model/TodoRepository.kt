package com.example.todo_mvp.data.model

class TodoRepository {
    private val todos = mutableListOf<TodoContent>()
    private var nextId = 1

    fun getTodos(): List<TodoContent> = todos.toList()

    fun addTodo(title: String): TodoContent {
        val todoItem =  TodoContent(nextId++, title)
        todos.add(todoItem)
        return todoItem
    }

    fun toggleTodo(todoContent: TodoContent) {
        todos.find { it.id == todoContent.id }?.let {
            it.isCompleted = !it.isCompleted
        }
    }

    fun deleteTodo(todoContent: TodoContent) {
        todos.removeAll { it.id == todoContent.id }
    }
}