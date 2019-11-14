package com.seetaoism.data.entity;

import java.util.List;


public class CommentReplyData {

    private long point_time;
    private  int start;

    private int more;

    private List<CommentData.Reply> reply_list;


    public long getPoint_time() {
        return point_time;
    }

    public void setPoint_time(long point_time) {
        this.point_time = point_time;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getMore() {
        return more;
    }

    public void setMore(int more) {
        this.more = more;
    }

    public List<CommentData.Reply> getReply_list() {
        return reply_list;
    }

    public void setReply_list(List<CommentData.Reply> reply_list) {
        this.reply_list = reply_list;
    }
}
