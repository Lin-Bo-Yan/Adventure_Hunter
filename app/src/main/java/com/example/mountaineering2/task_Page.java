package com.example.mountaineering2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class task_Page extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, OnMapReadyCallback,
        GoogleMap.OnInfoWindowCloseListener,
        GoogleMap.OnPoiClickListener{

//-------------------------------變數------------------------------------------
    public static final int REQUEST_COARSE_LOCATION = 0;
    public static final int REQUEST_FINE_LOCATION = 1;
    public static final int MARKER_Z_INDEX = 150;
    public static final int MAP_ZOOM_LEVEL = 16;
    public static final int POLYLINE_Z_INDEX = 100;
    public static final double END_POI_LAT = 24.181496;//經度
    public static final double END_POI_LOG = 121.281587;//緯度
    //合歡山北峰  24.181496,121.281587
    //七星山 25.1708318,121.5358237
    transient SimpleDateFormat dateFormat = new SimpleDateFormat("mm分ss秒");

    private GoogleMap mMap;
    private Location mLastLocation;
    private SupportMapFragment mMapFragment;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private boolean mIsMapInited = false;
    private Marker mUserMarker;
    private Marker mEndMarker;
    private Circle mUserAccuracyCircle;
    private ImageView mCurrentPositionIV;
    private ImageView mTargetPositionIV;
    private BottomSheetBehavior mBottomSheetBehavior;
    private LinearLayout poiInfoLL;
    private LinearLayout startNaviLL;
    private TextView startNavi;
    private LinearLayout stopNaviLL;
    private TextView stopNavi;
    private TextView time_tv;
    private TextView distance_tv;
    private Polyline mPolyline = null;
    private ArrayList points;
    private LinearLayout finishPageLL;
    private TextView finishPageTarget;
    private TextView finishPageTime;
    private TextView finishPageDistance;
    private Double totalDistance;

    private long startTime;
    private long endTime;
    private long runTime;
    private Handler mTimeHandler = new Handler();

    //---時間取得
    private final Runnable mTimeRunner = new Runnable() {
        @Override
        public void run() {
            runTime = System.currentTimeMillis() - startTime; //現在時間減最初時間
            time_tv.setText("經過時間：00時" + dateFormat.format(runTime));
            mTimeHandler.postDelayed(mTimeRunner, 1000);
        }
    };

    private TextView mountain_name;
    private String url;
    private SharedPreferences sp;

    // 建立OkHttpClient
    OkHttpClient client = new OkHttpClient().newBuilder().build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_page);

        mMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_content_view_map);
        if (mMap == null) {
            mMapFragment.getMapAsync(this);
        }

        poiInfoLL = findViewById(R.id.poi_info_view_ll_content);
        startNaviLL = findViewById(R.id.activity_main_start_navi_ll);
        startNavi = findViewById(R.id.activity_main_start_navi);
        stopNavi = findViewById(R.id.activity_main_stop_navi);
        stopNaviLL = findViewById(R.id.activity_main_stop_navi_ll);
        mCurrentPositionIV = findViewById(R.id.map_content_view_iv_current_position);
        mCurrentPositionIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMap != null && mLastLocation != null) {
                    LatLng current = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                    addUserMarker(current, mLastLocation);
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current, MAP_ZOOM_LEVEL));

                }
            }
        });
        mTargetPositionIV = findViewById(R.id.map_content_view_iv_target_position);
        mTargetPositionIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMap != null && mEndMarker != null) {
                    LatLng current = new LatLng(mEndMarker.getPosition().latitude,
                            mEndMarker.getPosition().longitude);
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current, MAP_ZOOM_LEVEL));

                }
            }
        });

        time_tv = findViewById(R.id.activity_main_time_tv);
        distance_tv = findViewById(R.id.activity_main_distance_tv);

        //開始時間
        startNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNaviLL.setVisibility(View.GONE);
                stopNaviLL.setVisibility(View.VISIBLE);
                doGoogleRouteDrawing(END_POI_LAT, END_POI_LOG);
                startTime = System.currentTimeMillis();
                mTimeHandler.postDelayed(mTimeRunner, 1000);
                totalDistance = (Math.round(getDistance(END_POI_LAT, END_POI_LOG, mLastLocation.getLatitude(), mLastLocation.getLongitude())
                        * 1000) / 1000.0);
            }
        });

        //停止時間
        stopNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                poiInfoLL.setVisibility(View.GONE);
                startNaviLL.setVisibility(View.VISIBLE);
                stopNaviLL.setVisibility(View.GONE);
                clearGoogleRoute();
                endTime = System.currentTimeMillis();
                runTime = endTime - startTime;
                mTimeHandler.removeCallbacks(mTimeRunner);
                time_tv.setText("經過時間：");
            }
        });

        finishPageLL = findViewById(R.id.activity_main_finish_page_ll);
        findViewById(R.id.activity_main_finish_page_back_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishPageLL.setVisibility(View.GONE);
            }
        });

        finishPageTarget = findViewById(R.id.activity_main_finish_page_target);
        finishPageTime = findViewById(R.id.activity_main_finish_page_time);
        finishPageDistance = findViewById(R.id.activity_main_finish_page_distance);

        mountain_name=(TextView)findViewById(R.id.mountain_name);
        //取資料
        sp=getApplicationContext().getSharedPreferences("MyUser", Context.MODE_PRIVATE);
        url=sp.getString("url","");
    }

    private void doGoogleRouteDrawing(double lat, double lng) {
        if (mPolyline != null) {
            mPolyline.remove();
        }
        LatLng origin = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        LatLng dest = new LatLng(lat, lng);

        // Getting URL to the Google Directions API  開通路線規劃
        String url = getDirectionsUrl(origin, dest);
        DownloadTask downloadTask = new DownloadTask();

        // Start downloading json data from Google Directions API
        downloadTask.execute(url);

        if (mMap != null && mLastLocation != null) {
            LatLng current = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            addUserMarker(current, mLastLocation);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()),
                    MAP_ZOOM_LEVEL));
        }
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=driving";
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getResources().getString(R.string.google_maps_key);


        return url;
    }

    private class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {
            String data = "";

            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ParserTask parserTask = new ParserTask();
            parserTask.execute(result);

        }
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();

            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList();
                lineOptions = new PolylineOptions();
                List<HashMap<String, String>> path = result.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);
                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);
                    points.add(position);
                }

                lineOptions.addAll(points);
                lineOptions.width(12);
                lineOptions.color(getResources().getColor(R.color.blue_41));
                lineOptions.geodesic(true);

            }
            // Drawing polyline in the Google Map for the i-th route
            if (lineOptions != null) {
                mPolyline = mMap.addPolyline(lineOptions);
                mPolyline.setZIndex(POLYLINE_Z_INDEX);
            }
        }

    }
    //---------清除谷歌路線
    private void clearGoogleRoute() {
        if (mPolyline != null) {
            mPolyline.remove();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        checkLocationService();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    //----------------檢查位置服務
    private void checkLocationService() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            ensurePermissions();
        } else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("位置服務尚未開啟，請設定");
            dialog.setPositiveButton("前往設定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(myIntent);
                }
            });
            dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                }
            });
            dialog.show();
        }
    }

    //-----------------確保權限 使用者定位
    private void ensurePermissions() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_COARSE_LOCATION);
        } else {
            if (mGoogleApiClient == null) {
                mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
                        .addApi(LocationServices.API)
                        .addApi(Places.PLACE_DETECTION_API)
                        .addApi(Places.GEO_DATA_API)
                        .build();
            }

            getLocationFromGoogle();
        }
    }
    //--------------------從谷歌獲取位置
    private void getLocationFromGoogle() {
        if (mGoogleApiClient != null && !mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }
    }

    //-------------------在信息窗口關閉
    @Override
    public void onInfoWindowClose(Marker marker) {
        collapseBottomSheet();
    }

    //--------------------折疊底頁
    private void collapseBottomSheet() {
        mBottomSheetBehavior.setPeekHeight(0);
    }

    //------------------地圖就緒
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnInfoWindowCloseListener(this);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.setOnPoiClickListener(this);

        LatLng endLocation = new LatLng(END_POI_LAT, END_POI_LOG);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(endLocation, MAP_ZOOM_LEVEL));
        mEndMarker = mMap.addMarker(new MarkerOptions()
                .flat(false)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_terminal_point_red))
                .anchor(0.5f, 0.5f)
                .position(endLocation)
                .zIndex(MARKER_Z_INDEX));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (marker.getPosition().latitude == END_POI_LAT) {
                    poiInfoLL.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });

        //----------------在地圖上設置點擊監聽器
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mLastLocation.setLatitude(latLng.latitude);
                mLastLocation.setLongitude(latLng.longitude);
                showUserPosition();

                if (getDistance(END_POI_LAT, END_POI_LOG, latLng.latitude, latLng.longitude) < 0.1) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(task_Page.this)
                            .setTitle("您已抵達終點")
                            .setPositiveButton("好", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    stopNavi.performClick();
                                    dialog.dismiss();
                                    finishPageTarget.setText("七星山");
                                    finishPageTime.setText(("00時" + dateFormat.format(runTime)));
                                    finishPageDistance.setText(String.valueOf(totalDistance) + "公里");
                                    finishPageLL.setVisibility(View.VISIBLE);
                                }
                            });
                    builder.create().show();
                }
            }
        });
    }

    @Override
    public void onPoiClick(PointOfInterest poi) {
//        Toast.makeText(this, "Clicked: " +
//                        poi.name + "\nPlace ID:" + poi.placeId +
//                        "\nLatitude:" + poi.latLng.latitude +
//                        " Longitude:" + poi.latLng.longitude,
//                Toast.LENGTH_SHORT).show();
    }

    //------------在位置改變 使用者位置更新時候做的事情
    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        showUserPosition();
    }

    //------顯示用戶位置
    private void showUserPosition() {
        if (!mIsMapInited && mMapFragment != null) {
            mIsMapInited = true;
            mMapFragment.getMapAsync(this);
        }
        LatLng current = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        addUserMarker(current, mLastLocation);

        distance_tv.setText("距離目的地：" +
                (Math.round(getDistance(END_POI_LAT, END_POI_LOG, mLastLocation.getLatitude(), mLastLocation.getLongitude())
                        * 1000) / 1000.0) + "公里");
    }

    //--------添加使用戶標記
    private void addUserMarker(LatLng position, Location location) {
        if (mMap == null) {
            return;
        }

        if (mUserMarker == null) {
            mUserMarker = mMap.addMarker(new MarkerOptions()
                    .flat(true)
                    .rotation(location.getBearing())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.location))
                    .anchor(0.5f, 0.5f)
                    .position(position)
                    .zIndex(MARKER_Z_INDEX));

            mUserAccuracyCircle = mMap.addCircle(new CircleOptions()
                    .center(position)
                    .radius(location.getAccuracy() / 2)
                    .strokeColor(ContextCompat.getColor(this, R.color.blue_41))
                    .strokeWidth(5)
                    .zIndex(MARKER_Z_INDEX));
        } else {
            mUserMarker.setPosition(position);
            mUserMarker.setRotation(location.getBearing());
            mUserAccuracyCircle.setCenter(position);
            mUserAccuracyCircle.setRadius(location.getAccuracy() / 2);
        }
    }

    //----------已連接
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_FINE_LOCATION);
        } else {
            if (mLocationRequest == null) {
                mLocationRequest = new LocationRequest();
                mLocationRequest.setInterval(1000);
                mLocationRequest.setFastestInterval(1000);
                mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            }
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

//------獲取距離
    private double getDistance(double routePointALat, double routePointALon, double routePointBLat, double routePointBLon) {

        Location l1 = new Location("One");
        l1.setLatitude(routePointALat);
        l1.setLongitude(routePointALon);

        Location l2 = new Location("Two");
        l2.setLatitude(routePointBLat);
        l2.setLongitude(routePointBLon);

        float distance = l1.distanceTo(l2);

//        if (distance > 1000.0f) {
//            distance = distance / 1000000.0f;
//        }

        distance = distance / 1000.0f;

        return distance;
    }
    //---------------------get方法取得山的經緯度-------------------------------------------------------------------

    private void setGET(){

        /**設置傳送需求*/
        Request request = new Request.Builder()
                .url(url+"/api/groups/")
                .build();
        /**設置回傳*/
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                /**如果傳送過程有發生錯誤*/
                e.printStackTrace();
                Log.v("joe", "Bad== " + e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                /**取得回傳*/
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();
                    //Log.v("joe", "OK==  " + myResponse);

                }else {

                    Log.e("joe",response.body().string());
                }

            }
        });

    }



}