package com.example.todo_mvp.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todo_mvp.data.model.TodoContent
import com.example.todo_mvp.presenter.TodoContract
import com.example.todo_mvp.data.model.TodoRepository
import com.example.todo_mvp.databinding.ActivityMainBinding
import com.example.todo_mvp.databinding.DialogAddTodoBinding
import com.example.todo_mvp.presenter.TodoPresenter

class MainActivity : AppCompatActivity(), TodoContract.View {
    private lateinit var binding: ActivityMainBinding
    private lateinit var presenter: TodoContract.Presenter
    private lateinit var adapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = TodoPresenter(this, TodoRepository())

        //setup RecyclerView
        adapter = TodoAdapter(
            onToggleClick = { todo -> presenter.toggleTodo(todo) },
            onDeleteClick = { todo -> presenter.deleteTodo(todo) }
        )
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        //FAB
        binding.fab.setOnClickListener {
            showAddTodoDialog()
        }
        //Load initial data
        presenter.loadTodos()
    }

    private fun showAddTodoDialog() {
        val dialogBinding = DialogAddTodoBinding.inflate(layoutInflater)

        AlertDialog.Builder(this)
            .setTitle("Add Todo")
            .setView(dialogBinding.root)
            .setPositiveButton("Add") { dialog, _ ->
                val title = dialogBinding.editTextTodo.text.toString()
                presenter.addTodo(title)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }
    override fun showTodos(todos: List<TodoContent>) {
        binding.emptyState.isVisible = false
        binding.recyclerView.isVisible = true
        adapter.submitList(todos)
    }

    override fun showEmptyState() {
        binding.emptyState.isVisible = true
        binding.recyclerView.isVisible = false
    }

    override fun showAddTodoSuccess() {
        Toast.makeText(this, "Todo added successfully", Toast.LENGTH_SHORT).show()
    }

    override fun showAddTodoError() {
        Toast.makeText(this, "Please enter a valid title", Toast.LENGTH_SHORT).show()
    }

    override fun showDeleteTodoSuccess() {
        Toast.makeText(this, "Todo deleted successfully", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}