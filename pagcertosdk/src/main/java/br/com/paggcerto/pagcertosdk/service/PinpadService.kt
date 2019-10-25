package br.com.paggcerto.pagcertosdk.service

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.os.Build
import br.com.paggcerto.pagcertosdk.service.interfaces.PinpadServiceCallBack
import br.com.paggcerto.pagcertosdk.util.Util
import br.com.paggcerto.pagcertosdk.util.Util.timeOut
import br.com.paggcerto.pagcertosdk.command.request.*
import br.com.paggcerto.pagcertosdk.command.response.CommandResponseClo
import br.com.paggcerto.pagcertosdk.command.response.CommandResponseDsp
import br.com.paggcerto.pagcertosdk.command.response.CommandResponseGin
import br.com.paggcerto.pagcertosdk.command.response.CommandResponseOpn
import br.com.paggcerto.pagcertosdk.communication.CommandReader
import br.com.paggcerto.pagcertosdk.communication.CommandWriter
import br.com.paggcerto.pagcertosdk.model.support.PinpadDescription
import br.com.paggcerto.pagcertosdk.model.support.PinpadObject
import br.com.paggcerto.pagcertosdk.ReadCardInterface
import br.com.paggcerto.pagcertosdk.model.payments.response.MobileDevice
import br.com.paggcerto.pagcertosdk.model.payments.response.PaymentDevice
import java.lang.RuntimeException

class PinpadService{

    private var bluetoothAdapter: BluetoothAdapter? = null

    val listDevice = arrayListOf<BluetoothDevice>()
    var device: BluetoothDevice? = null
        set(value) {
            field = value
            pinpadObject = PinpadObject().apply {
                name = device?.name
                macAddress = device?.address
            }
        }

    private var pinpadObject: PinpadObject? = null

    private var writer: CommandWriter? = null
    private var reader: CommandReader? = null

    private var pinpadConnectionProvider: PinpadConnectionProvider? = null

    init {
        try{
            if(BluetoothAdapter.getDefaultAdapter() != null){
                bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
            }
        }catch (ex: RuntimeException){
            bluetoothAdapter = null
        }

    }

    fun isPinpad(deviceName: String?): Boolean{
        return  deviceName != null && ( deviceName.contains("MP 5") ||
                                        deviceName.contains("MOBIPIN") ||
                                        deviceName.contains("PAX") ||
                                        deviceName.contains("ME30S"))
    }

    @Throws (Exception::class)
    fun loadDevices(){
        if(bluetoothAdapter?.isEnabled == true) {
            val pairedDevices: Set<BluetoothDevice> = bluetoothAdapter!!.bondedDevices

            pairedDevices.forEach { device: BluetoothDevice ->
                if(isPinpad(device.name) && !listDevice.contains(device)){
                    this.listDevice.add(device)
                }
            }
        }else{
            throw Exception("Bluetooth disable")
        }
    }

    fun removeDevice(device: BluetoothDevice){
        listDevice.remove(device)
    }

    fun clearListDevice(){
        listDevice.clear()
    }

    fun isConnected(): Boolean{
        return pinpadObject?.bluetoothSocket != null && pinpadObject?.bluetoothSocket?.isConnected == true
    }

    //OPN
    fun connect(): Boolean{

        if(pinpadObject!= null){
            Util.verifyPinpad(pinpadObject)

            writer = CommandWriter(pinpadObject?.inputStream, pinpadObject?.outPutStream, timeOut)
            reader = CommandReader(pinpadObject?.inputStream, pinpadObject?.outPutStream, timeOut)

            pinpadObject?.writer = writer
            pinpadObject?.reader = reader
        }

        writer?.writeRequest(CommandRequestOpn().createRequest())
        val response = reader?.getResponse()

        val opnResponse = CommandResponseOpn(response)
        return !opnResponse.hasError()
    }

    //CLO
    fun disconnect(message: String): Boolean{

        writer?.writeRequest(CommandRequestClo(message).createRequest())
        val response = reader?.getResponse()

        pinpadObject?.closeConnection()

        val cloResponse = CommandResponseClo(response)

        if(!cloResponse.hasError())
            return true

        return false
    }

    //DSP
    fun writeDisplayMessage(message: String): Boolean{

        writer?.writeRequest(CommandRequestDsp(message).createRequest())
        val response = reader?.getResponse()

        val dspResponse = CommandResponseDsp(response)

        if(!dspResponse.hasError())
            return true

        return false
    }

    //GIN
    fun getPinpadInformation(): PinpadDescription?{

        writer?.writeRequest(CommandRequestGin().createRequest())
        val response = reader?.getResponse()

        val ginResponse = CommandResponseGin(response)

        val pinpadDescription = PinpadDescription().apply {
            manufacturer = ginResponse.GIN_MNAME
            model = ginResponse.GIN_MODEL
            supportCtls = ginResponse.GIN_CTLSSUP == "C"
            version = ginResponse.GIN_SOVER
            specificVersion = ginResponse.GIN_SPECVER
            applicationVersion = ginResponse.GIN_MANVER
            serialNumber = ginResponse.GIN_SERNUM
        }

        if(!ginResponse.hasError())
            return pinpadDescription

        return null
    }

    //GCR GPN GOC
    fun getCard (
             activity: Activity,
             credit: Boolean,
             value: Double,
             installments: Int,
             readCardInterface: ReadCardInterface?,
             pinpadServiceCallBack: PinpadServiceCallBack){

        val commandRequestGcr = CommandRequestGcr(credit, value.toString())

        pinpadConnectionProvider = PinpadConnectionProvider(activity, pinpadObject, installments, readCardInterface, pinpadServiceCallBack)
        pinpadConnectionProvider?.execute(commandRequestGcr)
    }

    fun stopCardProcess() {
        if(pinpadConnectionProvider?.status != AsyncTask.Status.FINISHED && pinpadConnectionProvider?.isCancelled != true) {
            pinpadConnectionProvider?.cancel(true)
            CommandReader.forceBreak = true
        }
    }

    fun getMobileDevice(context: Context): MobileDevice?{

        return try {
            val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            val version = pInfo.versionName
            MobileDevice(
                appVersion = version,
                manufacturer = Build.MANUFACTURER,
                model = Build.MODEL,
                release = Build.VERSION.RELEASE,
                sdkVersion = Build.VERSION.SDK_INT
            )
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            null
        }
    }

    fun getPaymentDevice(): PaymentDevice?{

        writer?.writeRequest(CommandRequestGin().createRequest())
        val response = reader?.getResponse()

        val ginResponse = CommandResponseGin(response)

        if(!ginResponse.hasError()){

            val ginModel =
                    when {
                        (ginResponse.GIN_MODEL ?: "").contains("MP5") -> "MP5"
                        (ginResponse.GIN_MODEL ?: "").contains("D180") -> "D180"
                        (ginResponse.GIN_MODEL ?: "").replace(" ", "",  false).contains("MOBIPIN10") -> "MOBIPIN10"
                        else -> ginResponse.GIN_MODEL
                    }

            return PaymentDevice(
                serialNumber = ginResponse.GIN_SERNUM ?: "",
                model = ginModel ?: ""
            )
        }

        return null
    }

    private fun getGinPaymentDevice(): PaymentDevice?{

        val pinpadInfo = device?.name?.split("-")
        if(!pinpadInfo.isNullOrEmpty()){
            val deviceSerialNumber = pinpadInfo[1]
            val deviceModel = pinpadInfo[0].replace(" ", "")

            return PaymentDevice(
                serialNumber = deviceSerialNumber,
                model = deviceModel
            )
        }
        return null
    }
}