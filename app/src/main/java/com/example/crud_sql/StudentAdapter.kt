package com.example.crud_sql

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ScrollCaptureCallback
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(var onClickItem:((StudentModel)->Unit),var onClickDeleteItem:((StudentModel)->Unit)) :RecyclerView.Adapter<StudentAdapter.StudenViewHolder>(){

    private var stdList:ArrayList<StudentModel> =ArrayList()


            fun addItems(items:ArrayList<StudentModel>)
            {
                this.stdList=items
                notifyDataSetChanged()
            }
    fun setOnclickItem(callback:(StudentModel)->Unit){
        this.onClickItem=callback
    }
    fun setOnclickDeleteItem(callback:(StudentModel)->Unit){
        this.onClickDeleteItem=callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= StudenViewHolder(
        LayoutInflater.from(parent.context).inflate((R.layout.recycler_layout),parent,false))


    override fun getItemCount(): Int {
        return stdList.size    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: StudenViewHolder, position: Int) {
    val std=stdList[position]
        holder.bindView(std)
        holder.itemView.setOnClickListener {
            onClickItem?.invoke(std)
        }
        holder.delete.setOnClickListener {
            onClickDeleteItem?.invoke(std)
        }

    }
    class StudenViewHolder(var view: View):RecyclerView.ViewHolder(view){
        private var id =view.findViewById<TextView>(R.id.tvid)
        private var name =view.findViewById<TextView>(R.id.tvname)
        private var email =view.findViewById<TextView>(R.id.tvemail)
        var delete =view.findViewById<TextView>(R.id.btndelete)
        fun bindView(std:StudentModel) {
            id.text = std.id.toString()
            name.text = std.name
            email.text = std.email

            delete.setOnClickListener {


            }

        }}}