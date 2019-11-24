package com.mr.k.mvp.kotlin.widget

import android.content.Context
import android.view.ViewGroup
import android.widget.PopupWindow


/*
 * created by Cherry on 2019-11-21
**/



class MvpFullPopupWindow  @JvmOverloads constructor(var context: Context, var isBackgroundDim : Boolean= true) : PopupWindow(context) {




    init {

        height = ViewGroup.LayoutParams.MATCH_PARENT
        width = ViewGroup.LayoutParams.MATCH_PARENT
        isFocusable = true

    }


}