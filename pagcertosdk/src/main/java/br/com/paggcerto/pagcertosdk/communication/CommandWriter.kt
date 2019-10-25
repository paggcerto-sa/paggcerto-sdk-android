package br.com.paggcerto.pagcertosdk.communication

import br.com.paggcerto.pagcertosdk.util.Util
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

internal class CommandWriter constructor(private val inputStream: InputStream?, private val outputStream: OutputStream?, private val timeOut: Int) {

    fun writeRequest(commandRequest: String): Boolean{
        try {
            val frame = ByteArrayOutputStream()
            frame.write(Util.SYN)
            frame.write(commandRequest.toByteArray())
            frame.write(Util.ETB)
            appendCrc16(frame)

            var count = 0

            do {
                outputStream?.write(frame.toByteArray())
                val result = get(timeOut)
                if (result == Util.ACK) {
                    return true
                }

                ++count
            } while (count != 3)

            throw IOException("Communication error")
        } catch (ex: Exception) {
            ex.printStackTrace()
            return false
        }
    }

    private fun appendCrc16(frame: ByteArrayOutputStream) {
        val soFar = frame.toByteArray()
        val crc = Util.crc16(soFar, 1, soFar.size - 1)
        frame.write(crc shr 8 and 255)
        frame.write(crc and 255)
    }

    @Throws(IOException::class)
    private fun get(timeout: Int): Int? {
        val deadline = System.currentTimeMillis() + timeout.toLong()

        while (System.currentTimeMillis() <= deadline) {
            if (inputStream?.available() ?: 0 > 0) {
                return inputStream?.read()
            }

            try {
                Thread.sleep(2L)
            } catch (var5: InterruptedException) {}
        }

        return -1
    }
}