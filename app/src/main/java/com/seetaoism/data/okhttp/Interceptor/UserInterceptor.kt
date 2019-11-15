package com.seetaoism.data.okhttp.Interceptor

import android.text.TextUtils
import com.mr.k.mvp.exceptions.ResultException
import com.mr.k.mvp.getToken
import com.seetaoism.AppConstant
import com.seetaoism.JDApplication
import com.seetaoism.R
import com.seetaoism.user.login.LoginActivity
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import java.nio.charset.StandardCharsets


/*
 * created by Cherry on 2019-11-14
**/

class UserInterceptor : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        //拦截的请求的路径
        val oldUrl = request.url().toString()

        val token = getToken()
        if (TextUtils.isEmpty(token)) {
            for (url in AppConstant.SHOULD_LOGIN_URL) {
                if (oldUrl.contains(url)) {
                    LoginActivity.start()
                    throw ResultException(JDApplication.mApplicationContext.getString(R.string.text_need_login))
                }
            }
        }

        val response = chain.proceed(request)
        var value = response.body();
        if (value != null) {
            val mediaType = value.contentType()
            if (mediaType != null) {
                var charset = mediaType.charset()
                if (charset == null) {
                    charset = StandardCharsets.UTF_8
                }
                if (mediaType.toString().toLowerCase().contains("json")) {
                    val contentStr = String(value.bytes(), charset!!)
                    try {
                        val jsonObject = JSONObject(contentStr)
                        if (!jsonObject.isNull("code")) {
                            val code = jsonObject.getInt("code")
                            if (code != 1) {
                                if (code == 3 && !oldUrl.contains(AppConstant.RequestUrl.GET_USER_BY_TOKEN)) {
                                    LoginActivity.start()
                                }
                                throw ResultException(jsonObject.getString("message"))
                            }
                        }
                        return response.newBuilder().body(ResponseBody.create(mediaType, jsonObject.toString())).build()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            }
        }

        return response
    }

}
