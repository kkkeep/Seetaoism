package com.mr.k.mvp.kotlin.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.Checkable
import android.widget.ImageView
import android.widget.TextView


/*
 * created by Cherry on 2019-11-13
**/
class ToggleStateView : TextView,Checkable{

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    internal var CHECKED_STATE_SET = intArrayOf(android.R.attr.state_checked)
    private var mChecked = false

    override fun isChecked(): Boolean {
        return mChecked
    }

    override fun toggle() {
        setChecked(!mChecked)
    }

    override fun setChecked(checked: Boolean) {
        if (mChecked != checked) {
            mChecked = checked
            refreshDrawableState()
        }
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + 1)
        if (mChecked) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET)
        }
        return drawableState
    }

}