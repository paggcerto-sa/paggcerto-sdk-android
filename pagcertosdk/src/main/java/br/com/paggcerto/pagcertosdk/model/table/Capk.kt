package br.com.paggcerto.pagcertosdk.model.table

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

internal class Capk {

    @SerializedName("CapkIdx")
    @Expose
    var capkIdx: String? = null
    @SerializedName("CheckSum")
    @Expose
    var checkSum: String? = null
    @SerializedName("CheckSumStatus")
    @Expose
    var checkSumStatus: String? = null
    @SerializedName("DataSetVersion")
    @Expose
    var dataSetVersion: Double? = null
    @SerializedName("Exponent")
    @Expose
    var exponent: String? = null
    @SerializedName("Modulus")
    @Expose
    var modulus: String? = null
    @SerializedName("RID")
    @Expose
    var rid: String? = null
    @SerializedName("RecIdx")
    @Expose
    var recIdx: String? = null
    @SerializedName("ExponentLen")
    @Expose
    var exponentLen: Int? = null
    @SerializedName("ModulusLen")
    @Expose
    var modulusLen: Int? = null
    @SerializedName("AcquirerNumber")
    @Expose
    var acquirerNumber: Int? = null

    override fun toString(): String {

        var command = "01611" //tab len
        command += "2" //tab id
        command += acquirerNumber?.toString()?.padStart(2, '0')//acq
        command += recIdx?.padStart(2, '0')//recidx
        command += rid?.padEnd(10, '0')//rid
        command += capkIdx?.padStart(2, '0')//capkidx
        command += "00" //ruf1
        command += exponentLen?.toString() //exp len
        command += exponent?.padEnd(6, '0')//exp
        command += modulusLen?.toString()?.padStart(3, '0')//mod len
        command += modulus?.padEnd(496, '0')//modulus
        command += checkSumStatus
        command += if(checkSumStatus == "0"){
            "0000000000000000000000000000000000000000"
        }else{
            checkSum
        }
        command += "000000000000000000000000000000000000000000"

        return command
    }
}
