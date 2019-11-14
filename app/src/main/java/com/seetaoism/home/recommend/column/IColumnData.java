package com.seetaoism.home.recommend.column;


public interface IColumnData {

    boolean isEdit();
    boolean isFix();

    void setEdit(boolean edit);
    void setFix(boolean fix);

    String getName();
}
