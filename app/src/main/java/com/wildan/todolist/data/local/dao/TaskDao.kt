package com.wildan.todolist.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.wildan.todolist.data.model.Task

@Dao
interface TaskDao {
    @Insert
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task : Task)

    @Delete
    suspend fun  deleteTask(task: Task)

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): LiveData<List<Task>>
}
