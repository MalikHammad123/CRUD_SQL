package com.example.crud_sql

import kotlin.random.Random

data class StudentModel(
    var id: Int = getAutoNumber(),
    var email: String = "",
    var name: String = ""
) {
    companion object {
        fun getAutoNumber(): Int {
            return (0..100).random()
        }
    }

}