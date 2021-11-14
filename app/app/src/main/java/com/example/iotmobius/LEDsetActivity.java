package com.example.iotmobius;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LEDsetActivity extends AppCompatActivity {
ImageView red;
ImageView green;
ImageView blue;
ImageView btn_back;
LED led = new LED(0,0,0);
String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ledset);
        red = findViewById(R.id.red);
        green = findViewById(R.id.green);
        blue = findViewById(R.id.blue);
        btn_back = findViewById(R.id.back);
        check();//서버에 있는 값으로 led 객체 초기화
        View.OnClickListener back = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        };
        btn_back.setOnClickListener(back);
        View.OnClickListener tabr = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //0이면 1로, 1이면 0으로 toggle
                led.setR((led.getR()!= 0)? 0:1);
             /*   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    red.setImageDrawable(getDrawable(R.drawable.circle_off));
                }*/
                System.out.println("led="+led.getRGB());
                postAct();
                check();
            }
        };
        red.setOnClickListener(tabr);
        View.OnClickListener tabg = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                led.setG((led.getG()!= 0)? 0:1);
                System.out.println("led="+led.getRGB());
                postAct();
                check();
                check();
            }
        };
        green.setOnClickListener(tabg);
        View.OnClickListener tabb = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                led.setB((led.getB()!= 0)? 0:1);
                System.out.println("led="+led.getRGB());
                postAct();
                check();
                check();
            }
        };
        blue.setOnClickListener(tabb);
    }

    public void postAct(){//앱에서 설정한 액추에이팅 상태를 서버로 전달합니다.
        new Thread(){
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                MediaType mediaType = MediaType.parse("application/vnd.onem2m-res+json; ty=4");
                RequestBody body = RequestBody.create(mediaType,
                        "{\n" +
                                "    \"m2m:cin\": {\n" +
                                "        \"con\": \""+led.getRGB()+"\"\n" +
                                "    }\n" +
                                "}");
                Request request = new Request.Builder()
                        .url("http://203.253.128.161:7579/Mobius/AduFarm/act_led")
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
                            Log.i("tag", "LED set 실패");
                            System.out.println("LED set 실패");

                        } else {
                            // 응답 성공
                            System.out.println("LED set 응답성공");
                            Log.i("tag", "LED set 성공");
                            final String responseData = response.body().string();
                            // 서브 스레드 Ui 변경 할 경우 에러
                            // 메인스레드 Ui 설정
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        if (!response.isSuccessful()) {
                                            // 응답 실패
                                            Toast toast = Toast.makeText(LEDsetActivity.this,"LED failed", Toast.LENGTH_LONG);
                                            toast.show();
                                        }

                                        System.out.println("응답" + responseData);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
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
    public void check(){//화분 상태, 아이템의 동작을 확인합니다.
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

                result = getState("act_led");
                if(result!=null) {
                    int r, g, b;
                    r = Integer.parseInt(result.substring(0, 1));
                    g = Integer.parseInt(result.substring(1, 2));
                    b = Integer.parseInt(result.substring(2));
                    System.out.println("현재 삼색은" + r+ g+b);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            red = findViewById(R.id.red);
                            green = findViewById(R.id.green);
                            blue = findViewById(R.id.blue);

                            if (r == 1) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    red.setImageDrawable(getDrawable(R.drawable.circle_red));
                                    System.out.println("붉은색으로 바뀜");
                                }
                            } else {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    red.setImageDrawable(getDrawable(R.drawable.circle_off));
                                    System.out.println("붉은색으로 꺼짐");
                                }
                            }
                            if (g == 1) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    green.setImageDrawable(getDrawable(R.drawable.circle_green));
                                    System.out.println("초록색으로 바뀜");
                                }
                            } else {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    green.setImageDrawable(getDrawable(R.drawable.circle_off));
                                    System.out.println("초록색 꺼짐");
                                }
                            }
                            if (b == 1) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    blue.setImageDrawable(getDrawable(R.drawable.circle_blue));
                                    System.out.println("푸른색으로 바뀜");
                                }
                            } else {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    blue.setImageDrawable(getDrawable(R.drawable.circle_off));
                                    System.out.println("푸른색 꺼짐");
                                }
                            }
                        }

                    });
                }
                }//run
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}