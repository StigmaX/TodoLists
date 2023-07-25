package com.wildan.todolist.data.repository

import androidx.lifecycle.LiveData
import com.wildan.todolist.data.model.Task
import com.wildan.todolist.data.local.dao.TaskDao

class TaskRepository(private val taskDao: TaskDao) {
    val allTask: LiveData<List<Task>> = taskDao.getAllTasks()

    suspend fun insert(task : Task) {
        taskDao.insertTask(task)
    }

}