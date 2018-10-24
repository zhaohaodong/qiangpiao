package com.example.qiangpiao.wechat.service;

import java.util.List;

public interface EnterpriseWeChatService {
	/**
	 * 发送企业微信给某个部门的所有人
	 * 作者:王锋
	 * 时间:2017年4月5日
	 */
	boolean sendDepartmentWeChatMessage(List<Integer> toPartys, int msgType, String message, int agentId);
	/**
	 * 发送企业微信给某些人
	 * 作者:王锋
	 * 时间:2017年4月5日
	 */
	boolean sendPersonalWeChatMessage(List<String> toUsers, int msgType, String message, int agentId);
}
