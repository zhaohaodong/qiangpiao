package com.example.qiangpiao.wechat.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
@Service
public class ConfigUtil {
	@Value("${EnterpriseWeChat_CORPID}")
	private String EnterpriseWeChat_CORPID;
	
	@Value("${EnterpriseWeChat_CORPSECRET}")
	private String EnterpriseWeChat_CORPSECRET;
	
	//企业微信应用id	
	@Value("${EnterpriseWeChat_AgentId}")
	private int EnterpriseWeChat_AgentId;
	//企业微信图片id
	@Value("${EnterpriseWeChat_thumb_media_id}")
	private String EnterpriseWeChat_thumb_media_id;
	public String getEnterpriseWeChat_CORPID() {
		return EnterpriseWeChat_CORPID;
	}
	public void setEnterpriseWeChat_CORPID(String enterpriseWeChat_CORPID) {
		EnterpriseWeChat_CORPID = enterpriseWeChat_CORPID;
	}
	public String getEnterpriseWeChat_CORPSECRET() {
		return EnterpriseWeChat_CORPSECRET;
	}
	public void setEnterpriseWeChat_CORPSECRET(String enterpriseWeChat_CORPSECRET) {
		EnterpriseWeChat_CORPSECRET = enterpriseWeChat_CORPSECRET;
	}
	public int getEnterpriseWeChat_AgentId() {
		return EnterpriseWeChat_AgentId;
	}
	public void setEnterpriseWeChat_AgentId(int enterpriseWeChat_AgentId) {
		EnterpriseWeChat_AgentId = enterpriseWeChat_AgentId;
	}
	public String getEnterpriseWeChat_thumb_media_id() {
		return EnterpriseWeChat_thumb_media_id;
	}
	public void setEnterpriseWeChat_thumb_media_id(String enterpriseWeChat_thumb_media_id) {
		EnterpriseWeChat_thumb_media_id = enterpriseWeChat_thumb_media_id;
	}
	
}
