package org.niiish32x.fxdemo;

import org.junit.jupiter.api.Test;
import org.niiish32x.fxdemo.http.HttpUtil;
import org.niiish32x.fxdemo.service.DataSendService;
import org.niiish32x.fxdemo.service.ReadJsonTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;

@SpringBootTest
class FxdemoApplicationTests {

    @Autowired
    DataSendService dataSendService;


    @Test
    void contextLoads() {

        for (String rule : ReadJsonTool.RULE_COLLECTION) {
//             dataSendService.sendToSupos(HttpUtil.TEST_URL,HttpUtil.TEST_PREFIX,rule);
//             dataSendService.sendToSupos(HttpUtil.PROD_URL,HttpUtil.PROD_PREFIX,rule);

        }
//        dataSendService.sendToSupos(HttpUtil.TEST_URL,HttpUtil.TEST_PREFIX,ReadJsonTool.lmsPowerJsonData);
    }

}
