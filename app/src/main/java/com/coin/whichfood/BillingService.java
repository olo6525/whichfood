package com.coin.whichfood;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.HeterogeneousExpandableList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.AcknowledgePurchaseResponseListener;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.coin.whichfood.MainActivity.activity;
public class BillingService implements PurchasesUpdatedListener{
    private BillingClient billingClient;
    private List<SkuDetails> skuDetails_list;
    private ConsumeResponseListener consumeResponseListener;
    private AcknowledgePurchaseResponseListener acknowledgePurchaseResponseListener;
    private Context context;
    private FlagClass flagClass;
    private String foodnum;
    private String storenum;
    public BillingService(Context context, int mealListSize, int drinkListsize) {
        this.context = context;
        billingClient = BillingClient.newBuilder(context)
                .setListener(this::onPurchasesUpdated)
                .enablePendingPurchases()
                .build();


        billingClient.startConnection(new BillingClientStateListener() {

            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    // The BillingClient is ready. You can query purchases here.
                    Log.d(TAG, "biilingcount2");
                    getSkuDetailList(mealListSize, drinkListsize);
                }else{
                    Log.d(TAG, "biilingcount2 ?????? ??????");
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
                Log.d(TAG, "biilingcount2 ?????? ??????");
            }
        });

        // ?????? ???????????? ?????????
        consumeResponseListener = new ConsumeResponseListener() {
            @Override
            public void onConsumeResponse(BillingResult billingResult, String purchaseToken) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    Log.d(TAG, "bii????????? ??????????????? ?????????????????????. ????????? ?????? => " + purchaseToken);
                    return;
                } else {
                    Log.d(TAG, "bii?????? ????????? ?????????????????????. ???????????? (" + billingResult.getResponseCode() + "), ?????? ?????? ??????: " + purchaseToken);
                    return;
                }
            }
        };
    }

    @Override
    public void onPurchasesUpdated(@NonNull @NotNull BillingResult billingResult, @Nullable @org.jetbrains.annotations.Nullable List<Purchase> purchases) {

        Log.d(TAG,"biilingcount1");
            // To be implemented in a later section.
            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK
                    && purchases != null) {
                Log.d(TAG, "bii????????? ???????????????, ????????? ????????? ???????????? ?????????");
                for (Purchase purchase : purchases) {
                    Log.e(TAG, "bii?????? ??????????????????: " + purchases);
                    handlePurchase(purchase);

                }
            } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
                // Handle an error caused by a user cancelling the purchase flow.
                deletepartnership();
                billingClient.endConnection();
                Intent intent = new Intent(context,MainActivity.class);
                intent.putExtra("Fail",1);
                context.startActivity(intent);
                Log.d(TAG, "bii????????? ????????????????????? !");

            } else {
                // Handle any other error codes.
                deletepartnership();
                billingClient.endConnection();
                Intent intent = new Intent(context,MainActivity.class);
                intent.putExtra("Fail",1);
                context.startActivity(intent);
                Log.d(TAG, "bii???????????? ????????? ?????? ????????? ????????????????????? !");
            }
        }



        public void getSkuDetailList(int mealListSize, int drinkListsize) {
            Log.d(TAG, "biilingcount3");
            List<String> skuList = new ArrayList<>();
            for(int i = 0 ; i < mealListSize; i++){
                skuList.add("meal"+Integer.toString(i+1));
            }
            for(int i = 0; i< drinkListsize; i++){
                skuList.add("drink"+Integer.toString(i+1));
            }

            SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
            params.setSkusList(skuList).setType(BillingClient.SkuType.SUBS);
            billingClient.querySkuDetailsAsync(params.build(),
                    new SkuDetailsResponseListener() {
                        @Override
                        public void onSkuDetailsResponse(BillingResult billingResult,
                                                         List<SkuDetails> skuDetailsList) {
                            Log.d(TAG, "biilingcount3.1");
                            //????????????
                            if (billingResult.getResponseCode() != BillingClient.BillingResponseCode.OK) {
                                Log.d(TAG, "biilingcount3.2 ????????????");
                                return;
                            }
                            Log.d(TAG, "biilingcount3.3");
                            //??????????????? ???????????? ?????? -
                            if (skuDetailsList == null) {
                                Log.d(TAG,"bii?????? ?????? ???????????? ?????? ");
                                return;
                            }
                            Log.d(TAG, "biilingcount3.4");
                            //??????????????? ??????
                            Log.d(TAG, "bii?????? ?????? ????????? ?????? : " + skuDetailsList.size());

                            //?????????????????? : ?????????????????? ???????????? ????????? ?????????.
                            try {
                                for (SkuDetails skuDetails : skuDetailsList) {
                                    String title = skuDetails.getTitle();
                                    String sku = skuDetails.getSku();
                                    String price = skuDetails.getPrice();
//                                userSkuDetails = skuDetails;

                                    Log.d(TAG,"bii???????????? ?????? ????????? :" + title + ","+sku+", "+price);

                                }
                            } catch (Exception e) {
                                Log.d(TAG, "biiitemerror" + e.toString());
                            }

                            skuDetails_list = skuDetailsList;
                        }

                    });
        }
