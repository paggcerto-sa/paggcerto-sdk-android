package br.com.paggcerto.pagcertosdk.command.response

internal class CommandResponseGoc constructor(response: String?): CommandResponseBase(response){

    var RSP_LEN1: String? = null
    var GOC_DECISION: String? = null
    var GOC_SIGNAT: String? = null
    var GOC_PINOFF: String? = null
    var GOC_ERRPINOFF: String? = null
    var GOC_PBLOCKED: String? = null
    var GOC_PINONL: String? = null
    var GOC_PINBLK: String? = null
    var GOC_KSN: String? = null
    var GOC_EMVDTLEN: String? = null
    var GOC_EMVDAT: String? = null
    var GOC_ACQRDLEN: String? = null

    var userCancelled = false
    var timeout = false

    init {
        try {
            RSP_ID = readString(3)
            RSP_STAT = readString(3)//n
            when (RSP_STAT) {
                "000" -> {
                    RSP_LEN1 = readString(3)//n

                    GOC_DECISION = readString(1)//n
                    GOC_SIGNAT = readString(1)//n
                    GOC_PINOFF = readString(1)//n
                    GOC_ERRPINOFF = readString(1)//n
                    GOC_PBLOCKED = readString(1)//n
                    GOC_PINONL = readString(1)//n
                    GOC_PINBLK = readString(16)
                    GOC_KSN = readString(20)
                    GOC_EMVDTLEN = readString(3)//n
                    GOC_EMVDAT = if(GOC_EMVDTLEN?.toInt()!! > 0) readString(GOC_EMVDTLEN?.toInt()!! * 2) else null
                    GOC_ACQRDLEN = readString(3)//n
                }
                "012" -> {
                    timeout = true
                }
                "013" -> {
                    userCancelled = true
                }
            }

        }catch (ex: Exception){
            ex.printStackTrace()
        }
    }
}