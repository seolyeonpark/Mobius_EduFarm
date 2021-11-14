package com.example.iotmobius;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
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


    }

    private Button btn_point;
    TextView point;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_user, container, false);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        point = (TextView)rootView.findViewById(R.id.point_view);

        //btn_point = (Button)rootView.findViewById(R.id.btn_point);
        //btn_point.setOnClickListener(new View.OnClickListener(){
           // @Override
            //public void onClick(View v) {
                getPoint();
           // }
       // });
        return rootView;




    }

    void getPoint() {
        new Thread(new Runnable() { @Override public void run() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("http://203.253.128.161:7579/Mobius/AduFarm/"+"havepoint"+"?fu=2&ty=4&rcn=4")
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

            try {
                System.out.println(con.getString("name"));
                if (con.getString("name").equals("studentA") == true) {
                    String temp = con.getString("hp");
                   getActivity().runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           point.setText(temp);
                       }
                   });
                    break;
                } else
                    continue;
            } catch (JSONException e) {
                e.printStackTrace();
            }


            System.out.println("포인트 저장함");


        }
        }
        }).start();

    }


}