package br.com.paggcerto.pagcertosdk.command.response

internal open class CommandResponseBase constructor(private val response: String?) {

    var RSP_ID: String? = null
    var RSP_STAT: String? = null

    private var position: Int = 0

    fun readString(length: Int): String? {
        val substring = response?.substring(position, position + length)
        this.goToIndex(length)
        return substring
    }

    private fun goToIndex(length: Int) {
        position += length
    }

    fun hasError(): Boolean {
        return RSP_STAT != "000"
    }
}