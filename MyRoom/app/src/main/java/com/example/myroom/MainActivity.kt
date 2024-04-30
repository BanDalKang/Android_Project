package com.example.myroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myroom.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var myDao: MyDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        myDao = MyDatabase.getDatabase(this).getMyDao()

        observeStudents()
        setListeners()
    }

    private fun observeStudents() {
        myDao.getAllStudents().observe(this) { students ->
            val str = buildString {
                students.forEach { student ->
                    append("${student.id}-${student.name}\n")
                }
            }
            binding.textStudentList.text = str
        }
    }

    private fun setListeners() {
        with(binding) {
            addStudent.setOnClickListener {
                val id = editStudentId.text.toString().toIntOrNull() ?: 0
                val name = editStudentName.text.toString()
                if (id > 0 && name.isNotEmpty()) {
                    CoroutineScope(Dispatchers.IO).launch {
                        myDao.insertStudent(Student(id, name))
                    }
                }
                editStudentId.text = null
                editStudentName.text = null
            }

            queryStudent.setOnClickListener {
                val name = editStudentName.text.toString()
                CoroutineScope(Dispatchers.IO).launch {
                    val results = myDao.getStudentByName(name)
                    withContext(Dispatchers.Main) {
                        textQueryStudent.text = buildString {
                            results.forEach { student ->
                                append("${student.id}-${student.name}\n")
                            }
                        }
                    }
                }
            }

            deleteStudent.setOnClickListener {
                val id = editStudentId.text.toString().toIntOrNull() ?: 0
                val name = editStudentName.text.toString()
                if (id > 0) {
                    CoroutineScope(Dispatchers.IO).launch {
                        myDao.deleteStudent(Student(id, name))
                    }
                }
                editStudentId.text = null
                editStudentName.text = null
            }
        }
    }
}