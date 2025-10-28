package com.example.android_study

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var taskAdapter: TaskAdapter
    private val tasks = mutableListOf<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadTasks()

        val recyclerView: RecyclerView = findViewById(R.id.task_recycler_view)
        val taskInput: EditText = findViewById(R.id.task_input)
        val addButton: Button = findViewById(R.id.add_button)

        taskAdapter = TaskAdapter(
            onTaskClicked = { task ->
                val index = tasks.indexOfFirst { it.id == task.id }
                if (index != -1) {
                    val updatedTask = tasks[index].copy(isCompleted = !tasks[index].isCompleted)
                    tasks[index] = updatedTask
                    saveTasks()
                    taskAdapter.submitList(tasks.toList())
                }
            },
            onDeleteClicked = { task ->
                tasks.removeAll { it.id == task.id }
                saveTasks()
                taskAdapter.submitList(tasks.toList())
            }
        )

        recyclerView.adapter = taskAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        taskAdapter.submitList(tasks.toList())

        addButton.setOnClickListener {
            val title = taskInput.text.toString()
            if (title.isNotEmpty()) {
                val newTask = Task(title = title)
                tasks.add(newTask)
                saveTasks()
                taskAdapter.submitList(tasks.toList())
                taskInput.text.clear()
            }
        }
    }

    private fun saveTasks() {
        val sharedPreferences = getSharedPreferences("tasks", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val taskSet = tasks.map { "${it.id};${it.title};${it.isCompleted}" }.toSet()
        editor.putStringSet("task_list", taskSet)
        editor.apply()
    }

    private fun loadTasks() {
        val sharedPreferences = getSharedPreferences("tasks", Context.MODE_PRIVATE)
        val taskSet = sharedPreferences.getStringSet("task_list", null)
        if (taskSet != null) {
            tasks.clear()
            val loadedTasks = taskSet.mapNotNull {
                try {
                    val parts = it.split(";", limit = 3)
                    if (parts.size == 3) {
                        Task(id = parts[0].toLong(), title = parts[1], isCompleted = parts[2].toBoolean())
                    } else {
                        null
                    }
                } catch (e: Exception) {
                    null // Ignore corrupted entries
                }
            }
            tasks.addAll(loadedTasks.sortedBy { it.id })
        }
    }
}
