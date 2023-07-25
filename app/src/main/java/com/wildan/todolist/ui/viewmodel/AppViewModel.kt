package com.wildan.todolist.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.wildan.todolist.data.repository.TaskRepository
import com.wildan.todolist.data.local.AppDatabase
import com.wildan.todolist.data.model.Task
import kotlinx.coroutines.launch

class AppViewModel(application : Application) : AndroidViewModel(application) {
    private val repository: TaskRepository
    private val allTask: LiveData<List<Task>>

    init {
        val database = AppDatabase.getInstance(application).taskDao()
        repository = TaskRepository(database)
        allTask = repository.allTask
    }

    fun insertTask(task: Task) {
        viewModelScope.launch {
            repository.insert(task)
        }
    }


}
