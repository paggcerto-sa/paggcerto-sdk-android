package br.com.paggcerto.pagcertosdk.service

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.os.AsyncTask
import br.com.paggcerto.pagcertosdk.PagcertoSDK
import br.com.paggcerto.pagcertosdk.PagcertoSDKResponse

@SuppressLint("StaticFieldLeak")
class ConnectionProvider constructor(private val activity: Activity, private val message: String): AsyncTask<Void, Void, Boolean>() {

    private var dialog: ProgressDialog? = null

    var dialogTitle: String = ""
    var dialogMessage: String = ""
    var pagcertoCallBack: PagcertoSDKResponse? = null

    private val pinpadService = PagcertoSDK.pinpadService

    override fun onPreExecute() {
        dialog = ProgressDialog.show(activity, dialogTitle, dialogMessage, false, false)
    }

    override fun doInBackground(vararg p0: Void?): Boolean {

        if(!pinpadService.connect()) return false

        return pinpadService.writeDisplayMessage(message)
    }

    override fun onPostExecute(result: Boolean) {
        pagcertoCallBack?.onResult(result, if(result) "Conexão realizada com sucesso" else "Falha ao se conectar")

        dialog?.dismiss()
    }
}