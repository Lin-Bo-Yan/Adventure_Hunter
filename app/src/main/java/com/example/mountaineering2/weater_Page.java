package com.example.mountaineering2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class weater_Page extends AppCompatActivity {
    //https://opendata.cwb.gov.tw/api/v1/rest/datastore/F-D0047-075?locationName=%E5%A4%A7%E9%87%8C%E5%8D%80&Authorization=CWB-E090D0FF-2372-4403-A725-F032945C25C8


    // 建立OkHttpClient
    OkHttpClient client = new OkHttpClient().newBuilder().build();
    //金鑰
    String authorization="Authorization=CWB-E090D0FF-2372-4403-A725-F032945C25C8";
    //縣市區
    String Counties="F-D0047-075";
    //地區
    String area="locationName=%E9%9C%A7%E5%B3%B0%E5%8D%80";
    //locationName=%E9%9C%A7%E5%B3%B0%E5%8D%80 霧峰區
    //locationName=%E5%A4%A7%E9%87%8C%E5%8D%80 大里區
    JSONArray location;
    JSONArray weatherElement;
    TextView Location;
    String locationsName2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weater_page);

        Location=(TextView)findViewById(R.id.address);
        setGET();
    }
    private void setGET(){


/**設置傳送需求*/
        Request request = new Request.Builder()
                .url("https://opendata.cwb.gov.tw/api/v1/rest/datastore/"+Counties+"?"+area+"&"+authorization)
                .build();
        /**設置回傳*/
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                /**如果傳送過程有發生錯誤*/
                e.printStackTrace();
                Log.e("joe", "錯誤訊息：" + e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                /**取得回傳*/
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();
                    Log.v("joe","ok"+myResponse);
                    lat_lng_json(myResponse);
                }

            }
        });


    }

    //解json
    private void lat_lng_json(String json){

        try {
            JSONObject jsonObject=new JSONObject(json);
            JSONObject records=jsonObject.getJSONObject("records");
            JSONArray locations=records.getJSONArray("locations");

            for(int i=0;i<locations.length();i++){
                JSONObject jsonObject1 =locations.getJSONObject(i);

                //市區，台中市
                String locationsName=jsonObject1.getString("locationsName");
                location=jsonObject1.getJSONArray("location");

                for (int ii=0;ii<location.length();ii++){
                    JSONObject jsonObject2=location.getJSONObject(ii);
                    //地區，大里區
                    locationsName2=jsonObject2.getString("locationName");
                    weatherElement =jsonObject2.getJSONArray("weatherElement");
                    //Log.v("joe","JSONArray"+locationsName2);
                }
                for (int iii=0;iii<weatherElement.length();iii++){
                    JSONObject jsonObject3= weatherElement.getJSONObject(iii);



                    String dd=jsonObject3.getString("description");
                    Log.v("joe","dd:"+dd);


                    //weather weather=new weather();
                    //weather.setElementName(jsonObject3.getString("elementName"));
                    //weather.setDescription(jsonObject3.getString("description"));

                    // weatherList.add(weather);
                }

                //www(weatherList);


            }







            Location.setText(locationsName2);
            //Log.v("joe","JSONArray"+locationsName2);
            //Log.v("joe","JSONArray"+locationsName);
        } catch (Exception e) {
            Log.e("joe",e.toString());
        }

    }
}