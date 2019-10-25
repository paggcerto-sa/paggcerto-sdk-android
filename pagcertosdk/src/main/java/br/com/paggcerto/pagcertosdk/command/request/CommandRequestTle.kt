package br.com.paggcerto.pagcertosdk.command.request

internal class CommandRequestTle: RequestCard {

    var CMD_ID: String? = null

    init {
        CMD_ID = "TLE"
    }

    override fun createRequest(): String {
        return CMD_ID!!
    }
}