package com.example.iotmobius;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.Objects;

public class CustomDialog extends Dialog{

    private final Context mContext;
    private EditText username;
    private EditText userpoint;
    private String s_user_id;
    private String s_username;
    private int s_point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sample);

        // 다이얼로그의 배경을 투명으로 만든다.
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        username = (EditText)findViewById(R.id.username);
        userpoint = (EditText)findViewById(R.id.userpoint);
        Button saveButton = findViewById(R.id.submit);
        Button cancelButton = findViewById(R.id.cancel);

        // 버튼 리스너 설정
        saveButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                // '확인' 버튼 클릭시
                Toast.makeText(mContext,"구매신청완료", Toast.LENGTH_SHORT).show();
                s_username = username.getText().toString();
                s_point = Integer.parseInt(userpoint.getText().toString());

                httppost(s_username,s_point,s_user_id);
                // Custom Dialog 종료
                dismiss();
            }
        });
        cancelButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                // '취소' 버튼 클릭시
                // Custom Dialog 종료
                dismiss();
            }
        });

    }

    public CustomDialog(Context mContext, String ui) {
        super(mContext);
        this.mContext = mContext;
        this.s_user_id = ui;
    }

    public void httppost(String u, int p, String ui) {//인수 :보낼 데이터

//body에 내용 바꾸면 됨
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/vnd.onem2m-res+json; ty=4");
        RequestBody body = RequestBody.create(mediaType, "{\n    \"m2m:cin\": {\n        \"con\": \n        {\n              \"user\" :\"" + u + "\",\n              \"point\" :\""+ p + "\",\n              \"item\" : \""+ ui + "\"\n      }\n    }\n}");
        Request request = new Request.Builder()
                .url("http://203.253.128.161:7579/Mobius/AduFarm/auction")
                .method("POST", body)
                .addHeader("Accept", "application/json")
                .addHeader("X-M2M-RI", "12345")
                .addHeader("X-M2M-Origin", "SPLIKNC0doF")
                .addHeader("Content-Type", "application/vnd.onem2m-res+json; ty=4")
                .build();


        // 응답 콜백
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    // 응답 실패
                    Log.i("tag", "응답실패");
                    System.out.println("응답실패");
                } else {
                    // 응답 성공
                    System.out.println("응답성공");
                    Log.i("tag", "응답 성공");
                    final String responseData = response.body().string();

                }
            }
        });


    }


}

