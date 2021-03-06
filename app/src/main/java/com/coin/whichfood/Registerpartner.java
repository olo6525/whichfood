package com.coin.whichfood;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
    int imagecheck0 =10;
    int imagecheck1 = 10;
    int imagecheck2 = 10;
    int imagecheck3 = 10;
    int imagecheck4 = 10;
    int imagecheck5 = 10;
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
    private ArrayList<Bitmap> bitmapresize = new ArrayList<>();
    Intent intent1;
    Intent errorintent;
    Lodingclass lodingclass;
    FlagClass flagClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerpartner);

        //?????? ??????=======================================================================================
        lodingclass = new Lodingclass(this);
        lodingclass.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        errorintent = new Intent(Registerpartner.this,Errorpopup.class);
        intent1 = new Intent(Registerpartner.this, MainActivity.class);
        flagClass = (FlagClass)getApplication();
        billingService = new BillingService(this,flagClass.getThenumberoffoodoutmeal(), flagClass.getThenumberoffoodoutdrink());
//????????????????????? ??????===========================================================================================
        storenumedit = (EditText)findViewById(R.id.storenum);
// ????????????????????? ??????  ???===========================================================================================
//?????? ?????? ?????????, ??????????????? ?????? ============================================================================
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
                    if(imagepath.size() > 0) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(intent, 1);
                    }else{
                        Toast.makeText(getApplicationContext(),"???????????? ???????????? ????????? ???????????? ????????????.",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            adpicture2 = (ImageView) findViewById(R.id.adpicure2);
            adpicture2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(imagepath.size() > 1) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(intent, 2);
                    }else{
                        Toast.makeText(getApplicationContext(),"???????????? ???????????? ????????? ???????????? ????????????.",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            adpicture3 = (ImageView) findViewById(R.id.adpicure3);
            adpicture3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(imagepath.size() >2) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(intent, 3);
                    }else{
                        Toast.makeText(getApplicationContext(),"???????????? ???????????? ????????? ???????????? ????????????.",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        adpicture4 = (ImageView) findViewById(R.id.adpicure4);
        adpicture4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imagepath.size() > 3) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(intent, 4);
                }else{
                    Toast.makeText(getApplicationContext(),"???????????? ???????????? ????????? ???????????? ????????????.",Toast.LENGTH_SHORT).show();
                }
            }
        });
        adpicture5 = (ImageView) findViewById(R.id.adpicure5);
        adpicture5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imagepath.size() > 4) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(intent, 5);
                }else{
                    Toast.makeText(getApplicationContext(),"???????????? ???????????? ????????? ???????????? ????????????.",Toast.LENGTH_SHORT).show();
                }
            }
        });


//?????? ?????? ?????????, ??????????????? ?????? ??? ============================================================================
//?????? ?????? ===================================================================================================
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.checkoutfood);
        RadioButton pickmeal = (RadioButton)findViewById(R.id.pickmeal);
        RadioButton pickdrink = (RadioButton)findViewById(R.id.pickdrink);
        ArrayList<String>spinnerlist = new ArrayList<>();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                try {
                    if (checkedId == R.id.pickmeal) {
                        Toast.makeText(getApplicationContext(), "???????????? ?????? ????????? ????????????.", Toast.LENGTH_SHORT).show();
                        spinnerlist.clear();
                        for (int i = 0; i < flagClass.getOutfoodmealindex().getJSONObject(0).length(); i++) {

                            Log.d(TAG, "?????? ???????????? ??? ??????, ?????? :" + flagClass.getOutfoodmealindex().getJSONObject(0).length());
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
                                Log.d(TAG,"?????? ???????????? ?????? : "+pickindex+",,"+pickfood);
                            }

                            @Override
                            public void onNothingSelected(AdapterView parent) {
                                Log.d(TAG,"?????? ???????????? ?????? : ");
                            }
                        });
                    } else if (checkedId == R.id.pickdrink) {
                        Toast.makeText(getApplicationContext(), "???????????? ?????? ????????? ????????????.", Toast.LENGTH_SHORT).show();
                        spinnerlist.clear();
                        for (int i = 0; i < flagClass.getOutfooddrinkindex().getJSONObject(0).length(); i++) {
                            Log.d(TAG, "?????? ???????????? ??? ??????, ?????? :" + flagClass.getOutfooddrinkindex().getJSONObject(0).length());
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
                                Log.d(TAG,"?????? ???????????? ?????? : "+pickindex+",,"+pickfood);
                            }

                            @Override
                            public void onNothingSelected(AdapterView parent) {
                                Log.d(TAG,"?????? ???????????? ?????? : ");
                            }
                        });
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

