package com.mr.k.mvp.kotlin.widget

import android.graphics.*
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mr.k.mvp.MvpManager
import com.mr.k.mvp.utils.SystemFacade


/*
 * created by Cherry on 2019-11-18
**/
class CommentDecoration(private val startPosition: Int = -1) : RecyclerView.ItemDecoration() {

    private val mPaint = Paint()

    private var offsetX = 0f;
    private var offsetY = 0f;


    init {

        mPaint.isAntiAlias = true
        mPaint.color = Color.parseColor("#282B2E")
        val font = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
        mPaint.typeface = font
        mPaint.textSize = SystemFacade.dip2px(MvpManager.context, 16f).toFloat()
        offsetX = SystemFacade.dip2px(MvpManager.context, 15f).toFloat()
        offsetY = SystemFacade.dip2px(MvpManager.context, 30f).toFloat()

    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        if(!isShowDecoration(parent)){
            return
        }
        var position = 0;
        var view: View? = null;
        for (i in 0..parent.childCount) {
            view = parent.getChildAt(i)
            position = parent.getChildAdapterPosition(view)
            if (position == startPosition ) {

                c.drawText("全部评论", offsetX, (view.bottom + offsetY), mPaint)
            }
        }

    }


    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view);

        if (startPosition == position && isShowDecoration(parent)) {
            outRect.set(0, 0, 0, SystemFacade.dip2px(parent.context, 40f))
        }
    }

    private fun isShowDecoration(recyclerView: RecyclerView) : Boolean {

        recyclerView.adapter?.run {
          return   itemCount > startPosition + 1
        }

        return false
    }

}