package br.com.paggcerto.pagcertosdk.command.response

internal class CommandResponseGcr constructor(response: String?): CommandResponseBase(response){

    var RSP_LEN1: String? = null
    var GCR_CARDTYPE: String? = null
    var GCR_STATCHIP: String? = null
    var CGR_APPTYPE: String? = null
    var GCR_ACQIDX: String? = null
    var GCR_RECIDX: String? = null
    var GCR_TRK1LEN: String? = null
    var GCR_TRK1: String? = null
    var GCR_TRK2LEN: String? = null
    var GCR_TRK2: String? = null
    var GCR_TRK3LEN: String? = null
    var GCR_TRK3: String? = null
    var GCR_PANLEN: String? = null
    var GCR_PAN: String? = null
    var GCR_PANSEQNO: String? = null
    var GCR_APPLABEL: String? = null
    var GCR_SRVCODE: String? = null
    var GCR_CHNAME: String? = null
    var GCR_CARDEXP: String? = null
    var GCR_RUF1: String? = null
    var GCR_ISSCNTRY: String? = null
    var GCR_ACQRDLEN: String? = null

    var needLoadTable = false
    var userCancelled = false
    var readChipError = false
    var timeout = false
    var errorReadMagneticStripe = false

    init {
        try {
            RSP_ID = readString(3)
            RSP_STAT = readString(3)//n
            when (RSP_STAT) {
                "000" -> {
                    RSP_LEN1 = readString(3)//n
                    GCR_CARDTYPE = readString(2)//n
                    GCR_STATCHIP = readString(1)//n
                    CGR_APPTYPE = readString(2)//n
                    GCR_ACQIDX = readString(2)//n
                    GCR_RECIDX = readString(2)//n
                    GCR_TRK1LEN = readString(2)//n
                    GCR_TRK1 = readString(76)?.trimEnd(' ')
                    GCR_TRK2LEN = readString(2)//n
                    GCR_TRK2 = readString(37)?.trimEnd(' ')
                    GCR_TRK3LEN = readString(3)//n
                    GCR_TRK3 = readString(104)?.trimEnd(' ')
                    GCR_PANLEN = readString(2)//n
                    GCR_PAN = readString(19)?.trimEnd(' ')
                    GCR_PANSEQNO = readString(2)//n
                    GCR_APPLABEL = readString(16)?.trimEnd(' ')
                    GCR_SRVCODE = readString(3)//n
                    GCR_CHNAME = readString(26)?.trimEnd(' ')
                    GCR_CARDEXP = readString(6)
                    GCR_RUF1 = readString(29)//n
                    GCR_ISSCNTRY = readString(3)//n
                    GCR_ACQRDLEN = readString(3)//n

                    //Utiliza o nome do TRK1 caso seja utilizado tarja
                    GCR_CHNAME =
                    if (isChip()) {
                        GCR_CHNAME
                    } else if (isMagneticStripe()){
                        val trk1Split = GCR_TRK1?.split("\\^".toRegex())?.dropLastWhile { it.isEmpty() }?.toTypedArray()
                        if (trk1Split?.size!! >= 2){
                            trk1Split[1]
                        } else ""
                    } else ""
                }
                "012" -> {
                    timeout = true
                }
                "013" -> {
                    userCancelled = true
                }
                "020" -> {
                    needLoadTable = true
                }
                "041" ->{
                    errorReadMagneticStripe = true
                }
                "060" -> {
                    readChipError = true
                }
            }
        }catch (ex: Exception){
            ex.printStackTrace()
        }
    }

    fun needPassword(): Boolean {
        return if (GCR_TRK2 != null) {
            if (GCR_TRK2?.split("=".toRegex())?.dropLastWhile { it.isEmpty() }?.toTypedArray()?.size!! < 2) {
                true
            } else {
                val additionalData = GCR_TRK2!!.split("=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
                val serviceCode = additionalData.substring(4, 7)
                serviceCode[2].toInt() == 48 || serviceCode[2].toInt() == 51 || serviceCode[2].toInt() == 53 || serviceCode[2].toInt() == 54 || serviceCode[2].toInt() == 55
            }
        } else {
            true
        }
    }

    fun isMagneticStripe(): Boolean{
        return GCR_CARDTYPE == null || GCR_CARDTYPE == "00" || GCR_CARDTYPE == "05"
    }

    fun isChip(): Boolean{
        return GCR_CARDTYPE == "03" || GCR_CARDTYPE == "06"
    }
}