package com.example.todolistapp.data.model

import android.app.DatePickerDialog
import android.widget.DatePicker
import android.widget.EditText
import java.util.Date

class ToDo(
    val id: Int,
    val title: String,
    val task: String,
    val date: String,
    val priorityColorId: Int,
    var isChecked: Boolean = false

) {
}