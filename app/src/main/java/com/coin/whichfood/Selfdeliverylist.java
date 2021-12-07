package com.coin.whichfood;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.util.FusedLocationSource;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Selfdeliverylist extends Activity {

    private int where =0;
    private int kind = 0;
    private int foodnum = 0;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private static final String[] REQUESTED_PERMISSION = {ACCESS_COARSE_LOCATION,ACCESS_FINE_LOCATION};
    private FusedLocationSource locationSource;
    private Location location;
    double latitude;
    double longitude;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;
    protected LocationManager locationManager;
    private FlagClass flagClass;
    private JSONArray jsonArrayselfdeliveryinfo;
    public Griditemselfdelivery griditemselfdelivery;

    Lodingclass lodingclass;
    Thread thread;



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
        setContentView(R.layout.activity_selfdeliverylist);
        Intent getfoodinfo = getIntent();
        where = getfoodinfo.getIntExtra("where",0);
        kind = getfoodinfo.getIntExtra("kind",0);
        foodnum = getfoodinfo.getIntExtra("foodnum",0);
        flagClass = (FlagClass) getApplication();
        GridView selfdeliverystore = (GridView)findViewById(R.id.selfdeliverylist);
        Listviewadapter listviewadapter = new Listviewadapter();
        lodingclass = new Lodingclass(this);
        lodingclass.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //GPS위치정보 권한 =====================================================================
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

                    if (!hasPermissions(Selfdeliverylist.this, REQUESTED_PERMISSION)) {
                        ActivityCompat.requestPermissions(Selfdeliverylist.this, REQUESTED_PERMISSION, LOCATION_PERMISSION_REQUEST_CODE);

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

                    if (!hasPermissions(Selfdeliverylist.this, REQUESTED_PERMISSION)) {
                        ActivityCompat.requestPermissions(Selfdeliverylist.this, REQUESTED_PERMISSION, LOCATION_PERMISSION_REQUEST_CODE);

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
//GPS워치정보 권한 및 현재위치정보 갖고오기 끝===============================================================================

        lodingclass.show();
        lodingclass.setCanceledOnTouchOutside(false);
        lodingclass.setCancelable(false);
        Getselfdeliverlist();
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        lodingclass.dismiss();
        try {
            for (int i = 0; i < jsonArrayselfdeliveryinfo.length(); i++) {
                String storename = "";
                String storeaddress = "";
                String foodinfo = "";
                String storenumber = "";
                String storepage = "";
                String distance = "";
                String introduce = "";
                storename = jsonArrayselfdeliveryinfo.getJSONObject(0).getString("storename");
                storeaddress = jsonArrayselfdeliveryinfo.getJSONObject(0).getString("storeaddress");
                distance = jsonArrayselfdeliveryinfo.getJSONObject(0).getString("distance").substring(0,4);
                if(kind == 1) {
                    foodinfo = jsonArrayselfdeliveryinfo.getJSONObject(0).getString("meal" + foodnum);
                }else if(kind == 2){
                    foodinfo = jsonArrayselfdeliveryinfo.getJSONObject(0).getString("drink" + foodnum);
                }else{

                }
                storenumber = foodinfo.split(",")[0];
                storepage = foodinfo.split(",")[1];
                introduce = foodinfo.split(",")[2];
                Log.d(TAG, "selfdeliverystoregrid : " + storename+storenumber+storepage);
                listviewadapter.addItem(new Griditemselfdelivery(storename,storeaddress, storenumber, storepage,distance,introduce));
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.d(TAG, "selfdeliverystoregriderrrr : ");
        }

        selfdeliverystore.setAdapter(listviewadapter);

        selfdeliverystore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                  Griditemselfdelivery items = (Griditemselfdelivery)listviewadapter.getItem(position);

                PopupMenu popupMenu = new PopupMenu(getApplicationContext(),view);
                getMenuInflater().inflate(R.menu.delivery_info,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.calltostore:
                                startActivity(new Intent("android.intent.action.DIAL", Uri.parse("tel:"+items.getStorenumber())));
                                break;
                            case R.id.gotostorepage:
                                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(items.getStorepage())));
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

    }

    class Listviewadapter extends BaseAdapter{
        ArrayList<Griditemselfdelivery> items= new ArrayList<Griditemselfdelivery>();

        public void addItem(Griditemselfdelivery item){ items.add(item);}

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Gridsingleviewselfdelivery view = null;
            if(convertView == null){
                view = new Gridsingleviewselfdelivery(getApplicationContext());
            }else{
                view = (Gridsingleviewselfdelivery) convertView;
            }
            Griditemselfdelivery griditem = items.get(position);
            view.init(getApplicationContext());
            view.setselfdeliveryitem(griditem.storename, griditem.storeaddress, griditem.storenumber, griditem.storepage, griditem.distance, griditem.introduce);
            return view;
        }
    }

    void Getselfdeliverlist(){
        thread = new Thread(){
            @Override
            public void run(){
                String parameterString = "";
                if(kind == 1) {
                    String food = "meal"+foodnum;
                    parameterString= "purpose=getselfdeliverylist&where="+where+"&kind=" + kind + "&foodnum=" + food + "&latitude=" + latitude + "&longitude=" + longitude;
                }else if(kind ==2){
                    String food = "drink"+foodnum;
                    parameterString= "purpose=getselfdeliverylist&where="+where+"&kind=" + kind + "&foodnum=" + food + "&latitude=" + latitude + "&longitude=" + longitude;

                }else{

                }
                Log.d(TAG, "selfdeliverystorestring : " + parameterString);
                try {
                    URL url = new URL(flagClass.getServers().get(0)+"whichfoodstorelist.php");
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setReadTimeout(5000);
                    conn.setConnectTimeout(5000);
                    conn.setRequestMethod("POST");
                    conn.connect();
                    OutputStream outputStream = conn.getOutputStream();
                    outputStream.write(parameterString.getBytes("UTF-8"));
                    outputStream.flush();
                    outputStream.close();

                    int responseStatusCode = conn.getResponseCode();
                    Log.d(TAG, "selfdeliverystoreinfoget : " + responseStatusCode);

                    InputStream inputStream;
                    if(responseStatusCode == conn.HTTP_OK){
                        inputStream = conn.getInputStream();
                    }else{
                        inputStream = conn.getErrorStream();
                    }
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"UTF-8");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    while ((line = bufferedReader.readLine())!=null){
                        sb.append(line);
                    }

                    bufferedReader.close();
                    Log.d("TAG","selfdeliverystoreinfojson : "+sb.toString());
                    JSONObject jsonObject = new JSONObject(sb.toString());

                    jsonArrayselfdeliveryinfo = jsonObject.getJSONArray("getselfdeliverylistinfo");


                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("TAG","selfdeliverystoreinfojson error : ");
                }
            }

        };
        thread.start();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated()) { // 권한 거부됨

            }
            return;
        }
        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults);
    }
}
