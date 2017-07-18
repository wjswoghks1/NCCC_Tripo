package com.exam.administrator.nccc_trip;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class ScheduleActivity extends AppCompatActivity {
    int year;
    int month;
    int day;
    int toopleLength;
    int searchI = 0;

    ArrayList<TourItem> items;
    String ccyear;
    String ccmonth;
    String ccday;
    String getTitle;
    String id_client;
    String name;
    String adress;
    String imgUrl;
    int contId;
    Bitmap bmimg;

    Context mcontext;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList getTTitle = new ArrayList();

    String displayUrl = "http://222.116.135.79:8080/nccc_t/displaySchedule.jsp";
    String tourApiKey = "P6bhFFBWwGkij2sSFyuE1fYOhmljx2J0qqEjWC65a0BMXkdVEYQo44MRq0yZK7Txgqbp9GbSWfexAXQhBEwtLg%3D%3D";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_recycler_view);
        mcontext = getApplicationContext();

        id_client = getDeviceId();
        year = getIntent().getIntExtra("year", 0);
        month = getIntent().getIntExtra("month", 0);
        day = getIntent().getIntExtra("day", 0);


        ccyear = String.valueOf(year);
        ccmonth = String.valueOf(month);
        ccday = String.valueOf(day);


        recyclerView = (RecyclerView) findViewById(R.id.calendar_recycler_view);
        recyclerView.setHasFixedSize(true);
        items = new ArrayList();
        layoutManager = new LinearLayoutManager(this);
        adapter = new CalendarAdapter(items, mcontext);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);



        final Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        try {

                            getTitle = (String) msg.obj;
                            //String fdfd1 = insertScehdule.execute(id_client, title, "2017", "07", "13", "06").get();
                            Log.e("&&&&1234", getTitle);
                            Log.i("****222*****","****222****"+getTTitle.size());


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2:
                        try{

                        }
                        catch (Exception ee){
                            ee.printStackTrace();
                        }
                }


            }
        };
        Log.i("****222*****","****222****"+getTTitle.size());



        Thread displayThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String sendMsg;
                    URL url = new URL(displayUrl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setRequestMethod("POST");
                    OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                    sendMsg = "id_client=" + id_client + "&year=" + ccyear + "&month=" + ccmonth + "&day=" + ccday;  //

                    osw.write(sendMsg);
                    osw.flush();
                    Log.e("####i1", "dddddddddd");
                    try {
                        Log.e("####i2", "dddddddddd");
                        if (conn.getResponseCode() >= 200 || conn.getResponseCode() < 300) {
                            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                            StringBuffer buf = new StringBuffer();
                            String str= "";
                            Log.e("#####123", ""+buf.toString());
                            while ((str = reader.readLine()) != null) {
                                buf.append(str);
                            }


                            try {
                                Log.e("####i3", "dddddddddd");
                                JSONObject json = new JSONObject(buf.toString());
                                JSONArray jj = json.getJSONArray("List");

                                Log.e("####i4", "dddddddddd");
                                String displayTitle;

                                for (int i = 0; i < jj.length(); i++) {
                                    Log.e("####i5", "dddddddddd");
                                    JSONObject displayJson = jj.getJSONObject(i);
                                    displayTitle = displayJson.getString("title");

                                    getTTitle.add(displayTitle);
                                    Log.e("^^^", ""+getTTitle);
                                    searchI = getTTitle.size();

                                }
                                try {
                                    for(int ii=0; ii < searchI; ii++) {
                                        Log.i(TAG, "ii13");
                                        url = new URL("http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList?ServiceKey=" + tourApiKey + "&contentTypeId=12&areaCode=33&sigunguCode=&cat1=&cat2=&cat3=&listYN=Y&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&arrange=A&numOfRows=634&pageNo=1&_type=json");
                                        conn = (HttpURLConnection) url.openConnection();
                                        conn.setDefaultUseCaches(false);
                                        conn.setDoInput(true);
                                        conn.setDoOutput(false);
                                        conn.setRequestMethod("GET");
                                        Log.i(TAG, "ii14");
                                        if (conn != null) {
                                            Log.i(TAG, "ii15");
                                            conn.setConnectTimeout(10000);
                                            conn.setUseCaches(false);
                                            Log.i(TAG, "ii16");
                                            if (conn.getResponseCode() >= 200 || conn.getResponseCode() < 300) { //접속 잘 되었는지 안되었는지 파악
                                                Log.i(TAG, "ii1");
                                                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8")); // InputStreamReader로 가져온다음 Buffer에 넣으면 이상하게 에러가 남, 그냥 바로 넣을것.
                                                Log.i(TAG, "ii2");
                                                for (; ; ) {
                                                    String buf1 = "";
                                                    buf1 = br.readLine();
                                                    StringBuffer sb = new StringBuffer();
                                                    sb.append(buf1);
                                                    if (buf1 == null) {
                                                        Log.i(TAG, "break");
                                                        break;
                                                    }
                                                    JSONObject result = new JSONObject(buf1);
                                                    JSONArray results = result.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONArray("item"); //가장 큰 테두리부터 갈라갈라 가져오기
                                                    Log.i(TAG, "ii4");
                                                    Log.e("^^^^^", ""+buf1);

                                                    for (int i = 0; i < results.length(); i++) {
                                                        JSONObject json1 = results.getJSONObject(i);
                                                        name = json1.getString("title");
                                                        Log.e("&&&&", ""+name);

                                                        if (getTTitle.get(ii).equals(name)) {
                                                            Log.i("%%%%%%", "ii6"+name);
                                                            adress = json1.getString("addr1");
                                                            contId = json1.getInt("contentid");
                                                            try {
                                                                Log.i("%%%%%%", "ii7"+name);
                                                                imgUrl = json1.getString("firstimage");
                                                                URL imgurl = new URL(imgUrl);
                                                                HttpURLConnection imgConn = (HttpURLConnection) imgurl.openConnection();
                                                                imgConn.setDoInput(true);
                                                                imgConn.connect();
                                                                InputStream is = imgConn.getInputStream();
                                                                bmimg = BitmapFactory.decodeStream(is);
                                                                Log.i("%%%%%%", "ii8"+name);
                                                                items.add(new TourItem(name, adress, bmimg, contId));
                                                                imgConn.disconnect();

                                                            }
                                                            catch (Exception ee){
                                                                Log.i("%%%%%%", "ii9"+name);
                                                                BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(R.drawable.default_img);
                                                                bmimg = drawable.getBitmap();
                                                                Log.i("%%%%%%", "ii10"+name);
                                                                items.add(new TourItem(name, adress, bmimg, contId));

                                                            }

                                                          }

                                                    }


                                                }
                                                br.close();
                                            }
                                            conn.disconnect();
                                        }
                                    }

                                }
                                catch(Exception e){
                                    Log.e(TAG, "ii7");
                                    e.printStackTrace();
                                    Log.w(TAG, e.getMessage());
                                }


                            } catch (Exception e) {
                                Log.e("####i6", "dddddddddd");
                                e.printStackTrace();
                            }
                            reader.close();

                        }
                        conn.disconnect();

                        Log.e("####i10", "dddddddddd");
                    }
                    catch (Exception exxx) {
                        Log.e("##i11", "dddddddddd");
                        exxx.printStackTrace();
                    }
                } catch (Exception e) {
                    Log.e("##i12", "dddddddddd");
                }

            }
        });


        displayThread.start();

        try {
            displayThread.join();
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }

    public String getDeviceId(){
        String idByANDROID_ID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        return idByANDROID_ID;
    }



}
