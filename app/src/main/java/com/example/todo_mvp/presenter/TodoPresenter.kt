package com.example.todo_mvp.presenter

import android.os.Parcel
import android.os.Parcelable
import com.example.todo_mvp.data.TodoContent
import com.example.todo_mvp.data.TodoContract
import com.example.todo_mvp.data.model.TodoRepository

class TodoPresenter(
    private var view: TodoContract.View?,
    private val repository: TodoRepository
) : TodoContract.Presenter {

    override fun loadTodos() {
        val todos = repository.getTodos()
        if (todos.isEmpty()) {
            view?.showEmptyState()
        } else {
            view?.showTodos(todos)
        }
    }

    override fun addTodo(title: String) {
        if (title.isBlank()) {
            view?.showAddTodoError()
            return
        }
        repository.addTodo(title)
        view?.showAddTodoSuccess()
        loadTodos()
    }

    override fun toggleTodo(todo: TodoContent) {
        repository.toggleTodo(todo)
        loadTodos()
    }

    override fun deleteTodo(todo: TodoContent) {
        repository.deleteTodo(todo)
        view?.showDeleteTodoSuccess()
        loadTodos()
    }

    override fun onDestroy() {
        view = null
    }


}