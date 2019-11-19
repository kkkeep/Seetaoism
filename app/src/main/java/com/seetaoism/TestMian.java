package com.seetaoism;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.mr.k.mvp.utils.DataCacheUtils;
import com.mr.k.mvp.utils.SystemFacade;
import com.seetaoism.data.entity.HttpResult;
import com.seetaoism.data.entity.User;

import org.json.JSONException;
import org.json.JSONObject;


public class TestMian {



    public static void main(String args []){

        HttpResult httpResult = new HttpResult();

        httpResult.code = 2;
        httpResult.message = "失败";

        httpResult.data =  null;

        Gson gson = new Gson();
        System.out.println(gson.toJson(httpResult));



        DataCacheUtils.ParameterizedTypeImpl parameterizedTypeList = new DataCacheUtils.ParameterizedTypeImpl(HttpResult.class, new Class[]{User.class});

        String jsonStr = "{\"code\":2,\"message\":\"\\u5bc6\\u7801\\u9519\\u8bef!\",\"data\":\"\"}";
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);

            if(!jsonObject.isNull("data")){
               String dataJsonStr =  jsonObject.getString("data");
               if(TextUtils.isEmpty(dataJsonStr)){
                   jsonObject.remove("data");
               }
            }
            System.out.println(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        // HttpResult<User> userHttpResult =  gson.fromJson(jsonStr, parameterizedTypeList);

        HttpResult result  = gson.fromJson(jsonStr, HttpResult.class);
        System.out.println(result.data.getClass());

    }


    public static String unicodeToString(String unicode) {
        StringBuffer sb = new StringBuffer();
        String[] hex = unicode.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {
            int index = Integer.parseInt(hex[i], 16);
            sb.append((char) index);
        }
        return sb.toString();
    }
    public static void test(Food food){

        System.out.println("test food");
    }

    public static  void test(Meat meat){
        System.out.println("test meat");
    }



    static   class  Food{
        String name;
        double weight;

        public Food(String name, double weight) {
            this.name = name;
            this.weight = weight;
        }
    }



    // 默认继承Object
    static class Fruit extends Food{
        public Fruit(String name, double weight) {
            super(name, weight);
        }
    }
    static class Meat extends Food {
        public Meat(String name, double weight) {
            super(name, weight);
        }
    }

    static class Apple extends Fruit{
        public Apple(String name, double weight) {
            super(name, weight);
        }
    }
    static class Beef extends Meat{
        public Beef(String name, double weight) {
            super(name, weight);
        }
    }


    public static class First{


        public First() {
            method();
        }

        public void method(){
            System.out.println("in first");
        }

    }


    public static class Second extends First{
        public Second() {
            method();
        }


        @Override
        public void method(){
            System.out.println("in sencod");
        }
    }



    public static void test(){

        System.out.println("dd");
    }
}
