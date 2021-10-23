package com.coin.whichfood;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Registerpartnerchange extends Activity {

    ImageView storeimage;
    ImageView adpicture1;
    ImageView adpicture2;
    ImageView adpicture3;
    ImageView adpicture4;
    ImageView adpicture5;
    ImageButton register;
    EditText storenumedit;
    TextView foodname;
    int imagecheck0 = 10;
    int imagecheck1 = 10;
    int imagecheck2 = 10;
    int imagecheck3 = 10;
    int imagecheck4 = 10;
    int imagecheck5 = 10;
    int presentimagesize=0;
    private String storenum = "";
    private String getstorenum = "";
    private String getfoodnum = "";
    private int storenumcheck = 0;
    private ArrayList<String> imagepath = new ArrayList<>();
    private int imagecount=0;
    private String pickfood = "";
    private String pickindex = "";
    private ExecutorService executorService = Executors.newFixedThreadPool(2);
    ArrayList<Bitmap> registeredimages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerpartnerchange);
        final FlagClass flagClass = (FlagClass)getApplication();
        //첫세팅 매장번호, 음식종류, 음식, 기존의 홍보 사진들=====================================================================================
        storenumedit = (EditText)findViewById(R.id.storenumchange);
        foodname = (TextView)findViewById(R.id.foodnamechange);
        Intent getregisterinfo = getIntent();
        getstorenum = getregisterinfo.getStringExtra("storenum");
        getfoodnum = getregisterinfo.getStringExtra("kindfoodnum");
        storenumedit.setText(getstorenum);
        pickindex = getfoodnum.replaceAll("[^0-9]","");
        foodname.setText(getregisterinfo.getStringExtra("foodname"));
        Log.d(TAG,"registerchange:"+getstorenum+","+getfoodnum);
        Thread thread = new Thread(){
            @Override
            public void run() {
                for (int i = 0; i < 6; i++) {
                    try {
                        URL url = new URL("https://www.uristory.com/whichfoodadimages/"+getstorenum+"/"+getfoodnum+"/"+i+".jpg");
                        Log.d(TAG, "registerchange : adimage line1");
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setDoInput(true);
                        Log.d(TAG, "registerchange : adimage line2");
                        conn.connect();
                        Log.d(TAG, "registerchange : adimage line3");
                        InputStream is = conn.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        Log.d(TAG, "registerchange : adimage" + bitmap);
                        registeredimages.add(bitmap);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d(TAG, "registechange noimage");
                        break;
                    }
                }
                presentimagesize = registeredimages.size();
            }
        };
        thread.start();
        try {
           thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.d(TAG,"registerchange adimage count: "+ registeredimages.size());


        storeimage = (ImageView) findViewById(R.id.storeimagechange);
        adpicture1 = (ImageView)findViewById(R.id.adpicure1change);
        adpicture2 = (ImageView) findViewById(R.id.adpicure2change);
        adpicture3 = (ImageView) findViewById(R.id.adpicure3change);
        adpicture4 = (ImageView) findViewById(R.id.adpicure4change);
        adpicture5 = (ImageView) findViewById(R.id.adpicure5change);
        for(int i = 0; i< presentimagesize; i++){
            if(i == 0){
                imagepath.add(0,"presentimage");
                storeimage.setImageBitmap(registeredimages.get(0));
                imagecheck0 = 2;
            }else if(i == 1){
                imagepath.add(1,"presentimage");
                adpicture1.setImageBitmap(registeredimages.get(1));
                imagecheck1 =2;
            }else if(i==2){
                imagepath.add(2,"presentimage");
                adpicture2.setImageBitmap(registeredimages.get(2));
                imagecheck2 =2;
            }else if(i ==3){
                imagepath.add(3,"presentimage");
                adpicture3.setImageBitmap(registeredimages.get(3));
                imagecheck3 =2;
            }else if(i == 4){
                imagepath.add(4,"presentimage");
                adpicture4.setImageBitmap(registeredimages.get(4));
                imagecheck4 =2;
            }else if(i ==5){
                imagepath.add(5,"presentimage");
                adpicture5.setImageBitmap(registeredimages.get(5));
                imagecheck5 =2;
            }
        }

        //첫세팅 매장번호, 음식종류, 음식, 기존의 홍보 사진들 끝 ===================================================================================
//매장인허가번호 입력===========================================================================================




// 매장인허가번호 입력  끝===========================================================================================
//지도 표출 이미지, 홍보이미지 등록 ============================================================================

        storeimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                startActivityForResult(intent,100);
            }
        });

        adpicture1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imagepath.size() > 0) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(intent, 1);
                }else{
                    Toast.makeText(getApplicationContext(),"지도표출 이미지를 업로드 해주시길 바랍니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        adpicture2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imagepath.size() > 1) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(intent, 2);
                }else{
                    Toast.makeText(getApplicationContext(),"순서대로 이미지를 업로드 해주시길 바립니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        adpicture3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imagepath.size() >2) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(intent, 3);
                }else{
                    Toast.makeText(getApplicationContext(),"순서대로 이미지를 업로드 해주시길 바립니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        adpicture4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imagepath.size() > 3) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(intent, 4);
                }else{
                    Toast.makeText(getApplicationContext(),"순서대로 이미지를 업로드 해주시길 바립니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        adpicture5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imagepath.size() > 4) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(intent, 5);
                }else{
                    Toast.makeText(getApplicationContext(),"순서대로 이미지를 업로드 해주시길 바립니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });


//지도 표출 이미지, 홍보이미지 등록 끝 ============================================================================
//음식 등록 ===================================================================================================
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.checkoutfoodchange);
        RadioButton pickmeal = (RadioButton)findViewById(R.id.pickmealchange);
        RadioButton pickdrink = (RadioButton)findViewById(R.id.pickdrinkchange);

        char checkkind = getfoodnum.charAt(0);
        if(checkkind == 'm'){
            pickfood = "meal";
            Toast.makeText(getApplicationContext(), "식사음식 메뉴 입니다.", Toast.LENGTH_LONG).show();
            pickmeal.setChecked(true);
            pickdrink.setChecked(false);
        }else if(checkkind == 'd'){
            pickfood = "drink";
            Toast.makeText(getApplicationContext(), "안주음식 메뉴 입니다.", Toast.LENGTH_LONG).show();
            pickmeal.setChecked(false);
            pickdrink.setChecked(true);
        }


//음식 등록 끝 =================================================================================================


        register = (ImageButton)findViewById(R.id.registerchange);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] alreadyregisteredid = {""};
                storenum = storenumedit.getText().toString();
                Log.d(TAG,"check conditions : "+ storenum + "," + pickfood + "," + pickindex + "," + imagepath.size());
                if (storenum != "" && pickfood != "" && pickindex != "" && !imagepath.isEmpty()) {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(Registerpartnerchange.this);
                    Intent intent1 = new Intent(Registerpartnerchange.this, Myinfo.class);
                    dlg.setTitle("제휴 및 홍보 서비스 수정"); //제목
                    dlg.setMessage("정확한 정보를 입력하셨습니까? \n '확인' 버튼 클릭 시 입력하신 정보로 수정됩니다.");
                    dlg.setIcon(R.drawable.ic_storeimage);
                    dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Thread runnablthread = new Thread(){
                                @Override
                                public void run() {
                                    try {
                                        String lineend = "\r\n";
                                        String twohyphens = "--";
                                        String boundary = "*****";
                                        int bytesRead, bytesAvailable, bufferSize;
                                        byte[] buffer;
                                        int maximagesize = 1 * 2048 * 1024;
                                        String postParameters = "purpose=registerpartnerchange";
                                        URL url = new URL("https://uristory.com/whichfoodstorelist.php");
                                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                                        httpURLConnection.setDoInput(true);
                                        httpURLConnection.setDoOutput(true);
                                        httpURLConnection.setUseCaches(false);
                                        httpURLConnection.setRequestMethod("POST");
                                        httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
                                        httpURLConnection.setRequestProperty("ENCTYPE", "multipart/form-data");
                                        httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);


                                        DataOutputStream outputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                                        outputStream.writeBytes("\r\n--" + boundary + "\r\n");
                                        outputStream.writeBytes("Content-Disposition: form-data; name=\"purpose\"\r\n\r\nregisterpartnerchange");
                                        outputStream.writeBytes("\r\n--" + boundary + "\r\n");
                                        outputStream.writeBytes("\r\n--" + boundary + "\r\n");
                                        outputStream.writeBytes("Content-Disposition: form-data; name=\"userid\"\r\n\r\n" + flagClass.getLoginid());
                                        outputStream.writeBytes("\r\n--" + boundary + "\r\n");
                                        outputStream.writeBytes("\r\n--" + boundary + "\r\n");
                                        outputStream.writeBytes("Content-Disposition: form-data; name=\"storenum\"\r\n\r\n" + storenum);
                                        outputStream.writeBytes("\r\n--" + boundary + "\r\n");
                                        outputStream.writeBytes("\r\n--" + boundary + "\r\n");
                                        outputStream.writeBytes("Content-Disposition: form-data; name=\"adimagecount\"\r\n\r\n" + imagepath.size());
                                        outputStream.writeBytes("\r\n--" + boundary + "\r\n");
                                        outputStream.writeBytes("\r\n--" + boundary + "\r\n");
                                        outputStream.writeBytes("Content-Disposition: form-data; name=\"foodnum\"\r\n\r\n" + pickfood + pickindex);
                                        outputStream.writeBytes("\r\n--" + boundary + "\r\n");


                                        if (imagepath.size()  > 0) {
                                            for (int i = 0; i < imagepath.size(); i++) {
                                                String index = "";
                                                if(imagepath.get(i).equals("presentimage")){
                                                    outputStream.writeBytes("\r\n--" + boundary + "\r\n");
                                                    outputStream.writeBytes("Content-Disposition: form-data; name=\"presentimagecheck"+i+"\"\r\n\r\n" + 1);
                                                    outputStream.writeBytes("\r\n--" + boundary + "\r\n");
                                                }else{
                                                    outputStream.writeBytes("\r\n--" + boundary + "\r\n");
                                                    outputStream.writeBytes("Content-Disposition: form-data; name=\"presentimagecheck"+i+"\"\r\n\r\n" + 0);
                                                    outputStream.writeBytes("\r\n--" + boundary + "\r\n");
                                                    File imagefile = new File(imagepath.get(i));
                                                    FileInputStream fileInputStream = new FileInputStream(imagefile);
                                                    DataOutputStream sendimage;
                                                    Log.d(TAG, "imagelocationregisterpartner:" + imagepath.size());
                                                    index = String.valueOf(i);
                                                    sendimage = new DataOutputStream(httpURLConnection.getOutputStream());
                                                    sendimage.writeBytes(twohyphens + boundary + lineend);
                                                    sendimage.writeBytes("Content-Disposition: form-data; name=\"uploaded_file" + index + "\";filename=\"" + imagepath.get(i) + "\"" + lineend);
                                                    sendimage.writeBytes(lineend);
                                                    Log.d(TAG, "imagelocationregisterpartner:Content-Disposition: form-data; name=\"uploaded_file" + index + "\";filename=\"" + imagepath.get(i) + "\"" + lineend);
                                                    bytesAvailable = fileInputStream.available();
                                                    bufferSize = Math.min(bytesAvailable, maximagesize);
                                                    buffer = new byte[bufferSize];
                                                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                                                    while (bytesRead > 0) {
                                                        sendimage.write(buffer, 0, bufferSize);
                                                        bytesAvailable = fileInputStream.available();
                                                        bufferSize = Math.min(bytesAvailable, maximagesize);
                                                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                                                    }
                                                    sendimage.writeBytes(lineend);
                                                    sendimage.writeBytes(twohyphens + boundary + twohyphens + lineend);
                                                }

                                            }
                                        }

                                        int responseStatusCode = httpURLConnection.getResponseCode();
                                        Log.d(TAG, "POST response code - " + responseStatusCode);


                                        InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8");
                                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                                        StringBuilder builder = new StringBuilder();
                                        String sb;

                                        while ((sb = bufferedReader.readLine()) != null) {
                                            builder.append(sb);
                                        }

                                        String result = builder.toString();
                                        Log.d(TAG, "registerresult="+result);

                                        if(result.charAt(result.length()-1)=='7'){
                                            storenumcheck = 1;
                                        }



                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Log.d(TAG, "imagelocationregisterpartner:eroororo");
                                        intent1.putExtra("Fail", 1);
                                        startActivity(intent1);
                                    }
                                }
                            };
                            runnablthread.start();
                            try {
                                runnablthread.join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if(storenumcheck == 1){
                                intent1.putExtra("Success", 1);
                                startActivity(intent1);
                            }
                        }

                    });
                    dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dlg.show();

                }else{
                    Toast.makeText(getApplicationContext(),"인허가번호, 음식선택, 홍보이미지 등록을 해주시길 바랍니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 100 && resultCode == RESULT_OK && data != null && data.getData() != null) {

                if(imagecheck0 == 1) {
                    imagepath.remove(0);
                    Uri ImageUri = data.getData();
                    imagepath.add(0, getPath(ImageUri));
                    Log.d(TAG, "imagelocation = " + data.getData() + imagepath.size());
                    storeimage.setImageURI(ImageUri);
                    imagecheck0 = 1;
                }else if(imagecheck0 == 2){
                    imagepath.remove(0);
                    Uri ImageUri = data.getData();
                    imagepath.add(0, getPath(ImageUri));
                    Log.d(TAG, "imagelocation = " + data.getData() + imagepath.size());
                    storeimage.setImageURI(ImageUri);
                    imagecheck0 = 1;
                } else {
                    Uri ImageUri = data.getData();
                    imagepath.add(0, getPath(ImageUri));
                    Log.d(TAG, "imagelocation = " + data.getData() + imagepath.size());
                    storeimage.setImageURI(ImageUri);
                    imagecheck0 = 1;
                }

            }else if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){

                if(imagecheck1 ==1 ){
                    imagepath.remove(1);
                    Uri ImageUri = data.getData();
                    imagepath.add(1,getPath(ImageUri));
                    Log.d(TAG, "imagelocation = " + data.getData() + imagepath.size());
                    adpicture1.setImageURI(ImageUri);
                    imagecheck1 = 1;

                }else if(imagecheck1 == 2){
                    imagepath.remove(1);
                    Uri ImageUri = data.getData();
                    imagepath.add(1,getPath(ImageUri));
                    Log.d(TAG, "imagelocation = " + data.getData() + imagepath.size());
                    adpicture1.setImageURI(ImageUri);
                    imagecheck1 = 1;
                } else {
                    Uri ImageUri = data.getData();
                    imagepath.add(1,getPath(ImageUri));
                    Log.d(TAG, "imagelocation = " + data.getData() + imagepath.size());
                    adpicture1.setImageURI(ImageUri);
                    imagecheck1 = 1;
                }

            }else if(requestCode == 2 && resultCode == RESULT_OK && data != null && data.getData() != null){

                if(imagecheck2 ==1 ){
                    imagepath.remove(2);
                    Uri ImageUri = data.getData();
                    imagepath.add(2,getPath(ImageUri));
                    Log.d(TAG, "imagelocation = " + data.getData() + imagepath.size());
                    adpicture2.setImageURI(ImageUri);
                    imagecheck2 = 1;

                }else if(imagecheck2 == 2){
                    imagepath.remove(2);
                    Uri ImageUri = data.getData();
                    imagepath.add(2,getPath(ImageUri));
                    Log.d(TAG, "imagelocation = " + data.getData() + imagepath.size());
                    adpicture2.setImageURI(ImageUri);
                    imagecheck2 = 1;
                }else {
                    Uri ImageUri = data.getData();
                    imagepath.add(2,getPath(ImageUri));
                    Log.d(TAG, "imagelocation = " + data.getData() + imagepath.size());
                    adpicture2.setImageURI(ImageUri);
                    imagecheck2 = 1;
                }

            }else if(requestCode == 3 && resultCode == RESULT_OK && data != null && data.getData() != null){

                if(imagecheck3 ==1 ){
                    imagepath.remove(3);
                    Uri ImageUri = data.getData();
                    imagepath.add(3,getPath(ImageUri));
                    Log.d(TAG, "imagelocation = " + data.getData() + imagepath.size());
                    adpicture3.setImageURI(ImageUri);
                    imagecheck3 = 1;

                }else if(imagecheck3 == 2){
                    imagepath.remove(3);
                    Uri ImageUri = data.getData();
                    imagepath.add(3,getPath(ImageUri));
                    Log.d(TAG, "imagelocation = " + data.getData() + imagepath.size());
                    adpicture3.setImageURI(ImageUri);
                    imagecheck3 = 1;
                }else {
                    Uri ImageUri = data.getData();
                    imagepath.add(3,getPath(ImageUri));
                    Log.d(TAG, "imagelocation = " + data.getData() + imagepath.size());
                    adpicture3.setImageURI(ImageUri);
                    imagecheck3 = 1;
                }
            }else if(requestCode == 4 && resultCode == RESULT_OK && data != null && data.getData() != null){

                if(imagecheck4 ==1 ){
                    imagepath.remove(4);
                    Uri ImageUri = data.getData();
                    imagepath.add(4,getPath(ImageUri));
                    Log.d(TAG, "imagelocation = " + data.getData() + imagepath.size());
                    adpicture4.setImageURI(ImageUri);
                    imagecheck4 = 1;

                }else if(imagecheck4 == 2){
                    imagepath.remove(4);
                    Uri ImageUri = data.getData();
                    imagepath.add(4,getPath(ImageUri));
                    Log.d(TAG, "imagelocation = " + data.getData() + imagepath.size());
                    adpicture4.setImageURI(ImageUri);
                    imagecheck4 = 1;
                }else {
                    Uri ImageUri = data.getData();
                    imagepath.add(4,getPath(ImageUri));
                    Log.d(TAG, "imagelocation = " + data.getData() + imagepath.size());
                    adpicture4.setImageURI(ImageUri);
                    imagecheck4 = 1;
                }

            }else if(requestCode == 5 && resultCode == RESULT_OK && data != null && data.getData() != null){

                if(imagecheck5 ==1 ){
                    imagepath.remove(5);
                    Uri ImageUri = data.getData();
                    imagepath.add(5,getPath(ImageUri));
                    Log.d(TAG, "imagelocation = " + data.getData() + imagepath.size());
                    adpicture5.setImageURI(ImageUri);
                    imagecheck5 = 1;

                }else if(imagecheck5 == 2){
                    imagepath.remove(5);
                    Uri ImageUri = data.getData();
                    imagepath.add(5,getPath(ImageUri));
                    Log.d(TAG, "imagelocation = " + data.getData() + imagepath.size());
                    adpicture5.setImageURI(ImageUri);
                    imagecheck5 = 1;
                }else {
                    Uri ImageUri = data.getData();
                    imagepath.add(5,getPath(ImageUri));
                    Log.d(TAG, "imagelocation = " + data.getData() + imagepath.size());
                    adpicture5.setImageURI(ImageUri);
                    imagecheck5 = 1;
                }

            }
    }

    // 실제 경로 찾기
    private String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    // 파일명 찾기
    private String getName(Uri uri) {
        String[] projection = {MediaStore.Images.ImageColumns.DISPLAY_NAME};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DISPLAY_NAME);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    // uri 아이디 찾기
    private String getUriId(Uri uri) {
        String[] projection = {MediaStore.Images.ImageColumns._ID};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns._ID);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
