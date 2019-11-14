@file:JvmName("UserManager")

package com.mr.k.mvp

import android.app.Application
import android.content.Context
import com.mr.k.mvp.utils.DataCacheUtils
import com.mr.k.mvp.utils.SystemFacade
import java.io.File


/*
 * created by taofu on 2019-10-29
**/

private const val CACHE_USER_DATA_FILE_NAME = "user_info.json"

private lateinit var mApplication: Context

private var mUser: IUser? = null

internal fun init(application: Context) {
    mApplication = application.applicationContext;
}

@Synchronized
fun login(user: IUser?) {
    mUser = user
    user?.let { saveUserToSdcard(it) };
}

@Synchronized
fun getToken() = mUser?.getTokenString()

@Synchronized
fun getUser() = mUser;

fun loginOut() {
    login(null)
    clearUserFromSdcard()
}

private fun getCacheUserFile(): File? {
    return SystemFacade.getExternalCacheDir(mApplication, CACHE_USER_DATA_FILE_NAME)
}




private fun saveUserToSdcard(user : IUser){
    if(SystemFacade.isMainThread()){
        throw IllegalAccessException(" must invoked in work thread");
    }

    DataCacheUtils.saveDataToFile(user, getCacheUserFile())
}

fun <R : IUser>getUserFromSdcard(aClass : Class<R>) : R?{

    if(SystemFacade.isMainThread()){
        throw IllegalAccessException(" must invoked in work thread");
    }
    return DataCacheUtils.getDataFromFile(aClass, getCacheUserFile())
}

fun <R : IUser> loginLocal(cls :Class<R>): R?{
    var r = getUserFromSdcard(cls)
    mUser = r
    return r
}
private fun clearUserFromSdcard(){

    if(SystemFacade.isMainThread()){
        throw IllegalAccessException(" must invoked in work thread");
    }

    val file = getCacheUserFile()

    file?.let {
        if (it.exists()) it.delete()
    }
}

interface IUser {
    @Override fun getTokenString() : String?
}