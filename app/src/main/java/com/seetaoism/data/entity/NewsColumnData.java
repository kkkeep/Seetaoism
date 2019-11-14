package com.seetaoism.data.entity;



/**
 * list : {"my_column":[{"id":"recommend","name":"推荐","type":1,"back_color":"1943ab"},{"id":"6","name":"战略","type":2,"back_color":"8cc63e"},{"id":"10","name":"一带一路","type":2,"back_color":"01aaad"},{"id":"14","name":"工程","type":2,"back_color":"ffc20f"},{"id":"27","name":"社评","type":2,"back_color":"eb030d"},{"id":"28","name":"特写","type":2,"back_color":"e85298"},{"id":"29","name":"机械","type":2,"back_color":"000000"},{"id":"39","name":"传承","type":2,"back_color":"7b1b72"}],"more_column":[{"id":"42","name":"即时","type":2,"back_color":"fc5356"}]}
 */


public class NewsColumnData {

    private NewsColumnList list;

    public NewsColumnList getList() {
        return list;
    }

    public void setList(NewsColumnList list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "NewsColumnData{" +
                "list=" + list +
                '}';
    }
}



