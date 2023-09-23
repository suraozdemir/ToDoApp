package com.example.todolistapp.data.model

import com.example.todolistapp.R

enum class Priority (val priorityColorId: Int){
    HIGH(R.color.red),
    MEDIUM(R.color.blue),
    LOW(R.color.green)
}