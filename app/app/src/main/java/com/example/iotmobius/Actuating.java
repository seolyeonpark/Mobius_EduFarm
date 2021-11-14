package com.example.iotmobius;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Actuating {
    //식물과 관련한 동작을 저장한 클래스
    boolean motor;
    boolean autowater;

    public boolean isMotorrun() {
        return motor;
    }

    public void setMotor(boolean motor) {
        this.motor = motor;
    }

    public boolean isAutowaterrun() {
        return autowater;
    }

    public void setAutowater(boolean autowater) {
        this.autowater = autowater;
    }

    public Actuating(){

    }
    public Actuating(boolean motor, boolean autowater) {
        this.motor = motor;
        this.autowater = autowater;
    }

    public void postAct(Context context, String
            path, String act){//앱에서 설정한 액추에이팅 상태를 서버로 전달합니다.
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
                            Log.i("tag", "LED set 실패");
                            System.out.println("LED set 실패");

                        } else {
                            // 응답 성공
                            System.out.println("LED set 응답성공");
                            Log.i("tag", "LED set 성공");
                            final String responseData = response.body().string();

                            // 메인스레드 Ui 설정
                           /* context.runOnUiThread(new Runnable() {
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
}