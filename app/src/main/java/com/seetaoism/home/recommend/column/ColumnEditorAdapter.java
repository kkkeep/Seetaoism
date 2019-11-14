package com.seetaoism.home.recommend.column;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.mr.k.mvp.utils.Logger;
import com.mr.k.mvp.utils.SystemFacade;
import com.seetaoism.R;
import com.seetaoism.widgets.ColumnItemView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ColumnEditorAdapter<T extends IColumnData> extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ColumnItemTouchHelperCallback.OnItemMoveListener {

    public static final String TAG = "ColumnEditorAdapter";


    public final static int ITEM_TYPE_MY_COLUMN_TITLE = 0X100; // 我的栏目标题
    private final static int ITEM_TYPE_MY_COLUMN = 0X101; // 我的栏目
    public final static int ITEM_TYPE_MORE_COLUMN_TITLE = 0X102; // 更多栏目标题
    private final static int ITEM_TYPE_MORE_COLUMN = 0X103; // 更多栏目

    private final static int MAX_ITEM_MOVE_DURATION = 500; // recycler 默认 移动动画最大时间
    private final static int MIN_ITEM_MOVE_DURATION = 250; // recycler 默认 移动动画最大时间


    private GridLayoutManager mLayoutManager;

    private ItemTouchHelper mItemTouchHelper;

    private OnColumnChangedListener<T> mOnColumnChangedListener;

    private RecyclerView mRecyclerView;
    private ArrayList<T> mMyColumnList;
    private ArrayList<T> mMoreColumnList;


    private ArrayList<T> mMyOldColumnList;
    private ArrayList<T> mMoreOldColumnList;



    private int mRecyclerViewDiagonalLength = 0; // recycler view 对角线长度，

    private int mTouchSlap;

    private boolean isEditMode; // 是否为编辑模式
    private boolean mIsClickMyColumn;

    private boolean isExchangeFinished = true;


    public ColumnEditorAdapter(RecyclerView recyclerView, GridLayoutManager manager, ItemTouchHelper helper, ArrayList<T> myColumnList, ArrayList<T> moreColumnList) {
        this.mMyColumnList = myColumnList;
        this.mMoreColumnList = moreColumnList;


        this.mMyOldColumnList = new ArrayList<>(myColumnList);
        this.mMoreOldColumnList = new ArrayList<>(moreColumnList);

        mLayoutManager = manager;
        mRecyclerView = recyclerView;
        mItemTouchHelper = helper;

        ViewConfiguration viewConfiguration = ViewConfiguration.get(recyclerView.getContext());

        mTouchSlap = viewConfiguration.getScaledTouchSlop()/2;

    }


    private int count = 0;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Logger.d("%s  new  item %s ", TAG, count++);
        if (viewType == ITEM_TYPE_MY_COLUMN_TITLE) {
            return new MyColumnTitleHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_column_editor_title_my_column, parent, false));
        } else if (viewType == ITEM_TYPE_MY_COLUMN) {
            return new MyColumnHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_colunm, parent, false));
        } else if (viewType == ITEM_TYPE_MORE_COLUMN_TITLE) {
            return new MoreColumnTitleHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_column_editor_title_more_column, parent, false));
        } else {
            return new MoreColumnHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_colunm, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }



    @SuppressWarnings("unchecked")
    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int position = holder.getAdapterPosition();
        int viewType = getItemViewType(position);

        if (viewType == ITEM_TYPE_MY_COLUMN_TITLE) {
            ((MyColumnTitleHolder) holder).showMode();
        } else if (viewType == ITEM_TYPE_MY_COLUMN) {
            ((MyColumnHolder) holder).setData(mMyColumnList.get(position - 1));
        } else if (viewType == ITEM_TYPE_MORE_COLUMN) {
            ((MoreColumnHolder) holder).setData(mMoreColumnList.get(position - 2 - mMyColumnList.size()));
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM_TYPE_MY_COLUMN_TITLE;
        }

        if (!SystemFacade.isListEmpty(mMyColumnList)) {
            if (position > 0 && position <= mMyColumnList.size()) {
                return ITEM_TYPE_MY_COLUMN;
            } else {
                if (position == mMyColumnList.size() + 1) {
                    return ITEM_TYPE_MORE_COLUMN_TITLE;
                } else {
                    return ITEM_TYPE_MORE_COLUMN;
                }
            }
        } else {
            if (!SystemFacade.isListEmpty(mMoreColumnList)) {
                if (position == 1) {
                    return ITEM_TYPE_MORE_COLUMN_TITLE;
                }
                if (position > 1) {
                    return ITEM_TYPE_MORE_COLUMN;
                }
            }
            return -1;
        }
    }

    @Override
    public int getItemCount() {
        int count = 1; // 表示我的栏目标题

        if (!SystemFacade.isListEmpty(mMyColumnList)) {
            count += mMyColumnList.size();
        }

        count++; // 表示添加栏目标题

        if (!SystemFacade.isListEmpty(mMoreColumnList)) {
            count += mMoreColumnList.size();
        }

        return count;
    }


    public void setOnColumnChangedListener(OnColumnChangedListener<T> onColumnChangedListener) {
        this.mOnColumnChangedListener = onColumnChangedListener;
    }

    private void switchMode() {
        isEditMode = !isEditMode;

        TextView textView = null;

        View firstItemView = mLayoutManager.findViewByPosition(0);

        if (firstItemView != null) {
            textView = firstItemView.findViewById(R.id.item_colum_title_edit);
            if (isEditMode) {
                textView.setText(firstItemView.getContext().getString(R.string.text_finish));
            } else {
                textView.setText(firstItemView.getContext().getString(R.string.text_edit));
            }
        }

        int count = mRecyclerView.getChildCount();
        View itemView = null;
        ColumnItemView columnItemView;
        for (int i = 0; i < count; i++) {
            itemView = mRecyclerView.getChildAt(i);
            columnItemView = itemView.findViewById(R.id.item_column_tv_value);
            if (columnItemView != null && columnItemView.isMyColumn() && itemView.isEnabled() ) {
                columnItemView.setEdit(isEditMode);
            }
        }


    }

    @Override
    public void onItemMove(int oldPosition, int targetPosition) {
        int  fixCount = 0;

        for(T column : mMyColumnList){
            if(column.isFix()){
                fixCount++;
            }
        }
        if (targetPosition > fixCount && targetPosition <= mMyColumnList.size()) {
            T column = mMyColumnList.remove(oldPosition - 1);
            mMyColumnList.add(targetPosition - 1, column);
            notifyItemMoved(oldPosition, targetPosition);
        }

    }

    // 在编辑模式下，点击更多栏目时，移动更多栏目到我的栏目里面
    private void moveToMy(final int fromPosition) {
        if (!isExchangeFinished) {
            return;
        }

        if (mRecyclerViewDiagonalLength == 0) {
            mRecyclerViewDiagonalLength = (int) Math.sqrt(mRecyclerView.getWidth() * mRecyclerView.getWidth() + mRecyclerView.getHeight() * mRecyclerView.getHeight());
        }


        //算出 移动的距离；

        View fromItemView = mLayoutManager.findViewByPosition(fromPosition);

        if (fromItemView == null) {
            return;
        }


        int relativeDuration = MIN_ITEM_MOVE_DURATION;

        mIsClickMyColumn = false;

        // 点击的item 将要移动到的位置
        View targetItemView = null;


        boolean isNeedAddMirrorView;

        int fromX = fromItemView.getLeft(); // 动画移动的起始X 坐标
        int fromY = fromItemView.getTop(); // 动画起始移动的Y 坐标

        int targetX = 0; // 动画截至 X 坐标
        int targetY = 0;// 动画截止 Y 坐标

        int height = 0;
        int width = 0;

        if (!SystemFacade.isListEmpty(mMyColumnList)) { //  我的栏目里面有数据

            int firstVisiblePosition =  mLayoutManager.findFirstVisibleItemPosition();

            if(firstVisiblePosition <=  mMyColumnList.size()){ // 我的栏目栏目最后一个item 是可见的，
                isNeedAddMirrorView = true;
                View myLastItemView = mLayoutManager.findViewByPosition(mMyColumnList.size());

                if(mMyColumnList.size() % mLayoutManager.getSpanCount() == 0){
                    targetX = mLayoutManager.findViewByPosition(mMyColumnList.size() -3).getLeft();
                    targetY = myLastItemView.getBottom();
                }else{
                    targetX = myLastItemView.getRight();
                    targetY = myLastItemView.getTop();
                }
                width = Math.abs(targetX - fromItemView.getLeft());
                height = fromItemView.getTop() - targetY;

            }else{ //  我的栏目栏目最后一个item 是 不 可见的，
                isNeedAddMirrorView = false;

                int i = mMyColumnList.size() % mLayoutManager.getSpanCount();

                int m =(fromPosition - 2 - mMyColumnList.size()) % mLayoutManager.getSpanCount();

                // 获取点击item 这一行的第一个item的左上角坐标

                m = fromPosition - m;
                View currentRowFirstItem = mLayoutManager.findViewByPosition(m);

                if(i == 0){ // 表示添加到第一列
                    width = Math.abs(currentRowFirstItem.getLeft() - fromItemView.getLeft());
                }else{
                    width = Math.abs((currentRowFirstItem.getLeft() + (currentRowFirstItem.getWidth() * i)) - fromItemView.getLeft());
                }
                height = fromItemView.getTop();
            }

        } else { // 我的栏目没有数据
            int firstVisiblePosition =  mLayoutManager.findFirstVisibleItemPosition();

            if(firstVisiblePosition  == 0){ // 我的栏目标题看得见
                isNeedAddMirrorView = true;

                int m = (fromPosition - 2 - mMyColumnList.size()) % mLayoutManager.getSpanCount();

                // 获取点击item 这一行的第一个item的左上角坐标
                m = fromPosition - m;
                View currentRowFirstItem = mLayoutManager.findViewByPosition(m);

                targetX = currentRowFirstItem.getLeft();
                targetY = mLayoutManager.findViewByPosition(firstVisiblePosition).getBottom();

                width = Math.abs(targetX - fromItemView.getLeft());
                height = fromItemView.getTop() - fromItemView.getBottom();

            }else{ // 我的栏目标题看不见
                isNeedAddMirrorView = false;

                int m = fromPosition - 2 - mMyColumnList.size() % mLayoutManager.getSpanCount();

                // 获取点击item 这一行的第一个item的左上角坐标

                m = fromPosition - m;
                View currentRowFirstItem = mLayoutManager.findViewByPosition(m);

                width  =Math.abs( currentRowFirstItem.getLeft()- fromItemView.getLeft());
                height = fromItemView.getTop();
            }
        }

        int diagonalLength = (int) Math.sqrt(width * width + height * height);
        relativeDuration = Math.max(diagonalLength * MAX_ITEM_MOVE_DURATION / mRecyclerViewDiagonalLength, relativeDuration);


       //  relativeDuration = 2000;

        mRecyclerView.getItemAnimator().setMoveDuration(relativeDuration);
        if (!isNeedAddMirrorView) {
            Objects.requireNonNull(mRecyclerView.getItemAnimator()).setMoveDuration(relativeDuration);
            T column = mMoreColumnList.remove(fromPosition - mMyColumnList.size() - 2);
            mMyColumnList.add(column);
            notifyItemMoved(fromPosition, mMyColumnList.size());
        } else {
            final View mirrorView = addMirrorView((ConstraintLayout) mRecyclerView.getParent(), mRecyclerView, fromItemView, false);
            // mirrorView.setBackgroundColor(Color.RED);
            if(fromPosition != getItemCount()-1){
                T  column = mMoreColumnList.remove(fromPosition - mMyColumnList.size() - 2);
                mMyColumnList.add(column);
                notifyItemMoved(fromPosition, mMyColumnList.size());
            }else{

                fromItemView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        T  column = mMoreColumnList.remove(fromPosition - mMyColumnList.size() - 2);
                        mMyColumnList.add(column);
                        notifyItemMoved(fromPosition, mMyColumnList.size());
                    }
                },relativeDuration);
            }



            fromItemView.postDelayed(new Runnable() {
                @Override
                public void run() {


                    ColumnItemView columnItemView = mirrorView.findViewById(R.id.item_column_tv_value);
                    columnItemView.setEdit(isEditMode);
                    columnItemView.myColumn(true);
                }
            }, relativeDuration/2);


            fromItemView.setVisibility(View.INVISIBLE);

            Log.d("Test", " fromX = " + fromX + " toX = " + targetX + " fromY = " + fromY + " targetY = " + targetY);

            isExchangeFinished = false;
            startAnimation(fromItemView, mirrorView, relativeDuration, fromX, targetX, fromY, targetY);
        }
    }

    // 在编辑模式下，点击我的栏目时，移动我的栏目item 到更多栏目里面
    private void moveToMore(final int fromPosition) {

        if (!isExchangeFinished) {
            return;
        }

        if (mRecyclerViewDiagonalLength == 0) {
            mRecyclerViewDiagonalLength = (int) Math.sqrt(mRecyclerView.getWidth() * mRecyclerView.getWidth() + mRecyclerView.getHeight() * mRecyclerView.getHeight());
        }


        //算出 移动的距离；

        View fromItemView = mLayoutManager.findViewByPosition(fromPosition);

        if (fromItemView == null) {
            return;
        }

        int relativeDuration = MIN_ITEM_MOVE_DURATION;

        mIsClickMyColumn = true;

        // 点击的item 将要移动到的位置
        View targetItemView = mLayoutManager.findViewByPosition(mMyColumnList.size() + 2);

        boolean isNeedAddMirrorView;

        int fromX = fromItemView.getLeft(); // 动画移动的起始X 坐标
        int fromY = fromItemView.getTop(); // 动画起始移动的Y 坐标

        int targetX = 0; // 动画截至 X 坐标
        int targetY = 0;// 动画截止 Y 坐标

        int height = 0;

        // 目标view 不在屏幕内，分为两种情况
        // 一种是 我的栏目过多，导致目标view 还没显示到屏幕上
        // 第二种是 没有更多栏目，所以找不到
        if (targetItemView == null) {
            if (mMoreColumnList.size() > 0) { // 第一种，
                height = mRecyclerView.getHeight() - fromItemView.getTop();
                // 这种情况 不需要通过添加view 来模拟动画，recycler view 自己有动画，但是我们需要修改时间，不然动画时间太快了
                isNeedAddMirrorView = false;
            } else { // 第二种
                int lastVisiblePosition = mLayoutManager.findLastVisibleItemPosition();
                View lastVisibleView = mLayoutManager.findViewByPosition(lastVisiblePosition);
                if (lastVisibleView.getBottom() > mRecyclerView.getHeight()) {
                    isNeedAddMirrorView = false;
                    height = mRecyclerView.getHeight() - fromItemView.getTop();
                } else {
                    isNeedAddMirrorView = true;
                    View lastItemView = mLayoutManager.findViewByPosition(getItemCount() - 1); // 这一个其实就是 更多栏目的标题
                    assert lastItemView != null;

                    // 由于我的栏目现在最后一行，只有一个item,所以移除后，高度会变化, 更多栏目标题会上移,
                    if (mMyColumnList.size() % mLayoutManager.getSpanCount() == 1) {
                        height = lastItemView.getBottom() - fromItemView.getHeight() - fromItemView.getTop();
                        targetY = fromItemView.getTop() + height;
                    } else {
                        height = lastItemView.getBottom() - fromItemView.getTop();
                        targetY = lastItemView.getBottom();
                    }

                    // 由于更多栏目里面没有东西，所有从我的栏目里面找一个靠最左边了一列的一个来获取 X 轴坐标,那么我们就找我的栏目的第一个，即 position = 0;

                    int i = fromPosition % mLayoutManager.getSpanCount();
                    if(i == 0){
                        i = fromPosition - (mLayoutManager.getSpanCount() -1);
                    }else{
                        i = fromPosition - i + 1;
                    }

                    targetX = Objects.requireNonNull(mLayoutManager.findViewByPosition(i)).getLeft();
                }
            }
        } else {
            // 由于我的栏目现在最后一行，只有一个item,所以移除后，高度会变化, 更多栏目标题会上移
            if (mMyColumnList.size() % mLayoutManager.getSpanCount() == 1) {
                View moreTitleView = mLayoutManager.findViewByPosition(mMyColumnList.size() + 1);
                assert moreTitleView != null;
                targetY =  moreTitleView.getBottom() - fromItemView.getHeight();
                height = targetY - fromY;
            } else {
                targetY = targetItemView.getTop();
                height =  targetY - fromY;
            }



            isNeedAddMirrorView = true;
            targetX = targetItemView.getLeft();

        }

        int width = fromItemView.getLeft();

        int diagonalLength = (int) Math.sqrt(width * width + height * height);
        relativeDuration = Math.max(diagonalLength * MAX_ITEM_MOVE_DURATION / mRecyclerViewDiagonalLength, relativeDuration);


        /**
         * 保证 item 移动时，recycle人view 默认动画移动时间 和 我们增加的 mirror view 执行的动画时间一样，
         * 是因为 recycler view 的 adapter 调用 notifyItemMoved 方法时候，是先移动动画执行完成后才执行的 添加 item 的动画
         */
        mRecyclerView.getItemAnimator().setMoveDuration(relativeDuration);

        if (!isNeedAddMirrorView) {
            Objects.requireNonNull(mRecyclerView.getItemAnimator()).setMoveDuration(relativeDuration);
            T column = mMyColumnList.remove(fromPosition - 1);
            mMoreColumnList.add(0, column);
            notifyItemMoved(fromPosition, mMyColumnList.size() + 2);


        } else {
            final View mirrorView = addMirrorView((ConstraintLayout) mRecyclerView.getParent(), mRecyclerView, fromItemView, true);
            T column = mMyColumnList.remove(fromPosition - 1);
            mMoreColumnList.add(0, column);

            notifyItemMoved(fromPosition, mMyColumnList.size() + 2);

            fromItemView.postDelayed(new Runnable() {
                @Override
                public void run() {


                    ColumnItemView columnItemView = mirrorView.findViewById(R.id.item_column_tv_value);
                    columnItemView.setEdit(false);
                    columnItemView.myColumn(false);
                }
            }, relativeDuration/2);


            fromItemView.setVisibility(View.INVISIBLE);

            Log.d("Test", " fromX = " + fromX + " toX = " + targetX + " fromY = " + fromY + " targetY = " + targetY);

            isExchangeFinished = false;
            startAnimation(fromItemView, mirrorView, relativeDuration, fromX, targetX, fromY, targetY);
        }
    }


    private void startAnimation(final View originalView, final View itemView, int duration, float fromX, float targetX, float fromY, float targetY) {

        final ObjectAnimator objectAnimator = getAnimator(itemView, duration, fromX, targetX, fromY, targetY);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                removeMyColumnMirrorView(originalView, itemView);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                removeMyColumnMirrorView(originalView, itemView);
            }
        });


        objectAnimator.start();
    }


    private void removeMyColumnMirrorView(final View originalView, final View mirrorView) {

        if(mIsClickMyColumn){
            mLayoutManager.findViewByPosition(mMyColumnList.size() + 2).setVisibility(View.VISIBLE);
        }else{
            mLayoutManager.findViewByPosition(mMyColumnList.size()).setVisibility(View.VISIBLE);
        }

        mirrorView.postDelayed(new Runnable() {
            @Override
            public void run() {
                isExchangeFinished = true;
                ViewGroup viewGroup = (ViewGroup) mirrorView.getParent();
                viewGroup.removeView(mirrorView);
                originalView.setVisibility(View.VISIBLE);

            }
        }, mRecyclerView.getItemAnimator().getAddDuration() + 300);
    }


    @SuppressLint("ResourceType")
    private View addMirrorView(ConstraintLayout grandpa, RecyclerView recyclerView, View itemView, boolean isMyColumn) {

        final View newItemView = LayoutInflater.from(grandpa.getContext()).inflate(R.layout.item_colunm, grandpa, false);
        newItemView.setId(1000000);


        int[] itemLocationInScreen = new int[2];
        itemView.getLocationOnScreen(itemLocationInScreen);


        int[] grandpaLocationInScreen = new int[2];

        grandpa.getLocationOnScreen(grandpaLocationInScreen);


        ConstraintSet constraintSet = new ConstraintSet();

         constraintSet.clone(grandpa);

        int leftMargin = itemLocationInScreen[0] - grandpaLocationInScreen[0];
        int topMargin = itemLocationInScreen[1] - grandpaLocationInScreen[1];

        Log.d("Test", " leftMargin = " + leftMargin + " topMargin " + topMargin);
        constraintSet.connect(newItemView.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, leftMargin);
        constraintSet.connect(newItemView.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, topMargin);

        ColumnItemView original = itemView.findViewById(R.id.item_column_tv_value);
        ColumnItemView newColumn = newItemView.findViewById(R.id.item_column_tv_value);
        newColumn.setText(original.getText());

        newColumn.myColumn(isMyColumn);
        newColumn.setEdit(isEditMode);
        constraintSet.constrainWidth(newItemView.getId(), itemView.getWidth());
        constraintSet.constrainHeight(newItemView.getId(), itemView.getHeight());

        grandpa.addView(newItemView,1);

        constraintSet.applyTo(grandpa);
        return newItemView;
    }

    /**
     * 获取位移动画
     */
    private ObjectAnimator getAnimator(View view, int duration, float fromX, float targetX, float fromY, float targetY) {


        PropertyValuesHolder xValuesHolder = PropertyValuesHolder.ofFloat("translationX", targetX - fromX);
        PropertyValuesHolder yValuesHolder = PropertyValuesHolder.ofFloat("translationY", targetY - fromY);


        return ObjectAnimator.ofPropertyValuesHolder(view, xValuesHolder, yValuesHolder).setDuration(duration);

    }


    public class MyColumnTitleHolder extends RecyclerView.ViewHolder {

        private TextView tvAction;

        public MyColumnTitleHolder(@NonNull View itemView) {
            super(itemView);

            tvAction = itemView.findViewById(R.id.item_colum_title_edit);
            tvAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switchMode();
                    if(!isEditMode && mOnColumnChangedListener != null){
                        notifyChanged();
                    }
                }
            });

        }


        private void showMode() {
            if (isEditMode) {
                tvAction.setText(tvAction.getContext().getString(R.string.text_finish));
            } else {
                tvAction.setText(tvAction.getContext().getString(R.string.text_edit));
            }
        }

    }

    public class MyColumnHolder extends RecyclerView.ViewHolder {

        private ColumnItemView column;

        @SuppressLint("ClickableViewAccessibility")
        public MyColumnHolder(@NonNull View itemView) {
            super(itemView);

            column = itemView.findViewById(R.id.item_column_tv_value);
            column.myColumn(true);

            if (!isExchangeFinished) {
                //itemView.setVisibility(View.INVISIBLE);
            }
            column.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (!isEditMode  ) {
                        switchMode();
                        return true;
                    } else {
                        return false;
                    }
                }
            });

            column.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isEditMode ) {
                        int from = MyColumnHolder.this.getAdapterPosition();
                        moveToMore(from);
                    }
                }
            });

            column.setOnTouchListener(new View.OnTouchListener() {
                long startTime = 0;
                float downX,downY = 0;
                boolean isDrag;

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN: {
                            Log.d("Test", " onTouch down");
                            startTime = System.currentTimeMillis();
                            downX = event.getRawX();
                            downY = event.getRawY();
                            isDrag = false;
                            break;
                        }
                        case MotionEvent.ACTION_MOVE: {
                            if(isEditMode){
                                if(!isDrag){
                                    Log.d("Test", " onTouch " + event.getRawX() + " - " + event.getRawY() + " dx = " + downX + " dy " + downY);
                                    Log.d("Test", " onTouch moveToMore x = " + Math.abs(event.getRawX() - downX) + " y = " + (Math.abs(event.getRawY() - downY)) + " ts = " + mTouchSlap);
                                    if(Math.abs(event.getRawX() - downX) > mTouchSlap || (Math.abs(event.getRawY() - downY)) > mTouchSlap){
                                        isDrag = true;
                                    }
                                }
                            }
                            Log.d("Test", " onTouch moveToMore isDrag = " + isDrag);
                            if (isDrag ) {

                                mItemTouchHelper.startDrag(MyColumnHolder.this);
                            }
                            break;
                        }
                        case MotionEvent.ACTION_UP: {
                            Log.d("Test", " onTouch up");
                            break;
                        }
                    }
                    return false;
                }
            });
        }

        public void setData(T data) {
            int position = getAdapterPosition();
            if(mMyColumnList.get(position-1).isFix()){
                itemView.setEnabled(false);
                column.setEnabled(false);
            }else{
                itemView.setEnabled(true);
                column.setEnabled(true);
            }
            column.setText(data.getName());
            column.myColumn(true);
            column.setEdit(isEditMode);

        }
    }

    public class MoreColumnTitleHolder extends RecyclerView.ViewHolder {
        public MoreColumnTitleHolder(@NonNull View itemView) {
            super(itemView);
        }

    }

    public class MoreColumnHolder extends RecyclerView.ViewHolder {

        private ColumnItemView column;

        public MoreColumnHolder(@NonNull View itemView) {
            super(itemView);
            column = itemView.findViewById(R.id.item_column_tv_value);
           if (!isExchangeFinished) {
               // itemView.setVisibility(View.INVISIBLE);
            }
            column.myColumn(false);
            column.setEdit(false);

            column.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int from = MoreColumnHolder.this.getAdapterPosition();
                    moveToMy(from);
                }
            });
        }

        public void setData(T data) {
            column.setText(data.getName());
            column.myColumn(false);
            column.setEdit(false);

        }
    }
    public void notifyChanged(){
        if(isEditMode){
            return;
        }
        if(mMyOldColumnList.size() == mMyColumnList.size()){
            for(int i = 0; i < mMyOldColumnList.size(); i++){
                if(mMyOldColumnList.get(i) !=  mMyColumnList.get(i)){
                    mOnColumnChangedListener.onChanged(mMyColumnList, mMoreColumnList);
                    break;
                }
            }
        }else{
            mOnColumnChangedListener.onChanged(mMyColumnList, mMoreColumnList);
        }
    }

    public interface OnColumnChangedListener<T>{
       void onChanged(List<T> my, List<T> more);
    }

}
