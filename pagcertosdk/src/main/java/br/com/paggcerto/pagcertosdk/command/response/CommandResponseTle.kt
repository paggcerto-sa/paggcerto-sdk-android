package br.com.paggcerto.pagcertosdk.command.response

internal class CommandResponseTle constructor(response: String?) : CommandResponseBase(response) {

    var errorSaveTable = false

    init {
        try {
            RSP_ID = readString(3)
            RSP_STAT = readString(3)//n

            if(RSP_STAT == "021"){
                errorSaveTable = true
            }
        }catch (ex: Exception){
            ex.printStackTrace()
        }
    }
}