package com.example.mytask.adapter

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mytask.R
import com.example.mytask.constants.Constants
import com.example.mytask.model.Task
import com.example.mytask.repository.TaskRepository
import com.example.mytask.ui.AddTaskActivity
import kotlinx.android.synthetic.main.activity_add_task.*
import kotlinx.android.synthetic.main.alert_dialog.view.*
import kotlinx.android.synthetic.main.layout_list_task.view.*

class TaskAdapter(var listTask: ArrayList<Task>) :
    RecyclerView.Adapter<TaskAdapter.TaskAdapterViewHolder>() {

    inner class TaskAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(task: Task, position: Int) {
            itemView.findViewById<TextView>(R.id.txt_title_task).text = task.title
            itemView.findViewById<TextView>(R.id.txt_description_task).text = task.description

            itemView.img_more_options.setOnClickListener {
                val intent = Intent(itemView.context, AddTaskActivity::class.java)
                intent.putExtra("operation_new_task", Constants.OPERATION_DETAILS_TASK)
                intent.putExtra("operation_update_button", Constants.BUTTON_UPDATE)
                intent.putExtra("id", task.id)
                itemView.context.startActivity(intent)
            }

            itemView.setOnLongClickListener {
                createAlertDialog(task, position)
                true
            }

        }

        private fun createAlertDialog(task: Task, position: Int) {
            val dialogView =
                LayoutInflater.from(itemView.context).inflate(R.layout.alert_dialog, null)

            val buildAlertDialog = AlertDialog.Builder(itemView.context)
                .setView(dialogView)
                .setTitle(itemView.context.getString(R.string.alert_dialog_delete))
                .setMessage(itemView.context.getString(R.string.alert_dialog_delete_message))

            val mAlertDialog = buildAlertDialog.show()

            dialogView.btn_ad_yes.setOnClickListener {
                val repository = TaskRepository(itemView.context)
                repository.delete(task.id)
                mAlertDialog.dismiss()
                listTask.removeAt(position)
                notifyDataSetChanged()
                Toast.makeText(
                    itemView.context,
                    itemView.context.getString(R.string.task_delete_sucess),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
            dialogView.btn_ad_no.setOnClickListener {
                mAlertDialog.dismiss()
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdapterViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_list_task, parent, false)
        return TaskAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskAdapterViewHolder, position: Int) {
        val task = listTask[position]
        holder.bind(task, position)

    }

    override fun getItemCount(): Int {
        return listTask.size
    }
}