package com.coin.whichfood

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.KakaoSdk.init

class GlobalApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        //카카오톡 로그인 연동
        //카카오톡 로그인 연동
        KakaoSdk.init(this, "675ccfa4872a4eb6e1be8a61059dc307")
    }
}