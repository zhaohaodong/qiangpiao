package com.example.qiangpiao.wechat.service.impl;
import java.util.List;
import java.util.Map;

import com.example.qiangpiao.wechat.service.EnterpriseWeChatService;
import com.example.qiangpiao.wechat.service.cache.EnterpriseWeChatCache;
import com.example.qiangpiao.wechat.util.ConfigUtil;
import com.example.qiangpiao.wechat.util.ConnectionUtils;
import com.example.qiangpiao.wechat.util.EnterpriseWeChatutil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EnterpriseWeChatServiceImpl implements EnterpriseWeChatService {
	static final Logger logger = LogManager.getLogger(EnterpriseWeChatServiceImpl.class);
	@Autowired
	private EnterpriseWeChatutil enterpriseWeChatutil;
	@Autowired
	private EnterpriseWeChatCache enterpriseWeChatCache;
	@Autowired
	private ConfigUtil configUtil;
	
	private static String token="";
	
	@SuppressWarnings({"static-access" })
	@Override
	public boolean sendDepartmentWeChatMessage(List<Integer> toPartys, int msgType, String message,int agentId) {
	    token=enterpriseWeChatCache.getToken(configUtil.getEnterpriseWeChat_CORPID(), configUtil.getEnterpriseWeChat_CORPSECRET());
		//将用户账户数组格式化
		StringBuffer sb=new StringBuffer();
		for (Integer tp : toPartys) {
			sb.append("|"+tp.toString());
		}
		String thumbMediaId="";
		//代表是需要传缩略图Id
		if(msgType==7){
			thumbMediaId="";
		}
		//获取给一批用户发企业微信模板
		Map<Integer,String> map=enterpriseWeChatutil.getEnterpriseWeChatParam("", sb.toString().substring(1,sb.toString().length()), agentId, message,thumbMediaId);
		String param=map.get(1);
		String send_message_url="https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token="+token;
		String responseMessage=ConnectionUtils.getData("POST", send_message_url, param);
		logger.info(responseMessage+"发送微信返回信息");
		if(responseMessage.indexOf("ok")==-1){
	        	return false;
	    }
		return true;
	}

	@SuppressWarnings({ "static-access"})
	@Override
	public boolean sendPersonalWeChatMessage(List<String> toUsers, int msgType, String message,int agentId) {
		String responseMessage="";
		try {
			token=enterpriseWeChatCache.getToken(configUtil.getEnterpriseWeChat_CORPID(), configUtil.getEnterpriseWeChat_CORPSECRET());
			if(token==null||token.equals("")){
				return false;
			}
			//将用户账户数组格式化
			StringBuffer sb=new StringBuffer();
			for (String tu : toUsers) {
				sb.append("|"+tu);
			}
			String thumbMediaId="";
			//代表是需要穿缩略图Id
			if(msgType==7){
				thumbMediaId="";
			}
			//获取给一批用户发企业微信模板
			Map<Integer,String> map=enterpriseWeChatutil.getEnterpriseWeChatParam(sb.toString().substring(1,sb.toString().length()), "", agentId, message,thumbMediaId);
			String param=map.get(1);
			String send_message_url="https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token="+token;
			responseMessage=ConnectionUtils.getData("POST", send_message_url, param);
		} catch (Exception e) {
			logger.error("微信内部API发送个单人微信消息发生异常",e);
		}
		if(responseMessage.indexOf("ok")==-1){
        	return false;
        }
		return true;
	}
}
