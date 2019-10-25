package br.com.paggcerto.pagcertosdk.command.response

internal class CommandResponseGin constructor(response: String?): CommandResponseBase(response) {

    var RSP_LEN1: String? = null
    var GIN_MNAME: String? = null
    var GIN_MODEL: String? = null
    var GIN_CTLSSUP: String? = null
    var GIN_SOVER: String? = null
    var GIN_SPECVER: String? = null
    var GIN_MANVER: String? = null
    var GIN_SERNUM: String? = null

    init {
        try {
            RSP_ID = readString(3)
            RSP_STAT = readString(3)//n
            RSP_LEN1 = readString(3)//n
            GIN_MNAME = readString(20)?.trim()
            GIN_MODEL = readString(19)?.trim()
            GIN_CTLSSUP = readString(1)
            GIN_SOVER = readString(20)?.trim()
            GIN_SPECVER = readString(4)
            GIN_MANVER = readString(16)
            GIN_SERNUM = readString(20)?.trim()
        }catch (ex: Exception){
            ex.printStackTrace()
        }
    }
}