package com.example.todolistapp.data.source

import com.example.todolistapp.data.model.ToDo

object Database {
    private val taskDatabase = mutableListOf<ToDo>()

    fun getToDo (): List<ToDo> {
        return taskDatabase
    }

    fun addToDo (title: String, task: String, date: String, priorityColorId: Int ) {
        val newToDo= ToDo(
            id = (taskDatabase.lastOrNull()?.id ?: 0) + 1,
            title = title,
            task = task,
            date = date,
            priorityColorId = priorityColorId
        )
        taskDatabase.add(newToDo)
    }

    fun deleteToDo (toDo: ToDo){
        taskDatabase.remove(toDo)
    }

}