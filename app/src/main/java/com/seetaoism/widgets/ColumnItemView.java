package com.seetaoism.widgets;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.seetaoism.R;

/*
 * created by Cherry on 2019-10-11
 **/
public class ColumnItemView extends AppCompatTextView {

    int[] MY_COLUMM_EDIT_STATE_SET = {R.attr.state_column_edit,R.attr.state_column_my_column};
    int[] MY_COLUMN_NOT_EDIT_STATE_SET = {R.attr.state_column_my_column};

    private boolean isEdit; // 是否处于编辑状态
    private boolean isMyColumn; // 是否为我的栏目而非未添加的栏目



    public ColumnItemView(Context context) {
        super(context);
    }

    public ColumnItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ColumnItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        if(isEdit != edit){
            isEdit = edit;
            refreshDrawableState();
        }

    }

    public boolean isMyColumn(){
        return isMyColumn;
    }

    public void myColumn(boolean select){
        if(isMyColumn != select){
            isMyColumn = select;
            refreshDrawableState();
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        refreshDrawableState();
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {

        if(isMyColumn){
                if(isEdit){
                    final int[] drawableState = super.onCreateDrawableState(extraSpace + MY_COLUMM_EDIT_STATE_SET.length);
                    mergeDrawableStates(drawableState, MY_COLUMM_EDIT_STATE_SET);
                    return drawableState;
                }else{
                    final int[] drawableState = super.onCreateDrawableState(extraSpace + MY_COLUMN_NOT_EDIT_STATE_SET.length);
                    mergeDrawableStates(drawableState, MY_COLUMN_NOT_EDIT_STATE_SET);
                    return drawableState;
                }
        }else{
            return super.onCreateDrawableState(extraSpace);
        }


    }



}