// An activity reference from which the billing flow will be launched.
//        Activity activity = ...;




    public void purchase(String itemid, String storenumber, Activity activity) {
        foodnum = itemid;
        storenum = storenumber;
        SkuDetails skuDetails = null;
        if(null != skuDetails_list){
            for(int i=0; i<skuDetails_list.size(); i++){
                SkuDetails skuinfo = skuDetails_list.get(i);
                if(skuinfo.getSku().equals(itemid)){
                    skuDetails = skuinfo;
                    break;
                }
            }
            Log.d(TAG,"biilingcount4" + foodnum + storenum);
            // Retrieve a value for "skuDetails" by calling querySkuDetailsAsync().
            BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                    .setSkuDetails(skuDetails)
                    .build();
            Log.d(TAG,"biipurchase state : "+billingClient.launchBillingFlow(activity,flowParams).getResponseCode());
        }


    }
//????????? ????????? ???????????????=============================================================================
//    public void handlePurchase(Purchase purchase) {
//        Log.d(TAG,"biilingcount5");
//        // Purchase retrieved from BillingClient#queryPurchasesAsync or your PurchasesUpdatedListener.
//
//        // Verify the purchase.
//        // Ensure entitlement was not already granted for this purchaseToken.
//        // Grant entitlement to the user.
//
//        ConsumeParams consumeParams =
//                ConsumeParams.newBuilder()
//                        .setPurchaseToken(purchase.getPurchaseToken())
//                        .build();
//
//        ConsumeResponseListener listener = new ConsumeResponseListener() {
//            @Override
//            public void onConsumeResponse(BillingResult billingResult, String purchaseToken) {
//                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
//                    // Handle the success of the consume operation.
//                }
//            }
//        };
//
//        billingClient.consumeAsync(consumeParams, listener);
//    }
//???????????????????????????=====================================================================================

    void handlePurchase(Purchase purchase) {
        Log.d(TAG,"biil????????????0");
        if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
            if (!purchase.isAcknowledged()) {
                AcknowledgePurchaseParams acknowledgePurchaseParams =
                        AcknowledgePurchaseParams.newBuilder()
                                .setPurchaseToken(purchase.getPurchaseToken())
                                .build();

                Log.d(TAG,"biil????????????1");
                acknowledgePurchaseResponseListener = new AcknowledgePurchaseResponseListener() {
                    @Override
                    public void onAcknowledgePurchaseResponse(@NonNull @NotNull BillingResult billingResult) {
                        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                            // Handle the success of the consume operation.
                            Log.d(TAG,"biil????????????2");

                        }
                    }
                };
                billingClient.acknowledgePurchase(acknowledgePurchaseParams, acknowledgePurchaseResponseListener);
            }
        }
        Log.d(TAG,"biil????????????3");
        Log.d(TAG,"biipurchasegogo");
        billingClient.endConnection();
        Intent intent = new Intent(context,MainActivity.class);
        intent.putExtra("Success",1);
        context.startActivity(intent);
    }

    public void deletepartnership(){
        flagClass = (FlagClass)context.getApplicationContext();
        Thread thread = new Thread(){
            @Override
            public void run(){
                try {
                    String parameter = new String();
                    parameter = "purpose=removepartner&userid="+flagClass.getLoginid()+"&storenum="+storenum+"&foodnum="+foodnum;
                    Log.d("TAG", "bii param:" + parameter);
                    URL url = new URL(flagClass.getServers().get(0)+"whichfoodstorelist.php");
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setDoOutput(true);
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.connect();

                    OutputStream outputStream = conn.getOutputStream();
                    outputStream.write(parameter.getBytes("UTF-8"));
                    outputStream.flush();
                    outputStream.close();

                    InputStream inputStream;
                    if(conn.getResponseCode() == conn.HTTP_OK){
                        inputStream = conn.getInputStream();
                    }else{
                        inputStream = conn.getErrorStream();
                    }

                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while((line = bufferedReader.readLine()) != null){
                        sb.append(line);
                    }

                    bufferedReader.close();
                    inputStreamReader.close();
                    inputStream.close();

                    Log.d(TAG,"bii ???????????? ?????? : "+sb.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    
}
