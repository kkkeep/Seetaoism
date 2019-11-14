package com.seetaoism.data.entity;


import java.util.ArrayList;
import java.util.Arrays;

/**
 * list : {"my_column":[{"id":"recommend","name":"推荐","type":1,"back_color":"1943ab"},{"id":"6","name":"战略","type":2,"back_color":"8cc63e"},{"id":"10","name":"一带一路","type":2,"back_color":"01aaad"},{"id":"14","name":"工程","type":2,"back_color":"ffc20f"},{"id":"27","name":"社评","type":2,"back_color":"eb030d"},{"id":"28","name":"特写","type":2,"back_color":"e85298"},{"id":"29","name":"机械","type":2,"back_color":"000000"},{"id":"39","name":"传承","type":2,"back_color":"7b1b72"}],"more_column":[{"id":"42","name":"即时","type":2,"back_color":"fc5356"}]}
 */


public class NewsColumnList {

    private ArrayList<NewsColumn> my_column;
    private ArrayList<NewsColumn> more_column;


    public ArrayList<NewsColumn> getMyColumnList(){
        return my_column;
    }


    public ArrayList<NewsColumn> getMoreColumnList(){
        return more_column;
    }


    @Override
    public String toString() {
        return "NewsColumnList{" +
                "my_column=" + (my_column == null ? " null" : "my = " + Arrays.toString(my_column.toArray())) +
                ", more_column=" + (more_column == null ? " null " : "more = " +  Arrays.toString(more_column.toArray())) +
                '}';
    }
}



