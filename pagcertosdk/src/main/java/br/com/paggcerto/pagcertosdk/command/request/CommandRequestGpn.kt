package br.com.paggcerto.pagcertosdk.command.request

import br.com.paggcerto.pagcertosdk.command.response.CommandResponseGcr

internal class CommandRequestGpn constructor(commandRequestGcr: CommandRequestGcr, commandResponseGrc: CommandResponseGcr): RequestCard {

    var CMD_ID: String? = null
    var CMD_LEN1: String? = null

    var GPN_METHOD: String? = null
    var GPN_KEYIDX: String? = null
    var GPN_WKENC: String? = null

    var GPN_PANLEN: String? = null
    var GPN_PAN: String? = null
    var GPN_ENTRIES: String? = null
    var GPN_MIN1: String? = null
    var GPN_MAX1: String? = null


    var GPN_MSG1: String? = null

    private var request: String? = null

    private var requestLen: String? = null

    var exception = false

    init{
        try {
            CMD_ID = "GPN"
            CMD_LEN1 = "000"

            GPN_METHOD = "3"
            GPN_KEYIDX = "16"
            GPN_WKENC = "0".padEnd(32, '0')

            val pan = commandResponseGrc.GCR_TRK2?.split("=")!![0]

            GPN_PANLEN = pan.length.toString().padStart(2, '0')
            GPN_PAN = pan.padEnd(19, ' ')
            GPN_ENTRIES = "1"
            GPN_MIN1 = "04"
            GPN_MAX1 = "12"

            var message= ""

            if(commandRequestGcr.GCR_AMOUNT?.length!! <= 7){
                message += "VALOR:"
                message += commandRequestGcr.GCR_AMOUNT?.padStart(10, ' ')
                message += "Senha:"
            }else{
                message += commandRequestGcr.GCR_AMOUNT?.padEnd(16, ' ')
                message += "Senha:"
            }

            GPN_MSG1 = message.padEnd(32, ' ')

            request = CMD_ID + CMD_LEN1 + GPN_METHOD + GPN_KEYIDX + GPN_WKENC + GPN_PANLEN + GPN_PAN + GPN_ENTRIES + GPN_MIN1 + GPN_MAX1 + GPN_MSG1

            requestLen = (request?.length!! - 6).toString().padStart(3, '0')
        }catch (ex: Exception){
            exception = true
        }


    }

    /**Este comando captura a senha do portador do cartão (PIN) e retorna um bloco de
    dados criptografados segundo o método MK/WK (DES ou TDES) ou DUKPT (DES
    ou TDES).**/
    //00 ou 05 - Tarja
    override fun createRequest(): String {
        return CMD_ID + requestLen + request?.substring(6, request?.length!!)
    }
}