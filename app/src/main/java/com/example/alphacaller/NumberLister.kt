package com.example.alphacaller

import android.app.Application

class NumberLister : Application() {
    var numbers: ArrayList<String> = arrayListOf()

    fun getList(): ArrayList<String> {
        return numbers
    }

    fun setList(list: ArrayList<String>) {
        numbers = list
    }
}