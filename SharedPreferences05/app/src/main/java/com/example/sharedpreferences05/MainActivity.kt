package com.example.sharedpreferences05

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {
    private lateinit var taskAdapter: TaskAdapter
    private var tasks = mutableListOf<Task>()
    private val sharedPrefName = "TaskPrefs"
    private val taskListKey = "TaskList"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set up toolbar with theme color
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.title = "Danh sách công việc"

        // Initialize RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        taskAdapter = TaskAdapter(tasks,
            onDelete = { deleteTask(it) },
            onEdit = { showEditDialog(it) },
            onToggleComplete = { toggleTaskComplete(it) }
        )

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = taskAdapter
        }

        // Load saved tasks
        loadTasks()

        // Set up FAB
        findViewById<FloatingActionButton>(R.id.fabAdd).setOnClickListener {
            showAddDialog()
        }
    }

    private fun loadTasks() {
        val prefs = getSharedPreferences(sharedPrefName, MODE_PRIVATE)
        val tasksJson = prefs.getString(taskListKey, null)
        if (tasksJson != null) {
            val type = object : TypeToken<List<Task>>() {}.type
            tasks.clear()
            tasks.addAll(Gson().fromJson(tasksJson, type))
            taskAdapter.notifyDataSetChanged()
        }
    }

    private fun saveTasks() {
        val prefs = getSharedPreferences(sharedPrefName, MODE_PRIVATE)
        val tasksJson = Gson().toJson(tasks)
        prefs.edit().putString(taskListKey, tasksJson).apply()
    }

    private fun showAddDialog() {
        val dialogView = LayoutInflater.from(this)
            .inflate(R.layout.dialog_task, null)

        AlertDialog.Builder(this)
            .setTitle("Thêm công việc mới")
            .setView(dialogView)
            .setPositiveButton("Thêm") { _, _ ->
                val title = dialogView.findViewById<EditText>(R.id.etTitle).text.toString()
                val description = dialogView.findViewById<EditText>(R.id.etDescription).text.toString()

                if (title.isNotEmpty()) {
                    tasks.add(Task(title = title, description = description))
                    taskAdapter.notifyItemInserted(tasks.size - 1)
                    saveTasks()
                }
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    private fun showEditDialog(task: Task) {
        val dialogView = LayoutInflater.from(this)
            .inflate(R.layout.dialog_task, null)

        dialogView.findViewById<EditText>(R.id.etTitle).setText(task.title)
        dialogView.findViewById<EditText>(R.id.etDescription).setText(task.description)

        AlertDialog.Builder(this)
            .setTitle("Chỉnh sửa công việc")
            .setView(dialogView)
            .setPositiveButton("Cập nhật") { _, _ ->
                val title = dialogView.findViewById<EditText>(R.id.etTitle).text.toString()
                val description = dialogView.findViewById<EditText>(R.id.etDescription).text.toString()

                if (title.isNotEmpty()) {
                    val position = tasks.indexOf(task)
                    task.title = title
                    task.description = description
                    taskAdapter.notifyItemChanged(position)
                    saveTasks()
                }
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    private fun deleteTask(task: Task) {
        AlertDialog.Builder(this)
            .setTitle("Xóa công việc")
            .setMessage("Bạn có chắc muốn xóa công việc này?")
            .setPositiveButton("Xóa") { _, _ ->
                val position = tasks.indexOf(task)
                tasks.remove(task)
                taskAdapter.notifyItemRemoved(position)
                saveTasks()
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    private fun toggleTaskComplete(task: Task) {
        task.isCompleted = !task.isCompleted
        taskAdapter.notifyItemChanged(tasks.indexOf(task))
        saveTasks()
    }
}