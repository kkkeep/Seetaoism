package com.mr.k.mvp

import android.annotation.SuppressLint
import android.content.Context
import android.os.UserManager
import com.mr.k.mvp.exceptions.ResultException
import com.mr.k.mvp.ok.ApiDataService
import okhttp3.Interceptor
import retrofit2.Converter
import retrofit2.http.GET
import java.lang.NullPointerException
import com.mr.k.mvp.init as userManagerInit


/*
 * created by Cherry on 2019-10-26
**/

@SuppressLint("StaticFieldLeak")
object MvpManager {

    @JvmStatic
    var messageGetter : ExceptionMessageGetter = object : ExceptionMessageGetter {
        override fun getNetError(): String {
            return context.getString(R.string.mvp_text_error_net_exception)
        }

        override fun getServerError(): String {
            return context.getString(R.string.mvp_text_error_server_exception)
        }

        override fun getUnKnowError(): String {
            return context.getString(R.string.mvp_text_error_un_know_exception)
        }
    };


    @JvmStatic
    private var context_back: Context? = null;

    @JvmStatic
     val context: Context
     get() {
        if(context_back == null){
            throw NullPointerException("Must invoke MvpManager init method to init before to use!");
        }
        return context_back!!
    }


    @JvmStatic
    fun init(context: Context){

         context_back = context.applicationContext
         userManagerInit(context.applicationContext)
    }

    @JvmStatic
    @JvmOverloads
    fun initOkHttp(baseUrl: String, aClass: Class<out Any>, factory: Converter.Factory? = null, interceptors: List<Interceptor>? = null) {
        ApiDataService.init(interceptors, factory, baseUrl, aClass)
    }

    @JvmStatic
    fun initExceptionMessage(messageGetter: ExceptionMessageGetter) {
        MvpManager.messageGetter = messageGetter;
    }


    @JvmStatic
    fun getApiService() : Any{
        return ApiDataService.getApiService();
    }


}


interface ExceptionMessageGetter {


    fun getNetError(): String

    fun getServerError(): String

    fun getUnKnowError(): String
}

