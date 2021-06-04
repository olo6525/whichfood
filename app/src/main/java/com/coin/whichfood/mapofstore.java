package com.coin.whichfood;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.maps.model.MarkerOptions;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.MapView;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;
import com.naver.maps.map.widget.ZoomControlView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.ContentValues.TAG;
import static androidx.activity.result.ActivityResultCallerKt.registerForActivityResult;


public class mapofstore extends FragmentActivity implements OnMapReadyCallback{


    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private static final String[] REQUESTED_PERMISSION = {ACCESS_COARSE_LOCATION,ACCESS_FINE_LOCATION};
    private FusedLocationSource locationSource;
    private MapView mapView;
    private static NaverMap naverMap;
    private Location location;
    double latitude;
    double longitude;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;
    protected LocationManager locationManager;
    private ExecutorService executorService = Executors.newFixedThreadPool(1);




    public boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapofstore);
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.toolbar);




        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment)fm.findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }


        mapFragment.getMapAsync(this);
        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);
//권한 =============================================================================================


        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED ){


        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_COARSE_LOCATION)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("'이거먹자' 위치기반 서비스 이용안내");
            builder.setMessage("위치기반 권한 허용을 하시면 사용자의 현재위치를 기반으로 주변 식당들의 위치를 파악하실 수 있습니다. \n " +
                    "미 허용시 위치기반 서비스를이용하실 수 없습니다.");
            builder.setIcon(android.R.drawable.ic_menu_save);
            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (!hasPermissions(mapofstore.this, REQUESTED_PERMISSION)) {
                        ActivityCompat.requestPermissions(mapofstore.this, REQUESTED_PERMISSION, LOCATION_PERMISSION_REQUEST_CODE);

                    }

                }
            });
            builder.create();
            builder.show();
        } else {
            Toast.makeText(getApplicationContext(),"권한 허용을 하지 않아 위치기반 서비스 이용이 불가능합니다. \n앱 옵션에서 위치기반 권한 허용을 해주시길 바랍니다.",Toast.LENGTH_LONG).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("'이거먹자' 위치기반 서비스 이용안내");
            builder.setMessage("위치기반 권한 허용을 하시면 사용자의 현재위치를 기반으로 주변 식당들의 위치를 파악하실 수 있습니다. \n " +
                    "미 허용시 위치기반 서비스를이용하실 수 없습니다.");
            builder.setIcon(android.R.drawable.ic_menu_save);
            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (!hasPermissions(mapofstore.this, REQUESTED_PERMISSION)) {
                        ActivityCompat.requestPermissions(mapofstore.this, REQUESTED_PERMISSION, LOCATION_PERMISSION_REQUEST_CODE);

                    }

                }
            });
            builder.create();
            builder.show();

        }
//GPS사용 함수 =======================================================================================
        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);

        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        int permissionCheck1;
        int permissionCheck2;
        permissionCheck1 = ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION);
        permissionCheck2 = ContextCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if (!isGPSEnabled && !isNetworkEnabled) {

        } else {

            int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION);
            int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION);


            if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED ||
                    hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {



                if (isNetworkEnabled) {


                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListener);

                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();

                        }
                    }
                }


                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListener);
                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();

                            }
                        }
                    }
                }
            }
        }
        mapFragment.getMapAsync((NaverMap naverMap) -> {
            final FlagClass flag = (FlagClass)getApplication();
            int food1 = flag.getOne_food1();
            int food2 = flag.getOne_food2();
            this.naverMap = naverMap;
            ArrayList<Marker> markers = new ArrayList<Marker>();
            Geocoder geocoder = new Geocoder(this);
            double addlatitude =0;
            double addlongitude =0;
            Log.d("addlist","맵 시작");
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        String postParameters = new String();
                        if(flag.getFindstore() == 1){
                            postParameters = "purpose=findstore&food=" + food1 + "&latitude="+latitude+"&longitude="+longitude;
                        }else if(flag.getFindstore()==2){
                            postParameters = "purpose=findstore&food=" + food2 + "&latitude="+latitude+"&longitude="+longitude;
                        }
                        URL url = new URL("https://uristory.com/whichfoodstorelist.php");
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                        httpURLConnection.setReadTimeout(5000);
                        httpURLConnection.setConnectTimeout(5000);
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.connect();


                        OutputStream outputStream = httpURLConnection.getOutputStream();
                        outputStream.write(postParameters.getBytes("UTF-8"));
                        outputStream.flush();
                        outputStream.close();


                        int responseStatusCode = httpURLConnection.getResponseCode();
                        Log.d(TAG, "POST response code - " + responseStatusCode);

                        InputStream inputStream;
                        if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                            inputStream = httpURLConnection.getInputStream();
                        }
                        else{
                            inputStream = httpURLConnection.getErrorStream();
                        }


                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                        StringBuilder sb = new StringBuilder();
                        String line = null;

                        while((line = bufferedReader.readLine()) != null){
                            sb.append(line);
                        }


                        bufferedReader.close();

                        Log.d("TAG","ㅇㅇ"+sb.toString()+"test");
                        JSONObject jsonObject = new JSONObject(sb.toString());
                        JSONArray jsonArray = jsonObject.getJSONArray("storedb");

                        try {

                            for(int i =0; i < jsonArray.length(); i++) {
                                JSONObject storejson = jsonArray.getJSONObject(i);
                                String storelatitudestring = storejson.getString("latitude");
                                String storelongitudestring = storejson.getString("longitude");
                                double storelatitudedouble = Double.parseDouble(storelatitudestring);
                                double storelongitudedouble = Double.parseDouble(storelongitudestring);
                                Marker marker = new Marker();
                                marker.setPosition(new LatLng(storelatitudedouble,storelongitudedouble));
                                markers.add(marker);
                                markers.get(i).setMap(naverMap);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d("addlist","ㅇㅇ입출력 오류 - 서버에서 주소변환시 에러발생");
                        }

                    } catch (Exception e) {

                        Log.d(TAG, "ㅇㅇInsertData: Error ", e);
                    }
                }
            };
            executorService.execute(runnable);
            executorService.shutdown();


            UiSettings uiSettings = naverMap.getUiSettings();
            uiSettings.setLocationButtonEnabled(true);
            uiSettings.setZoomControlEnabled(true);
            naverMap.setLocationSource(locationSource);
            naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
            CameraPosition cameraPosition = new CameraPosition(new LatLng(latitude, longitude), 16,0, 0);
            naverMap.setCameraPosition(cameraPosition);


        });

    }



    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,  @NonNull int[] grantResults) {
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated()) { // 권한 거부됨
                naverMap.setLocationTrackingMode(LocationTrackingMode.None);
            }
            return;
        }
        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults);
    }

    @Override
    public void onMapReady(@NonNull @NotNull NaverMap naverMap) {


    }

}