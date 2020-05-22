package com.mark.ss;

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

    public static String request(String uri,boolean ifHeader) throws IOException {
        URL url = new URL(uri);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        if (ifHeader){
            connection.setRequestProperty("Accept","application/json, text/javascript, */*; q=0.01");
//            connection.setRequestProperty("Accept-Encoding","gzip, deflate, br");
            connection.setRequestProperty("Accept-Language","zh-CN,zh;q=0.9");
            connection.setRequestProperty("Connection","keep-alive");
            connection.setRequestProperty("Host","www.kuaidi100.com");
            connection.setRequestProperty("Referer","https://www.kuaidi100.com/?from=openv");
            connection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36");
            connection.setRequestProperty("X-Requested-With","XMLHttpRequest");
        }
        connection.setDoInput(true);
        connection.setDoOutput(true);
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK){
            InputStream inputStream = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine())!=null){
                sb.append(line);
            }
            return sb.toString();
        }
        return null;
    }

    public static String request(String uri) throws IOException {
        return  request(uri,false);
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
