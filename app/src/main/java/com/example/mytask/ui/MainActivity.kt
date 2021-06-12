package com.example.mytask.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytask.R
import com.example.mytask.adapter.TaskAdapter
import com.example.mytask.constants.Constants
import com.example.mytask.repository.TaskRepository
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        includeToolBar()
        fab_add_task.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.rv_task_list)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val repository = TaskRepository(this)
        recyclerView.adapter = TaskAdapter(repository.getTasks())
    }

    private fun includeToolBar() {
        setSupportActionBar(toolbar)
        supportActionBar!!.title = this.getString(R.string.title_tool_bar_mytask)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.fab_add_task -> {
                val intent = Intent(this, AddTaskActivity::class.java)
                intent.putExtra("operation_new_task", Constants.OPERATION_NEW_TASK)
                intent.putExtra("operation_update_button", Constants.BUTTON_ADD)
                startActivity(intent)
            }
        }
    }
}