//?????? ?????? ??? =================================================================================================


        register = (ImageButton)findViewById(R.id.register);




        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                storenum = storenumedit.getText().toString();
                if (storenum != "" && pickfood != "" && pickindex != "" && !imagepath.isEmpty()) {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(Registerpartner.this);
                    dlg.setTitle("?????? ??? ?????? ????????? ??????"); //??????
                    dlg.setMessage("????????? ????????? ?????????????????????? \n '??????' ?????? ?????? ??? ???????????? ????????? ???????????????.");
                    dlg.setIcon(R.drawable.ic_storeimage);
                    dlg.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d(TAG, "imagelocation1111:" );
                            lodingclass.show();
                            lodingclass.setCanceledOnTouchOutside(false);
                            lodingclass.setCancelable(false);
                            gothread();


                        }

                    });
                    dlg.setNegativeButton("??????", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dlg.show();


                }else{
                    Toast.makeText(getApplicationContext(),"???????????????, ????????????, ??????????????? ????????? ???????????? ????????????.", Toast.LENGTH_LONG).show();
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
                imagecheck0 = 1;
                //????????? ????????? ?????? ??? ??????==========================================
                Bitmap makebitmap = BitmapFactory.decodeFile(imagepath.get(0));
                imagepath.set(0,saveBitmapToJpeg(Registerpartner.this,makebitmap,"resizefile0"));
                Log.d(TAG,"imagelocation tempimagepath : "+ imagepath.get(0));
                storeimage.setImageURI(ImageUri);


            }else if(imagecheck0 == 2){
                imagepath.remove(0);
                Uri ImageUri = data.getData();
                imagepath.add(0, getPath(ImageUri));
                Log.d(TAG, "imagelocation = " + data.getData() + imagepath.size());
                imagecheck0 = 1;

                //????????? ????????? ?????? ??? ??????==========================================
                Bitmap makebitmap = BitmapFactory.decodeFile(imagepath.get(0));
                imagepath.set(0,saveBitmapToJpeg(Registerpartner.this,makebitmap,"resizefile0"));
                Log.d(TAG,"imagelocation tempimagepath : "+ imagepath.get(0));
                storeimage.setImageURI(ImageUri);

            } else {
                Uri ImageUri = data.getData();
                imagepath.add(0, getPath(ImageUri));
                Log.d(TAG, "imagelocation = " + data.getData() + imagepath.size());
                imagecheck0 = 1;

                //????????? ????????? ?????? ??? ??????==========================================
                Bitmap makebitmap = BitmapFactory.decodeFile(imagepath.get(0));
                imagepath.set(0,saveBitmapToJpeg(Registerpartner.this,makebitmap,"resizefile0"));
                Log.d(TAG,"imagelocation tempimagepath : "+ imagepath.get(0));
                storeimage.setImageURI(ImageUri);

            }



        }else if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){

            if(imagecheck1 ==1 ){
                imagepath.remove(1);
                Uri ImageUri = data.getData();
                imagepath.add(1,getPath(ImageUri));
                Log.d(TAG, "imagelocation = " + data.getData() + imagepath.size());
                imagecheck1 = 1;

                //????????? ????????? ?????? ??? ??????==========================================
                Bitmap makebitmap = BitmapFactory.decodeFile(imagepath.get(1));
                imagepath.set(1,saveBitmapToJpeg(Registerpartner.this,makebitmap,"resizefile1"));
                Log.d(TAG,"imagelocation tempimagepath : "+ imagepath.get(1));
                adpicture1.setImageURI(ImageUri);

            }else if(imagecheck1 == 2){
                imagepath.remove(1);
                Uri ImageUri = data.getData();
                imagepath.add(1,getPath(ImageUri));
                Log.d(TAG, "imagelocation = " + data.getData() + imagepath.size());
                imagecheck1 = 1;

                //????????? ????????? ?????? ??? ??????==========================================
                Bitmap makebitmap = BitmapFactory.decodeFile(imagepath.get(1));
                imagepath.set(1,saveBitmapToJpeg(Registerpartner.this,makebitmap,"resizefile1"));
                Log.d(TAG,"imagelocation tempimagepath : "+ imagepath.get(1));
                adpicture1.setImageURI(ImageUri);

            } else {
                Uri ImageUri = data.getData();
                imagepath.add(1,getPath(ImageUri));
                Log.d(TAG, "imagelocation = " + data.getData() + imagepath.size());
                imagecheck1 = 1;

                //????????? ????????? ?????? ??? ??????==========================================
                Bitmap makebitmap = BitmapFactory.decodeFile(imagepath.get(1));
                imagepath.set(1,saveBitmapToJpeg(Registerpartner.this,makebitmap,"resizefile1"));
                Log.d(TAG,"imagelocation tempimagepath : "+ imagepath.get(1));
                adpicture1.setImageURI(ImageUri);

            }



        }else if(requestCode == 2 && resultCode == RESULT_OK && data != null && data.getData() != null){

            if(imagecheck2 ==1 ){
                imagepath.remove(2);
                Uri ImageUri = data.getData();
                imagepath.add(2,getPath(ImageUri));
                Log.d(TAG, "imagelocation = " + data.getData() + imagepath.size());
                imagecheck2 = 1;

                //????????? ????????? ?????? ??? ??????==========================================
                Bitmap makebitmap = BitmapFactory.decodeFile(imagepath.get(2));
                imagepath.set(2,saveBitmapToJpeg(Registerpartner.this,makebitmap,"resizefile2"));
                Log.d(TAG,"imagelocation tempimagepath : "+ imagepath.get(2));
                adpicture2.setImageURI(ImageUri);

            }else if(imagecheck2 == 2){
                imagepath.remove(2);
                Uri ImageUri = data.getData();
                imagepath.add(2,getPath(ImageUri));
                Log.d(TAG, "imagelocation = " + data.getData() + imagepath.size());
                imagecheck2 = 1;

                //????????? ????????? ?????? ??? ??????==========================================
                Bitmap makebitmap = BitmapFactory.decodeFile(imagepath.get(2));
                imagepath.set(2,saveBitmapToJpeg(Registerpartner.this,makebitmap,"resizefile2"));
                Log.d(TAG,"imagelocation tempimagepath : "+ imagepath.get(2));
                adpicture2.setImageURI(ImageUri);

            }else {
                Uri ImageUri = data.getData();
                imagepath.add(2,getPath(ImageUri));
                Log.d(TAG, "imagelocation = " + data.getData() + imagepath.size());
                imagecheck2 = 1;

                //????????? ????????? ?????? ??? ??????==========================================
                Bitmap makebitmap = BitmapFactory.decodeFile(imagepath.get(2));
                imagepath.set(2,saveBitmapToJpeg(Registerpartner.this,makebitmap,"resizefile2"));
                Log.d(TAG,"imagelocation tempimagepath : "+ imagepath.get(2));
                adpicture2.setImageURI(ImageUri);

            }



        }else if(requestCode == 3 && resultCode == RESULT_OK && data != null && data.getData() != null){

            if(imagecheck3 ==1 ){
                imagepath.remove(3);
                Uri ImageUri = data.getData();
                imagepath.add(3,getPath(ImageUri));
                Log.d(TAG, "imagelocation = " + data.getData() + imagepath.size());
                imagecheck3 = 1;

                //????????? ????????? ?????? ??? ??????==========================================
                Bitmap makebitmap = BitmapFactory.decodeFile(imagepath.get(3));
                imagepath.set(3,saveBitmapToJpeg(Registerpartner.this,makebitmap,"resizefile3"));
                Log.d(TAG,"imagelocation tempimagepath : "+ imagepath.get(3));
                adpicture3.setImageURI(ImageUri);


            }else if(imagecheck3 == 2){
                imagepath.remove(3);
                Uri ImageUri = data.getData();
                imagepath.add(3,getPath(ImageUri));
                Log.d(TAG, "imagelocation = " + data.getData() + imagepath.size());
                imagecheck3 = 1;

                //????????? ????????? ?????? ??? ??????==========================================
                Bitmap makebitmap = BitmapFactory.decodeFile(imagepath.get(3));
                imagepath.set(3,saveBitmapToJpeg(Registerpartner.this,makebitmap,"resizefile3"));
                Log.d(TAG,"imagelocation tempimagepath : "+ imagepath.get(3));
                adpicture3.setImageURI(ImageUri);

            }else {
                Uri ImageUri = data.getData();
                imagepath.add(3,getPath(ImageUri));
                Log.d(TAG, "imagelocation = " + data.getData() + imagepath.size());
                imagecheck3 = 1;

                //????????? ????????? ?????? ??? ??????==========================================
                Bitmap makebitmap = BitmapFactory.decodeFile(imagepath.get(3));
                imagepath.set(3,saveBitmapToJpeg(Registerpartner.this,makebitmap,"resizefile3"));
                Log.d(TAG,"imagelocation tempimagepath : "+ imagepath.get(3));
                adpicture3.setImageURI(ImageUri);

            }




        }else if(requestCode == 4 && resultCode == RESULT_OK && data != null && data.getData() != null){

            if(imagecheck4 ==1 ){
                imagepath.remove(4);
                Uri ImageUri = data.getData();
                imagepath.add(4,getPath(ImageUri));
                Log.d(TAG, "imagelocation = " + data.getData() + imagepath.size());
                imagecheck4 = 1;

                //????????? ????????? ?????? ??? ??????==========================================
                Bitmap makebitmap = BitmapFactory.decodeFile(imagepath.get(4));
                imagepath.set(4,saveBitmapToJpeg(Registerpartner.this,makebitmap,"resizefile4"));
                Log.d(TAG,"imagelocation tempimagepath : "+ imagepath.get(4));
                adpicture4.setImageURI(ImageUri);


            }else if(imagecheck4 == 2){
                imagepath.remove(4);
                Uri ImageUri = data.getData();
                imagepath.add(4,getPath(ImageUri));
                Log.d(TAG, "imagelocation = " + data.getData() + imagepath.size());
                imagecheck4 = 1;

                //????????? ????????? ?????? ??? ??????==========================================
                Bitmap makebitmap = BitmapFactory.decodeFile(imagepath.get(4));
                imagepath.set(4,saveBitmapToJpeg(Registerpartner.this,makebitmap,"resizefile4"));
                Log.d(TAG,"imagelocation tempimagepath : "+ imagepath.get(4));
                adpicture4.setImageURI(ImageUri);

            }else {
                Uri ImageUri = data.getData();
                imagepath.add(4,getPath(ImageUri));
                Log.d(TAG, "imagelocation = " + data.getData() + imagepath.size());
                imagecheck4 = 1;

                //????????? ????????? ?????? ??? ??????==========================================
                Bitmap makebitmap = BitmapFactory.decodeFile(imagepath.get(4));
                imagepath.set(4,saveBitmapToJpeg(Registerpartner.this,makebitmap,"resizefile4"));
                Log.d(TAG,"imagelocation tempimagepath : "+ imagepath.get(4));
                adpicture4.setImageURI(ImageUri);

            }



        }else if(requestCode == 5 && resultCode == RESULT_OK && data != null && data.getData() != null){

            if(imagecheck5 ==1 ){
                imagepath.remove(5);
                Uri ImageUri = data.getData();
                imagepath.add(5,getPath(ImageUri));
                Log.d(TAG, "imagelocation = " + data.getData() + imagepath.size());
                imagecheck5 = 1;

                //????????? ????????? ?????? ??? ??????==========================================
                Bitmap makebitmap = BitmapFactory.decodeFile(imagepath.get(5));
                imagepath.set(5,saveBitmapToJpeg(Registerpartner.this,makebitmap,"resizefile5"));
                Log.d(TAG,"imagelocation tempimagepath : "+ imagepath.get(5));
                adpicture5.setImageURI(ImageUri);


            }else if(imagecheck5 == 2){
                imagepath.remove(5);
                Uri ImageUri = data.getData();
                imagepath.add(5,getPath(ImageUri));
                Log.d(TAG, "imagelocation = " + data.getData() + imagepath.size());
                imagecheck5 = 1;

                //????????? ????????? ?????? ??? ??????==========================================
                Bitmap makebitmap = BitmapFactory.decodeFile(imagepath.get(5));
                imagepath.set(5,saveBitmapToJpeg(Registerpartner.this,makebitmap,"resizefile5"));
                Log.d(TAG,"imagelocation tempimagepath : "+ imagepath.get(5));
                adpicture5.setImageURI(ImageUri);

            }else {
                Uri ImageUri = data.getData();
                imagepath.add(5,getPath(ImageUri));
                Log.d(TAG, "imagelocation = " + data.getData() + imagepath.size());
                imagecheck5 = 1;

                //????????? ????????? ?????? ??? ??????==========================================
                Bitmap makebitmap = BitmapFactory.decodeFile(imagepath.get(5));
                imagepath.set(5,saveBitmapToJpeg(Registerpartner.this,makebitmap,"resizefile5"));
                Log.d(TAG,"imagelocation tempimagepath : "+ imagepath.get(5));
                adpicture5.setImageURI(ImageUri);

            }

        }

    }

    // ?????? ?????? ??????
    private String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    // ????????? ??????
    private String getName(Uri uri) {
        String[] projection = {MediaStore.Images.ImageColumns.DISPLAY_NAME};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DISPLAY_NAME);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    // uri ????????? ??????
    private String getUriId(Uri uri) {
        String[] projection = {MediaStore.Images.ImageColumns._ID};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns._ID);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public static String saveBitmapToJpeg(Context context, Bitmap bitmap, String name){
        int maximagesize = 50 * 10000;
        int realimagesize = maximagesize;
        int quality = 100;

        File storage = context.getCacheDir(); // ??? ????????? ???????????? ?????? ??????

        String fileName = name + ".jpg";  // ??????????????? ????????????!

        File tempFile = new File(storage,fileName);

        try{
            tempFile.createNewFile();  // ????????? ???????????????



            while(realimagesize >= maximagesize) {
                if(quality < 0){
                    return "tobig";
                }
                FileOutputStream out = new FileOutputStream(tempFile);
                Log.d(TAG,"imagelocation resizefilesize before : " + realimagesize);
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);  // ?????? ?????? bitmap??? jpeg(????????????)?????? ????????????
                realimagesize = (int)tempFile.length();
                Log.d(TAG,"imagelocation resizefilesize after : " + realimagesize);
                quality -= 19;

                out.close(); // ???????????? ???????????????.

            }

            Log.d(TAG,"imagelocation resizefilesize result: " + realimagesize);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tempFile.getAbsolutePath();   // ???????????? ??????????????? ??????????????? ???!
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    void gothread(){

        //?????? ??? ?????? ?????? ????????? ========================================================
        Thread runnablthread = new Thread(){
            @Override
            public void run() {
                try {
                    final String[] alreadyregisteredid = {""};
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
                    Log.d(TAG, "imagelocation1111:" );

                    if (imagepath.size() > 0) {
                        for (int i = 0; i < imagepath.size(); i++) {
                            if (imagepath.get(i).equals("tobig")) {
                                outputStream.writeBytes("\r\n--" + boundary + "\r\n");
                                outputStream.writeBytes("Content-Disposition: form-data; name=\"presentimagecheck" + i + "\"\r\n\r\n" + 2);
                                outputStream.writeBytes("\r\n--" + boundary + "\r\n");
                            } else {
                                outputStream.writeBytes("\r\n--" + boundary + "\r\n");
                                outputStream.writeBytes("Content-Disposition: form-data; name=\"presentimagecheck" + i + "\"\r\n\r\n" + 0);
                                outputStream.writeBytes("\r\n--" + boundary + "\r\n");
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
                                Log.d(TAG, "imagelocationgogo 1");
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
                    Log.d(TAG, "imagelocationgogo 2");
                    int responseStatusCode = httpURLConnection.getResponseCode();
                    Log.d(TAG, "imagelocationgogo POST response code1 - " + responseStatusCode);


                    InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder builder = new StringBuilder();
                    String sb;

                    while ((sb = bufferedReader.readLine()) != null) {
                        builder.append(sb);
                    }

                    inputStreamReader.close();
                    outputStream.close();

                    String result = builder.toString();
                    Log.d(TAG, "registerresult="+result);
                    if(result.equals("nostorenum")){
                        storenumcheck = 1;
                        Log.d(TAG, "imagelocation1111:" + result);
                    }else if(result.charAt(0)=='I'){
                        alreadyregisteredid[0] = result;
                        storenumcheck = 2;
                        Log.d(TAG, "imagelocation1111:" + result);
                    } else if (result.equals("nomember")) {
                        storenumcheck = 3;
                        Log.d(TAG, "imagelocation1111:" + result);
                    } else if (result.charAt(result.length() - 1) == '7') {
                        lodingclass.cancel();
                        storenumcheck = 0;
                        billingService.purchase(pickfood + pickindex, storenum,Registerpartner.this);
                        Log.d(TAG, "imagelocation1111:" + result);
                    } else if (result.charAt(result.length() -1) == '4') {
                        storenumcheck = 4;
                    } else {
                        successcheck = 1;
                    }

                    lodingclass.setCancelable(true);

                    if(storenumcheck == 1){
                        lodingclass.cancel();
                        errorintent.putExtra("errormassage","???????????? ?????? ??????????????? ?????????. \n????????????????????? ?????? -???????????????- ??? ??????????????? ?????????. \n ????????? ??????????????? ??????????????? ?????????, \n??????????????? ?????????????????? ????????????. \n???????????? : H.P : 010-6525-3883");
                        startActivity(errorintent);
                        Log.d(TAG,"imagelocation register error ????????? ?????? ??????");
                    }else if(storenumcheck == 2){
                        lodingclass.cancel();
                        errorintent.putExtra("errormassage","?????? ?????? ????????? ??????\n"+alreadyregisteredid[0]+"????????????\n ?????? ????????? ??? ?????? ?????????. \n???????????? : H.P : 010-6525-3883");
                        startActivity(errorintent);
                        Log.d(TAG,"imagelocation register error ?????? ????????? ?????? ??????");
                    }else if(storenumcheck == 3){
                        lodingclass.cancel();
                        errorintent.putExtra("errormassage","???????????? ?????? ?????????(?????????) ?????????. \n???????????? : H.P : 010-6525-3883");
                        startActivity(errorintent);
                        Log.d(TAG,"imagelocation register error ???????????? ?????? ?????????(?????????)");
                    }else if(storenumcheck == 4){
                        lodingclass.cancel();
                        errorintent.putExtra("errormassage","500KB ????????? ???????????? \n????????? ???????????? ????????????.");
                        startActivity(errorintent);
                        Log.d(TAG,"imagelocation register error ?????? 500KB??????");
                    }else if(storenumcheck == 0){

                    }else{
                        lodingclass.cancel();
                        errorintent.putExtra("errormassage","????????? ?????? ?????????.\n???????????? : H.P : 010-6525-3883");
                        startActivity(errorintent);
                        Log.d(TAG,"imagelocation register error ???????????? ????????? ??????");
                    }
                    if(successcheck ==1){
                        lodingclass.cancel();
                        errorintent.putExtra("errormassage","????????? ????????? ????????? ????????? ????????????. \n???????????? : H.P : 010-6525-3883");
                        startActivity(errorintent);
                        Log.d(TAG,"imagelocation register error ?????? ?????????");
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
        //?????? ??? ?????? ?????? ????????? ========================================================
    }

}
