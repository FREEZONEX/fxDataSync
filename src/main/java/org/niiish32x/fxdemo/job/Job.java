package org.niiish32x.fxdemo.job;


import org.niiish32x.fxdemo.http.HttpUtil;
import org.niiish32x.fxdemo.service.DataSendService;
import org.niiish32x.fxdemo.service.ReadJsonTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class Job {
    @Autowired
    DataSendService dataSendService;



    /**
     * 两秒 执行一次
     */
    @Scheduled(cron = "0/2 * * * * ? ")
    public void sendData() {
        for (String rule : ReadJsonTool.RULE_COLLECTION) {
            CompletableFuture.runAsync(() -> dataSendService.sendToSupos(HttpUtil.TEST_URL,HttpUtil.TEST_PREFIX,rule));
            CompletableFuture.runAsync(() -> dataSendService.sendToSupos(HttpUtil.PROD_URL,HttpUtil.PROD_PREFIX,rule));
        }
    }
}
