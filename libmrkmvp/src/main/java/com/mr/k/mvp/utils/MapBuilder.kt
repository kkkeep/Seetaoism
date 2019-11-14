package com.mr.k.mvp.utils


/*
 * created by taofu on 2019-11-09
**/

class MapBuilder<K,V> {

    private val map = HashMap<K,V?>()


    fun  put(k: K,v : V?) : MapBuilder<K,V>{
        map[k] = v
        return this
    }

    fun builder() : HashMap<K,V?> = map
}