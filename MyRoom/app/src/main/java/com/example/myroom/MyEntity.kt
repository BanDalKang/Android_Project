package com.example.myroom

import androidx.room.*

@Entity(tableName = "student_table") // 테이블 명 지정: student_table
data class Student (
    @PrimaryKey @ColumnInfo(name = "student_id") val id: Int,
    val name: String
)