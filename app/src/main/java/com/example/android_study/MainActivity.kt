package com.example.android_study

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var taskAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.task_recycler_view)
        val taskInput: EditText = findViewById(R.id.task_input)
        val addButton: Button = findViewById(R.id.add_button)

        taskAdapter = TaskAdapter(
            onTaskClicked = { task ->
                val currentList = taskAdapter.currentList.toMutableList()
                val index = currentList.indexOfFirst { it.id == task.id }
                if (index != -1) {
                    val updatedTask = currentList[index].copy(isCompleted = !currentList[index].isCompleted)
                    currentList[index] = updatedTask
                    taskAdapter.submitList(currentList)
                }
            },
            onDeleteClicked = { task ->
                val currentList = taskAdapter.currentList.toMutableList()
                currentList.remove(task)
                taskAdapter.submitList(currentList)
            }
        )

        recyclerView.adapter = taskAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        addButton.setOnClickListener {
            val title = taskInput.text.toString()
            if (title.isNotEmpty()) {
                val newTask = Task(title = title)
                val currentList = taskAdapter.currentList.toMutableList()
                currentList.add(newTask)
                taskAdapter.submitList(currentList)
                taskInput.text.clear()
            }
        }
    }
}
