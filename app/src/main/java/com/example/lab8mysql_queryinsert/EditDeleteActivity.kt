package com.example.lab8mysql_queryinsert

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.lab8mysql_queryinsert.databinding.ActivityEditDeleteBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditDeleteActivity : AppCompatActivity() {
    private lateinit var bindingEditDelete :ActivityEditDeleteBinding
    val createClient = StudentAPI.create()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingEditDelete = ActivityEditDeleteBinding.inflate(layoutInflater)
        setContentView(bindingEditDelete.root)

        val mID = intent.getStringExtra("mId")
        val mName = intent.getStringExtra("mName")
        val mAge = intent.getStringExtra("mAge")

        bindingEditDelete.edtId.setText(mID)
        bindingEditDelete.edtId.isEnabled = false
        bindingEditDelete.edtName.setText(mName)
        bindingEditDelete.edtAge.setText(mAge)
    }
    fun saveStudent(v:View){
        createClient.undateStudent(
            bindingEditDelete.edtId.text.toString(),
            bindingEditDelete.edtName.text.toString(),
            bindingEditDelete.edtAge.text.toString().toInt()
        ).enqueue(object :Callback<Student>{
            override fun onResponse(call: Call<Student>, response: Response<Student>) {
                if (response.isSuccessful){
                    Toast.makeText(applicationContext,"SuccessFully Updated", Toast.LENGTH_LONG).show()
                    finish()
                }else{
                    Toast.makeText(applicationContext,"Update Failure", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Student>, t: Throwable) {
                Toast.makeText(applicationContext,"Error onFailure", Toast.LENGTH_LONG).show()
            }
        })
    }
    fun deleteStudent(v:View){

    }
}