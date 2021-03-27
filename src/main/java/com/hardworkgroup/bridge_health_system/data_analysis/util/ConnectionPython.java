package com.hardworkgroup.bridge_health_system.data_analysis.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectionPython {

    public static String connectionPython(String method) throws IOException {
        //第一步：创建服务地址
        URL url = new URL("http://127.0.0.1:8000/" + method);
        //第二步：打开一个通向服务地址的连接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        //第五步：接收服务端响应，打印
        int responseCode = connection.getResponseCode();
        if(200 == responseCode){//表示服务端响应成功
            //获取当前连接请求返回的数据流
            InputStream is = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            StringBuilder sb = new StringBuilder();
            String temp = null;
            while(null != (temp = br.readLine())){
                sb.append(temp);

            }

            is.close();
            isr.close();
            br.close();

            /**
             * 打印结果
             */
            return sb.toString();
        }
        return null;
    }
}
