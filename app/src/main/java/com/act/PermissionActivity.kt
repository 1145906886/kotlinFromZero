package com.act

import android.Manifest
import android.Manifest.permission.*
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.com.myapplication.R

class PermissionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)
        findViewById<Button>(R.id.btn).setOnClickListener {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    ACCESS_FINE_LOCATION,
                    ACCESS_COARSE_LOCATION
                ), 11
            )
        }
        findViewById<Button>(R.id.abc).setOnClickListener {
            val b =
                ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                )
            Toast.makeText(this, "$b", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 11) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        ACCESS_BACKGROUND_LOCATION
                    ), 12
                )
            }
        }
    }

}