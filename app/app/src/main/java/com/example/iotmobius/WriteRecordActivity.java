package com.example.iotmobius;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class WriteRecordActivity extends AppCompatActivity {

    public Uri imageUri;
    ImageView NewImage;
    public TextView date;
    public EditText RecordIntext;
    public  EditText RecordTitle;
    Button btn_ok;
    ImageView btn_back;
    String date_text;
    State state = new State();
    TextView light;
    TextView soil;
    TextView temp;
    TextView humid;
    DiaryFragment DiaryFragment;
    ImageView testt;
    int watercounted =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_record);

        btn_ok = findViewById(R.id.okbtn);
        btn_back = findViewById(R.id.back);
        RecordTitle = findViewById(R.id.title);
        RecordIntext = findViewById(R.id.intext);
        NewImage = findViewById(R.id.test);
        date = findViewById(R.id.record_date);
        light = (TextView) findViewById(R.id.record_sun);
        soil = findViewById(R.id.record_water);
        temp = findViewById(R.id.record_temp);
        humid = findViewById(R.id.record_humid);
        testt = findViewById(R.id.test);
        DiaryFragment = new DiaryFragment();
        //맨 상단 날짜 자동 기입
        Date currentTime = Calendar.getInstance().getTime();
        date_text = new SimpleDateFormat("yyyy. MM. dd. E",Locale.getDefault()).format(currentTime);
       //  Locale.KOREAN
        date.setText(date_text);

        check();

        System.out.println(state.getTemp());
        //getSupportFragmentManager().beginTransaction().replace(R.id.container, DiaryFragment).commit();
        View.OnClickListener selectImag = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImage();

            }
        };
        NewImage.setOnClickListener(selectImag);
        View.OnClickListener back = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        };
        btn_back.setOnClickListener(back);
        View.OnClickListener ok = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = RecordTitle.getText().toString();
                String finalText = RecordIntext.getText().toString();
              //  text = text.replace(System.getProperty("line.separator"), "+\"\n\"+");
                AlertDialog.Builder builder = new AlertDialog.Builder(WriteRecordActivity.this);
                builder.setTitle("Complete");
                builder.setMessage("Have you completed your diary?");

               // String finalText = text;
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        upload(imageUri, title, finalText);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"Check again plz", Toast.LENGTH_LONG).show();
                    }
                });
                builder.show();

            }
        };
        btn_ok.setOnClickListener(ok);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }


    @Override//시스템 백키 눌렸을 때
        public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("leave without saving?");
        builder.setPositiveButton("Yes", dialogListner);
        builder.setNegativeButton("no! I want to write it more!", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    DialogInterface.OnClickListener dialogListner = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            finish();
        }
    };

    public void check() {//화분 상태, 아이템의 동작을 확인합니다.
        Thread thread = new Thread() {
            public String getState(String path) {//주어진 string에 따른 경로에서 cin의 con 값을 가져옵니다.

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

            @Override
            public void run() {


                String path[] = {"soil", "light", "temp", "Soil_result"}; //temp = temp & humid

                for (int j = 0; j < 4; j++) {

                    System.out.println("j = " + j + "실행");
                    if (j == 0) state.setSoil(Double.parseDouble(getState(path[j])));
                    else if (j == 1)
                        state.setLight(Double.parseDouble(getState(path[j]).substring(1, 3)));
                    else if (j == 2) {
                        state.setTemp(Double.parseDouble(getState(path[j]).substring(0, 3)));
                        state.setHumid(Double.parseDouble(getState(path[j]).substring(5)));
                    } else if (j==3){
                        if(Integer.parseInt(getState(path[j]))>0)
                            {watercounted = 1;
                            System.out.println("You gave water! water is counted.");
                            }
                    }
                    else ;
                }


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                          light = (TextView) findViewById(R.id.record_sun);
                          soil = findViewById(R.id.record_water);
                          temp = findViewById(R.id.record_temp);
                          humid = findViewById(R.id.record_humid);

                        light.setText("" + state.getLight());
                        soil.setText("" + state.getSoil());
                        temp.setText("" + state.getTemp());
                        humid.setText("" + state.getHumid());
                        System.out.println("갱신");
                    }
                });

            }//run
        };
        thread.start();
    }
    public void getImage(){
        CropImage.activity(null).setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1,1).start(WriteRecordActivity.this);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            //크롭 성공시
            if (resultCode == RESULT_OK) {
                ((ImageView) findViewById(R.id.test)).setImageURI(result.getUri());
                imageUri = result.getUri();
                //실패시
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

            }
        }
    }

    public void upload(Uri image,String ti, String in){
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // BytArrayOutputStream을 이용해 Bitmap 인코딩
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 10, byteArrayOutputStream);
        // 인코딩된 ByteStream을 String으로 획득
        byte[] bytes = byteArrayOutputStream.toByteArray();
        String base64Image = Base64.encodeToString(bytes, Base64.NO_WRAP);

        String light = ""+state.light;
        String soil = ""+state.soil;
        String temp = ""+state.temp;
        String humid = ""+state.humid;

        OkHttpClient client = new OkHttpClient().newBuilder()
                .writeTimeout(100, TimeUnit.SECONDS)
                .build();
        MediaType mediaType = MediaType.parse("application/vnd.onem2m-res+json; ty=4");
        RequestBody body = RequestBody.create(mediaType,
                "{\n    \"m2m:cin\": {\n        \"con\": \n        {\n " +
                        " \"id\": \""+"studentA"+"\",\n " +
                        " \"image\": \""+base64Image+"\",\n " +
                        " \"title\" :\""+ti+"\",\n " +
                        "\"intext\" :\""+in+"\",\n " +
                        "\"date\" : \""+date_text+"\",\n " +
                        "\"light\" : \""+light +"\",\n " +
                        "\"cds\" : \""+soil+"\",\n " +
                        "\"temp\" : \""+temp+"\",\n " +
                        "\"humid\" : \""+humid+"\",\n " +
                        "\"water\" : \""+watercounted+"\"\n " +
                        // " \"comments\": \"[]\"\n " +
                        " }\n    }\n}");
        System.out.println(body);
        Request request = new Request.Builder()
                .url("http://203.253.128.161:7579/Mobius/AduFarm/record")
                .method("POST", body)
                .addHeader("Accept", "application/json")
                .addHeader("X-M2M-RI", "12345")
                .addHeader("X-M2M-Origin", "SPLIKNC0doF")
                .addHeader("Content-Type", "application/vnd.onem2m-res+json; ty=4")
                .build();
        byte[] by = new byte[0];
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            by = java.util.Base64.getDecoder().decode(base64Image);
            testt.setImageBitmap(BitmapFactory.decodeByteArray(by,0, by.length));
        }
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
                    Log.i("tag", "글 올리기 응답실패");
                    System.out.println("글 올리기 응답실패공");
                } else {
                    // 응답 성공
                    System.out.println("글 올리기 응답성공");
                    Log.i("tag", "글 올리기 응답 성공");
                    final String responseData = response.body().string();
                    // 서브 스레드 Ui 변경 할 경우 에러
                    // 메인스레드 Ui 설정
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Toast toast = Toast.makeText(WriteRecordActivity.this,"Diary is recorded", Toast.LENGTH_LONG);
                                toast.show();
                                System.out.println("응답" + responseData);
                                finish();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });

    }


}
