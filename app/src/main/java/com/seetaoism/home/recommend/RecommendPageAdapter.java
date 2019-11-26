package com.seetaoism.home.recommend;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.mr.k.mvp.utils.Logger;
import com.seetaoism.data.entity.NewsColumn;

import java.util.List;


public class RecommendPageAdapter extends FragmentStatePagerAdapter {

    private static final String TAG = "RecommendPageAdapter";
    private List<NewsColumn> mDatas;


    public RecommendPageAdapter(FragmentManager fm) {
        this(fm,null);
    }

    public RecommendPageAdapter(FragmentManager fm,List<NewsColumn> datas) {
        super(fm);
        setData(datas);
    }


    public void refreshColumn(List<NewsColumn> newsColumns){
        setData(newsColumns);
        notifyDataSetChanged();
    }

    public List<NewsColumn> getColumns(){
        return mDatas;
    }

    private void setData(List<NewsColumn> datas){
        mDatas = datas;
        for(int i = 0; i< datas.size();i++){
            datas.get(i).setPosition(i);
        }
    }

    // 必须重写这个方法，调用 notifyDataSetChanged 时 viewpager 才会刷新，  getItem 或者 instantiateItem 才会执行
    // 如果 使用的是 FragmentStatePagerAdapter 刷新时 getItem 和 instantiateItem 都会走
    // 如果是 使用的 FragmentPagerAdapter  刷新时 getItem 不走， instantiateItem 走
    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
       //Logger.d("%s %s %s", TAG,position,mDatas.get(position).getName());
        NewsPageFragment fragment = new NewsPageFragment();
        Bundle bundle = new Bundle();

        bundle.putString(NewsPageFragment.COLUMN_ID,mDatas.get(position).getId());
        fragment.setArguments(bundle);
        return fragment;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Log.d(TAG, "instantiateItem " +  mDatas.get(position).getName());

        super.instantiateItem(container, position);
        NewsPageFragment fragment = (NewsPageFragment) super.instantiateItem(container, position);

        // 只有在使用 FragmentPageAdapter 时才用到下面这个代码

      /*  Bundle bundle = new Bundle();
        bundle.putString(NewsPageFragment.COLUMN_ID,mDatas.get(position).getId());
        fragment.setArguments(bundle);*/
        return fragment;

    }


    public NewsColumn getColumn(int position){
        if(mDatas != null && position < mDatas.size()){
            return mDatas.get(position);
        }
        return null;
    }
    @Override
    public int getCount() {
        return  mDatas == null ? 0 : mDatas.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mDatas.get(position).getName();

    }
}
