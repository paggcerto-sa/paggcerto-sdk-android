package br.com.paggcerto.pagcertosdk

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.paggcerto.pagcertosdk.model.account.response.Token
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
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
        PagcertoSDK.token = Token("token")

        PagcertoSDK.activate(context, object : PagcertoSDKResponse{
            override fun onResult(result: Boolean, message: String) {
                signal.countDown()
                Assert.assertEquals(true, PagcertoSDK.isActive())
            }
        })

        signal.await()
    }
}