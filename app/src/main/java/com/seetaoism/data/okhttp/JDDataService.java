package com.seetaoism.data.okhttp;

import com.mr.k.mvp.MvpManager;
import com.mr.k.mvp.ok.ApiDataService;


public class JDDataService {


    public static JDApiService service(){

       return (JDApiService) MvpManager.getApiService();
    }
}
