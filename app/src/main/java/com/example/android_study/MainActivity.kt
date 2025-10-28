package com.example.android_study

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var taskAdapter: TaskAdapter
    private lateinit var taskViewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.task_recycler_view)
        val taskInput: EditText = findViewById(R.id.task_input)
        val addButton: Button = findViewById(R.id.add_button)

        val repository = TaskRepository(TaskDatabase.getDatabase(this).taskDao())
        val viewModelFactory = TaskViewModelFactory(repository)
        taskViewModel = ViewModelProvider(this, viewModelFactory).get(TaskViewModel::class.java)

        taskAdapter = TaskAdapter(
            onTaskClicked = { task ->
                val updatedTask = task.copy(isCompleted = !task.isCompleted)
                taskViewModel.update(updatedTask)
            },
            onDeleteClicked = { task ->
                taskViewModel.delete(task)
            }
        )

        recyclerView.adapter = taskAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            taskViewModel.allTasks.collect { tasks ->
                taskAdapter.submitList(tasks)
            }
        }

        addButton.setOnClickListener {
            val title = taskInput.text.toString()
            if (title.isNotEmpty()) {
                taskViewModel.insert(Task(title = title))
                taskInput.text.clear()
            }
        }
    }
}
