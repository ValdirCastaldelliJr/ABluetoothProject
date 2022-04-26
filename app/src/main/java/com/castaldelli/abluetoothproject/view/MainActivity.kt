package com.castaldelli.abluetoothproject.view

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.ActivityResultCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.castaldelli.abluetoothproject.R
import com.castaldelli.abluetoothproject.data.Device
import com.castaldelli.abluetoothproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ActivityResultCallback<Int> {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bluetoothManager: BluetoothManager
    private var bluetoothAdapter: BluetoothAdapter? = null

    private val requestCode = 124

    override fun onCreate(savedInstanceState: Bundle?) {
        // Binding //
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Actions //
        binding.bSearch.setOnClickListener { clickSearchDevicesButton() }

        bluetoothManager = getSystemService(BluetoothManager::class.java)
        bluetoothAdapter = bluetoothManager.adapter

        // Permission handle

    }

    @SuppressLint("MissingPermission")
    private fun clickSearchDevicesButton(){
        check()// FIXME: Check permission

        val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter?.bondedDevices
        val devices = arrayListOf<Device>()
        pairedDevices?.forEach { devices.add(Device(it.name,isConnected(it), it.type)) }

        binding.rvDevices.layoutManager = LinearLayoutManager(this)
        binding.rvDevices.adapter = ItemDeviceAdapter(devices)

    }

    private fun whichType(type: Int):String {
        return when(type) {
            BluetoothProfile.HEADSET -> "HeadSet"
            BluetoothProfile.HID_DEVICE -> "HID"
            else -> "Unknow type($type)"
        }
    }


    private fun isConnected(device: BluetoothDevice): Boolean {
        return try {
            val m = device.javaClass.getMethod("isConnected", *arrayOf<Class<*>>())
            val connected = m.invoke(device) as Boolean
            connected
        } catch (e:Exception) {
            false
        }
    }


    private fun check(){
        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED -> {
                // You can use the API that requires the permission.
            }
            shouldShowRequestPermissionRationale(Manifest.permission.BLUETOOTH_CONNECT) -> {
            showUserInformationDialog(R.string.perm_dialog_msg)
        }
            else -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    requestPermissions(arrayOf(Manifest.permission.BLUETOOTH_CONNECT),requestCode )
                }
            }
        }
    }


    private fun showUserInformationDialog(message: Int) {
        val dialog: AlertDialog.Builder = AlertDialog.Builder(this)
        dialog.apply {
            setMessage(message)
            setTitle(R.string.perm_dialog_title)
            setPositiveButton(R.string.ok) { dialog, _->
                dialog.dismiss()
            }
        }.create().show()
    }

    override fun onActivityResult(result: Int?) {
        TODO("Not yet implemented")
    }


}