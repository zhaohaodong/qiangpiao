package com.example.qiangpiao;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.example.qiangpiao.qiangpiao.Data;
import com.example.qiangpiao.qiangpiao.Ticket;
import com.example.qiangpiao.wechat.service.EnterpriseWeChatService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StringUtils;

import java.beans.Beans;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class QiangpiaoApplication implements CommandLineRunner {

    @Autowired
    private EnterpriseWeChatService enterpriseWeChatService;
    private static ExecutorService pool = Executors.newFixedThreadPool(50);
    private static final Logger logger = LogManager.getLogger(QiangpiaoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(QiangpiaoApplication.class, args);
    }
    @Override
    public void run(String... args) throws Exception {


        getPiao("https://kyfw.12306.cn/otn/leftTicket/queryA?leftTicketDTO.train_date=2018-10-07&leftTicketDTO.from_station=HPP&leftTicketDTO.to_station=SHH&purpose_codes=ADULT"
                ,"zhaohaodong","13135630495");
        return;
    }

    private void getPiao(String url,String user,String phone) {
        pool.submit(()->{
            int i= 0;
            while (true){
                if (i>1){
                    logger.info("结束循环");
                    return ;
                }
                String result = null;
                try {
                    result = Ticket.sendGet(url,"utf-8");
                } catch (Exception e) {
                    try {
                        Thread.sleep(1000*10);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    continue;
                }
                logger.info(result);
                Data data=JSONObject.parseObject(result, Data.class);
                for (String s:data.getData().getResult()) {
                    if (s.contains("|Y|") && s.contains("|G2811|")
                            && s.contains("|G1955|")&& s.contains("|G1951|")){
                        if (!StringUtils.isEmpty(phone)){
                            aliyunDunxin(phone);
                        }
                        i++;
                    }
                }
                try {
                    Thread.sleep(1000*20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void aliyunDunxin(String phone) {
        //设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化ascClient需要的几个参数
        final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
        final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
        //替换成你的AK
        final String accessKeyId = "LTAIldoCXSk4LyFv";//你的accessKeyId,参考本文档步骤2
        final String accessKeySecret = "oku4uwz4HAF6ePsTU2d9YjqznjJUBu";//你的accessKeySecret，参考本文档步骤2
        //初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
                accessKeySecret);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        //使用post提交
        request.setMethod(MethodType.POST);
        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为00+国际区号+号码，如“0085200000000”
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("Java对接订阅号测试");
        //必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
        request.setTemplateCode("SMS_144452399");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        request.setTemplateParam("");
        //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        //request.setOutId("yourOutId");
        //请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse = null;
        try {
            sendSmsResponse = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
            //请求成功
            logger.info("短信发送成功");
        }
    }

    private void wxMessage(String user) {
        //微信提醒消息
        List<String> toUsers = new ArrayList<>();
        toUsers.add(user);
        boolean flag = enterpriseWeChatService.sendPersonalWeChatMessage(toUsers, 1, "有票了！！！快快快：https://kyfw.12306.cn/otn/leftTicket/init", 12);
    }
}
