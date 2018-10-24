package com.example.qiangpiao.wechat.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;

public class ConnectionUtils {

    // post请求
    public static final String HTTP_POST = "POST";

    // get请求
    public static final String HTTP_GET = "GET";

    // utf-8字符编码
    public static final String CHARSET_UTF_8 = "utf-8";

    // HTTP内容类型。如果未指定ContentType，默认为TEXT/HTML
    public static final String CONTENT_TYPE_TEXT_HTML = "text/xml";

    // HTTP内容类型。相当于form表单的形式，提交暑假
    public static final String CONTENT_TYPE_FORM_URL = "application/x-www-form-urlencoded";

    // 请求超时时间
    public static final int SEND_REQUEST_TIME_OUT = 50000;

    // 将读超时时间
    public static final int READ_TIME_OUT = 50000;

    /**
     * 
     * @param requestType
     *            请求类型
     * @param urlStr
     *            请求地址
     * @param body
     *           
     * @return 返回内容
     */
    public static String getData(String requestType, String urlStr, String body) {
        // 是否有http正文提交
        boolean isDoInput = false;
        if (body != null && body.length() > 0)
            isDoInput = true;
        OutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;
        try {
            // 统一资源
            URL url = new URL(urlStr);
            // 连接类的父类，抽象类
            URLConnection urlConnection = url.openConnection();
            // http的连接类
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;

            // 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在
           
            if (isDoInput) {
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestProperty("Content-Length", String.valueOf(body.length()));
            }
            // 设置是否从httpUrlConnection读入，默认情况下是true;
            httpURLConnection.setDoInput(true);
           
            httpURLConnection.setConnectTimeout(SEND_REQUEST_TIME_OUT);
          
            httpURLConnection.setReadTimeout(READ_TIME_OUT);
            // Post 请求不能使用缓存
            httpURLConnection.setUseCaches(false);
            // 设置字符编码
            httpURLConnection.setRequestProperty("Accept-Charset", CHARSET_UTF_8);
            // 设置内容类型
            httpURLConnection.setRequestProperty("Content-Type", CONTENT_TYPE_FORM_URL);
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            // 设定请求的方法，默认是GET
            httpURLConnection.setRequestMethod(requestType);

            httpURLConnection.connect();

            if (isDoInput) {
                outputStream = httpURLConnection.getOutputStream();
                outputStreamWriter = new OutputStreamWriter(outputStream,"UTF-8");
                outputStreamWriter.write(body);
                outputStreamWriter.flush();// 刷新
            }
            if (httpURLConnection.getResponseCode() >= 300) {
                throw new Exception(
                        "HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
            }

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream,"UTF-8");
                reader = new BufferedReader(inputStreamReader);
                while ((tempLine = reader.readLine()) != null) {
                    resultBuffer.append(tempLine);
                    resultBuffer.append("\n");
                }
            }

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {

            try {
                if (outputStreamWriter != null) {
                    outputStreamWriter.close();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return resultBuffer.toString();
    }

    /**
     * 将map集合的键值对转化成：key1=value1&key2=value2 的形�?
     * 
     * @param parameterMap
     *            �?要转化的键�?�对集合
     * @return 字符�?
     */
    @SuppressWarnings("rawtypes")
	public static String convertStringParamter(Map parameterMap) {
        StringBuffer parameterBuffer = new StringBuffer();
        if (parameterMap != null) {
			Iterator iterator = parameterMap.keySet().iterator();
            String key = null;
            String value = null;
            while (iterator.hasNext()) {
                key = (String) iterator.next();
                if (parameterMap.get(key) != null) {
                    value = (String) parameterMap.get(key);
                } else {
                    value = "";
                }
                parameterBuffer.append(key).append("=").append(value);
                if (iterator.hasNext()) {
                    parameterBuffer.append("&");
                }
            }
        }
        return parameterBuffer.toString();
    }

}
