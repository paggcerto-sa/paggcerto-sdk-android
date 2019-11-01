package br.com.paggcerto.pagcertosdk

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.paggcerto.pagcertosdk.model.payments.request.PayCard
import br.com.paggcerto.pagcertosdk.service.interfaces.PinpadServiceCallBack
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Exception
import java.util.concurrent.CountDownLatch

@RunWith(AndroidJUnit4::class)
class InstrumentedTest {

    private lateinit var context: Context

    @Before
    fun startContext(){
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun activateTest(){
        val signal = CountDownLatch(1)
        PagcertoSDK.environment = Environment.HOMOL
        PagcertoSDK.token = "token"

        PagcertoSDK.enablePinpadService(context, object : PagcertoSDKResponse{
            override fun onResult(result: Boolean, message: String) {
                signal.countDown()
                Assert.assertEquals(true, PagcertoSDK.isEnablePinpadService())
            }
        })

        signal.await()
    }

    @Test
    fun cardTransaction(){
        if(PagcertoSDK.isEnablePinpadService()){
            val pinpadService = PagcertoSDK.pinpadService
            try{
                pinpadService.loadDevices()
                pinpadService.device = pinpadService.listDevice[0]
                if(pinpadService.connect()){
                    val activity = context as AppCompatActivity
                    val credit = true
                    val amount = 100.0
                    val instalment = 3

                    val readCardInterface = object : ReadCardInterface{
                        override fun didReadCard() {/*TASK*/}
                    }

                    val pinpadServiceCallBack = object : PinpadServiceCallBack{
                        override fun onSuccess(card: PayCard, online: Boolean) {
                            Assert.assertEquals(true, card.brand != null)
                        }
                        override fun onError(message: String) {/*TASK*/}
                    }

                    pinpadService.getCard(
                        activity,
                        credit,
                        amount,
                        instalment,
                        readCardInterface,
                        pinpadServiceCallBack
                    )
                }else{
                    println("Pinpad connection failed.")
                }
            }catch (ex: Exception){
                println("Bluetooth disabled.")
            }
        }else{
            println("PinpadService disabled.")
        }
    }
}