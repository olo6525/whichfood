package com.coin.whichfood;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.MapView;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.LocationOverlay;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.FusedLocationSource;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
    private JSONArray jsonArray;
    int mapscope = 1;
    int getmapscope;
    ImageButton enter;

    private AdView mAdView; //?????? ?????? ??????
    private InterstitialAd mInterstitialAd; //????????????




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

        //??????-----------------------------------------------------------------------------------------------------
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView3);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //????????? ????????????-----------------------------------------------------------------------------
        MobileAds.initialize(this,
                "ca-app-pub-8231620186256321~9673217647");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8231620186256321/1155279399");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        //?????? ???---------------------------------------------------------------------------------------

//???????????? ?????? ??????=========================================================================================
        Intent getscope = getIntent();
        mapscope = getscope.getIntExtra("scope",1);
        Spinner spinnerscope = findViewById(R.id.spinnerscope);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.spinnerscope, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerscope.setAdapter(adapter);
        spinnerscope.setSelection(0);
        spinnerscope.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getmapscope = position+1;
                spinnerscope.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        enter = (ImageButton)findViewById(R.id.enter);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapscope = new Intent(mapofstore.this,mapofstore.class);
                mapscope.putExtra("scope",getmapscope);
                finish();
                startActivity(mapscope);
            }
        });
//???????????? ???????????? ??? =====================================================================================
////????????? ??? ?????? ??????\=======================================================================================
//        mapfragment fragment = new mapfragment();
//        getSupportFragmentManager().beginTransaction().add(R.id.maplayout, fragment).commit();
        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment)fm.findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }

        mapFragment.getMapAsync(this);
        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);
//????????? ??? ?????? ????????? =======================================================================================
//?????? =============================================================================================


        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED ){


        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_COARSE_LOCATION)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("'????????????' ???????????? ????????? ????????????");
            builder.setMessage("???????????? ?????? ????????? ????????? ???????????? ??????????????? ???????????? ?????? ???????????? ????????? ???????????? ??? ????????????. \n " +
                    "??? ????????? ???????????? ???????????????????????? ??? ????????????.");
            builder.setIcon(android.R.drawable.ic_menu_save);
            builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
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
            Toast.makeText(getApplicationContext(),"?????? ????????? ?????? ?????? ???????????? ????????? ????????? ??????????????????. \n??? ???????????? ???????????? ?????? ????????? ???????????? ????????????.",Toast.LENGTH_LONG).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("'????????????' ???????????? ????????? ????????????");
            builder.setMessage("???????????? ?????? ????????? ????????? ???????????? ??????????????? ???????????? ?????? ???????????? ????????? ???????????? ??? ????????????. \n " +
                    "??? ????????? ???????????? ???????????????????????? ??? ????????????.");
            builder.setIcon(android.R.drawable.ic_menu_save);
            builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
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
//GPS?????? ?????? =======================================================================================
        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);

        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//        int permissionCheck1;
