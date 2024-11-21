package org.niiish32x.fxdemo.service.impl;

import org.niiish32x.fxdemo.dto.item.ItemReqDto;
import org.niiish32x.fxdemo.http.HttpUtil;
import org.niiish32x.fxdemo.service.DataProcessService;
import org.niiish32x.fxdemo.service.DataSendService;
import org.niiish32x.fxdemo.service.ReadJsonTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DataSendServiceImpl implements DataSendService {
    @Autowired
    DataProcessService dataProcessService;



    @Override
    public void sendToSupos(String post_url,String prefix,String jsonData) {

        List<ItemReqDto> resList = dataProcessService.processData(ReadJsonTool.readDataFromJson(jsonData));

        for (ItemReqDto req : resList) {
            System.out.println(req);
        }

//        final String POST_URL = "http://192.168.31.223:8080/open-api/supos/oodm/v2/attributes/current";
//        String prefix = "sugarplant_sugarplant.CMS.CANE_MANAGEMENT_SYSTEM_SUGAR.sugarplant_sugarplant.";
        for (ItemReqDto req : resList) {
            Map<String, Object> paramMap = new HashMap<>();

            paramMap.put("attributeFullPath",req.getName());

            double value = req.getOnDate() == 0 ? req.getToDate() : req.getOnDate();;

            paramMap.put("value",prefix+ value);

            Map<String, String> headerMap = new HashMap<>();
            headerMap.put("Content-Type", "application/json");

            HttpUtil.executeHttpPutWithHeaderMap(post_url,paramMap,headerMap);

        }
    }

}
