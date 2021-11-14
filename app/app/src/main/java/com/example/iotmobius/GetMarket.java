package com.example.iotmobius;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetMarket {




    public ArrayList<DataMarket> getMarket(){


            ArrayList<DataMarket> data = new ArrayList<>();


        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("http://203.253.128.161:7579/Mobius/AduFarm/"+"market_teacher"+"?fu=2&la=3&ty=4&rcn=4")
                .method("GET", null)
                .addHeader("Accept", "application/json")
                .addHeader("X-M2M-RI", "12345")
                .addHeader("X-M2M-Origin", "SOrigin")
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject con = new JSONObject();
        JSONObject obj = new JSONObject();

        try {
            obj = new JSONObject(response.body().string());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(obj);

        JSONObject o1 = new JSONObject();
        try {
            o1 = obj.getJSONObject("m2m:rsp");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(o1);

        JSONArray a1 = new JSONArray();
        try {
            a1 = o1.getJSONArray("m2m:cin");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(a1);
        System.out.println(a1.length());
        for (int i =0; i<a1.length();i++) {
            //뿌잉 매번 새로 루프 돌때마다 객체 생성을 해줘야 깊은 복사가 일어난다.
            DataMarket curdata= new DataMarket();
            try {
                o1 = a1.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                con = o1.getJSONObject("con");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println(con);

                try {//오류나는 부분,,
                    curdata.setId(con.getString("id"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    curdata.setTitle(con.getString("name"));
                    System.out.println(con.getString("name"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    curdata.setQty(con.getString("qty"));
                    System.out.println(con.getString("qty"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            System.out.println(curdata);

                data.add(curdata);
                //adapter.addItem(data);


                //data.setId(Integer.parseInt(con.substring(0,3)));
                // data.setTitle(con.substring(5));
                //data.setAuthor(con.substring(0,3));


            }
        System.out.println("한번만 수행되어야 함");
        for(int i = 0; i < data.size(); i++){
            System.out.println(data.get(i));
        }

        return data;

        }


}
