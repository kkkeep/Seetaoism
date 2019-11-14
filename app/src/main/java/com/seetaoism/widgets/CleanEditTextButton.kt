package com.seetaoism.widgets

import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextSwitcher
import androidx.appcompat.widget.AppCompatImageView


/*
 * created by taofu on 2019-09-15
**/

class CleanEditTextButton : AppCompatImageView{

    private lateinit var  mEditText: EditText

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {

        setOnClickListener {
            mEditText.setText("");
        }



    }

    fun bindEditText(editText: EditText){

        visibility = if(TextUtils.isEmpty(editText.text.toString())) View.GONE else View.VISIBLE

        mEditText = editText.apply {
            addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                    Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                this@CleanEditTextButton.visibility = if(TextUtils.isEmpty(editText.text.toString())) View.GONE else View.VISIBLE
            }

        })
        };

    }
}