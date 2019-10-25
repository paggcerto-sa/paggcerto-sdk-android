package br.com.paggcerto.pagcertosdk.command.request

internal class CommandRequestGoc constructor(commandRequestGcr: CommandRequestGcr): RequestCard {

    private val acrprPinmsg = "R$0,01"
    private val acrprPinmsg2 = "PIN:"
    private val acrprTransactionType = ""
    private val acrprBypassPin = "0"
    private val tag1 = "5F2A82959A9C9F029F109F1A9F269F279F349F369F339F37"/*9F1E*/
    private val tag2 = ""

    private var request: String? = null

    var CMD_ID: String? = null
    var CMD_LEN1: String? = null

    var GOC_AMOUNT: String? = null
    var GOC_CASHBACK: String? = null
    var GOC_EXCLIST: String? = null
    var GOC_CONNECT: String? = null
    var GOC_RUF1: String? = null
    var GOC_METHOD: String? = null
    var GOC_KEYIDX: String? = null
    var GOC_WKENC: String? = null
    var GOC_RISKMAN: String? = null
    var GOC_FLRLIMIT: String? = null
    var GOC_TPBRS: String? = null
    var GOC_TVBRS: String? = null
    var GOC_MTPBRS: String? = null

    var GOC_ACQPRLEN: String? = null

    var GOC_TAGS1LEN: String? = null
    var CMD_LEN2: String? = null
    var GOC_TAGS1: String? = null

    var GOC_TAGS2LEN: String? = null
    var CMD_LEN3: String? = null
    var GOC_TAGS2: String? = null

    var exception = false

    //03 ou 06 - CHIP
    init{
        try {
            CMD_ID = "GOC"
            CMD_LEN1 = "086"

            GOC_AMOUNT = commandRequestGcr.GCR_AMOUNT?.padStart(12, '0')
            GOC_CASHBACK = "0".padStart(12, '0')
            GOC_EXCLIST = "0"
            GOC_CONNECT = "0"
            GOC_RUF1 = "0"
            GOC_METHOD = "3"
            GOC_KEYIDX = "16"
            GOC_WKENC = "0".padEnd(32, '0')
            GOC_RISKMAN = "0"
            GOC_FLRLIMIT = "0".padEnd(8, '0')
            GOC_TPBRS = "0".padEnd(2, '0')
            GOC_TVBRS = "0".padEnd(8, '0')
            GOC_MTPBRS = "00"

            when (commandRequestGcr.GCR_ACQIDXREQ) {
                "01" -> {
                    GOC_ACQPRLEN = "003"
                    GOC_ACQPRLEN += acrprTransactionType.padStart(2, '0')
                    GOC_ACQPRLEN += acrprBypassPin
                }
                "02" -> {
                    GOC_ACQPRLEN = "032"
                    GOC_ACQPRLEN +=
                            if(acrprPinmsg.isNotEmpty()){
                                val GOC_ACQPR = acrprPinmsg.padEnd(32, ' ')

                                GOC_ACQPR
                            }else{
                                val GOC_ACQPR_1 = "".padEnd(16, ' ')
                                val GOC_ACQPR_2 = acrprPinmsg2.padEnd(16, ' ')
                                val GOC_ACQPR = GOC_ACQPR_1 + GOC_ACQPR_2

                                GOC_ACQPR
                            }
                }
                else -> GOC_ACQPRLEN = "000"
            }

            val requestOne = CMD_ID + CMD_LEN1 + GOC_AMOUNT + GOC_CASHBACK + GOC_EXCLIST + GOC_CONNECT + GOC_RUF1 + GOC_METHOD + GOC_KEYIDX + GOC_WKENC + GOC_RISKMAN + GOC_FLRLIMIT + GOC_TPBRS + GOC_TVBRS + GOC_MTPBRS + GOC_ACQPRLEN

            val len = (requestOne.length-6).toString().padStart(3, '0')
            val requestStart = requestOne.substring(0, 3)
            val requestEnd = requestOne.substring(6, requestOne.length)

            request = requestStart + len + requestEnd

            GOC_TAGS1LEN = (tag1.length / 2).toString().padStart(3, '0')
            CMD_LEN2 = (GOC_TAGS1LEN?.length!! + tag1.length).toString().padStart(3, '0')
            GOC_TAGS1 = tag1

            GOC_TAGS2LEN = (tag2.length / 2).toString().padStart(3, '0')
            CMD_LEN3 = (GOC_TAGS2LEN?.length!! + tag2.length).toString().padStart(3, '0')
            GOC_TAGS2 = tag2

        }catch (ex: Exception){
            exception = true
        }
    }

    override fun createRequest(): String{
        return request + CMD_LEN2 + GOC_TAGS1LEN + GOC_TAGS1 + CMD_LEN3 + GOC_TAGS2LEN + GOC_TAGS2
    }
}