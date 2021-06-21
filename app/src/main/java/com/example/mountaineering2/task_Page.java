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
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    public static final double END_POI_LAT = 25.1708318;
    public static final double END_POI_LNG = 121.5358237;

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
    private BottomSheetBehavior mBottomSheetBehavior;
    private LinearLayout poiInfoLL;
    private LinearLayout startNaviLL;
    private TextView startNavi;
    private TextView stopNavi;
    private Polyline mPolyline = null;
    private ArrayList points;

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

        startNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNaviLL.setVisibility(View.GONE);
                stopNavi.setVisibility(View.VISIBLE);
                doGoogleRouteDrawing(END_POI_LAT, END_POI_LNG);
            }
        });

        stopNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                poiInfoLL.setVisibility(View.GONE);
                startNaviLL.setVisibility(View.VISIBLE);
                stopNavi.setVisibility(View.GONE);
                clearGoogleRoute();
            }
        });
    }

    private void doGoogleRouteDrawing(double lat, double lng) {
        if (mPolyline != null) {
            mPolyline.remove();
        }
        LatLng origin = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        LatLng dest = new LatLng(lat, lng);

        // Getting URL to the Google Directions API
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

    private void getLocationFromGoogle() {
        if (mGoogleApiClient != null && !mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onInfoWindowClose(Marker marker) {
        collapseBottomSheet();
    }

    private void collapseBottomSheet() {
        mBottomSheetBehavior.setPeekHeight(0);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnInfoWindowCloseListener(this);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.setOnPoiClickListener(this);

        LatLng endLocation = new LatLng(END_POI_LAT, END_POI_LNG);
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

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mLastLocation.setLatitude(latLng.latitude);
                mLastLocation.setLongitude(latLng.longitude);
                showUserPosition();

                if (getDistance(END_POI_LAT, END_POI_LNG, latLng.latitude, latLng.longitude) > 1 &&
                        getDistance(END_POI_LAT, END_POI_LNG, latLng.latitude, latLng.longitude) < 100) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(task_Page.this)
                            .setTitle("您已抵達終點")
                            .setPositiveButton("好", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    stopNavi.performClick();
                                    dialog.dismiss();
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

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        showUserPosition();
    }

    private void showUserPosition() {
        if (!mIsMapInited && mMapFragment != null) {
            mIsMapInited = true;
            mMapFragment.getMapAsync(this);
        }
        LatLng current = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        addUserMarker(current, mLastLocation);
    }

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

    private double getDistance(double routePointALat, double routePointALon, double routePointBLat, double routePointBLon) {

        Location l1 = new Location("One");
        l1.setLatitude(routePointALat);
        l1.setLongitude(routePointALon);

        Location l2 = new Location("Two");
        l2.setLatitude(routePointBLat);
        l2.setLongitude(routePointBLon);

        float distance = l1.distanceTo(l2);

        if (distance > 1000.0f) {
            distance = distance / 1000000.0f;
        }

        return distance;
    }
}