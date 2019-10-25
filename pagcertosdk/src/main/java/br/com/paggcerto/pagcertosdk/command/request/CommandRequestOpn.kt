package br.com.paggcerto.pagcertosdk.command.request

internal class CommandRequestOpn: RequestCard {

    /**Este comando aloca os recursos de hardware e software necessários ao funcionamento do pinpad**/
    override fun createRequest(): String {
        return "OPN"
    }
}