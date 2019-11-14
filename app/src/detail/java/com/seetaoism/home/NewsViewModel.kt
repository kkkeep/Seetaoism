package com.seetaoism.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.seetaoism.data.entity.NewsData


/*
 * created by taofu on 2019-11-04
**/
class NewsViewModel : ViewModel() {

    private val mNewsDataLiveData : MutableLiveData<NewsData> = MutableLiveData();



    fun getNewsDataLiveData() : LiveData<NewsData>{
        return mNewsDataLiveData
    }

    fun setData(data : NewsData){
        mNewsDataLiveData.value = data
    }
}