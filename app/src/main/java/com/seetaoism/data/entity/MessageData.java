package com.seetaoism.data.entity;

import java.util.List;

public class MessageData {
    private int start;
    private int point_time;
    private int more;
    private List<MessageList> list;

    public MessageData(int start, int point_time, int more, List<MessageList> list) {
        this.start = start;
        this.point_time = point_time;
        this.more = more;
        this.list = list;
    }

    @Override
    public String toString() {
        return "MessageData{" +
                "start=" + start +
                ", point_time=" + point_time +
                ", more=" + more +
                ", list=" + list +
                '}';
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getPoint_time() {
        return point_time;
    }

    public void setPoint_time(int point_time) {
        this.point_time = point_time;
    }

    public int getMore() {
        return more;
    }

    public void setMore(int more) {
        this.more = more;
    }

    public List<MessageList> getList() {
        return list;
    }

    public void setList(List<MessageList> list) {
        this.list = list;
    }


    public static  class  MessageList{
        private int id;
        private String content;
        private int is_read;
        private String time;
        private int notice_status;
        private boolean select;

        public MessageList(boolean select) {
            this.select = select;
        }

        public boolean isSelect() {
            return select;
        }

        public void setSelect(boolean select) {
            this.select = select;
        }

        public MessageList(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public MessageList(String content, int is_read, String time, int notice_status) {
            this.content = content;
            this.is_read = is_read;
            this.time = time;
            this.notice_status = notice_status;
        }

        @Override
        public String toString() {
            return "MessageList{" +
                    "content='" + content + '\'' +
                    ", is_read=" + is_read +
                    ", time='" + time + '\'' +
                    ", notice_status=" + notice_status +
                    '}';
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getIs_read() {
            return is_read;
        }

        public void setIs_read(int is_read) {
            this.is_read = is_read;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getNotice_status() {
            return notice_status;
        }

        public void setNotice_status(int notice_status) {
            this.notice_status = notice_status;
        }
    }

}
