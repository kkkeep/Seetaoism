package com.seetaoism.widgets

import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.widget.addTextChangedListener
import com.seetaoism.R


/*
 * created by Cherry on 2019-09-15
**/

class TogglePasswordButton : AppCompatImageView {

    private lateinit var  mPassword : EditText ;

    private var isShowPsd : Boolean = false;

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    init {

        setOnClickListener{
            toggle()
        }
    }

    fun bindPasswordEditText(psd : EditText){
        visibility = if(TextUtils.isEmpty(psd.text.toString())) View.GONE else View.VISIBLE
        mPassword = psd.apply {

            addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {

                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                        Unit

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    this@TogglePasswordButton.visibility = if(TextUtils.isEmpty(text.toString())) View.GONE else View.VISIBLE
                }

            })
        }
        setImageResource(R.drawable.ic_eyes_close)
        //toggle()
    }

     fun toggle(){
        if(isShowPsd){
            mPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            isShowPsd = false
            setImageResource(R.drawable.ic_eyes_close)
        }else{
            mPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            isShowPsd = true
            setImageResource(R.drawable.ic_eyes_open)
        }
    }
}