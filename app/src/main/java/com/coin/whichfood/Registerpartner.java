package com.coin.whichfood;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static android.content.ContentValues.TAG;

public class Registerpartner extends Activity {

    ImageView storeimage;
    ImageView adpicture1;
    ImageView adpicture2;
    ImageView adpicture3;
    ImageView adpicture4;
    ImageView adpicture5;
    Button register;
    private String storenum = "";
    private ArrayList<String> imagepath = new ArrayList<>();
    private ArrayList<String> imagename = new ArrayList<>();
    private int imagecount=0;
    private String pickitem = "";
    private String pickindex = "";
    private ExecutorService executorService = Executors.newFixedThreadPool(2);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerpartner);
//지도 표출 이미지, 홍보이미지 등록 ============================================================================
        storeimage = (ImageView) findViewById(R.id.storeimage);
        storeimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                startActivityForResult(intent,100);
            }
        });
        adpicture1 = (ImageView)findViewById(R.id.adpicure1);
        adpicture1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                startActivityForResult(intent,1);
            }
        });
        adpicture2 = (ImageView)findViewById(R.id.adpicure2);
        adpicture2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                startActivityForResult(intent,2);
            }
        });
        adpicture3 = (ImageView)findViewById(R.id.adpicure3);
        adpicture3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                startActivityForResult(intent,3);
            }
        });
        adpicture4 = (ImageView)findViewById(R.id.adpicure4);
        adpicture4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                startActivityForResult(intent,4);
            }
        });
        adpicture5 = (ImageView)findViewById(R.id.adpicure5);
        adpicture5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                startActivityForResult(intent,5);
            }
        });
//지도 표출 이미지, 홍보이미지 등록 끝 ============================================================================
//음식 등록 ===================================================================================================
        final FlagClass flagClass = (FlagClass)getApplication();
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.checkoutfood);
        RadioButton pickmeal = (RadioButton)findViewById(R.id.pickmeal);
        RadioButton pickdrink = (RadioButton)findViewById(R.id.pickdrink);
        ArrayList<String>spinnerlist = new ArrayList<>();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                try {
                    if (checkedId == R.id.pickmeal) {
                        Toast.makeText(getApplicationContext(), "식사음식 메뉴 선택을 하십시요.", Toast.LENGTH_SHORT).show();
                        spinnerlist.clear();
                        for (int i = 0; i < flagClass.getOutfoodmealindex().getJSONObject(0).length(); i++) {

                            Log.d(TAG, "메뉴 가져오기 쭉 식사, 길이 :" + flagClass.getOutfoodmealindex().getJSONObject(0).length());
                            spinnerlist.add(flagClass.getOutfoodmealindex().getJSONObject(0).getString(Integer.toString(i+1)));

                        }
                        Spinner spinner = (Spinner)findViewById(R.id.spinner);
                        ArrayAdapter adapter = new ArrayAdapter(Registerpartner.this,android.R.layout.simple_expandable_list_item_1,spinnerlist);
                        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                        spinner.setAdapter(adapter);
                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView parent, View view, int position, long id) {
                                Log.d(TAG,"메뉴 선택된거 보기 : "+position+",,"+spinnerlist.get(position));
                            }

                            @Override
                            public void onNothingSelected(AdapterView parent) {
                                Log.d(TAG,"메뉴 선택된거 보기 : ");
                            }
                        });
                    } else if (checkedId == R.id.pickdrink) {
                        Toast.makeText(getApplicationContext(), "안주음식 메뉴 선택을 하십시요.", Toast.LENGTH_SHORT).show();
                        spinnerlist.clear();
                        for (int i = 0; i < flagClass.getOutfooddrinkindex().getJSONObject(0).length(); i++) {
                            Log.d(TAG, "메뉴 가져오기 쭉 안주, 길이 :" + flagClass.getOutfooddrinkindex().getJSONObject(0).length());
                            spinnerlist.add(flagClass.getOutfooddrinkindex().getJSONObject(0).getString(Integer.toString(i+1)));

                        }
                        Spinner spinner = (Spinner)findViewById(R.id.spinner);
                        ArrayAdapter adapter = new ArrayAdapter(Registerpartner.this,android.R.layout.simple_expandable_list_item_1,spinnerlist);
                        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                        spinner.setAdapter(adapter);
                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView parent, View view, int position, long id) {
                                Log.d(TAG,"메뉴 선택된거 보기 : "+position+",,"+spinnerlist.get(position));
                            }

                            @Override
                            public void onNothingSelected(AdapterView parent) {
                                Log.d(TAG,"메뉴 선택된거 보기 : ");
                            }
                        });
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });



