package com.example.alphacaller

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.widget.TextView
import android.widget.Toast
import java.text.DecimalFormat

class CallScreen : AppCompatActivity()
{

    var index: Int = 0
    var numberList: ArrayList<Double> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call_screen)
        numberList = (this.application as NumberLister).getList()
    }

    override fun onBackPressed() {
        val goToMain = Intent(this@CallScreen, MainActivity::class.java)
        startActivity(goToMain)
        this.finish()
    }

    override fun onResume()
    {
        val phoneNumber: TextView = findViewById(R.id.phoneNumber)
        val df = DecimalFormat("#")
        val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        val callStateListener = object : PhoneStateListener()
        {
            override fun onCallStateChanged(state: Int, incomingNumber: String)
            {
                if (state == TelephonyManager.CALL_STATE_OFFHOOK)
                {
                    index++
                }

                if (state == TelephonyManager.CALL_STATE_IDLE)
                {
                    if (index < numberList.size)
                    {
                        phoneNumber.text = df.format(numberList[index])

                        val makeCall = Intent(Intent.ACTION_CALL)
                        makeCall.data = Uri.parse("tel:" + phoneNumber.text)
                        startActivity(makeCall)
                    }
                    else
                        Toast.makeText(applicationContext, "Finished calls...", Toast.LENGTH_SHORT).show()
                }
            }
        }
        telephonyManager.listen(callStateListener, PhoneStateListener.LISTEN_CALL_STATE)
        super.onResume()
    }
}