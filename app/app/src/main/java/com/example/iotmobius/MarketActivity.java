package com.example.iotmobius;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
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

public class MarketActivity extends AppCompatActivity {


    MarketAdapter adapter;
    private ArrayList<DataMarket> datas;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        init();

        getData();



    }
        private void init () {
            RecyclerView recyclerView = findViewById(R.id.vertical_recycler_view);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);

            adapter = new MarketAdapter(MarketActivity.this);
            recyclerView.setAdapter(adapter);


        }

        private void getData () {
            //DataMarket data = new DataMarket(1,"물주기 3일", "김쌤");
            //adapter.addItem(data);
            //data = new DataMarket(3,"무드등 이용권", "박쌤");
            //adapter.addItem(data);
            //data = new DataMarket(2,"환풍 자동 2일", "이쌤");
            //adapter.addItem(data);

            //이부분에 서버에서 가져온다.

                GetMarket get = new GetMarket();
                datas = get.getMarket();
                for(int i = 0; i<datas.size();i++) {
                    System.out.println(datas.get(i));
                    adapter.addItem(datas.get(i));
                    adapter.notifyDataSetChanged();
                }




        }

    }
