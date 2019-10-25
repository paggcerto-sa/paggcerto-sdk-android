package br.com.paggcerto.pagcertosdk.model.table

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

internal class Aid {

    @SerializedName("AID")
    @Expose
    var aid: String? = null
    @SerializedName("BrandId")
    @Expose
    var brandId: Int? = null
    @SerializedName("AppName")
    @Expose
    var appName: String? = null
    @SerializedName("AidLen")
    @Expose
    var aidLen: Int? = null
    @SerializedName("SelFlag")
    @Expose
    var selFlag: String? = null
    @SerializedName("Priority")
    @Expose
    var priority: String? = null
    @SerializedName("TargetPer")
    @Expose
    var targetPer: String? = null
    @SerializedName("MaxTargetPer")
    @Expose
    var maxTargetPer: String? = null
    @SerializedName("FloorLimitCheck")
    @Expose
    var floorLimitCheck: String? = null
    @SerializedName("RandTransSel")
    @Expose
    var randTransSel: String? = null
    @SerializedName("VelocityCheck")
    @Expose
    var velocityCheck: String? = null
    @SerializedName("FloorLimit")
    @Expose
    var floorLimit: String? = null
    @SerializedName("Threshold")
    @Expose
    var threshold: String? = null
    @SerializedName("TACDenial")
    @Expose
    var tacDenial: String? = null
    @SerializedName("TACDefault")
    @Expose
    var tacDefault: String? = null
    @SerializedName("TACOnline")
    @Expose
    var tacOnline: String? = null
    @SerializedName("dDol")
    @Expose
    var dDol: String? = null
    @SerializedName("tDol")
    @Expose
    var tDol: String? = null
    @SerializedName("Version")
    @Expose
    var version: String? = null
    @SerializedName("RiskMainData")
    @Expose
    var riskMainData: String? = null
    @SerializedName("Recidx")
    @Expose
    var recidx: String? = null
    @SerializedName("ApplicationType")
    @Expose
    var applicationType: String? = null
    @SerializedName("TerminalCountry")
    @Expose
    var terminalCountry: String? = null
    @SerializedName("TerminalCurrencyCode")
    @Expose
    var terminalCurrencyCode: Int? = null
    @SerializedName("TerminalCurrencyExponent")
    @Expose
    var terminalCurrencyExponent: Int? = null
    @SerializedName("AdditionalTerminalCapabilities")
    @Expose
    var additionalTerminalCapabilities: String? = null
    @SerializedName("TerminalType")
    @Expose
    var terminalType: Int? = null
    @SerializedName("CTLSZEROAM")
    @Expose
    var ctlszeroam: String? = null
    @SerializedName("TCC")
    @Expose
    var tcc: String? = null
    @SerializedName("CTLSMODE")
    @Expose
    var ctlsmode: String? = null
    @SerializedName("CTLSTRNLIM")
    @Expose
    var ctlstrnlim: String? = null
    @SerializedName("CTLSFLRLIM")
    @Expose
    var ctlsflrlim: String? = null
    @SerializedName("CTLSCVMLIM")
    @Expose
    var ctlscvmlim: String? = null
    @SerializedName("CTLSAPPVER")
    @Expose
    var ctlsappver: String? = null
    @SerializedName("AcquirerNumber")
    @Expose
    var acquirerNumber: String? = null
    @SerializedName("MerchantId")
    @Expose
    var merchantId: String? = null
    @SerializedName("TerminalId")
    @Expose
    var terminalId: String? = null
    @SerializedName("Mcc")
    @Expose
    var mcc: String? = null
    @SerializedName("TerminalCapabilities")
    @Expose
    var terminalCapabilities: String? = null

    override fun toString(): String {
        var command = "1" //tab id
        command += acquirerNumber?.padStart(2, '0')//acq
        command += recidx?.padStart(2, '0')//recidx
        command += aidLen?.toString()?.padStart(2, '0')//aid len
        command += aid?.padEnd(32, '0')//aid
        command += applicationType?.padStart(2, '0')//apptype
        command += appName?.padEnd(16, ' ')//def label
        command += "03" //iccstd
        command += "0002" //appver1
        command += "0002" //appver2
        command += "0000" //appver3
        command += terminalCountry?.padStart(3, '0')//trmcntry
        command += terminalCurrencyCode?.toString()//trmcurr
        command += terminalCurrencyExponent?.toString()//trmcurrexp
        command += "020000080750001" //merchid
        command += "0000" //mcc
        command += "00000000" //trmid
        command += terminalCapabilities?.padEnd(6, '0')//trmcapab
        command += additionalTerminalCapabilities?.padEnd(10, '0')//addtrmcp
        command += terminalType?.toString()?.padStart(2, '0') //trmtyp
        command += tacDefault?.padEnd(10, '0')//tacdef
        command += tacDenial?.padEnd(10, '0')//tacden
        command += tacOnline?.padEnd(10, '0') //taconl
        command += floorLimit?.padEnd(8, '0') //flrlimit
        command += tcc //tcc
        command += ctlszeroam //ctlszeroam
        command += ctlsmode //ctls mode
        command += ctlstrnlim?.padEnd(8, '0') //CTLSTRNLIM
        command += ctlsflrlim?.padEnd(8, '0') //CTLSFLRLIM
        command += ctlscvmlim?.padEnd(8, '0') //CTLSCVMLIM
        command += ctlsappver?.padEnd(4, '0') //CTLSAPPVER
        command += "0" //ruf1
        command += tDol?.padEnd(40, '0') //tdoldef
        command += dDol?.padEnd(40, '0') //ddoldef
        command += "Y1Z1Y3Z3" //ARCOFFLN

        return "01" + (command.length + 3).toString().padStart(3, '0') + command
    }
}
