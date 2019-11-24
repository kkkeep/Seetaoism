@file:JvmName("UserManager")

package com.mr.k.mvp

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.text.TextUtils
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.mr.k.mvp.utils.DataCacheUtils
import com.mr.k.mvp.utils.Logger
import com.mr.k.mvp.utils.SystemFacade
import java.io.File


/*
 * created by Cherry on 2019-10-29
**/

private const val CACHE_USER_DATA_FILE_NAME = "user_info.json"

private const val BROADCAST_USER_LOGIN_OR_OUT = "user.login.or.out"

private lateinit var mApplication: Context

private var mUser: IUser? = null

internal fun init(application: Context) {
    mApplication = application.applicationContext;
}

@Synchronized
fun login(user: IUser?) {
    if (user == null) {
        Logger.d("UserManager  login out clean user info  : { ${user.toString()} }")
    } else {
        Logger.d("UserManager  login  cache user info : { ${user.toString()} }")
    }
    user?.run {
        if(TextUtils.isEmpty(getTokenString())){
            return
        }
    }
    mUser = user
    mUser?.run {
        setRefresh(true)
    }
    broadcastUserGlobally()
    user?.let { saveUserToSdcard(it) };
}


fun onUserUpdate(){
    broadcastUserGlobally()
}

//private val mBroadcastReceiverList  = mutableListOf<BroadcastReceiver>()

fun  registerUserBroadcastReceiver(callback : (T : IUser?) -> Unit) : BroadcastReceiver{

    val filter = IntentFilter(BROADCAST_USER_LOGIN_OR_OUT)
    val broadcastManager = LocalBroadcastManager.getInstance(mApplication)
    val broadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            callback.invoke(mUser)
        }
    }
   // mBroadcastReceiverList.add(broadcastReceiver)
    broadcastManager.registerReceiver(broadcastReceiver,filter)
    return broadcastReceiver
}

fun unRegisterUserBroadcastReceiver(receiver: BroadcastReceiver){
    val broadcastManager = LocalBroadcastManager.getInstance(mApplication)
    broadcastManager.unregisterReceiver(receiver)
}


@Synchronized
fun getToken() = mUser?.getTokenString()

@Synchronized
fun getUser() = mUser;

fun loginOut() {
    login(null)
    clearUserFromSdcard()
}


private fun broadcastUserGlobally() {

    val broadcastManager = LocalBroadcastManager.getInstance(mApplication)
    broadcastManager.sendBroadcast(Intent(BROADCAST_USER_LOGIN_OR_OUT))
}

private fun getCacheUserFile(): File? {
    return SystemFacade.getExternalCacheDir(mApplication, CACHE_USER_DATA_FILE_NAME)
}


private fun saveUserToSdcard(user: IUser) {
    if (SystemFacade.isMainThread()) {
        throw IllegalAccessException(" must invoked in work thread");
    }

    DataCacheUtils.saveDataToFile(user, getCacheUserFile())
}

fun <R : IUser> getUserFromSdcard(aClass: Class<R>): R? {

    if (SystemFacade.isMainThread()) {
        throw IllegalAccessException(" must invoked in work thread");
    }
    return DataCacheUtils.getDataFromFile(aClass, getCacheUserFile())
}

fun <R : IUser> loginLocal(cls: Class<R>): R? {
    var r = getUserFromSdcard(cls)
    r?.run {
        setRefresh(false)
    }
    mUser = r
    return r
}

private fun clearUserFromSdcard() {

    if (SystemFacade.isMainThread()) {
        throw IllegalAccessException(" must invoked in work thread");
    }

    val file = getCacheUserFile()

    file?.let {
        if (it.exists()) it.delete()
    }
}


interface IUser {
    @Override
    fun getTokenString(): String?

    fun isRefresh() : Boolean

    fun setRefresh(refresh : Boolean)
}