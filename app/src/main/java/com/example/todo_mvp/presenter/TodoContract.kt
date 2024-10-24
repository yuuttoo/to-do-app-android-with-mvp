package com.example.todo_mvp.presenter

import com.example.todo_mvp.data.model.TodoContent


interface TodoContract {//類似viewModel的工作
interface View {
    fun showTodos(todos: List<TodoContent>)
    fun showEmptyState()
    fun showAddTodoSuccess()
    fun showAddTodoError()
    fun showDeleteTodoSuccess()
}

    interface Presenter {
        fun loadTodos()
        fun addTodo(title: String)
        fun toggleTodo(todo: TodoContent)
        fun deleteTodo(todo: TodoContent)
        fun onDestroy()
    }
}