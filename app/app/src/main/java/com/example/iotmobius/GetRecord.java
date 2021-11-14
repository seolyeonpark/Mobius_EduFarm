package com.example.iotmobius;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetRecord {
    ArrayList<Record> records = new ArrayList<>();
    public ArrayList<Record> get(){
        getRecord thread = new getRecord();
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return records;
    }

    private class getRecord extends Thread{
        public void run(){
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

            JSONObject o1 = null;
            try {
                o1 = obj.getJSONObject("m2m:rsp");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONArray a1 = null;
            try {
                a1 = o1.getJSONArray("m2m:cin");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < a1.length(); i++) {

                Record curRecord = new Record();
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

                try {
                    String idd = con.getString("id");
                    curRecord.setId(idd);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    curRecord.setImage(con.getString("image"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    curRecord.setTitle(con.getString("title"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    curRecord.setIntext(con.getString("intext"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    curRecord.setDate(con.getString("date"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                com.example.iotmobius.State curState = new com.example.iotmobius.State();
                try {
                    curState = new com.example.iotmobius.State(
                            Double.parseDouble(con.getString("light")),
                            Double.parseDouble(con.getString("cds")),
                            Double.parseDouble(con.getString("temp")),
                            Double.parseDouble(con.getString("humid")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                curRecord.setState(curState);
               // try {
                    //curRecord.setComments(con.getString("comments"));
              /*  } catch (JSONException e) {
                    e.printStackTrace();
                }*/

               OkHttpClient client2 = new OkHttpClient().newBuilder().build();
                Request request2 = new Request.Builder()
                        .url("http://203.253.128.161:7579/Mobius/AduFarm/" +"feedback" + "?fu=2&ty=4&rcn=4")
                        .method("GET", null)
                        .addHeader("Accept", "application/json")
                        .addHeader("X-M2M-RI", "12345")
                        .addHeader("X-M2M-Origin", "SOrigin")
                        .build();
                Response response2 = null;
                try {
                    response2 = client2.newCall(request2).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //String result = response.body().string();
                String con2 = null;
                JSONObject obj2 = null;
                try {
                    obj2 = new JSONObject(response2.body().string());
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                JSONObject o12 = null;
                try {
                    o12 = obj2.getJSONObject("m2m:rsp");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                JSONArray a12 = null;
                try {
                    a12 = o12.getJSONArray("m2m:cin");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    o12 = a12.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
              ;
                try {
                    con2 = o12.getString("con");
                    curRecord.setComments(con2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(con2==null)
                    con2 = "Teacher doesn't ckeck your record yet!";
                curRecord.setComments(con2);

                System.out.printf("recor 어레이 추가!");
                records.add(curRecord);

            }//for 0 to end
        }//run
    }//Thread

}
