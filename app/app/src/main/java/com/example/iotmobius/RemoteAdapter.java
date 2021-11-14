package com.example.iotmobius;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class RemoteAdapter extends RecyclerView.Adapter<RemoteAdapter.CustomViewHolder> {
    private ArrayList<RemoteItem> arrayList;
    private Context context;
    Activity mAct;

    public RemoteAdapter(Activity parentcontext, Context context, ArrayList<RemoteItem> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        this.mAct = parentcontext;
    }


    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item_view,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        int a = arrayList.get(position).getitem_type();
        System.out.println(a+"dy");
        switch (a){
            case 1:
                holder.item_image.setImageResource(R.drawable.drop_1);
                holder.item_name.setText("auto water");
                System.out.println("1선택");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                            Thread thread = new Thread() {
                                @Override
                                public void run() {
                                    OkHttpClient client = new OkHttpClient().newBuilder().build();
                                    Request request = new Request.Builder()
                                            .url("http://203.253.128.161:7579/Mobius/AduFarm/" + "act_water" + "?fu=2&la=1&ty=4&rcn=4")
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
                                    System.out.println("서버의값은" + con);

                                    String finalCon = con;
                                    mAct.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            System.out.println("파이널 콘" +finalCon);
                                            if (Integer.parseInt(finalCon) == 1) {
                                                Toast toast = Toast.makeText(context,"watering off", Toast.LENGTH_SHORT);
                                                toast.show();
                                                postAct(context,"act_water",0+"");
                                            }else {
                                                Toast toast = Toast.makeText(context, "watering on", Toast.LENGTH_SHORT);
                                                toast.show();
                                                postAct(context, "act_water", 1 + "");
                                            }
                                        }
                                    });
                                }//run()
                            };//Thread 선언
                            thread.start();
                        try {
                            thread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //thread
                    }//onclick
                });//setOn
                break;

            case 2:
                holder.item_image.setImageResource(R.drawable.fan);
                holder.item_name.setText("auto air fan");
                System.out.println("2선택");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Thread thread = new Thread() {
                            @Override
                            public void run() {
                                OkHttpClient client = new OkHttpClient().newBuilder().build();
                                Request request = new Request.Builder()
                                        .url("http://203.253.128.161:7579/Mobius/AduFarm/" + "act_motor" + "?fu=2&la=1&ty=4&rcn=4")
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
                                System.out.println("서버의값은" + con);

                                String finalCon = con;
                                mAct.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        System.out.println("파이널 콘" +finalCon);
                                        if (Integer.parseInt(finalCon) == 1) {
                                            Toast toast = Toast.makeText(context,"fan off", Toast.LENGTH_SHORT);
                                            toast.show();
                                            postAct(context,"act_motor",0+"");
                                        }else {
                                            Toast toast = Toast.makeText(context, "fan on", Toast.LENGTH_SHORT);
                                            toast.show();
                                            postAct(context, "act_motor", 1 + "");
                                        }
                                    }
                                });
                            }//run()
                        };//Thread 선언
                        thread.start();
                        try {
                            thread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //thread
                    }
                });
                break;
            case 3:
                holder.item_image.setImageResource(R.drawable.rgb);
                holder.item_name.setText("LED light");
                System.out.println("3선택");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context,LEDsetActivity.class);
                        context.startActivity(intent);
                    }
                });
                break;
            default:
                System.out.println("아무 값도 인식되지 않았습니다.");break;

        }
    }

    @Override
    public int getItemCount() {
        return (null != arrayList) ? arrayList.size() : 0;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView item_name;
        protected ImageView item_image;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.item_name = (TextView) itemView.findViewById(R.id.item_name);
            this.item_image = (ImageView) itemView.findViewById(R.id.item_image);

        }

    }
    public void postAct(Context context, String path, String act){//앱에서 설정한 액추에이팅 상태를 서버로 전달합니다.
        new Thread(){
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                MediaType mediaType = MediaType.parse("application/vnd.onem2m-res+json; ty=4");
                RequestBody body = RequestBody.create(mediaType,
                        "{\n" +
                                "    \"m2m:cin\": {\n" +
                                "        \"con\": \""+act+"\"\n" +
                                "    }\n" +
                                "}");
                Request request = new Request.Builder()
                        .url("http://203.253.128.161:7579/Mobius/AduFarm/"+path)
                        .method("POST", body)
                        .addHeader("Accept", "application/json")
                        .addHeader("X-M2M-RI", "12345")
                        .addHeader("X-M2M-Origin", "{{aei}}")
                        .addHeader("Content-Type", "application/vnd.onem2m-res+json; ty=4")
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        if (!response.isSuccessful()) {
                            // 응답 실패
                            Log.i("tag", " 실패");
                            System.out.println("실패");
                            Toast toast = Toast.makeText(context, "fail", Toast.LENGTH_SHORT);
                            toast.show();
                        } else {
                            // 응답 성공
                            System.out.println(" 응답성공");
                            Log.i("tag", " 성공");
                            final String responseData = response.body().string();
                            // 서브 스레드 Ui 변경 할 경우 에러
                            // 메인스레드 Ui 설정
                           /* runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        if (!response.isSuccessful()) {
                                            // 응답 실패
                                            Toast toast = Toast.makeText(context,"LED설정에 실패했어요", Toast.LENGTH_LONG);
                                            toast.show();
                                        }

                                        System.out.println("응답" + responseData);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });*/
                        }
                    }
                });
                try {
                    Response response = client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    public static void getState(String path) {//주어진 string에 따른 경로에서 cin의 con 값을 가져옵니다.

        Thread thread = new Thread() {
            @Override
            public void run() {
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
            }

        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
