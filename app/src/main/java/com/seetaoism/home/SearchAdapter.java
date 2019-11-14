package com.seetaoism.home;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.seetaoism.GlideApp;
import com.seetaoism.GlideRequests;
import com.seetaoism.R;
import com.seetaoism.data.entity.SearchData;
import com.seetaoism.utils.SharedPrefrenceUtils;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter {

    public ArrayList<SearchData.Searchlist> mlist;
    private Context mContext;

    public SearchAdapter(ArrayList<SearchData.Searchlist> mlist, Context mContext) {
        this.mlist = mlist;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        return new ShowViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_news_item_news_left, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BaseHolder holder1 = (BaseHolder) holder;
        holder1.setData(mlist.get(position));
    }

    @Override
    public int getItemCount() {
        return mlist.size() > 0 ? mlist.size() : 0;
    }


    class ShowViewHolder extends BaseHolder {
        private ImageView pic;
        private TextView tvTitle;
        private TextView tvHot;
        private TextView tvLabel;

        public ShowViewHolder(@NonNull View itemView) {
            super(itemView);
            pic = itemView.findViewById(R.id.detail_item_news_pic);
            tvTitle = itemView.findViewById(R.id.news_item_tv_title);
            tvHot = itemView.findViewById(R.id.news_item_tv_hot);
            tvLabel = itemView.findViewById(R.id.news_item_tv_label);
        }

        @Override
        protected void setData(SearchData.Searchlist t) {
            mGlide.load(t.getImage_url()).into(pic);
            TextPaint paint = tvTitle.getPaint();
            paint.setFakeBoldText(true);
            String theme = t.getTheme();

            String keyword = SharedPrefrenceUtils.getString(mContext, "isWords", "");
            if (theme != null && theme.contains(keyword)) {
                int index = theme.indexOf(keyword);
                int len = keyword.length();

                Spanned temp = Html.fromHtml(theme.substring(0, index)
                        + "<font color=#FF0000>"
                        + theme.substring(index, index + len) + "</font>"
                        + theme.substring(index + len, theme.length()));
                tvTitle.setText(temp);
            } else {
                tvTitle.setText(theme);
            }
            tvLabel.setText(t.getColumn_name());
        }


    }


    //加载图片
    public abstract class BaseHolder extends RecyclerView.ViewHolder {

        protected GlideRequests mGlide;

        public BaseHolder(@NonNull View itemView) {
            super(itemView);
            mGlide = GlideApp.with(itemView)/*.applyDefaultRequestOptions(RequestOptions.bitmapTransform(new RoundCorner(itemView.getContext(), 4)))*/;

        }

        protected abstract void setData(SearchData.Searchlist t);

    }

}
