package br.com.paggcerto.pagcertosdk.model.support

import android.bluetooth.BluetoothClass.Device
import android.bluetooth.BluetoothSocket
import br.com.paggcerto.pagcertosdk.communication.CommandReader
import br.com.paggcerto.pagcertosdk.communication.CommandWriter
import java.io.InputStream
import java.io.OutputStream

internal class PinpadObject: Device() {
    var id: Int? = null

    var name: String? = null
    var macAddress: String? = null

    @Transient
    var bluetoothSocket: BluetoothSocket? = null
    @Transient
    var inputStream: InputStream? = null
    @Transient
    var outPutStream: OutputStream? = null

    @Transient
    var writer: CommandWriter? = null
    @Transient
    var reader: CommandReader? = null

    fun closeConnection() {
        inputStream?.close()
        outPutStream?.close()
        bluetoothSocket?.close()

        bluetoothSocket = null
    }
}
