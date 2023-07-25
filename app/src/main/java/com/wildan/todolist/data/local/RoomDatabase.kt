package com.wildan.todolist.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.wildan.todolist.data.model.Task
import com.wildan.todolist.data.local.dao.TaskDao

@Database(entities = [Task::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun  taskDao() : TaskDao

    companion object {
        private var instance : AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
            }
            return instance as AppDatabase
        }
    }
}