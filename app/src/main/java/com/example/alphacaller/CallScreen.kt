package com.example.alphacaller

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import java.text.DecimalFormat

class CallScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call_screen)

        val numberDisplay1 = findViewById<TextView>(R.id.numbers1)
        val numberDisplay2 = findViewById<TextView>(R.id.numbers2)
        val numberDisplay3 = findViewById<TextView>(R.id.numbers3)
        val numberDisplay4 = findViewById<TextView>(R.id.numbers4)

        val numberList: ArrayList<Double> = (this.application as NumberLister).getList()

        val df = DecimalFormat("#")

        numberDisplay1.text = df.format(numberList[0])
        numberDisplay2.text = df.format(numberList[1])
        numberDisplay3.text = df.format(numberList[2])
        numberDisplay4.text = df.format(numberList[3])
    }

    override fun onBackPressed() {
        val goToMain = Intent(this@CallScreen, MainActivity::class.java)
        startActivity(goToMain)
        this.finish()
    }
}
