package com.seetaoism.widgets

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.*
import android.widget.Button
import android.widget.PopupWindow
import android.widget.TextView
import com.seetaoism.R
import com.seetaoism.data.entity.CheckUpdateData
import com.seetaoism.home.detail.page.OnActionListener
import com.seetaoism.user.register.PrivacyPolicyFragment
import com.seetaoism.user.register.UserProtocolFragment
import kotlinx.android.synthetic.main.recyclerview_itme_articlelike.view.*


/*
 * created by Cherry on 2019-11-28
**/
class SplashPopWindow(var context: Activity) : PopupWindow(){

    private lateinit var tvContent : TextView
    private lateinit var btnAgree: TextView
    private lateinit var btnStop : TextView



    private lateinit var listener : OnPopButtonClickListener

    init {
        height = ViewGroup.LayoutParams.MATCH_PARENT
        width = ViewGroup.LayoutParams.MATCH_PARENT

        val contentView = LayoutInflater.from(context).inflate(R.layout.layout_splash_pop, null);

        tvContent = contentView.findViewById(R.id.splash_pop_tv_content);
        btnAgree = contentView.findViewById(R.id.splash_pop_btn_agree)

        btnStop = contentView.findViewById(R.id.splash_pop_btn_stop)

        contentView.requestFocus()
        contentView.setOnKeyListener { v, keyCode, event ->
            if(keyCode ==  KeyEvent.KEYCODE_BACK){
                return@setOnKeyListener true
            }
            return@setOnKeyListener false

        }

        btnStop.setOnClickListener {
            listener.onStop()
            dismiss()
        }


        btnAgree.setOnClickListener {
            listener.onAgree()
            dismiss()
        }

        setContentView(contentView)

        softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE

        isFocusable = false
        setBackgroundDrawable(ColorDrawable())
        backgroundAlpha(context,0.5f)
        setOnDismissListener {
            backgroundAlpha(context,1.0f)
        }




    }


    fun show(view : View,listener: OnPopButtonClickListener) {

        val str = context.resources.getString(R.string.text_service_agreement_content)

        val spannableString = SpannableStringBuilder(str)

        var s1 = context.getString(R.string.text_service_agreement)

        var s2 = context.getString(R.string.text_service_privacy_policy)

        var start = str.indexOf(s1)

        //setSpan(ForegroundColorSpan(context.getResources().getColor(R.color.red_2)), start, start + s1.length, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)

        spannableString.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {

                listener.onClick(1)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = Color.parseColor("#4A90E2")
                ds.isUnderlineText = false
            }
        }, start, start + s1.length, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)


        start = str.indexOf(s2)
       // spannableString.setSpan(ForegroundColorSpan(context.getResources().getColor(R.color.red_2)), start, start + s2.length, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)

        spannableString.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                //addFragment<PrivacyPolicyFragment>(getFragmentManager(), PrivacyPolicyFragment::class.java, R.id.login_fragment_container, null)
                listener.onClick(2)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = Color.parseColor("#4A90E2")
                ds.isUnderlineText = false
            }

        }, start, start +s2.length, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
        tvContent.text = spannableString
        tvContent.movementMethod = LinkMovementMethod.getInstance()
        this.listener = listener
        tvContent.highlightColor = context.getResources().getColor(android.R.color.transparent)
        showAtLocation(view, Gravity.TOP, 0, 0);
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

    public interface OnPopButtonClickListener{

        fun onStop()
        fun onAgree()

        fun onClick(position : Int)
    }
}