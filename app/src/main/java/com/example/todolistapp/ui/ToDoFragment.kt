package com.example.todolistapp.ui

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.todolistapp.R
import com.example.todolistapp.common.viewBinding
import com.example.todolistapp.data.model.ToDo
import com.example.todolistapp.data.source.Database
import com.example.todolistapp.databinding.DialogAddTodoBinding
import com.example.todolistapp.databinding.FragmentTodoBinding
import java.util.Calendar

class ToDoFragment : Fragment(R.layout.fragment_todo) {
    private val binding by viewBinding(FragmentTodoBinding::bind)
    private val toDoAdapter = ToDoAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            rvToDoList.adapter = toDoAdapter
            toDoAdapter.updateList(Database.getToDo())

            fabAdd.setOnClickListener {
                showAddDialog()
            }
        }
    }
    private fun showAddDialog() {

        val builder = AlertDialog.Builder(requireContext())
        val dialogBinding = DialogAddTodoBinding.inflate(layoutInflater)
        builder.setView(dialogBinding.root)
        val dialog = builder.create()

        var priorityColorId = R.color.white
        var date = ""

        with(dialogBinding) {
            etDate.setOnClickListener {
                val calendar = Calendar.getInstance()

                DatePickerDialog(
                    requireContext(),
                    { _, y, a, g ->
                        etDate.setText("$g/${a + 1}/$y")
                        date = "$g/${a + 1}/$y"
                    },
                    calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
                ).apply {
                    this.setTitle("Enter the date")
                    this.setButton(DialogInterface.BUTTON_POSITIVE, "OKEY", this)
                    this.setButton(DialogInterface.BUTTON_NEGATIVE, "CANCEL", this)

                    this.show()
                }
            }

            radioGroup.setOnCheckedChangeListener { _, selectedId ->
                priorityColorId = when (selectedId) {
                    R.id.rb_btn_low -> R.color.green
                    R.id.rb_btn_medium -> R.color.blue
                    R.id.rb_btn_high -> R.color.red
                    else -> R.color.white
                }
            }

            btnAddToDo.setOnClickListener {
                val title = etTitle.text.toString()
                val task = etTask.text.toString()

                if (title.isNotEmpty() && task.isNotEmpty() && date.isNotEmpty()) {
                    Database.addToDo(title, task, date, priorityColorId)
                    binding.rvToDoList.adapter = toDoAdapter
                    toDoAdapter.updateList(Database.getToDo())
                    dialog.dismiss()
                } else {
                    Toast.makeText(requireContext(), "Please fill in the blanks!", Toast.LENGTH_SHORT).show()
                }
            }
            dialog.show()
        }

    }

}