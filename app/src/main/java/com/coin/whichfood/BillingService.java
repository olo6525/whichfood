package com.coin.whichfood;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.coin.whichfood.MainActivity.activity;
public class BillingService implements PurchasesUpdatedListener{
    private BillingClient billingClient;
    private List<SkuDetails> skuDetails_list;
    private ConsumeResponseListener consumeResponseListener;
    private Context context;

    public BillingService(Context context) {
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
                    getSkuDetailList();
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
            }
        });

        // 상품 소모결과 리스너
        consumeResponseListener = new ConsumeResponseListener() {
            @Override
            public void onConsumeResponse(BillingResult billingResult, String purchaseToken) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    Log.d(TAG, "상품을 성공적으로 소모하였습니다. 소모된 상품 => " + purchaseToken);
                    return;
                } else {
                    Log.d(TAG, "상품 소모에 실패하였습니다. 오류코드 (" + billingResult.getResponseCode() + "), 대상 상품 코드: " + purchaseToken);
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
                Log.d(TAG, "결제에 성공했으며, 아래에 구매한 상품들이 나열됨");
                for (Purchase purchase : purchases) {
                    Log.e(TAG, "결제 구매완료상품: " + purchases);
                    handlePurchase(purchase);
                }
            } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
                // Handle an error caused by a user cancelling the purchase flow.
            } else {
                // Handle any other error codes.
            }
        }



        public void getSkuDetailList() {
            Log.d(TAG, "biilingcount3");
            List<String> skuList = new ArrayList<>();
            skuList.add("VIP정기구독");
            skuList.add("VVIP정기구독");
            SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
            params.setSkusList(skuList).setType(BillingClient.SkuType.SUBS);
            billingClient.querySkuDetailsAsync(params.build(),
                    new SkuDetailsResponseListener() {
                        @Override
                        public void onSkuDetailsResponse(BillingResult billingResult,
                                                         List<SkuDetails> skuDetailsList) {
                            Log.d(TAG, "biilingcount3.1");
                            if (billingResult.getResponseCode() != BillingClient.BillingResponseCode.OK) {
                                Log.d(TAG, "biilingcount3.2");
                                return;
                            }
                            Log.d(TAG, "biilingcount3.3");
                            //상품정보를 가저오지 못함 -
                            if (skuDetailsList == null) {
                                Log.d(TAG,"결제 상품 리스트에 없음 ");
                                return;
                            }

                            //상품사이즈 체크
                            Log.d(TAG, "결제 상품 리스트 크기 : " + skuDetailsList.size());

                            //상품가저오기 : 정기결제상품 하나라서 한개만 처리함.
                            try {
                                for (SkuDetails skuDetails : skuDetailsList) {
                                    String title = skuDetails.getTitle();
                                    String sku = skuDetails.getSku();
                                    String price = skuDetails.getPrice();
//                                userSkuDetails = skuDetails;

                                }
                            } catch (Exception e) {
                                Log.d(TAG, "itemerror" + e.toString());
                            }

                            skuDetails_list = skuDetailsList;
                        }

                    });
        }
// An activity reference from which the billing flow will be launched.
//        Activity activity = ...;




    public void purchase(String itemid, Activity activity) {
        SkuDetails skuDetails = null;
        if(null != skuDetails_list){
            for(int i=0; i<skuDetails_list.size(); i++){
                SkuDetails skuinfo = skuDetails_list.get(i);
                if(skuinfo.getSku().equals(itemid)){
                    skuDetails = skuinfo;
                    break;
                }
            }
            Log.d(TAG,"biilingcount4");
            // Retrieve a value for "skuDetails" by calling querySkuDetailsAsync().
            BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                    .setSkuDetails(skuDetails)
                    .build();
            Log.d(TAG,"purchase state : "+billingClient.launchBillingFlow(activity,flowParams).getResponseCode());
        }

    }

    public void handlePurchase(Purchase purchase) {
        Log.d(TAG,"biilingcount5");
        // Purchase retrieved from BillingClient#queryPurchasesAsync or your PurchasesUpdatedListener.

        // Verify the purchase.
        // Ensure entitlement was not already granted for this purchaseToken.
        // Grant entitlement to the user.

        ConsumeParams consumeParams =
                ConsumeParams.newBuilder()
                        .setPurchaseToken(purchase.getPurchaseToken())
                        .build();

        ConsumeResponseListener listener = new ConsumeResponseListener() {
            @Override
            public void onConsumeResponse(BillingResult billingResult, String purchaseToken) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    // Handle the success of the consume operation.
                }
            }
        };

        billingClient.consumeAsync(consumeParams, listener);
    }


    
    
}
