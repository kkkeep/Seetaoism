package com.seetaoism.home.detail.page

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.TextUtils
import android.view.*
import android.view.inputmethod.InputMethod.SHOW_FORCED
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.PopupWindow
import android.widget.TextView
import com.seetaoism.R
import kotlinx.android.synthetic.main.layout_comment.*
import org.w3c.dom.Text

/*
 * created by Cherry on 2019-11-10
**/
class CommentPopView(var context: Activity) : PopupWindow(context) {

    private lateinit var mEtContent: EditText
    private lateinit var mTvCancel: TextView
    private lateinit var mTvSend: TextView



    private lateinit var mOnActionListener : OnActionListener

    private var mType = 0
    companion object {
        const val TYPE_COMMENT = 2;
        const val TYPE_REPLY = 3;
    }

    init {

        height = ViewGroup.LayoutParams.MATCH_PARENT
        width = ViewGroup.LayoutParams.MATCH_PARENT

        val contentView = LayoutInflater.from(context).inflate(R.layout.layout_comment, null);
        setContentView(contentView)

        softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE

        isFocusable = true

        // 如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
        setBackgroundDrawable(ColorDrawable())
        backgroundAlpha(context,0.5f)


        setOnDismissListener {
            softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN;
            backgroundAlpha(context,1.0f)
        }


        mEtContent = contentView.findViewById<EditText>(R.id.commentEtContent).apply {

        }

        mTvCancel = contentView.findViewById<TextView>(R.id.commentTvCancel).apply {
          setOnClickListener {
              hideKeyboard()
              dismiss()
          }

        }
        mTvSend = contentView.findViewById<TextView>(R.id.commentTvSent).apply {
            setOnClickListener {

                val content = mEtContent.text.toString()
                if(TextUtils.isEmpty(content)){
                    mEtContent.highlightColor = Color.RED
                    mEtContent.hint = "评论不能为空"
                    return@setOnClickListener
                }
                hideKeyboard()
                dismiss()
                mOnActionListener.onAction(mType,content)
            }

        }


    }



    fun show(view : View, type: Int,content: String? = null,hint : String? = null,actionListener: OnActionListener) {
        if(TextUtils.isEmpty(content)){
            mEtContent.hint = hint
        }else{
            mEtContent.setText(content)
            if (content != null) {
                mEtContent.setSelection(content.length)
            }
        }

        mType = type
        mOnActionListener = actionListener;
        showAtLocation(view, Gravity.BOTTOM, 0, 0);
        showKeyboard()
    }

    private fun hideKeyboard () {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }
    private fun showKeyboard () {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        //imm.showSoftInput(mEtContent,SHOW_FORCED)
    }


    fun backgroundAlpha(context: Activity, bgAlpha: Float) {
        val lp = context.window.attributes
        lp.alpha = bgAlpha
        if(bgAlpha < 1){
            context.window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        }else{
            context.window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        }

        context.window.attributes = lp
    }
}

interface OnActionListener {
    fun onAction(type: Int, content: String)
}



