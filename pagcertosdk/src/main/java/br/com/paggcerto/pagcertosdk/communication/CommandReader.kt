package br.com.paggcerto.pagcertosdk.communication

import android.util.Log
import br.com.paggcerto.pagcertosdk.util.Util
import br.com.paggcerto.pagcertosdk.util.Util.errorTimeOut
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

internal class CommandReader constructor(private val inputStream: InputStream?, private val outputStream: OutputStream?, private val timeOut: Int){

    companion object {
        var forceBreak = false
    }

    fun getResponse(): String {

        return try{
            var response: String
            var cmdId: String
            do {
                response = read()
                if(response == errorTimeOut){
                    if(forceBreak){
                        forceBreak = false
                        return Util.cancelOperation
                    }
                    return errorTimeOut
                }

                cmdId = response.substring(0, 3)
            } while (cmdId == "NTM")

            response
        }catch (ex: Exception){
            ex.message!!
        }
    }

    private fun read(): String {
        try {
            if(skipUntil(Util.SYN, timeOut)){
                val frame = ByteArrayOutputStream()
                if (readUntil(Util.ETB, timeOut, frame)) {
                    val inputReader1 = get(timeOut)
                    if(inputReader1 >= 0){
                        val inputReader2 = get(timeOut)
                        if(inputReader2 >= 0){
                            val frameData = frame.toByteArray()
                            if (Util.crc16(frameData, 0, frameData.size) != (inputReader1 shl 8) + inputReader2) {
                                Log.e("CRC", "CRC check fail")
                            }
                            put(Util.ACK)
                            return String(frameData, 0, frameData.size - 1)
                        }else{
                            throw IOException("Incomplete frame")
                        }
                    }else{
                        throw IOException("Incomplete frame")
                    }
                }else{
                    throw IOException("Incomplete frame")
                }
            }else{
                throw IOException(errorTimeOut)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            return ex.message ?: ""
        }
    }

    @Throws(IOException::class)
    private fun put(input: Int) {
        outputStream?.write(input)
    }

    @Throws(IOException::class)
    private fun skipUntil(terminator: Int, timeout: Int): Boolean {
        return readUntil(terminator, timeout, null as ByteArrayOutputStream?)
    }

    @Throws(IOException::class)
    private fun readUntil(terminator: Int, timeout: Int, byteArrayOutputStream: ByteArrayOutputStream?): Boolean {
        val deadline = System.currentTimeMillis() + timeout.toLong()

        while (System.currentTimeMillis() <= deadline) {
            val inputReader = get(timeout)
            if(inputReader == -2) return false

            if (inputReader >= 0) {
                byteArrayOutputStream?.write(inputReader)

                if (inputReader == terminator) {
                    return true
                }
            }
        }

        return false
    }

    @Throws(IOException::class)
    private fun get(timeout: Int): Int {
        val deadline = System.currentTimeMillis() + timeout.toLong()

        while (System.currentTimeMillis() <= deadline) {
            if(forceBreak){
                return -2
            }

            if (inputStream?.available()!! > 0) {
                return inputStream.read()
            }

            try {
                Thread.sleep(1L)
            } catch (var5: InterruptedException) {}

        }

        return -1
    }

}