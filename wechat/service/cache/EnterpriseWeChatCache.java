package com.example.qiangpiao.wechat.service.cache;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.druid.support.json.JSONParser;
@Service
public class EnterpriseWeChatCache {
	public static String token="";
	public static long sendMillis=0;
	public static String getToken(String corpid,String corpsecret){
		long currentTimeMillis=System.currentTimeMillis();
		if(null==token||token.length()==0){
			sendMillis=System.currentTimeMillis();
			token=setToken(corpid,corpsecret);
		}
		if((currentTimeMillis-sendMillis)/1000>7100){
			sendMillis=System.currentTimeMillis();
			token=setToken(corpid,corpsecret);
		}
		return token;
	}
	/**
	 * 获取token
	 * 作者:王锋
	 * 时间:2017年4月5日
	 */
	public static String setToken(String corpid,String corpsecret){
		//access_token(token)   expires(秒数)
		HttpURLConnection connection;
		try {
			String getTokenUrl="https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid="+corpid+"&corpsecret="+corpsecret;
			URL getURL = new URL(getTokenUrl);
			connection = (HttpURLConnection) getURL.openConnection();
			connection.connect();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuilder sbStr = new StringBuilder();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
			  sbStr.append(line);
			}
			bufferedReader.close();
			connection.disconnect();
			String responseMeg=new String(sbStr.toString().getBytes(), "utf-8");		
			JSONParser s=new JSONParser(responseMeg);
	        @SuppressWarnings({ "unchecked", "rawtypes" })
			Map<String,String> map=(Map)s.parse();
			return map.get("access_token");
		} catch (IOException e) {
			new Throwable(e+"获取token发生异常");
			e.printStackTrace();
		}
		return corpsecret;
		
	}
}
