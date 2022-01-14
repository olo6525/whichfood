package com.coin.whichfood;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class SlideViewFlagment extends Fragment {

    // When requested, this adapter returns a DemoObjectFragment,
    // representing an object in the collection.

    private Bitmap adimage;
    ImageView adimages;
    ArrayList<Bitmap> bitmapArrayList = new ArrayList<>();
    ArrayList<String> images = new ArrayList<>();
    TextView storename;
    TextView storeaddress;

    enum TOUCH_MODE {
        NONE,   // 터치 안했을 때
        SINGLE, // 한손가락 터치
        MULTI   //두손가락 터치
    }
    private TOUCH_MODE touchMode;

    private Matrix matrix;      //기존 매트릭스
    private Matrix savedMatrix; //작업 후 이미지에 매핑할 매트릭스

    private PointF startPoint;  //한손가락 터치 이동 포인트

    private PointF midPoint;    //두손가락 터치 시 중신 포인트
    private float oldDistance;  //터치 시 두손가락 사이의 거리

    private double oldDegree = 0; // 두손가락의 각도
    int i = 0; // 두손가락 확대, 축소 플레그
    int doubletabflag = 0; // 더블 텝 플래그
    int touchflage =0 ;
    private Activity activity;

    GestureDetector.OnDoubleTapListener doubleTapListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity) {
            activity = (Activity) context;
        }

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup adview = (ViewGroup) inflater.inflate(R.layout.slidepages, container, false);

//이미치 크기 확대 축소===============================================================

        matrix = new Matrix();
        savedMatrix = new Matrix();
        adimages = (ImageView)adview.findViewById(R.id.adimage);
        adimages.setScaleType(ImageView.ScaleType.MATRIX);
        doubleTapListener = new GestureDetector.OnDoubleTapListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if(doubletabflag == 0){
                    doubletabflag =1;
                    Log.d("TAG", "doubletouch 1");
                }else{
                    doubletabflag =0;
                    Log.d("TAG", "doubletouch 0");
                }

                return true;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                return false;
            }
        };

        final GestureDetector gesture = new GestureDetector(getActivity(), new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return false;
            }
        });
        gesture.setOnDoubleTapListener(doubleTapListener);
        adimages.setOnTouchListener(new View.OnTouchListener (){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(gesture != null){
                    gesture.onTouchEvent(event);
                }
                if(doubletabflag == 1) {
                    Log.d("TAG", "action :" + event.getAction());
                    if (v.equals(adimages)) {
                        int action = event.getAction();
                        switch (action & MotionEvent.ACTION_MASK) {
                            case MotionEvent.ACTION_DOWN:
                                donwSingleEvent(event);
                                Log.d("TAG", "touched : " + event.getPointerCount());
                                break;
                            case MotionEvent.ACTION_MOVE:

                                v.getParent().requestDisallowInterceptTouchEvent(true);
                                if (event.getPointerCount() == 2) {
                                    touchMode = TOUCH_MODE.MULTI;
                                } else if (event.getPointerCount() == 1) {
                                    touchMode = TOUCH_MODE.SINGLE;
                                }
                                if (touchMode == TOUCH_MODE.SINGLE) {
                                    moveSingleEvent(event);
                                    Log.d("TAG", "singlemove :" + event.getPointerCount());
                                } else if (touchMode == TOUCH_MODE.MULTI) {

                                    moveMultiEvent(event);
                                    Log.d("TAG", "multymove :" + event.getPointerCount());
                                } else {
                                    touchflage = 0;
                                }

                                break;

                            case MotionEvent.ACTION_UP:
                            case MotionEvent.ACTION_POINTER_UP:
                                i = 0;
                                touchMode = TOUCH_MODE.NONE;
                                Log.d("TAG", "testtouch");
                                break;
                        }
                    }
                }
                return true;

            }
        });
