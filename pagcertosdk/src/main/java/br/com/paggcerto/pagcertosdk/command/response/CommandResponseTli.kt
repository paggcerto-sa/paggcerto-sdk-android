package br.com.paggcerto.pagcertosdk.command.response

internal class CommandResponseTli constructor(response: String?): CommandResponseBase(response) {

    var tableError = false

    init {
        try {
            RSP_ID = readString(3)
            RSP_STAT = readString(3)//n

            if(RSP_STAT != "000" && RSP_STAT != "020"){
                tableError = true
            }
        }catch (ex: Exception){
            ex.printStackTrace()
        }
    }
}