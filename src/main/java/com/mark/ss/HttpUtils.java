package com.mark.ss;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2017/1/6.
 */

public class HttpUtils {

    public static String request(String uri) throws IOException {
        String response = null;
        URL url = new URL(uri);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(true);
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK){
            InputStream inputStream = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine())!=null){
                sb.append(line);
            }
            response = sb.toString();
            return response;
        }else {

        }
        return response;
    }


    public static boolean isNetworkAvailable(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null){
            return false;
        }else {
            if (Build.VERSION.SDK_INT < 21){
                NetworkInfo[] networkInfo = manager.getAllNetworkInfo();
                if (networkInfo!=null && networkInfo.length>0){
                    for (NetworkInfo info:networkInfo){
                        Log.i("TAG", "isNetworkAvailable: 状态:"+info.getState()+"===类型："+info.getTypeName());
                        if (info.getState() ==  NetworkInfo.State.CONNECTED){
                            return true;
                        }
                    }
                }
            }else {
                Network[] allNetworks = manager.getAllNetworks();
                if (allNetworks != null && allNetworks.length>0){
                    for (Network net:allNetworks){
                        NetworkInfo info = manager.getNetworkInfo(net);
                        Log.i("TAG", "isNetworkAvailable: 状态:"+info.getState()+"===类型："+info.getTypeName());
                        if (info.getState() ==  NetworkInfo.State.CONNECTED){
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }
}
