package com.example.sharedpreferences05

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    private val tasks: List<Task>,
    private val onDelete: (Task) -> Unit,
    private val onEdit: (Task) -> Unit,
    private val onToggleComplete: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleText: TextView = view.findViewById(R.id.tvTitle)
        val descriptionText: TextView = view.findViewById(R.id.tvDescription)
        val checkBox: CheckBox = view.findViewById(R.id.checkbox)
        val editButton: ImageButton = view.findViewById(R.id.btnEdit)
        val deleteButton: ImageButton = view.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]

        holder.titleText.text = task.title
        holder.descriptionText.text = task.description
        holder.descriptionText.visibility =
            if (task.description.isEmpty()) View.GONE else View.VISIBLE

        holder.checkBox.isChecked = task.isCompleted
        holder.checkBox.setOnClickListener { onToggleComplete(task) }

        holder.editButton.setOnClickListener { onEdit(task) }
        holder.deleteButton.setOnClickListener { onDelete(task) }
    }

    override fun getItemCount() = tasks.size
}