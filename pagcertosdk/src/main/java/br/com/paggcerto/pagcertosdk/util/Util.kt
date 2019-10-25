package br.com.paggcerto.pagcertosdk.util

import android.bluetooth.BluetoothAdapter
import br.com.paggcerto.pagcertosdk.model.support.PinpadObject
import org.json.JSONObject
import retrofit2.Response

internal object Util {

    const val SYN = 22
    const val ETB = 23
    const val ACK = 6

    const val timeOut = 30000

    private const val UUID = "00001101-0000-1000-8000-00805f9b34fb"

    const val baseUrl = "http://apicore.paggcerto.com.br/pinpad/"

    const val baneseRegex = "^63(6117|7470|7473)"

    private var ENVIRONMENT = "null"
    private var PROTOCOL = "https"
    var PAYMENTS_API_URL = "$PROTOCOL://payments.${ENVIRONMENT}paggcerto.com.br/api/v2/"
    var PAYMENTS_V3_API_URL = "$PROTOCOL://payments.${ENVIRONMENT}paggcerto.com.br/api/v3/"
    var BILLING_API_URL = "$PROTOCOL://billing.${ENVIRONMENT}paggcerto.com.br/api/v1/"
    var ACCOUNT_API_URL = "$PROTOCOL://account.${ENVIRONMENT}paggcerto.com.br/api/v2/"
    var PAYMENT_ACCOUNT_API_URL = "$PROTOCOL://payment-accounts.${ENVIRONMENT}paggcerto.com.br/api/v1/"
    var WARNINGS_API_URL = "$PROTOCOL://warnings.${ENVIRONMENT}paggcerto.com.br/api/v2/"
    var RECURRING_API_URL = "$PROTOCOL://recurring.${ENVIRONMENT}paggcerto.com.br/api/v2/"

    const val errorTimeOut = "PAG012"
    const val cancelOperation = "PAG013"

    fun updateEnvironment(environment: String, protocol: String){
        ENVIRONMENT = environment
        PROTOCOL = protocol

        PAYMENTS_API_URL = "$PROTOCOL://payments.${ENVIRONMENT}paggcerto.com.br/api/v2/"
        PAYMENTS_V3_API_URL = "$PROTOCOL://payments.${ENVIRONMENT}paggcerto.com.br/api/v3/"
        BILLING_API_URL = "$PROTOCOL://billing.${ENVIRONMENT}paggcerto.com.br/api/v1/"
        ACCOUNT_API_URL = "$PROTOCOL://account.${ENVIRONMENT}paggcerto.com.br/api/v2/"
        PAYMENT_ACCOUNT_API_URL = "$PROTOCOL://payment-accounts.${ENVIRONMENT}paggcerto.com.br/api/v1/"
        WARNINGS_API_URL = "$PROTOCOL://warnings.${ENVIRONMENT}paggcerto.com.br/api/v2/"
        RECURRING_API_URL = "$PROTOCOL://recurring.${ENVIRONMENT}paggcerto.com.br/api/v2/"
    }

    fun crc16(buffer: ByteArray, offset: Int, length: Int): Int {
        var crc = 0

        val bit16 = 65535
        val rangeData = 32768

        for (i in 0 until length) {
            var word = buffer[offset + i] * 256

            var k = 0
            while (k < 8) {
                crc = if (crc xor word and rangeData != 0) {
                    crc shl 1 xor 4129
                } else {
                    crc shl 1
                }

                ++k
                word = word shl 1
            }
        }

        return crc and bit16
    }

    fun verifyPinpad(pinpadObject: PinpadObject?){
        if(pinpadObject?.bluetoothSocket == null){

            val btDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(pinpadObject?.macAddress)
            pinpadObject?.bluetoothSocket = btDevice.createRfcommSocketToServiceRecord(java.util.UUID.fromString(UUID))

            if(pinpadObject?.bluetoothSocket?.isConnected != true) {
                try {
                    pinpadObject?.bluetoothSocket?.connect()

                    pinpadObject?.inputStream = pinpadObject?.bluetoothSocket?.inputStream
                    pinpadObject?.outPutStream = pinpadObject?.bluetoothSocket?.outputStream
                }catch (ex: Exception){
                    ex.printStackTrace()
                }
            }
        }
    }

    fun printError(response: Response<String>): String{
        return try {
            JSONObject(response.errorBody()?.string()).getString("error")
        } catch (e: Exception) {
            e.printStackTrace()
            "RESPONSE ERROR"
        }
    }
}