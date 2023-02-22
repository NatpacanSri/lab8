package com.example.lab8mysql_queryinsert

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lab8mysql_queryinsert.databinding.EditDeleteItemLayoutBinding
import com.example.lab8mysql_queryinsert.databinding.StdItemLayoutBinding


class EditStudentsAdapter(val items:List<Student>,val context: Context):
    RecyclerView.Adapter<EditStudentsAdapter.ViewHolder>(){
    inner class ViewHolder(view:View,val binding: EditDeleteItemLayoutBinding):
        RecyclerView.ViewHolder(view){
            init {
                binding.txtEditDelete.setOnClickListener{
                    val item = items[adapterPosition]
                    val contextView:Context = view.context
                    val intent = Intent(contextView,EditDeleteActivity::class.java)
                    intent.putExtra("mId",item.std_id)
                    intent.putExtra("mName",item.std_name)
                    intent.putExtra("mAge",item.std_age.toString())
                    contextView.startActivity(intent)
                }
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = EditDeleteItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),parent,false )
        return ViewHolder(binding.root,binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding_holder = holder.binding
        binding_holder.txtID.text = items[position].std_id
        binding_holder.txtName.text = items[position].std_name
    }

    override fun getItemCount(): Int {
        return items.size
    }
}