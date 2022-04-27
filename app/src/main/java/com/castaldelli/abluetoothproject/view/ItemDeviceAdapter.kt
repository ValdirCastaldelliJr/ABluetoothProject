package com.castaldelli.abluetoothproject.view

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
                binding.iDeviceIco.setImageDrawable(getDeviceIco(isConnected))
                binding.iDeviceIco.setBackgroundColor(getBackgroundColor(isConnected))
                binding.lPair.text = getStatus(isConnected)
            }
        }
    }

    private fun getStatus(connected: Boolean) : CharSequence {
        return if (connected) {
            context.getText(R.string.connect)
        } else {
            context.getText(R.string.paired)
        }
    }

    private fun getBackgroundColor(connected: Boolean) : Int {
        return context.getColor(
            if (connected)
                R.color.primaryColor
            else
                R.color.primaryDarkColor
        )
    }

    private fun getDeviceIco(connected: Boolean) : Drawable? {
        return AppCompatResources.getDrawable(context,
            if (connected)
                R.drawable.ic_bluetooth_connected_white
            else
                R.drawable.ic_bluetooth_disconnected_white
        )
    }
}