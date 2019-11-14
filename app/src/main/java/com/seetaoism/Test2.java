package com.seetaoism;

import com.google.gson.Gson;
import com.seetaoism.data.entity.HttpResult;

import org.json.JSONObject;

import java.util.List;


public class Test2 {


    public static void test2(List<? extends TestMian.Food> foods){

        //foods.add(new TestMian.Beef());

        HttpResult httpResult = new HttpResult();

        httpResult.code = 2;
        httpResult.message = "失败";

        httpResult.data =  null;

        Gson gson = new Gson();
       System.out.println(gson.toJson(httpResult));
    }
}