//음식 등록 끝 =================================================================================================


        register = (Button)findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(Registerpartner.this);
                Intent intent1 = new Intent(Registerpartner.this, MainActivity.class);
                dlg.setTitle("제휴 서비스 등록"); //제목
                dlg.setMessage("입력하신 정보로 제휴 서비스 등록을 하시겠습니까?");
                dlg.setIcon(R.drawable.ic_storeimage);
                dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    String lineend = "\r\n";
                                    String twohyphens = "--";
                                    String boundary = "*****";
                                    int bytesRead, bytesAvailable, bufferSize;
                                    byte[] buffer;
                                    int maximagesize = 1*2048*1024;
                                    String postParameters = "purpose=registerpartner";
                                    URL url =  new URL("https://uristory.com/whichfoodstorelist.php");
                                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                                    httpURLConnection.setDoInput(true);
                                    httpURLConnection.setDoOutput(true);
                                    httpURLConnection.setUseCaches(false);
                                    httpURLConnection.setRequestMethod("POST");
                                    httpURLConnection.setRequestProperty("Connection","Keep-Alive");
                                    httpURLConnection.setRequestProperty("ENCTYPE","multipart/form-data");
                                    httpURLConnection.setRequestProperty("Content-Type","multipart/form-data;boundary="+boundary);



                                    DataOutputStream outputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                                    outputStream.writeBytes("\r\n--" + boundary + "\r\n");
                                    outputStream.writeBytes("Content-Disposition: form-data; name=\"purpose\"\r\n\r\nregisterpartner");
                                    outputStream.writeBytes("\r\n--" + boundary + "\r\n");
                                    outputStream.writeBytes("\r\n--" + boundary + "\r\n");
                                    outputStream.writeBytes("Content-Disposition: form-data; name=\"storenum\"\r\n\r\n1234");
                                    outputStream.writeBytes("\r\n--" + boundary + "\r\n");
                                    outputStream.writeBytes("\r\n--" + boundary + "\r\n");
                                    outputStream.writeBytes("Content-Disposition: form-data; name=\"adimagecount\"\r\n\r\n"+imagepath.size());
                                    outputStream.writeBytes("\r\n--" + boundary + "\r\n");
                                    outputStream.writeBytes("\r\n--" + boundary + "\r\n");
                                    outputStream.writeBytes("Content-Disposition: form-data; name=\"foodnum\"\r\n\r\n"+1);
                                    outputStream.writeBytes("\r\n--" + boundary + "\r\n");


                                    if(imagepath.size() > 0){
                                        for( int i = 0 ; i < imagepath.size(); i++){
                                            String index = "";
                                            File imagefile = new File(imagepath.get(i));
                                            FileInputStream fileInputStream = new FileInputStream(imagefile);
                                            DataOutputStream sendimage;
                                            Log.d(TAG,"imagelocationregisterpartner:"+imagepath.size());
                                            index = String.valueOf(i);
                                            sendimage= new DataOutputStream(httpURLConnection.getOutputStream());
                                            sendimage.writeBytes(twohyphens + boundary + lineend);
                                            sendimage.writeBytes("Content-Disposition: form-data; name=\"uploaded_file"+index+"\";filename=\""+ imagepath.get(i) + "\"" + lineend);
                                            sendimage.writeBytes(lineend);
                                            Log.d(TAG,"imagelocationregisterpartner:Content-Disposition: form-data; name=\"uploaded_file"+index+"\";filename=\""+ imagepath.get(i) + "\"" + lineend);
                                            bytesAvailable = fileInputStream.available();
                                            bufferSize = Math.min(bytesAvailable, maximagesize);
                                            buffer = new byte[bufferSize];
                                            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                                            while (bytesRead > 0){
                                                sendimage.write(buffer, 0, bufferSize);
                                                bytesAvailable = fileInputStream.available();
                                                bufferSize = Math.min(bytesAvailable, maximagesize);
                                                bytesRead = fileInputStream.read(buffer,0, bufferSize);
                                            }
                                            sendimage.writeBytes(lineend);
                                            sendimage.writeBytes(twohyphens + boundary + twohyphens + lineend);
                                        }
                                    }

                                    int responseStatusCode = httpURLConnection.getResponseCode();
                                    Log.d(TAG, "POST response code - " + responseStatusCode);


                                    InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream(),"UTF-8");
                                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                                    StringBuilder builder = new StringBuilder();
                                    String sb;

                                    while((sb = bufferedReader.readLine()) != null){
                                        builder.append(sb);
                                    }

                                    String result = builder.toString();
                                    Log.d(TAG,"imagelocation:"+result);
                                    intent1.putExtra("Success",1);
                                    startActivity(intent1);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Log.d(TAG,"imagelocationregisterpartner:eroororo");
                                    intent1.putExtra("Fail",2);
                                    startActivity(intent1);
                                }
                            }
                        };
                        executorService.execute(runnable);
                        executorService.shutdown();

                    }
                });
                dlg.show();

            }

        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 100 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri ImageUri = data.getData();
            imagepath.add(0,getPath(ImageUri));
            imagename.add(0,getName(ImageUri));
            Log.d(TAG,"imagelocation = "+data.getData()+",path:" + imagepath.get(0)+",name:"+imagename.get(0)+"arraycount : "+imagepath.size());
            storeimage.setImageURI(ImageUri);

        }else if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){

            Uri ImageUri = data.getData();
            imagepath.add(1,getPath(ImageUri));
            imagename.add(1,getName(ImageUri));
            Log.d(TAG,"imagelocation = "+data.getData()+",path:" + imagepath.get(1)+",name:"+imagename.get(1)+"arraycount : "+imagepath.size());
            adpicture1.setImageURI(ImageUri);

        }else if(requestCode == 2 && resultCode == RESULT_OK && data != null && data.getData() != null){

            Uri ImageUri = data.getData();
            imagepath.add(2,getPath(ImageUri));
            imagename.add(2,getName(ImageUri));
            Log.d(TAG,"imagelocation = "+data.getData()+",path:" + imagepath.get(2)+",name:"+imagename.get(2)+"arraycount : "+imagepath.size());
            adpicture2.setImageURI(ImageUri);

        }else if(requestCode == 3 && resultCode == RESULT_OK && data != null && data.getData() != null){

            Uri ImageUri = data.getData();
            imagepath.add(3,getPath(ImageUri));
            imagename.add(3,getName(ImageUri));
            Log.d(TAG,"imagelocation = "+data.getData()+",path:" + imagepath.get(3)+",name:"+imagename.get(3)+"arraycount : "+imagepath.size());
            adpicture3.setImageURI(ImageUri);

        }else if(requestCode == 4 && resultCode == RESULT_OK && data != null && data.getData() != null){

            Uri ImageUri = data.getData();
            imagepath.add(4,getPath(ImageUri));
            imagename.add(4,getName(ImageUri));
            Log.d(TAG,"imagelocation = "+data.getData()+",path:" + imagepath.get(4)+",name:"+imagename.get(4)+"arraycount : "+imagepath.size());
            adpicture4.setImageURI(ImageUri);

        }else if(requestCode == 5 && resultCode == RESULT_OK && data != null && data.getData() != null){

            Uri ImageUri = data.getData();
            imagepath.add(5,getPath(ImageUri));
            imagename.add(5,getName(ImageUri));
            Log.d(TAG,"imagelocation = "+data.getData()+",path:" + imagepath.get(5)+",name:"+imagename.get(5)+"arraycount : "+imagepath.size());
            adpicture5.setImageURI(ImageUri);

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

}
