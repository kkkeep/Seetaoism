package jy.com.libbanner;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import java.util.List;

/*
 * created by Cherry on 2019-11-11
 **/
public class MarqueeTextView extends AppCompatTextView {
    public MarqueeTextView(Context context) {
        super(context);
    }

    public MarqueeTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqueeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isFocused() {
        return true;
    }

    @Override
    public boolean isSelected() {
        return true;
    }

    @Override
    public boolean canScrollHorizontally(int direction) {
        return false;
    }

    public void setData(List<MarqueeData> datas){


        if(datas == null || datas.size() == 0){
            return;
        }
        setHighlightColor(getResources().getColor(android.R.color.transparent));
        final CircleMovementMethod method = new CircleMovementMethod(0xffcccccc, 0xffcccccc);

       SpannableStringBuilder builder = new SpannableStringBuilder();
        //StringBuilder builder = new StringBuilder();
        for(MarqueeData margueeData : datas){
            builder.append(setClickableSpan(margueeData)).append("    ");
        }
       // setMovementMethod(LinkMovementMethod.getInstance());
        setText(builder);
        setSelected(true);
    }

    public SpannableString setClickableSpan( final MarqueeData data) {

        final SpannableString string = new SpannableString(data.theme);
        ClickableSpan span = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // TODO: 2017/9/3 评论用户名字点击事件
                Toast.makeText(getContext(), data.getId(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                // 设置显示的用户名文本颜色
                ds.setColor(getPaint().getColor());
                ds.setUnderlineText(false);
            }
        };

        string.setSpan(span, 0, string.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
      /*  string.setSpan(new ForegroundColorSpan(Color.BLUE),0, string.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        string.setSpan(new UnderlineSpan(),0, string.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);*/
        return string;
    }
    /**
     * 用于 EditText 存在时抢占焦点
     */
    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        if (focused) {
            super.onFocusChanged(focused, direction, previouslyFocusedRect);
        }
    }

    /**
     * Window与Window间焦点发生改变时的回调
     * 解决 Dialog 抢占焦点问题
     *
     * @param hasWindowFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        if (hasWindowFocus) {
            super.onWindowFocusChanged(hasWindowFocus);
        }
    }


    public static class MarqueeData{

        private String id;
        private String theme;
        private String description;
        private String image_url;
        private int is_good;
        private int is_collect;
        private String link;
        private String share_link;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTheme() {
            return theme;
        }

        public void setTheme(String theme) {
            this.theme = theme;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public int getIs_good() {
            return is_good;
        }

        public void setIs_good(int is_good) {
            this.is_good = is_good;
        }

        public int getIs_collect() {
            return is_collect;
        }

        public void setIs_collect(int is_collect) {
            this.is_collect = is_collect;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getShare_link() {
            return share_link;
        }

        public void setShare_link(String share_link) {
            this.share_link = share_link;
        }
    }
}