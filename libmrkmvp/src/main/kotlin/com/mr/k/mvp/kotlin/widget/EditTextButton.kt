package com.mr.k.mvp.kotlin.widget

import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.EditText


/*
 * created by Cherry on 2019-11-16
 *
 *  如果edit text 有内容 ，button 变为可点击按，否则不可点击
**/
class EditTextButton : Button{

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)




    fun bindEditText(psd : EditText){
        isEnabled = !TextUtils.isEmpty(psd.text.toString())
         psd.apply {

            addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {

                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                        Unit

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    this@EditTextButton.isEnabled = !TextUtils.isEmpty(psd.text.toString())
                }

            })
        }
    }
}