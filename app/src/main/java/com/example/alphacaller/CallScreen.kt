package com.example.alphacaller

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.telephony.TelephonyManager
import android.widget.TextView
import android.widget.Toast
import java.text.DecimalFormat

class CallScreen : AppCompatActivity() {

    var index: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call_screen)
    }

    override fun onBackPressed() {
        val goToMain = Intent(this@CallScreen, MainActivity::class.java)
        startActivity(goToMain)
        this.finish()
    }

    override fun onResume() {
        super.onResume()
        val phoneNumber: TextView = findViewById(R.id.phoneNumber)
        val numberList: ArrayList<Double> = (this.application as NumberLister).getList()
        val df = DecimalFormat("#")

        if (index < numberList.size) {
            phoneNumber.text = df.format(numberList[index++])

            Handler().postDelayed({
                val makeCall = Intent(Intent.ACTION_CALL)
                makeCall.data = Uri.parse("tel:" + phoneNumber.text)
                startActivity(makeCall)
            }, 2500)
        }
        else
            Toast.makeText(this, "Finished calls...", Toast.LENGTH_SHORT).show()
    }
}
