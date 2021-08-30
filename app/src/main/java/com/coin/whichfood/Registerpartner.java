package com.coin.whichfood;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
    ImageButton register;
    EditText storenumedit;
    private String storenum = "";
    private int storenumcheck = 0;
    private int successcheck = 0;
    private ArrayList<String> imagepath = new ArrayList<>();
    private ArrayList<String> imagename = new ArrayList<>();
    private int imagecount=0;
    private String pickfood = "";
    private String pickindex = "";
    private ExecutorService executorService = Executors.newFixedThreadPool(2);
    private BillingService billingService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerpartner);
        final FlagClass flagClass = (FlagClass)getApplication();
        billingService = new BillingService(this,flagClass.getThenumberoffoodoutmeal(), flagClass.getThenumberoffoodoutdrink());
//매장인허가번호 입력===========================================================================================
        storenumedit = (EditText)findViewById(R.id.storenum);



// 매장인허가번호 입력  끝===========================================================================================
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
                    if(!imagepath.isEmpty()) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(intent, 1);
                    }else{
                        Toast.makeText(getApplicationContext(),"지도표출 이미지를 업로드 해주시길 바랍니다.",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            adpicture2 = (ImageView) findViewById(R.id.adpicure2);
            adpicture2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!imagepath.isEmpty()) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(intent, 2);
                    }else{
                        Toast.makeText(getApplicationContext(),"지도표출 이미지를 업로드 해주시길 바랍니다.",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            adpicture3 = (ImageView) findViewById(R.id.adpicure3);
            adpicture3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!imagepath.isEmpty()) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(intent, 3);
                    }else{
                        Toast.makeText(getApplicationContext(),"지도표출 이미지를 업로드 해주시길 바랍니다.",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            adpicture4 = (ImageView) findViewById(R.id.adpicure4);
            adpicture4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!imagepath.isEmpty()) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(intent, 4);
                    }else{
                        Toast.makeText(getApplicationContext(),"지도표출 이미지를 업로드 해주시길 바랍니다.",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            adpicture5 = (ImageView) findViewById(R.id.adpicure5);
            adpicture5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!imagepath.isEmpty()) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(intent, 5);
                    }else{
                        Toast.makeText(getApplicationContext(),"지도표출 이미지를 업로드 해주시길 바랍니다.",Toast.LENGTH_SHORT).show();
                    }
                }
            });

//지도 표출 이미지, 홍보이미지 등록 끝 ============================================================================
//음식 등록 ===================================================================================================
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
                                pickfood = "meal";
                                pickindex = Integer.toString(position+1);
                                Log.d(TAG,"메뉴 선택된거 보기 : "+pickindex+",,"+pickfood);
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
                                pickfood = "drink";
                                pickindex = Integer.toString(position+1);
                                Log.d(TAG,"메뉴 선택된거 보기 : "+pickindex+",,"+pickfood);
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


        register = (ImageButton)findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storenum = storenumedit.getText().toString();
                if (storenum != "" && pickfood != "" && pickindex != "" && !imagepath.isEmpty()) {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(Registerpartner.this);
                    AlertDialog.Builder faildlg = new AlertDialog.Builder(Registerpartner.this);
                    Intent intent1 = new Intent(Registerpartner.this, MainActivity.class);
                    dlg.setTitle("제휴 서비스 등록"); //제목
                    dlg.setMessage("입력하신 정보로 제휴 서비스 등록을 하시겠습니까?");
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
                                        String postParameters = "purpose=registerpartner";
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
                                        outputStream.writeBytes("Content-Disposition: form-data; name=\"purpose\"\r\n\r\nregisterpartner");
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


                                        if (imagepath.size() > 0) {
                                            for (int i = 0; i < imagepath.size(); i++) {
                                                String index = "";
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
                                        if(result.equals("nostorenum")){
                                            storenumcheck = 1;
                                        }else if(result.charAt(result.length()-1) == '7'){
                                            storenumcheck = 0;
                                            Log.d(TAG, "imagelocation:" + result);
                                            intent1.putExtra("Success", 1);
                                            startActivity(intent1);
                                        }else{
                                            successcheck = 1;
                                            faildlg.setTitle("형식 불일치");
                                            faildlg.setMessage("형식에 알맞은 입력값을 기입해주시길 바랍니다.");
                                            faildlg.setIcon(R.drawable.ic_storeimage);
                                            faildlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    finish();
                                                    Intent reregister = new Intent(Registerpartner.this, Registerpartner.class);
                                                    startActivity(reregister);
                                                }
                                            });
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Log.d(TAG, "imagelocationregisterpartner:eroororo");
                                        intent1.putExtra("Fail", 2);
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
                                Toast toast = Toast.makeText(getApplicationContext(),"등록되지 않은 인허가번호 입니다. \n※사업자번호가 아닌 -인허가번호- 를 입력하셔야 합니다. \n 정확한 인허가번호 입력시에도 안될시, 고객센터에 문의해주시길 바랍니다. \n H.P : 010-6525-3883", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER|Gravity.CENTER,0,0);
                                toast.show();
                            }
                            if(successcheck ==1){
                                faildlg.show();
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

            Uri ImageUri = data.getData();
            imagepath.add(0,getPath(ImageUri));
            imagename.add(0,getName(ImageUri));
            Log.d(TAG,"imagelocation = "+data.getData()+imagepath.size());
            storeimage.setImageURI(ImageUri);

        }else if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){

            Uri ImageUri = data.getData();
            imagepath.add(getPath(ImageUri));
            imagename.add(getName(ImageUri));
            Log.d(TAG,"imagelocation = "+data.getData()+imagepath.size());
            adpicture1.setImageURI(ImageUri);

        }else if(requestCode == 2 && resultCode == RESULT_OK && data != null && data.getData() != null){

            Uri ImageUri = data.getData();
            imagepath.add(getPath(ImageUri));
            imagename.add(getName(ImageUri));
            Log.d(TAG,"imagelocation = "+data.getData()+imagepath.size());
            adpicture2.setImageURI(ImageUri);

        }else if(requestCode == 3 && resultCode == RESULT_OK && data != null && data.getData() != null){

            Uri ImageUri = data.getData();
            imagepath.add(getPath(ImageUri));
            imagename.add(getName(ImageUri));
            Log.d(TAG,"imagelocation = "+data.getData()+imagepath.size());
            adpicture3.setImageURI(ImageUri);

        }else if(requestCode == 4 && resultCode == RESULT_OK && data != null && data.getData() != null){

            Uri ImageUri = data.getData();
            imagepath.add(getPath(ImageUri));
            imagename.add(getName(ImageUri));
            Log.d(TAG,"imagelocation = "+data.getData()+imagepath.size());
            adpicture4.setImageURI(ImageUri);

        }else if(requestCode == 5 && resultCode == RESULT_OK && data != null && data.getData() != null){

            Uri ImageUri = data.getData();
            imagepath.add(getPath(ImageUri));
            imagename.add(getName(ImageUri));
            Log.d(TAG,"imagelocation = "+data.getData()+imagepath.size());
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
