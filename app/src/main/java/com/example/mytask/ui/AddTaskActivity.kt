package com.example.mytask.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.mytask.R
import com.example.mytask.constants.Constants
import com.example.mytask.model.Task
import com.example.mytask.repository.TaskRepository
import kotlinx.android.synthetic.main.activity_add_task.*
import kotlinx.android.synthetic.main.alert_dialog.view.*
import kotlinx.android.synthetic.main.toolbar.*

class AddTaskActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        includeToolBar()
        if (intent.getStringExtra("operation_new_task") != Constants.OPERATION_NEW_TASK) {
            fillForm()
        }

        btn_add_task.text = intent.getStringExtra("operation_update_button")
        btn_add_task.setOnClickListener(this)

    }

    private fun fillForm() {
        var task = Task()
        val id = intent.getIntExtra("id", 0)

        val repository = TaskRepository(this)

        task = repository.getTask(id = id)

        edit_title.setText(task.title)
        edit_description.setText(task.description)
        checkbox_fist.isChecked = task.priority_fist
        checkbox_later.isChecked = task.priority_later
        checkbox_delegate.isChecked = task.priority_delegate
        checkbox_eliminate.isChecked = task.priority_eliminate
    }

    private fun includeToolBar() {
        setSupportActionBar(toolbar)
        supportActionBar!!.title = intent.getStringExtra("operation_new_task")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_add_task -> {
                if (validationTask() && intent.getStringExtra("operation_new_task") == Constants.OPERATION_NEW_TASK) {
                    saveTask()
                } else {
                    updateTask()
                }
            }
        }

    }

    private fun updateTask() {
        val task = Task(
            id = intent.getIntExtra("id", 0),
            title = edit_title.text.toString(),
            description = edit_description.text.toString(),
            priority_fist = checkbox_fist.isChecked,
            priority_later = checkbox_later.isChecked,
            priority_delegate = checkbox_delegate.isChecked,
            priority_eliminate = checkbox_eliminate.isChecked
        )
        val repository = TaskRepository(this)
        var count = repository.update(task)

        if (count > 0) {
            Toast.makeText(this, getString(R.string.task_update_sucess), Toast.LENGTH_SHORT).show()
            onBackPressed()
        }
    }

    private fun validationTask(): Boolean {
        var correct = true

        if (edit_title.length() <= 0) {
            txt_inp_title.isErrorEnabled = true
            txt_inp_title.setErrorTextAppearance(R.style.ErrorTextAppearance)
            txt_inp_title.error = getString(R.string.error_txt_title)
            correct = false
        } else {
            txt_inp_title.isErrorEnabled = false
            txt_inp_title.error = null
        }

        if (edit_description.length() <= 0) {
            txt_inp_description.isErrorEnabled = true
            txt_inp_description.setErrorTextAppearance(R.style.ErrorTextAppearance)
            txt_inp_description.error = getString(R.string.error_txt_description)
            correct = false
        } else {
            txt_inp_description.isErrorEnabled = false
            txt_inp_description.error = null
        }

        return correct
    }


    private fun saveTask() {
        val task = Task(
            title = edit_title.text.toString(),
            description = edit_description.text.toString(),
            priority_fist = checkbox_fist.isChecked,
            priority_later = checkbox_later.isChecked,
            priority_delegate = checkbox_delegate.isChecked,
            priority_eliminate = checkbox_eliminate.isChecked
        )

        val repository = TaskRepository(this)

        val id = repository.save(task)

        createDialog()

    }

    private fun createDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.alert_dialog, null)
        val alertDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle(getString(R.string.alert_dialog_sucess))
            .setMessage(getString(R.string.alert_dialog_another_task))

        val mAlertDialog = alertDialog.show()

        dialogView.btn_ad_yes.setOnClickListener {
            clearForm()
            edit_title.requestFocus()
            mAlertDialog.dismiss()
        }
        dialogView.btn_ad_no.setOnClickListener {
            onBackPressed()
            mAlertDialog.dismiss()
        }

    }

    private fun clearForm() {
        edit_title.setText("")
        edit_description.setText("")
        checkbox_fist.isChecked = false
        checkbox_later.isChecked = false
        checkbox_delegate.isChecked = false
        checkbox_eliminate.isChecked = false

    }

}