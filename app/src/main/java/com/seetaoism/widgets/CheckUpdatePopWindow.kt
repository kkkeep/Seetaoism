package com.seetaoism.widgets

import android.app.Activity
import android.graphics.drawable.ColorDrawable
import android.text.TextUtils
import android.view.*
import android.widget.Button
import android.widget.PopupWindow
import android.widget.TextView
import com.seetaoism.R
import com.seetaoism.data.entity.CheckUpdateData
import com.seetaoism.home.detail.page.OnActionListener


/*
 * created by Cherry on 2019-11-28
**/
class CheckUpdatePopWindow(var context: Activity) : PopupWindow(){

    private lateinit var tvVersion : TextView
    private lateinit var tvDescription : TextView
    private lateinit var btnUpdate : Button
    private lateinit var btnClose : Button


    private lateinit var checkData : CheckUpdateData;

    private lateinit var listener : OnUpdateButtonClickListener

    init {
        height = ViewGroup.LayoutParams.MATCH_PARENT
        width = ViewGroup.LayoutParams.MATCH_PARENT

        val contentView = LayoutInflater.from(context).inflate(R.layout.layout_check_upate, null);

        tvVersion = contentView.findViewById(R.id.update_tv_version)
        tvDescription = contentView.findViewById(R.id.check_update_conent);
        btnUpdate = contentView.findViewById(R.id.update_btn_update)

        btnClose = contentView.findViewById(R.id.check_update_close)

        btnClose.setOnClickListener {
            dismiss()
        }


        btnUpdate.setOnClickListener {
            listener.onUpdate(checkData)
            dismiss()
        }

        setContentView(contentView)

        softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE

        isFocusable = true
        setBackgroundDrawable(ColorDrawable())
        backgroundAlpha(context,0.5f)
        setOnDismissListener {
            backgroundAlpha(context,1.0f)
        }




    }


    fun show(view : View,data : CheckUpdateData,listener: OnUpdateButtonClickListener) {
        tvVersion.text = data.version
        tvDescription.text = data.upgrade_point
        checkData = data;
        this.listener = listener
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

    public interface OnUpdateButtonClickListener{
       fun onUpdate(data : CheckUpdateData)
    }
}