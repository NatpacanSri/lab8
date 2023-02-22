package com.example.lab8mysql_queryinsert

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab8mysql_queryinsert.databinding.ActivityShowEditDeleteBinding
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.security.auth.callback.Callback

class ShowEditDeleteActivity : AppCompatActivity() {
    private lateinit var bindingShow:ActivityShowEditDeleteBinding
    var studentList = arrayListOf<Student>()
    var createClient = StudentAPI.create()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingShow = ActivityShowEditDeleteBinding.inflate(layoutInflater)
        setContentView(bindingShow.root)

        bindingShow.recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        bindingShow.recyclerView.addItemDecoration(
            DividerItemDecoration(bindingShow.recyclerView.context,
            DividerItemDecoration.VERTICAL)
        )

    }

    override fun onResume() {
        super.onResume()
        callStudent()
    }
    fun callStudent(){
        bindingShow.edtSearch.text?.clear()
        studentList.clear();
        createClient.retrieveStudent()
            .enqueue(object :retrofit2.Callback<List<Student>>{
                override fun onResponse(
                    call: Call<List<Student>>,
                    response: Response<List<Student>>
                ){
                    response.body()?.forEach{
                        studentList.add(Student(it.std_id,it.std_name,it.std_age))
                    }
                    bindingShow.recyclerView.adapter = EditStudentsAdapter(studentList,applicationContext)
                }
                override fun onFailure(call: Call<List<Student>>,t:Throwable){
                    t.printStackTrace()
                    Toast.makeText(applicationContext,"Error2",Toast.LENGTH_LONG).show()
                }
            })
//        studentList.clear();
//        val serv : StudentAPI = Retrofit.Builder()
//            .baseUrl("http://10.0.2.2:3000/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(StudentAPI::class.java)
//
//        serv.retrieveStudent()
//            .enqueue(object : retrofit2.Callback<List<Student>> {
//                override fun onResponse( call: Call<List<Student>>,
//                                         response: Response<List<Student>>) {
//                    response.body()?.forEach{
//                        studentList.add(Student(it.std_id,it.std_name,it.std_age))
//                    }
//                    // set Data to RecyclerRecyclerView
//                    bindingShow.recyclerView.adapter = EditStudentsAdapter(studentList,applicationContext)
//                }
//
//                override fun onFailure(call: Call<List<Student>>, t: Throwable) {
//                    Toast.makeText(applicationContext,"Error onFailure"+ t.message,Toast.LENGTH_LONG).show()
//                }
//            })
    }

    fun clickSearch(v:View){
        studentList.clear()
        if(bindingShow.edtSearch.text!!.isEmpty()){
            callStudent()
        }else{
            createClient.retrieveStudentID(bindingShow.edtSearch.text.toString())
                .enqueue(object : retrofit2.Callback<Student>{
                    override fun onResponse(call:Call<Student>,response: Response<Student>){
                        if(response.isSuccessful){
                            studentList.add(Student(response.body()?.std_id.toString(),
                                response.body()?.std_name.toString(),
                                response.body()?.std_age.toString().toInt()
                                ))
                            bindingShow.recyclerView.adapter = EditStudentsAdapter(studentList,applicationContext)
                        }else{
                            Toast.makeText(applicationContext,"Student ID NOT Found",Toast.LENGTH_LONG).show()
                        }
                    }
                    override fun onFailure(call:Call<Student>,t:Throwable){
                        Toast.makeText(applicationContext,"Error onFailure",Toast.LENGTH_LONG).show()
                    }
                })
        }
    }

}