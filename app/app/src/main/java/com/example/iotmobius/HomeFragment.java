package com.example.iotmobius;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private RemoteAdapter remoteadapter;
    private GridLayoutManager gridLayoutManager;
    private ArrayList<RemoteItem> arrayList = new ArrayList<>();
    Activity myActivity;
    // State state = new State();
    com.example.iotmobius.State state = new com.example.iotmobius.State();
    Actuating actuating = new Actuating();
    int timegap;
    TextView light;
    TextView soil;
    TextView temp;
    TextView humid;
    ImageView myplant;
    View graph_light;
    View graph_soil;
    View graph_temp;
    View graph_humid;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        arrayList.clear();
        Thread thread =  new Thread(){
            @Override
            public void run(){
                JSONObject able = null;
                OkHttpClient client = new OkHttpClient().newBuilder().build();
                Request request = new Request.Builder()
                        .url("http://203.253.128.161:7579/Mobius/AduFarm/" + "user_control/studentA" + "?fu=2&la=1&ty=4&rcn=4")
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
                JSONObject con = null;
                JSONObject obj = null;
                try {
                    obj = new JSONObject(response.body().string());
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(obj);

                JSONObject o1 = null;
                try {
                    o1 = obj.getJSONObject("m2m:rsp");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println(o1);

                JSONArray a1 = null;
                try {
                    a1 = o1.getJSONArray("m2m:cin");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println(a1);
                try {
                    o1 = a1.getJSONObject(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println(o1);
                try {
                    con = o1.getJSONObject("con");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println(con);
                System.out.println("서버의값은" + con);
                try {
                    if(Boolean.parseBoolean(con.getString("item1")))
                        arrayList.add(new RemoteItem(1));
                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }
                try {
                    if(Boolean.parseBoolean(con.getString("item2")))
                        arrayList.add(new RemoteItem(2));
                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }
                try {
                    if(Boolean.parseBoolean(con.getString("item3")))
                        arrayList.add(new RemoteItem(3));
                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home,container, false);
        myActivity = getActivity();
        timegap = 3000;
        check();
        light = v.findViewById(R.id.home_sun);
        soil = v.findViewById(R.id.home_water);
        temp = v.findViewById(R.id.home_temp);
        humid = v.findViewById(R.id.home_humid);

        graph_light = v.findViewById(R.id.graph_sun);
        graph_soil = v.findViewById(R.id.graph_water);
        graph_temp = v.findViewById(R.id.graph_temp);
        graph_humid = v.findViewById(R.id.graph_humid);
        myplant = v.findViewById(R.id.plant);

        Thread thread = new Thread(){
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                Request request = new Request.Builder()
                        .url("http://203.253.128.161:7579/Mobius/AduFarm/" + "record" + "?fu=2&la=10&ty=4&rcn=4")
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
                JSONObject con = null;
                JSONObject obj = null;
                try {
                    obj = new JSONObject(response.body().string());
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(obj);

                JSONObject o1 = null;
                try {
                    o1 = obj.getJSONObject("m2m:rsp");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println(o1);

                JSONArray a1 = null;
                try {
                    a1 = o1.getJSONArray("m2m:cin");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println(a1);
                try {
                    o1 = a1.getJSONObject(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println(o1);
                try {
                    con = o1.getJSONObject("con");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println(con);
                try {
                    String abc = con.getString("image");
                    byte[] bytes = new byte[0];
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        bytes = Base64.getDecoder().decode(abc);
                    }  myplant.setImageBitmap(BitmapFactory.decodeByteArray(bytes,0, bytes.length));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // arrayList.add(new RemoteItem(1));

        /*arrayList.add(new RemoteItem(2));
        arrayList.add(new RemoteItem(3));*/


        recyclerView = (RecyclerView) v.findViewById(R.id.home_recycler);
        gridLayoutManager = new GridLayoutManager(getActivity(),3);
        remoteadapter = new RemoteAdapter(myActivity,getActivity(),arrayList);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(remoteadapter);

        // Inflate the layout for this fragment
        return v;
        // return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void check(){//화분 상태, 아이템의 동작을 확인합니다.
        System.out.println("timegap = "+timegap);
        Thread thread = new Thread() {
            public String getState(String path){//주어진 string에 따른 경로에서 cin의 con 값을 가져옵니다.

                OkHttpClient client = new OkHttpClient().newBuilder().build();
                Request request = new Request.Builder()
                        .url("http://203.253.128.161:7579/Mobius/AduFarm/" + path + "?fu=2&la=1&ty=4&rcn=4")
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
                //String result = response.body().string();
                String con = null;
                JSONObject obj = null;
                try {
                    obj = new JSONObject(response.body().string());
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(obj);

                JSONObject o1 = null;
                try {
                    o1 = obj.getJSONObject("m2m:rsp");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println(o1);

                JSONArray a1 = null;
                try {
                    a1 = o1.getJSONArray("m2m:cin");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println(a1);
                try {
                    o1 = a1.getJSONObject(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println(o1);
                try {
                    con = o1.getString("con");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println(con);
                return con;
            }
            @Override public void run () {
                while (true) {

                    String path[] = {"soil", "light","temp","act_water"}; //temp = temp & humid

                    for (int j = 0; j < 4; j++) {
                        if (j == 0) state.setSoil(Double.parseDouble(getState(path[j])));
                        else if (j == 1)
                            state.setLight(Double.parseDouble(getState(path[j]).substring(1, 3)));
                        else if (j == 2) {
                            state.setTemp(Double.parseDouble(getState(path[j]).substring(0, 3)));
                            state.setHumid(Double.parseDouble(getState(path[j]).substring(5)));
                        }else if (j == 3) actuating.setAutowater((Integer.parseInt(getState(path[j])))!=0);
                        else ;
                    }
                    try {

                        if(myActivity == null){
                            System.out.println("널이야");
                            return;}
                        myActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                light = (TextView)myActivity.findViewById(R.id.home_sun);
                                soil = (TextView)myActivity.findViewById(R.id.home_water);
                                temp = (TextView)myActivity.findViewById(R.id.home_temp);
                                humid = (TextView)myActivity.findViewById(R.id.home_humid);
                                graph_light = myActivity.findViewById(R.id.graph_sun);
                                graph_soil = myActivity.findViewById(R.id.graph_water);
                                graph_temp = myActivity.findViewById(R.id.graph_temp);
                                graph_humid = myActivity.findViewById(R.id.graph_humid);
                                if(light!= null|soil!= null|temp!= null|humid!= null) {
                                    light.setText("" + state.getLight());
                                    soil.setText("" + state.getSoil());
                                    temp.setText("" + state.getTemp());
                                    humid.setText("" + state.getHumid());
                                    //TODO: 그래프 값을 어떻게 해야할지...ㅜㅜ;
                                    graph_light.getLayoutParams().width = (int) (5* (state.getLight()));
                                    graph_soil.getLayoutParams().width = (int)((200000/((state.getSoil()))));
                                    graph_temp.getLayoutParams().width = (int) (700 *(state.getTemp()/45));
                                    graph_humid.getLayoutParams().width = (int) (10*(state.getHumid()));

                                    //이상 값일때 : graph_light.setImageDrawable(getDrawable(R.drawable.circle_red));
/*
                                        graph_light.getLayoutParams().width = (int) (280-(110 - state.getLight()));
                                        graph_soil.getLayoutParams().width = (int) (280-(- state.getSoil()));
                                        graph_temp.getLayoutParams().width = (int) (280 -(45 - state.getLight()));
                                        graph_humid.getLayoutParams().width = (int) (280 - state.getLight());*/
                                }
                            }
                        });
                        Thread.sleep(timegap);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }//run
            }
        };
        thread.start();
       /* try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

    }


}
