package com.seetaoism.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.seetaoism.data.entity.DetailExclusiveData
import com.seetaoism.data.entity.NewsData


/*
 * created by Cherry on 2019-11-04
**/
class NewsViewModel : ViewModel() {

    private val mNewsDataLiveData : MutableLiveData<DetailExclusiveData> = MutableLiveData();



    fun getNewsDataLiveData() : LiveData<DetailExclusiveData>{
        return mNewsDataLiveData
    }

    fun setData(data : DetailExclusiveData){
        mNewsDataLiveData.value = data
    }
}