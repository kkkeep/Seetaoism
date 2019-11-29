package com.seetaoism.data.entity


/*
 * created by Cherry on 2019-11-15
**/
class DetailExclusiveData constructor(val from: FROM,var list: MutableList<out NewsData.NewsBean>,val index : Int) {



    var mColumnId : String? = null

    var start : Int = 0
    var videoStartForRecommend = 0
    var time : Long = 0L



}

enum class FROM{
    RECOMMEND,VIDEO,TOPIC,INNER,COLLECT,NOTIFICATION
}