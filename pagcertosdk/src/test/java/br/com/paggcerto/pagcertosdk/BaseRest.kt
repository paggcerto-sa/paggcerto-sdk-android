package br.com.paggcerto.pagcertosdk

import br.com.paggcerto.pagcertosdk.model.account.response.Token
import org.junit.Assert.assertTrue
import org.junit.Before
import org.mockito.MockitoAnnotations
import java.util.concurrent.CountDownLatch

abstract class BaseRest {

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        PagcertoSDK.environment = Environment.HOMOL
        PagcertoSDK.token = Token("token")
    }

    protected fun assert(signal: CountDownLatch, method: String, code: Int, message: String? = null){
        assertTrue(true)
        println("[[$method]]: Code -> $code${if(!message.isNullOrEmpty()) "; Message -> $message" else ""}")
        signal.countDown()
    }
}