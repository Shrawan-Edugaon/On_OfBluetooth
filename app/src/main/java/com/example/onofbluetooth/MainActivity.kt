package com.example.onofbluetooth

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {

    lateinit var bluetoothAdapter: BluetoothAdapter
    private val REQUEST_CODE = 1
    lateinit var statusText: TextView

    lateinit var btnOn:Button
    lateinit var btnOff:Button
    lateinit var btnVisible:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnOn = findViewById(R.id.onButton)
        btnOff = findViewById(R.id.offButton)
        btnVisible = findViewById(R.id.getVisible)

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        statusText = findViewById(R.id.status)

        if (bluetoothAdapter == null)
        {
            btnOn.isEnabled = false
            btnOff.isEnabled = false
            btnVisible.isEnabled = false
            statusText.text = "Bluetooth not supported.."

            Toast.makeText(applicationContext,"Your Device Does not Support Bluetooth..",Toast.LENGTH_LONG).show()
        }
        else
        {
            btnOn.setOnClickListener {
                onBluetooth()
            }
        }
        btnOff.setOnClickListener {
            offBluetooth()
        }
        btnVisible.setOnClickListener {
            getVisible()
        }
    }
    private fun onBluetooth()
    {
        btnVisible.isEnabled = true

        if (!bluetoothAdapter.isEnabled){
            val turnOn = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(turnOn,REQUEST_CODE)
            Toast.makeText(applicationContext,"Bluetooth Turned on..",Toast.LENGTH_LONG).show()
        }else
        {
            Toast.makeText(applicationContext,"Bluetooth is already on..",Toast.LENGTH_LONG).show()
        }
    }

    private fun offBluetooth()
    {
        btnVisible.isEnabled = false
        statusText.text = "Status:Disable"

        bluetoothAdapter.disable()
        Toast.makeText(applicationContext,"Bluetooth Turned off..",Toast.LENGTH_LONG).show()
    }
    private fun getVisible()
    {
        val visible = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)

        startActivityForResult(visible,REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE)
        {
            if (bluetoothAdapter.isEnabled)
            {
                statusText.text = "Status:Enabled"
            }
            else
            {
                statusText.text ="Status:Disabled"
            }
        }
    }
}