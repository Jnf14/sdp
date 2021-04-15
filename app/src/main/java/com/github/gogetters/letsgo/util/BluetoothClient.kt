package com.github.gogetters.letsgo.util

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.os.Handler
import android.util.Log
import java.io.IOException
import java.util.*

class BluetoothClient(val handler: Handler) {

    private val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

    private inner class ConnectThread(device: BluetoothDevice): Thread() {
        private val uuid: UUID = UUID.fromString("8ce255c0-223a-11e0-ac64-0803450c9a66")
        private val mmSocket: BluetoothSocket? by lazy(LazyThreadSafetyMode.NONE) {
            device.createRfcommSocketToServiceRecord(uuid)
        }

        public override fun run() {
            // Cancel discovery because it otherwise slows down the connection.
            bluetoothAdapter?.cancelDiscovery()

            mmSocket?.use { socket ->
                // Connect to the remote device through the socket. This call blocks
                // until it succeeds or throws an exception.
                //TODO handle IOException for timeout???? surely....
                socket.connect()

                // The connection attempt succeeded. Perform work associated with
                // the connection in a separate thread.

                BluetoothGTPService(handler).connect(socket)
            }
        }

        // Closes the client socket and causes the thread to finish.
        fun cancel() {
            try {
                mmSocket?.close()
            } catch (e: IOException) {
                Log.e("BLUETOOTHCLIENT", "Could not close the client socket", e)
            }
        }
    }
}