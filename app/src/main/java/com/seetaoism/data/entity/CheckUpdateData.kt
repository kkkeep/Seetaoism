package com.seetaoism.data.entity


/*
 * created by Cherry on 2019-11-28
**/
class CheckUpdateData{

    var is_upgrade : Int = 0;//是否需要升级，1需要，2不需要
    var force_upgrade : Int = 0; //是否需要强制升级，1需要，2不需要
    var upgrade_point : String? = null;//升级提示
    var version_id : String? = null;//秘钥
    var version : String? = null;


}