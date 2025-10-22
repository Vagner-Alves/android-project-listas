package com.example.android_study

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var taskAdapter: TaskAdapter
    private var taskList = mutableListOf<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.task_recycler_view)
        val taskInput: EditText = findViewById(R.id.task_input)
        val addButton: Button = findViewById(R.id.add_button)

        taskAdapter = TaskAdapter(
            onTaskClicked = { task ->
                val updatedTask = task.copy(isCompleted = !task.isCompleted)
                val index = taskList.indexOfFirst { it.id == task.id }
                if (index != -1) {
                    taskList[index] = updatedTask
                    taskAdapter.submitList(taskList.toList())
                }
            },
            onDeleteClicked = { task ->
                taskList.remove(task)
                taskAdapter.submitList(taskList.toList())
            }
        )

        recyclerView.adapter = taskAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        addButton.setOnClickListener {
            val title = taskInput.text.toString()
            if (title.isNotEmpty()) {
                val newTask = Task(title = title)
                taskList.add(newTask)
                taskAdapter.submitList(taskList.toList())
                taskInput.text.clear()
            }
        }
    }
}