package com.seetaoism.data.entity;



import android.graphics.Color;
import android.text.TextUtils;

import com.seetaoism.home.recommend.column.IColumnData;

import java.util.Objects;

/**
 * list : {"my_column":[{"id":"recommend","name":"推荐","type":1,"back_color":"1943ab"},{"id":"6","name":"战略","type":2,"back_color":"8cc63e"},{"id":"10","name":"一带一路","type":2,"back_color":"01aaad"},{"id":"14","name":"工程","type":2,"back_color":"ffc20f"},{"id":"27","name":"社评","type":2,"back_color":"eb030d"},{"id":"28","name":"特写","type":2,"back_color":"e85298"},{"id":"29","name":"机械","type":2,"back_color":"000000"},{"id":"39","name":"传承","type":2,"back_color":"7b1b72"}],"more_column":[{"id":"42","name":"即时","type":2,"back_color":"fc5356"}]}
 */

public class NewsColumn implements IColumnData {


    private String id;
    private String name;
    private int type;
    private String back_color;
    private int color;

    private   boolean isEdit; // 是否为编辑状态
    private   boolean isFix; // 是否为固定不懂的

    private int position;

    public void setColor(int color) {
        this.color = color;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBackColor() {
        return back_color;
    }

    public void setBackColor(String back_color) {
        this.back_color = back_color;
    }

    public int getColor(){
        if(color == 0){
            String colorStr = back_color;
            if(TextUtils.isEmpty(colorStr)){
                colorStr = "ff0066";
            }
            color = Color.parseColor("#" + colorStr);
        }
      return color;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsColumn column = (NewsColumn) o;
        return id.equals(column.id);
    }



    @Override
    public String toString() {
        return "NewsColumn{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", back_color='" + back_color + '\'' +
                '}';
    }


    @Override
    public boolean isEdit() {
        return isEdit;
    }

    @Override
    public boolean isFix() {
        return isFix;
    }

    @Override
    public void setEdit(boolean edit) {
        this.isEdit = edit;
    }

    @Override
    public void setFix(boolean fix) {
        this.isFix = fix;
    }
}
