package org.niiish32x.fxdemo.service.impl;

import com.alibaba.fastjson.JSON;
import com.squareup.okhttp.*;
import lombok.extern.slf4j.Slf4j;
import org.niiish32x.fxdemo.dto.item.ItemReqDto;
import org.niiish32x.fxdemo.service.DataProcessService;
import org.niiish32x.fxdemo.service.DataSendService;
import org.niiish32x.fxdemo.tools.Common;
import org.niiish32x.fxdemo.tools.ReadJsonTool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class DataSendServiceImpl implements DataSendService {
    @Autowired
    DataProcessService dataProcessService;

    // test
    @Value("${supos.test.url}")
    private String URL_TEST;

//    @Value("${supos.test.prefix}")
//    private String PREFIX_TEST;

    @Value("${supos.test.ak}")
    private String AK_TEST;

    @Value("${supos.test.sk}")
    private String SK_TEST;

    // prod
    @Value("${supos.prod.url}")
    private String URL_PROD;

    @Value("${supos.prod.prefix}")
    private String PREFIX_PROD;

    @Value("${supos.prod.ak}")
    private String AK_PROD;

    @Value("${supos.prod.sk}")
    private String SK_PROD;



    private void sendToHttpToSuposLMS(String url, String prefix, String jsonData, String ak, String sk) {

        List<ItemReqDto> resList = dataProcessService.processData(ReadJsonTool.readDataFromJson(jsonData));

//        for (ItemReqDto req : resList) {
//            System.out.println(req);
//        }

        Map<String, Object> paramMap = new HashMap<>();

        for (ItemReqDto req : resList) {
            double value = req.getOnDate() == 0 ? req.getToDate() : req.getOnDate();
            String attributeFullPath = prefix + req.getName();

            paramMap.put("value", value);
            System.out.println(req.getName());

//            paramMap.put("attributeFullPath",attributeFullPath);
            paramMap.put("attributeFullPath","sugarapi_sugarapi.LMS.SUGAR_AREA.sugarapi_sugarapi.Total_Cane_Crushed_On_Date");
            Map<String, String> headerMap = new HashMap<>();
            headerMap.put("Content-Type", "application/json;charset=utf-8");

//            String sign = SignUtils.signHeader(url, "PUT", headerMap, null, ak, sk);

//            headerMap.put("Authorization", sign);
            headerMap.put("Authorization", "Sign 7ef904aa722a8d2418d848fba652f3b2-001986297db1c8f2c883cac8384d1f91f92c31a8b2e158708a8a956889ee4efe");

//            HttpUtil.executeHttpPutWithHeaderMap(url,paramMap,headerMap);
        }


    }



    @Override
    public void postToSuposTest(String jsonData, String prefix) throws IOException, InterruptedException {
        List<ItemReqDto> resList = dataProcessService.processData(ReadJsonTool.readDataFromJson(jsonData));

        for (ItemReqDto req : resList) {
            Map<String, Object> paramMap = new HashMap<>();
            double value = req.getOnDate() == 0 ? req.getToDate() : req.getOnDate();
            String attributeFullPath = prefix + req.getName();

            paramMap.put("value", value);
            paramMap.put("attributeFullPath",attributeFullPath);
            Map<String, String> headerMap = new HashMap<>();
            headerMap.put("Content-Type", "application/json;charset=utf-8");

            OkHttpClient client = new OkHttpClient();
            client.setReadTimeout(300, TimeUnit.MINUTES);
            MediaType mediaType = MediaType.parse("application/json;charset=utf-8");

            RequestBody body = RequestBody.create(mediaType,generateRequestBody(attributeFullPath,String.valueOf(value)));


            Request request = new Request.Builder()
                    .url(URL_TEST)
                    .method("PUT", body)
                    .addHeader("Authorization", Common.SIGN_TEST)
                    .addHeader("Content-Type", "application/json;charset=utf-8")
                    .build();

            Response response = client.newCall(request).execute();

            if(response.isSuccessful()) {
                log.info("ok {}",req.getName());
            }else {
                log.error("error:  name {}  ",req.getName());
            }

            Thread.sleep(500);

        }


//        sendToHttpToSuposLMS(URL_TEST,PREFIX_TEST,jsonData,AK_TEST,SK_TEST);
    }

    private String generateRequestBody(String attributeFullPath, String value) {
        // 构造请求体Map列表
        List<Map<String, Object>> requestBodyList = new ArrayList<>();
        Map<String, Object> requestBodyMap = new HashMap<>();
        requestBodyMap.put("attributeFullPath", attributeFullPath);
        requestBodyMap.put("value", value);
        requestBodyList.add(requestBodyMap);

        return JSON.toJSONString(requestBodyList);

    }

    @Override
    public void postToSuposProd(String jsonData) {
        sendToHttpToSuposLMS(URL_PROD,PREFIX_PROD,jsonData,AK_PROD,SK_PROD);
    }



}
