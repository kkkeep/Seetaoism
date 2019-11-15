package com.seetaoism.data.entity;

import java.util.List;

/*
 * created by Cherry on 2019-10-22
 **/
public class CommentData {

    private long point_time;
    private int start;

    private int more;

    private List<Comment> comment_list;


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

    public List<Comment> getComment_list() {
        return comment_list;
    }

    public void setComment_list(List<Comment> comment_list) {
        this.comment_list = comment_list;
    }

    public static  class Comment{
        private String comment_id;
        private String reply_id;
        private String user_id;
        private String content;
        private String username;
        private String head_url;
        private String time_describe;

        private int type;
        private int praise_count_describe;
        private int is_praise;
        private int reply_start;

        public long getReplyPointTime() {
            return point_time;
        }

        public void setReplyPointTime(long point_time) {
            this.point_time = point_time;
        }

        private long point_time;
        private int reply_more;

        private List<Reply> reply_list;

        public String getComment_id() {
            return comment_id;
        }

        public void setComment_id(String comment_id) {
            this.comment_id = comment_id;
        }

        public String getReply_id() {
            return reply_id;
        }

        public void setReply_id(String reply_id) {
            this.reply_id = reply_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getHead_url() {
            return head_url;
        }

        public void setHead_url(String head_url) {
            this.head_url = head_url;
        }

        public String getTime_describe() {
            return time_describe;
        }

        public void setTime_describe(String time_describe) {
            this.time_describe = time_describe;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getPraise_count_describe() {
            return praise_count_describe;
        }

        public void setPraise_count_describe(int praise_count_describe) {
            this.praise_count_describe = praise_count_describe;
        }

        public int getIs_praise() {
            return is_praise;
        }

        public void setIs_praise(int is_praise) {
            this.is_praise = is_praise;
        }

        public int getReply_start() {
            return reply_start;
        }

        public void setReply_start(int reply_start) {
            this.reply_start = reply_start;
        }

        public int getReply_more() {
            return reply_more;
        }

        public void setReply_more(int reply_more) {
            this.reply_more = reply_more;
        }

        public List<Reply> getReply_list() {
            return reply_list;
        }

        public void setReply_list(List<Reply> reply_list) {
            this.reply_list = reply_list;
        }
    }


    public static class Reply{
        private String reply_id;
        private String comment_id;
        private String content;
        private String from_name;
        private String to_name;
        private String from_id;
        private String to_id;

        private int type;


        public String getReply_id() {
            return reply_id;
        }

        public void setReply_id(String reply_id) {
            this.reply_id = reply_id;
        }

        public String getComment_id() {
            return comment_id;
        }

        public void setComment_id(String comment_id) {
            this.comment_id = comment_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getFrom_name() {
            return from_name;
        }

        public void setFrom_name(String from_name) {
            this.from_name = from_name;
        }

        public String getTo_name() {
            return to_name;
        }

        public void setTo_name(String to_name) {
            this.to_name = to_name;
        }

        public String getFrom_id() {
            return from_id;
        }

        public void setFrom_id(String from_id) {
            this.from_id = from_id;
        }

        public String getTo_id() {
            return to_id;
        }

        public void setTo_id(String to_id) {
            this.to_id = to_id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
