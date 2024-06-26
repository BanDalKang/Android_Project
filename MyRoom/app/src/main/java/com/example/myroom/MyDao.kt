package com.example.myroom

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MyDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE) // INSERT, key 충돌이 나면 새 데이터로 교체
    suspend fun insertStudent(student: Student)

    @Query("SELECT * FROM student_table")
    fun getAllStudents(): LiveData<List<Student>> // LiveData<> 사용

    @Query("SELECT * FROM student_table WHERE name = :sname") // 메소드 인자를 SQL문에서 :을 붙여 사용
    suspend fun getStudentByName(sname: String): List<Student>

    @Delete
    suspend fun deleteStudent(student: Student)
}