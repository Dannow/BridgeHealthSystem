package com.hardworkgroup.bridge_health_system.system_common.utils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.stereotype.Component;

@Component
public class SendMessageUtils {
    //阿里云accessKeyId
    private static final String accessKeyId="LTAI4GEvvi9MjMFy9oHTzwQo";
    //阿里云accessSecret
    private static final String accessSecret="jB2p07yoLQ2ZJ8GP9cTVEivINWAWhR";
    //阿里云的签名名称
    private static final String signName="云智桥梁健康监测系统";

    //阿里云模版CODE
//    private static final String templateCode="SMS_217436840";


    //发送短信
    public static void sendMsg(String mobile,String bridgeName, String userName, String sendType){
        // 选择短信模板
        String templateCode = null;
        switch (sendType){
            case "completePlan":
                templateCode = "SMS_217416806";
                break;
            case "auditPlan":
                templateCode = "SMS_217436841";
                break;
            case "alarm":
                templateCode = "SMS_217436840";
                break;
        }

        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);

        //设置参数
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", mobile);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam","{\"bridgeName\":\""+bridgeName+"\",\"userName\":\""+userName+"\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
