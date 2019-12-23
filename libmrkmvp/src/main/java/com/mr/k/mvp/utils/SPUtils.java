package com.mr.k.mvp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.mr.k.mvp.MvpManager;


/*
 * created by Cherry on 2019-06-11
 **/
public class SPUtils {


    private static final String GLOBAL_SP_FILE_NAME = "sp_config";

    public static void commitValue(String name,String key,String value){
        SharedPreferences sharedPreferences = MvpManager.getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void applayValue(String name,String key,String value){
        SharedPreferences sharedPreferences = MvpManager.getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void saveValueToDefaultSpByApply(String key,String value) {
            applayValue(GLOBAL_SP_FILE_NAME, key, value);
    }
    public static void saveIntValueToDefaultSpByApply(String key,int value) {
        SharedPreferences sharedPreferences = MvpManager.getContext().getSharedPreferences(GLOBAL_SP_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }
    public static void saveValueToDefaultSpByCommit(String key,String value) {
        commitValue(GLOBAL_SP_FILE_NAME, key, value);
    }
    public static String getValue(String name,String key){
        SharedPreferences sharedPreferences = MvpManager.getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(key, "");
    }
    public static String getValue(String key){

        return getValue(GLOBAL_SP_FILE_NAME, key);

    }

    public static int getIntValue(String key){

        SharedPreferences sharedPreferences = MvpManager.getContext().getSharedPreferences(GLOBAL_SP_FILE_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getInt(key, 0);
    }


}