//이미치 크기 확대 축소  끝===============================================================
        Bundle args = getArguments();
        Log.d(TAG,"adimagepath  : "+args.getStringArrayList("path").get(0));
        images = args.getStringArrayList("path");
        storename = (TextView)adview.findViewById(R.id.storename);
        storeaddress = (TextView)adview.findViewById(R.id.storeaddress);
        Thread runnablthread = new Thread(){
            @Override
            public void run() {
                try{
                    URL url = new URL(images.get(args.getInt("page")));
                    Log.d(TAG,"adimage line1");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    Log.d(TAG,"adimage line2");
                    conn.connect();
                    Log.d(TAG, "adimage line3");
                    InputStream is = conn.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    Log.d(TAG, "adimage" + bitmap);
                    bitmapArrayList.add(bitmap);

                }catch (Exception e){
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


//비트멥 이지미 MATRIX로 하기 위해 화면 크기에 맞게 사이즈 조정 =================================
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        if(args.getInt("page") == 0 && !bitmapArrayList.isEmpty()){
            storename.setText("매장이름 : "+args.getString("storename"));
            storeaddress.setText("매장주소 : "+args.getString("storeaddress"));

            if(bitmapArrayList.get(0).getWidth() > width) {
                float scale = (float)width / (float)bitmapArrayList.get(0).getWidth();
                float fitwidth = (float) width;
                float fitheight = (float)bitmapArrayList.get(0).getHeight()*scale;
                Bitmap fitbitmap = Bitmap.createScaledBitmap(bitmapArrayList.get(0), (int)fitwidth, (int)fitheight, true);
                adimages.setImageBitmap(fitbitmap);
            }else{
                adimages.setImageBitmap(bitmapArrayList.get(0));
            }

            bitmapArrayList.clear();
        }else if (args.getInt("page") == 1 && !bitmapArrayList.isEmpty()){
            storename.setText("매장이름 : "+args.getString("storename"));
            storeaddress.setText("매장주소 : "+args.getString("storeaddress"));

            if(bitmapArrayList.get(0).getWidth() > width) {
                float scale = (float)width / (float)bitmapArrayList.get(0).getWidth();
                float fitwidth = (float) width;
                float fitheight = (float)bitmapArrayList.get(0).getHeight()*scale;
                Bitmap fitbitmap = Bitmap.createScaledBitmap(bitmapArrayList.get(0), (int)fitwidth, (int)fitheight, true);
                adimages.setImageBitmap(fitbitmap);
            }else{
                adimages.setImageBitmap(bitmapArrayList.get(0));
            }

            bitmapArrayList.clear();
        }else if (args.getInt("page") == 2 && !bitmapArrayList.isEmpty()){
            storename.setText("매장이름 : "+args.getString("storename"));
            storeaddress.setText("매장주소 : "+args.getString("storeaddress"));

            if(bitmapArrayList.get(0).getWidth() > width) {
                float scale = (float)width / (float)bitmapArrayList.get(0).getWidth();
                float fitwidth = (float) width;
                float fitheight = (float)bitmapArrayList.get(0).getHeight()*scale;
                Bitmap fitbitmap = Bitmap.createScaledBitmap(bitmapArrayList.get(0), (int)fitwidth, (int)fitheight, true);
                adimages.setImageBitmap(fitbitmap);
            }else{
                adimages.setImageBitmap(bitmapArrayList.get(0));
            }

            bitmapArrayList.clear();
        }else if (args.getInt("page") == 3 && !bitmapArrayList.isEmpty()){
            storename.setText("매장이름 : "+args.getString("storename"));
            storeaddress.setText("매장주소 : "+args.getString("storeaddress"));

            if(bitmapArrayList.get(0).getWidth() > width) {
                float scale = (float)width / (float)bitmapArrayList.get(0).getWidth();
                float fitwidth = (float) width;
                float fitheight = (float)bitmapArrayList.get(0).getHeight()*scale;
                Bitmap fitbitmap = Bitmap.createScaledBitmap(bitmapArrayList.get(0), (int)fitwidth, (int)fitheight, true);
                adimages.setImageBitmap(fitbitmap);
            }else{
                adimages.setImageBitmap(bitmapArrayList.get(0));
            }

            bitmapArrayList.clear();
        }else if (args.getInt("page")== 4 && !bitmapArrayList.isEmpty()){
            storename.setText("매장이름 : "+args.getString("storename"));
            storeaddress.setText("매장주소 : "+args.getString("storeaddress"));

            if(bitmapArrayList.get(0).getWidth() > width) {
                float scale = (float)width / (float)bitmapArrayList.get(0).getWidth();
                float fitwidth = (float) width;
                float fitheight = (float)bitmapArrayList.get(0).getHeight()*scale;
                Bitmap fitbitmap = Bitmap.createScaledBitmap(bitmapArrayList.get(0), (int)fitwidth, (int)fitheight, true);
                adimages.setImageBitmap(fitbitmap);
            }else{
                adimages.setImageBitmap(bitmapArrayList.get(0));
            }

            bitmapArrayList.clear();
        }


        return adview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {



    }

        private PointF getMidPoint(MotionEvent e) {

            float x = (e.getX(0) + e.getX(1)) / 2;
            float y = (e.getY(0) + e.getY(1)) / 2;

            return new PointF(x, y);
        }

        private float getDistance(MotionEvent e) {
            float x = e.getX(0) - e.getX(1);
            float y = e.getY(0) - e.getY(1);
            return (float) Math.sqrt(x * x + y * y);
        }

    private void donwSingleEvent(MotionEvent event) {
        savedMatrix.set(matrix);
        startPoint = new PointF(event.getX(), event.getY());
    }

    private void moveSingleEvent(MotionEvent event) {
        matrix.set(savedMatrix);
        matrix.postTranslate(event.getX() - startPoint.x, event.getY() - startPoint.y);
        adimages.setImageMatrix(matrix);
        Log.d("TAG", "movemove : " +event.getX() +","+event.getY());
    }

    private void downMultiEvent(MotionEvent event) {
        oldDistance = getDistance(event);
        if (oldDistance > 5f) {
            savedMatrix.set(matrix);
            midPoint = getMidPoint(event);
            double radian = Math.atan2(event.getY() - midPoint.y, event.getX() - midPoint.x);
            oldDegree = (radian * 180) / Math.PI;
        }
    }

    private void moveMultiEvent(MotionEvent event) {
        float newDistance = getDistance(event);

        if(i != 1){
            oldDistance = newDistance;
            i = 1;
        }
        if (newDistance > 10f) {
            matrix.set(savedMatrix);
            midPoint = getMidPoint(event);
            float scale = newDistance / oldDistance;
            matrix.postScale(scale, scale, midPoint.x, midPoint.y);

//            double nowRadian = Math.atan2(event.getY() - midPoint.y, event.getX() - midPoint.x);
//            double nowDegress = (nowRadian * 180) / Math.PI;
//            float degree = (float) (nowDegress - oldDegree);
//            matrix.postRotate(degree, midPoint.x, midPoint.y);

            Log.d("TAG", "distance : " + oldDistance +","+ newDistance);



            adimages.setImageMatrix(matrix);

        }
    }

//===========================================================================================================================================
//===========================================================================================================================================
//===========================================================================================================================================

    //===========================================================================================================================================
    //===========================================================================================================================================
    //===========================================================================================================================================
}

