package jy.com.libbanner;

import android.widget.ImageView;

/*
 * created by taofu on 2019-06-19
 **/
public interface IJBannerAdapter<M> {

    void fillBannerItemData(JBanner banner, ImageView imageView, M mode, int position);
}
