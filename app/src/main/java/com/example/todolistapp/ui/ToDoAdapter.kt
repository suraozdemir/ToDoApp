package com.example.todolistapp.ui

import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.todolistapp.R
import com.example.todolistapp.data.model.ToDo
import com.example.todolistapp.data.source.Database
import com.example.todolistapp.databinding.CardviewTodoBinding
import java.text.SimpleDateFormat
import java.util.logging.Handler

class ToDoAdapter () : RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>(){

    private val toDoList = mutableListOf<ToDo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoAdapter.ToDoViewHolder {
        val binding = CardviewTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToDoViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ToDoAdapter.ToDoViewHolder, position: Int) {
        holder.bind(toDoList[position])
    }

    inner class ToDoViewHolder(
        private val binding: CardviewTodoBinding
    ) : RecyclerView.ViewHolder(binding.root){


        fun bind(todo: ToDo){
            with(binding){
                tvTitle.text =todo.title
                tvTask.text = todo.task
                tvPriority.text = getPriorityText(todo.priorityColorId)
                tvDate.text = todo.date.toString()

                cbDelete.setOnClickListener {
                    val position = adapterPosition
                    notifyItemRemoved(position)
                    val removedToDo = toDoList.removeAt(position)
                    Database.deleteToDo(removedToDo)
                }
                tvPriority.setTextColor(ContextCompat.getColor(itemView.context, todo.priorityColorId ))
            }
        }

        private fun getPriorityText(priorityColorId: Int): String {
            val priorityTextMap = mapOf(
                R.color.red to "High Priority",
                R.color.blue to "Medium Priority",
                R.color.green to "Low Priority"
            )
            return priorityTextMap[priorityColorId] ?: ""
        }

    }

    override fun getItemCount(): Int {
        return toDoList.size
    }

    fun updateList(list: List<ToDo>) {
        toDoList.clear()
        toDoList.addAll(list)
        //notifyDataSetChanged()
        notifyItemRangeChanged(0, list.size)
    }

}