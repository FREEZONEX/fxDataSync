package org.niiish32x.fxdemo.job;


import org.niiish32x.fxdemo.http.HttpUtil;
import org.niiish32x.fxdemo.service.DataSendService;
import org.niiish32x.fxdemo.tools.Common;
import org.niiish32x.fxdemo.tools.ReadJsonTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Service
public class Job {
    @Autowired
    DataSendService dataSendService;



    /**
     * 5分钟执行一次      */
    @Scheduled(cron = "0 0/5 * * * ? ")
    public void sendData() throws IOException, InterruptedException {

        dataSendService.postToSuposTest(ReadJsonTool.lmsSugarJsonData, Common.PREFIX_SUGAR_AREA_TEST);
        dataSendService.postToSuposTest(ReadJsonTool.lmsPowerJsonData, Common.PREFIX_POWER_AREA_TEST);
        dataSendService.postToSuposTest(ReadJsonTool.lmsSugarJsonData, Common.PREFIX_DISTILLERY_AREA_TEST);
        dataSendService.postToSuposTest(ReadJsonTool.lmsGrainJsonData, Common.PREFIX_Grains_Production_Summary_On_Maize_Rice_TEST);


    }
}
