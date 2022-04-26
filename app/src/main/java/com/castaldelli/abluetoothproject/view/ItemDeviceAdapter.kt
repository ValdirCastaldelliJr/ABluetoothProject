package com.castaldelli.abluetoothproject.view

import android.bluetooth.BluetoothProfile
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.castaldelli.abluetoothproject.R
import com.castaldelli.abluetoothproject.data.Device
import com.castaldelli.abluetoothproject.databinding.ItemViewBinding

class ItemDeviceAdapter(private val devices: List<Device>) : RecyclerView.Adapter<ItemDeviceAdapter.ViewHolder>() {

    private lateinit var context: Context

    class ViewHolder(val binding: ItemViewBinding): RecyclerView.ViewHolder(binding.root)
    override fun getItemCount(): Int = devices.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(ItemViewBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(devices[position]){
                binding.tDeviceName.text = name
                binding.tDeviceStatus.text = if(isConnected) "Device is connected" else "Device is disconnected"
                binding.iDeviceIco.setImageDrawable(getDeviceIco(type, isConnected))
            }
        }
    }

    private fun getDeviceIco(type:Int, connected: Boolean) : Drawable? {
        return AppCompatResources.getDrawable(context,
            when(type) {
                BluetoothProfile.HEADSET -> R.drawable.ic_headset
                else -> if (connected) R.drawable.ic_bluetooth_connected else R.drawable.ic_bluetooth_disconnected
            }
        )
    }
}