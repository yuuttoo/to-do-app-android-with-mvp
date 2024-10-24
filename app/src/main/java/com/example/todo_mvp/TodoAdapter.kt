package com.example.todo_mvp

import android.os.strictmode.UntaggedSocketViolation
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_mvp.data.TodoContent
import com.example.todo_mvp.databinding.ItemTodoBinding

class TodoAdapter(
    private val onToggleClick: (TodoContent) -> Unit,
    private val onDeleteClick: (TodoContent) -> Unit
) : ListAdapter<TodoContent, TodoAdapter.TodoViewHolder>(TodoDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = ItemTodoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TodoViewHolder(
        private val binding: ItemTodoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(todoContent: TodoContent) {
            binding.apply {
                textViewTitle.text = todoContent.title
                checkBoxCompleted.isChecked = todoContent.isCompleted

                checkBoxCompleted.setOnClickListener {
                    onToggleClick(todoContent)
                }

                buttonDelete.setOnClickListener {
                    onDeleteClick(todoContent)
                }
            }
        }
    }

}

class TodoDiffCallback : DiffUtil.ItemCallback<TodoContent>() {
    override fun areItemsTheSame(oldItem: TodoContent, newItem: TodoContent): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TodoContent, newItem: TodoContent): Boolean {
        return oldItem == newItem
    }
}