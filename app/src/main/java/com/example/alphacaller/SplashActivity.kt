package com.example.alphacaller

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.widget.Toast

class SplashActivity : AppCompatActivity() {

    val permissionArray = arrayOf(
        Manifest.permission.CALL_PHONE,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.PROCESS_OUTGOING_CALLS,
        Manifest.permission.READ_EXTERNAL_STORAGE)

    val REQUEST_PERMISSION = 131 //test code to request all permissions from the system

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if(!hasPermissions(this@SplashActivity, *permissionArray))
        {
            //request for permissions
            ActivityCompat.requestPermissions(this@SplashActivity, permissionArray, REQUEST_PERMISSION)
        }
        else
        {
            //show Splash Screen for 2.5 sec
            Handler().postDelayed({
                val startMain = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(startMain)
                this.finish()
            }, 2500)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode) {
            //check if all permissions have been granted or not
            REQUEST_PERMISSION -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                    && grantResults[2] == PackageManager.PERMISSION_GRANTED
                    && grantResults[3] == PackageManager.PERMISSION_GRANTED)
                {
                    //show Splash Screen for 2.5 sec
                    Handler().postDelayed({
                        val startMain = Intent(this@SplashActivity, MainActivity::class.java)
                        startActivity(startMain)
                        this.finish()
                    }, 2500)
                }
                else
                {
                    //kill activity if permissions are not granted while showing a prompt
                    Toast.makeText(this@SplashActivity, "Please grant Necessary Permissions to Continue...", Toast.LENGTH_SHORT).show()
                    this.finish()
                }
                return
            }
            else -> {
                //error case
                Toast.makeText(this@SplashActivity, "Something went wrong :/", Toast.LENGTH_SHORT).show()
                this.finish()
            }
        }
    }

    //checks if permissions have been granted
    fun hasPermissions(context: Context, vararg permissions: String): Boolean
    {
        var hasAllPermissions = true
        for(permission in permissions)
        {
            val res = context.checkCallingOrSelfPermission(permission)
            if(res != PackageManager.PERMISSION_GRANTED)
                hasAllPermissions = false
        }
        return hasAllPermissions
    }
}