//        int permissionCheck2;
//        permissionCheck1 = ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION);
//        permissionCheck2 = ContextCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION);
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
            List<Marker> markers = new ArrayList<>();
            LocationOverlay locationOverlay = naverMap.getLocationOverlay();
            Handler handler = new Handler(Looper.getMainLooper());
            Geocoder geocoder = new Geocoder(this);
            double addlatitude =0;
            double addlongitude =0;
            Log.d(TAG,"????????????"+latitude+"??????"+longitude);
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG,"mapscope :"+mapscope);
                    String foodnumber = "";
                    try {
                        String postParameters = new String();
                        if(flag.getKind() == 1) {
                            if (flag.getFindstore() == 1) {
                                postParameters = "purpose=findstore&kind=meal&food=meal" + food1 + "&latitude=" + latitude + "&longitude=" + longitude +"&mapscope="+mapscope;
                                Log.d(TAG, "??????1"+postParameters);
                               foodnumber = "meal"+food1;
                            } else if (flag.getFindstore() == 2) {
                                postParameters = "purpose=findstore&kind=meal&food=meal" + food2 + "&latitude=" + latitude + "&longitude=" + longitude +"&mapscope="+mapscope;
                                Log.d(TAG, "??????"+postParameters);
                                foodnumber = "meal"+food2;
                            }
                        }else if(flag.getKind() ==2){
                            if (flag.getFindstore() == 1) {
                                postParameters = "purpose=findstore&kind=drink&food=drink" + food1 + "&latitude=" + latitude + "&longitude=" + longitude +"&mapscope="+mapscope;
                                Log.d(TAG, "??????"+postParameters);
                                foodnumber = "drink"+food1;
                            } else if (flag.getFindstore() == 2) {
                                postParameters = "purpose=findstore&kind=drink&food=drink" + food2 + "&latitude=" + latitude + "&longitude=" + longitude +"&mapscope="+mapscope;
                                Log.d(TAG, "??????"+postParameters);
                                foodnumber = "drink"+food2;
                            }
                        }
                        URL url = new URL(flag.getServers().get(0)+"whichfoodstorelist.php");
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

                        Log.d("TAG","??????"+sb.toString());
                        jsonArray = new JSONArray(sb.toString());

                    } catch (Exception e) {

                        Log.d(TAG, "??????InsertData: Error ", e);
                    }

                    try {

                        for(int i =0; i < jsonArray.length(); i++) {
                            JSONObject storejson = jsonArray.getJSONObject(i);
                            String storelatitudestring = storejson.getString("latitude");
                            String storelongitudestring = storejson.getString("longitude");
                            String storenum = storejson.getString("storenum");
                            String custom = storejson.getString(foodnumber);
                            String foodnum = foodnumber;
                            double storelatitudedouble = Double.parseDouble(storelatitudestring);
                            double storelongitudedouble = Double.parseDouble(storelongitudestring);
                            Log.d(TAG, "?????? : " + storelatitudestring + "," + storelongitudestring+","+custom);
                            if(custom.charAt(0) == '1') {
                                Marker marker = new Marker();
                                marker.setPosition(new LatLng(storelatitudedouble, storelongitudedouble));
                                marker.setIcon(OverlayImage.fromResource(R.drawable.ic_storeimage));
                                marker.setCaptionText(storejson.getString("storename"));
                                marker.setWidth(75);
                                marker.setHeight(75);
                                marker.setFlat(true);
                                markers.add(marker);
                            }else if(custom.charAt(0)=='2') {
                                Marker marker = new Marker();
                                marker.setPosition(new LatLng(storelatitudedouble, storelongitudedouble));
                                if (storejson.isNull("storeimage")) {
                                    marker.setIcon(OverlayImage.fromResource(R.drawable.ic_storeimage));
                                } else {
                                    String finalFoodnumber = foodnumber;
                                    Thread runnablthread = new Thread() {
                                        @Override
                                        public void run() {
                                            try {
                                                URL url = new URL(flag.getServers().get(0) + "whichfoodadimages/" + storenum + "/" + finalFoodnumber + "/0.jpg");
                                                Log.d(TAG, "adimage line1");
                                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                                conn.setDoInput(true);
                                                Log.d(TAG, "adimage line2");
                                                conn.connect();
                                                Log.d(TAG, "adimage line3");
                                                InputStream is = conn.getInputStream();
                                                Bitmap bitmap = BitmapFactory.decodeStream(is);
                                                Log.d(TAG, "adimage" + bitmap);
                                                marker.setIcon(OverlayImage.fromBitmap(bitmap));
                                                is.close();

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    };
                                    runnablthread.start();
                                    try {
                                        runnablthread.join();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                marker.setCaptionText(storejson.getString("storename"));
                                String finalFoodnumber1 = foodnumber;
                                marker.setOnClickListener(new Overlay.OnClickListener() {
                                    @Override
                                    public boolean onClick(@NonNull @NotNull Overlay overlay) {
                                        Intent adintent = new Intent(mapofstore.this, ShowStoreAd.class);
                                        try {
                                            adintent.putExtra("path", flag.getServers().get(0) + "whichfoodadimages/" + storejson.getString("storenum") + "/" + finalFoodnumber1 + "/");
                                            adintent.putExtra("storename", storejson.getString("storename"));
                                            adintent.putExtra("storeaddress", storejson.getString("storeaddress"));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        startActivity(adintent);
                                        return true;
                                    }
                                });
                                marker.setWidth(130);
                                marker.setHeight(130);
                                marker.setFlat(true);
                                markers.add(marker);
                            }
//                            }else if(custom.charAt(0)=='3'){
//                                Marker marker = new Marker();
//                                marker.setPosition(new LatLng(storelatitudedouble, storelongitudedouble));
//                                if(storejson.isNull("storeimage")){
//                                    marker.setIcon(OverlayImage.fromResource(R.drawable.ic_storeimage));
//                                }else{
//                                    String finalFoodnumber = foodnumber;
//                                    Thread runnablthread = new Thread(){
//                                        @Override
//                                        public void run() {
//                                            try{
//                                                URL url = new URL(flag.getServers().get(0)+"whichfoodadimages/"+storenum+"/"+ finalFoodnumber+"/0.jpg");
//                                                Log.d(TAG,"adimage line1");
//                                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                                                conn.setDoInput(true);
//                                                Log.d(TAG,"adimage line2");
//                                                conn.connect();
//                                                Log.d(TAG, "adimage line3");
//                                                InputStream is = conn.getInputStream();
//                                                Bitmap bitmap = BitmapFactory.decodeStream(is);
//                                                Log.d(TAG, "adimage" + bitmap);
//                                                marker.setIcon(OverlayImage.fromBitmap(bitmap));
//                                                is.close();
//
//                                            }catch (Exception e){
//                                                e.printStackTrace();
//                                            }
//                                        }
//                                    };
//                                    runnablthread.start();
//                                    try {
//                                        runnablthread.join();
//                                    } catch (InterruptedException e) {
//                                        e.printStackTrace();
//                                    }
//
//                                }
//                                marker.setCaptionText(storejson.getString("storename"));
//                                String finalFoodnumber1 = foodnumber;
//                                marker.setOnClickListener(new Overlay.OnClickListener() {
//                                    @Override
//                                    public boolean onClick(@NonNull @NotNull Overlay overlay) {
//                                        Intent adintent = new Intent(mapofstore.this,ShowStoreAdvvip.class);
//                                        try {
//                                            adintent.putExtra("path",flag.getServers().get(0)+"whichfoodadimages/"+storejson.getString("storenum")+"/"+ finalFoodnumber1+"/");
//                                            adintent.putExtra("storename",storejson.getString("storename"));
//                                        } catch (Exception e) {
//                                            e.printStackTrace();
//                                        }
//                                        startActivity(adintent);
//                                        return true;
//                                    }
//                                });
//                                marker.setWidth(130);
//                                marker.setHeight(130);
//                                marker.setFlat(true);
//                                markers.add(marker);
//                            }
                            else{
                                Marker marker = new Marker();
                                marker.setPosition(new LatLng(storelatitudedouble, storelongitudedouble));
                                marker.setIcon(OverlayImage.fromResource(R.drawable.ic_storeimage));
                                marker.setCaptionText(storejson.getString("storename"));
                                marker.setWidth(75);
                                marker.setHeight(75);
                                marker.setFlat(true);
                                markers.add(marker);
                            }
                        }

                            handler.post(() -> {
                                // ?????? ?????????
                                for (Marker marker1 : markers) {
                                    marker1.setMap(naverMap);
                                    Log.d(TAG,"??????????????????");
                                }
                            });


                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("addlist","??????????????? ?????? - ???????????? ??????????????? ????????????");
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
            Log.d(TAG,"mylocation: ??????:"+latitude+",??????:"+longitude);
            naverMap.setCameraPosition(cameraPosition);


        });

    }



    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,  @NonNull int[] grantResults) {
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated()) { // ?????? ?????????
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
