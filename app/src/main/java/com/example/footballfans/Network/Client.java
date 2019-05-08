package com.example.footballfans.Network;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {

    public static final String BASE_URL = "https://www.thesportsdb.com/api/v1/json/1/";
    private static Retrofit mRetrofit;

    public static Retrofit getClient(){
        if (mRetrofit == null){

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            List<CipherSuite> cipherSuites = ConnectionSpec.MODERN_TLS.cipherSuites();
            if (!cipherSuites.contains(CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA)) {
                cipherSuites = new ArrayList(cipherSuites);
                cipherSuites.add(CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA);
            }
            final ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                    .cipherSuites(cipherSuites.toArray(new CipherSuite[0]))
                    .build();

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectionSpecs(Collections.singletonList(spec))
                    .build();

//
//            OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                    .addInterceptor(loggingInterceptor)
//                    .readTimeout(60,TimeUnit.SECONDS)
//                    .connectTimeout(60,TimeUnit.SECONDS)
//                    .writeTimeout(60,TimeUnit.SECONDS)
//                    .build();

//4480
           mRetrofit = new Retrofit.Builder()
                   .client(okHttpClient)
                   .baseUrl(BASE_URL)
                   .addConverterFactory(GsonConverterFactory.create())
                   .build();
        }
        return mRetrofit;
    }
}
