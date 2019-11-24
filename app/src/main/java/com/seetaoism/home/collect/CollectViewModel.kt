package com.seetaoism.home.collect

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.seetaoism.data.entity.NewsData


/*
 * created by Cherry on 2019-11-24
**/

class CollectViewModel : ViewModel(){

    private val mNewsLiveData = MutableLiveData<NewsData.NewsBean>()



    /*fun read(news : NewsData.NewsBean){
        mNewsLiveData.value = news
    }*/


    fun unCollect(news : NewsData.NewsBean){
        mNewsLiveData.value = news
    }


    fun getNewsLiveData() : LiveData<NewsData.NewsBean> {
        return mNewsLiveData
    }

}