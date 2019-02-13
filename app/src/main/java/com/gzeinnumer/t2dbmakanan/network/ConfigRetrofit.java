package com.gzeinnumer.t2dbmakanan.network;

import android.os.Build;

import com.gzeinnumer.t2dbmakanan.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
//todo 4. buat class COnfig
public class ConfigRetrofit {
    //todo 5. setInit
    public static Retrofit setInit(){
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.BASE_URL)
                .build();
    }

    //todo 9. hubungkan interface dan ConfigRetrofit dengan function getInstancee
    public  static ApiService getInstancee(){
        return setInit().create(ApiService.class);
    }
}
