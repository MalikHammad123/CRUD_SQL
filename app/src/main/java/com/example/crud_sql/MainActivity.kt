package com.example.crud_sql


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Adapter
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    private lateinit var sqLiteHelper: SQLiteHelper
    lateinit var name: EditText
    lateinit var email: EditText
    lateinit var view: Button
    lateinit var update: Button
    lateinit var add: Button
    lateinit var recyclerView: RecyclerView
    private var adapter:StudentAdapter?=null
    private var std:StudentModel?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        name = findViewById(R.id.etname)
        email = findViewById(R.id.etemail)
        view = findViewById(R.id.btnview)
        update=  findViewById(R.id.btnupdate)
        add = findViewById(R.id.btnadd)
        recyclerView=findViewById(R.id.recycler)
        sqLiteHelper = SQLiteHelper(this)
        add.setOnClickListener {
            addStudent()
        }
        view.setOnClickListener{
            getStudents()
        }
        update.setOnClickListener {
            updateStudent()
        }
       /* adapter?.setOnclickItem{
            Toast.makeText(this, it.name, Toast.LENGTH_SHORT).show()
            //now we need to update data
            name.setText(it.name)
            email.setText(it.email)
            std=it
        }*/
        /*adapter?.setOnclickDeleteItem {
            deleteStudent(it.id)
        }*/
        initrecycler()






    }
    fun initrecycler() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = StudentAdapter({
            adapter?.setOnclickItem{
                Toast.makeText(this, it.name, Toast.LENGTH_SHORT).show()
                //now we need to update data
                name.setText(it.name)
                email.setText(it.email)
                std=it
            }
        },{
            adapter?.setOnclickDeleteItem {
                deleteStudent(it.id)
            }

        })
        recyclerView.adapter = adapter
    }
    private fun updateStudent(){
        val name=name.text.toString()
        val email=email.text.toString()
        if (name==std?.name && email==std?.email ){
            Toast.makeText(this, "Record not change", Toast.LENGTH_SHORT).show()
        }
        if (std==null)return

        val std = StudentModel(id=std!!.id,name = name, email = email)
        val status = sqLiteHelper.updateStudent(std)
        if (status > -1) {
            clearEditTect()
            getStudents()
        } else {
            Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show()
        }


    }

    fun deleteStudent(id:Int){
        if(id==null)return
        val builder=AlertDialog.Builder(this)
        builder.setMessage("Are you sure to delete this data")
        builder.setCancelable(true)
        builder.setPositiveButton("YES"){dialog, _ ->
        sqLiteHelper.deleteById(id)
            getStudents()
            dialog.dismiss()
        }
        builder.setNegativeButton("NO"){
            dialog, _ -> dialog.dismiss()
        }
        val alert =builder.create()
        alert.show()

    }
    private fun getStudents() {
        val stdList = sqLiteHelper.getAllStudent()
        //Log.e("PPPPPPPPPPPPPPPPPP", "${stdList?.size}")
        if (stdList != null) {
            adapter?.addItems(stdList)
        }

    }

    private fun addStudent() {
        val name = name.text
        val email = email.text
        if (name.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Entre the required views", Toast.LENGTH_SHORT).show()
        } else {
            val std = StudentModel(name = name.toString(), email = email.toString())
            val status = sqLiteHelper.insertStudent(std)
            if (status > -1) {
                Toast.makeText(this, "student added ", Toast.LENGTH_SHORT).show()
                clearEditTect()
                getStudents()
            } else {
                Toast.makeText(this, "Record saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun clearEditTect() {
        name.setText("")
        email.setText("")
        name.requestFocus()
    }

}