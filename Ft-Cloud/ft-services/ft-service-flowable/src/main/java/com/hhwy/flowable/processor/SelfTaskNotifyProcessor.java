package com.hhwy.flowable.processor;

import com.hhwy.common.core.utils.http.HttpUtils;
import com.hhwy.flowable.core.processor.TaskNotifyProcessor;
import com.hhwy.system.api.RemoteNotifyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.net.URLEncoder;

@Component
public class SelfTaskNotifyProcessor implements TaskNotifyProcessor {

    @Autowired
    private RemoteNotifyService notifyService;

    @Value("${socketio.remote.systemServer.uri}") //网关转发系统服务："http://127.0.0.1:8086/system" 或 直连系统服务："http://127.0.0.1:9300"
    String remoteSystemServerUri;

    @Override
    public void publish(String clientId, String topic, String message) {
        notifyService.publish(clientId,topic,message);
        if(StringUtils.isNotEmpty(remoteSystemServerUri)){
            String url = remoteSystemServerUri + "/notify/publish/" + clientId;
            try {
                String param;
                if(StringUtils.isNotEmpty(message)){
                    param = "topic="+URLEncoder.encode(topic,"UTF-8")+"&message="+ URLEncoder.encode(message,"UTF-8");
                }else {
                    param = "topic="+URLEncoder.encode(topic,"UTF-8");
                }
                String result = HttpUtils.sendGet(url, param);
                System.out.println("---notify result---:"+result);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}
