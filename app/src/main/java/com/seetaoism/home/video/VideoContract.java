package com.seetaoism.home.video;

import com.mr.k.mvp.base.IBaseCallBack;
import com.mr.k.mvp.base.IBasePresenter;
import com.mr.k.mvp.base.IBaseView;
import com.seetaoism.data.entity.VideoData;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.Map;

public interface VideoContract {

    public interface VideoView extends IBaseView<VideoPresenter> {
        //缓存数据
        void onVideoCacheResult(VideoData data);
        //网络数据
        void onVideoSuccess(VideoData data,String msg);

    }

    public interface VideoPresenter extends IBasePresenter<VideoView> {
        void video(int start,int point_time,int number);
    }

    public interface VideoModel {


//        int REQUEST_TYPE_FIRST_LOAD = 0x100;
//        int REQUEST_TYPE_REFRESH_LOAD = 0x101;
//        int REQUEST_TYPE_MORE_LOAD = 0x102;



        void getVideo(LifecycleProvider provider, Map<String, String> params, IBaseCallBack<VideoData> callBack);
    }


//    @Retention(RetentionPolicy.SOURCE)
//    @IntDef({VideoModel.REQUEST_TYPE_FIRST_LOAD, VideoModel.REQUEST_TYPE_REFRESH_LOAD, VideoModel.REQUEST_TYPE_MORE_LOAD})
//    @interface RequestType {
//    }
}
