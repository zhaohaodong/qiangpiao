package com.example.qiangpiao.wechat.util;

import java.util.HashMap;
import java.util.Map;

import com.example.qiangpiao.wechat.EnterpriseWeChatMessageType;
import org.springframework.stereotype.Service;


@Service
public class EnterpriseWeChatutil {
	/**
	 * 根据消息类型获取消息参数模板
	 * 作者:王锋
	 * 时间:2017年4月5日
	 */
	public Map<Integer,String> getEnterpriseWeChatParam(String touser,String toparty,int agentid,String content,String thumb_media_id){
		Map<Integer,String> map=new HashMap<Integer,String>();
		map.put(EnterpriseWeChatMessageType.TEXT.value(),"{\"touser\": \""+touser+"\",\"toparty\": \""+toparty+"\",\"totag\": \"\",\"msgtype\": \"text\",\"agentid\": "+agentid+",\"text\": {\"content\": \""+content+"\"}, \"safe\":0}");
		map.put(EnterpriseWeChatMessageType.MPNEWS.value(), "{\"touser\": \""+touser+"\",\"toparty\": \""+toparty+"\",\"totag\": \"\",\"msgtype\": \"mpnews\",\"agentid\": "+agentid+",\"mpnews\": { "
				+ "\"articles\":[{ \"title\": \"标题\", \"thumb_media_id\": \""+thumb_media_id+"\", \"author\": \"Author\", \"content_source_url\": \"URL\","
				+ "\"content\": \""+content+"\", \"digest\": \"Digest description\", \"show_cover_pic\": \"1\"   }]}, \"safe\":0}");
		return map;
	}
}
