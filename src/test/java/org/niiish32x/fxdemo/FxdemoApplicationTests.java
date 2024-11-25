package org.niiish32x.fxdemo;

import org.junit.jupiter.api.Test;
import org.niiish32x.fxdemo.service.DataSendService;
import org.niiish32x.fxdemo.tools.Common;
import org.niiish32x.fxdemo.tools.ReadJsonTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class FxdemoApplicationTests {

    @Autowired
    DataSendService dataSendService;




    @Test
    void contextLoads() throws IOException, InterruptedException {

        dataSendService.postToSuposTest(ReadJsonTool.lmsSugarJsonData, Common.PREFIX_SUGAR_AREA_TEST);
        dataSendService.postToSuposTest(ReadJsonTool.lmsPowerJsonData, Common.PREFIX_POWER_AREA_TEST);
        dataSendService.postToSuposTest(ReadJsonTool.lmsSugarJsonData, Common.PREFIX_DISTILLERY_AREA_TEST);
        dataSendService.postToSuposTest(ReadJsonTool.lmsGrainJsonData, Common.PREFIX_Grains_Production_Summary_On_Maize_Rice_TEST);

    }

}
