package br.com.paggcerto.pagcertosdk.command.response

internal class CommandResponseGpn constructor(response: String?): CommandResponseBase(response){

    var RSP_LEN1: String? = null
    var GPN_PINBLK: String? = null
    var GPN_KSN: String? = null

    var userCancelled = false
    var errorReadMagneticStripe = false
    var timeout = false

    init {
        try {
            RSP_ID = readString(3)
            RSP_STAT = readString(3)//n

            when (RSP_STAT){
                "000" ->{
                    RSP_LEN1 = readString(3)//n

                    GPN_PINBLK = readString(16)
                    GPN_KSN = readString(20)}
                "012" -> {
                    timeout = true
                }
                "013" ->{
                    userCancelled = true
                }
                "041" -> {
                    errorReadMagneticStripe = true
                }
            }

        }catch (ex: Exception){
            ex.printStackTrace()
        }
    }

}