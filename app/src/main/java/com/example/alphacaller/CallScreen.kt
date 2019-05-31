package com.example.alphacaller

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast

class CallScreen : AppCompatActivity() {

    var numberData: Database ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call_screen)
        numberData = Database(this@CallScreen)

        val numberDisplay = findViewById<TextView>(R.id.numbers)

        val numberList = numberData?.queryDB()
        var index = 0

        while(numberList?.size != null && index < numberList.size)
        {
            Toast.makeText(this, numberList[index++], Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        val goToMain = Intent(this@CallScreen, MainActivity::class.java)
        startActivity(goToMain)
        this.finish()
    }
}
