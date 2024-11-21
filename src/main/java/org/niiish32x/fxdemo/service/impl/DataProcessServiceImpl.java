package org.niiish32x.fxdemo.service.impl;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.niiish32x.fxdemo.dto.*;
import org.niiish32x.fxdemo.dto.item.ItemDto;
import org.niiish32x.fxdemo.dto.item.ItemReqDto;
import org.niiish32x.fxdemo.http.HttpResponseData;
import org.niiish32x.fxdemo.http.HttpUtil;
import org.niiish32x.fxdemo.service.DataProcessService;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
public class DataProcessServiceImpl implements DataProcessService {

    private final String DMR_URL = "http://labapi.dhampursugar.com:8110/api/ImportMasterData/GetDMRParameter";



    @Override
    public List<ItemReqDto> processSugarData ()  {
        List<DMRDto> dmrJsonData = getDMRReturnFromPostUrl();
        List<ItemDto> sugarDataFromJson = readSugarDataFromJson();

        List<ItemReqDto> res = new ArrayList<>();

        for (DMRDto dmr : dmrJsonData) {
            for (ItemDto itemDto : sugarDataFromJson) {
                if(StringUtils.isNumeric(itemDto.getParaCode())) {
                    Integer code = Integer.valueOf(itemDto.getParaCode());
                    ItemReqDto itemReqDto = ItemReqDto.builder()
                            .name(itemDto.getName())
                            .build();
                    if(code == dmr.getParaCode()) {
                        if(itemDto.isOnDate()) {
                            itemReqDto.setOnDate(dmr.getOnDate());
                        }else if (itemDto.isToDate()) {
                            itemReqDto.setToDate(dmr.getToDate());
                        }

                        res.add(itemReqDto);
                    }
                }
            }
        }

        return  res;
    }

    @Override
    public List<ItemReqDto> processData(List<ItemDto>  dataFromJson) {
        List<DMRDto> dmrJsonData = getDMRReturnFromPostUrl();
        List<ItemReqDto> res = new ArrayList<>();

        for (DMRDto dmr : dmrJsonData) {
            for (ItemDto itemDto : dataFromJson) {
                if(StringUtils.isNumeric(itemDto.getParaCode())) {
                    Integer code = Integer.valueOf(itemDto.getParaCode());
                    ItemReqDto itemReqDto = ItemReqDto.builder()
                            .name(itemDto.getName())
                            .build();
                    if(code == dmr.getParaCode()) {
                        if(itemDto.isOnDate()) {
                            itemReqDto.setOnDate(dmr.getOnDate());
                        }else if (itemDto.isToDate()) {
                            itemReqDto.setToDate(dmr.getToDate());
                        }

                        res.add(itemReqDto);
                    }
                }
            }
        }

        return  res;
    }

    public  List<DMRDto> getDMRReturnFromPostUrl() {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("unit",100011);
        paramMap.put("DATE","12-11-2024");
        HttpResponseData responseData = HttpUtil.executeHttpPost(DMR_URL,paramMap,2000);

        String responseText = responseData.getResponseText();
        JSONObject responseJson = JSON.parseObject(responseText);

        if (responseData == null || responseData.getStatusCode() != HttpStatus.SC_OK) {
            log.error("getOpenAccessToken failed, paramMap:{}", JSON.toJSONString(paramMap));
            return null;
        }

        DMRParameterDto parameterDto = JSON.parseObject(responseJson.toJSONString(), DMRParameterDto.class);
        List<DMRDto> dmrDto = parameterDto.getResponseDto().getDto();
        return dmrDto;
    }

    public List<ItemDto> readSugarDataFromJson() {
        List<ItemDto> itemDtos = JSON.parseArray(cmsSugarJsonData, ItemDto.class);

        // 数据处理
        for (ItemDto dto : itemDtos) {
            String str = dto.getName();
            if (str.endsWith("on_Date")) {
                dto.setOnDate(true);
            }

            if (str.endsWith("to_Date")) {
                dto.setToDate(true);
            }
        }

        return itemDtos;
    }



    static final  String cmsSugarJsonData = "[\n" +
            "  {\"name\":\"Total_Cane_Crushed_on_Date\",\"paraCode\":1},\n" +
            "  {\"name\":\"Total_Cane_Crushed_to_Date\",\"paraCode\":1},\n" +
            "  {\"name\":\"Average_Cane_Crush_Rate_on_Date\",\"paraCode\":2},\n" +
            "  {\"name\":\"Average_Cane_Crush_Rate_to_Date\",\"paraCode\":2},\n" +
            "  {\"name\":\"Sugar_Production_Total_on_Date\",\"paraCode\":4},\n" +
            "  {\"name\":\"Sugar_Production_Total_to_Date\",\"paraCode\":4},\n" +
            "  {\"name\":\"Recovery_on_Date\",\"paraCode\":3},\n" +
            "  {\"name\":\"Recovery_to_Date\",\"paraCode\":3},\n" +
            "  {\"name\":\"Bagasse_Moisture_on_Date\",\"paraCode\":884},\n" +
            "  {\"name\":\"Bagasse_Moisture_to_Date\",\"paraCode\":884},\n" +
            "  {\"name\":\"Bagasse_Pol_on_Date\",\"paraCode\":539},\n" +
            "  {\"name\":\"Bagasse_Pol_to_Date\",\"paraCode\":539},\n" +
            "  {\"name\":\"Filter_Cake_Pol_on_Date\",\"paraCode\":547},\n" +
            "  {\"name\":\"Filter_Cake_Pol_to_Date\",\"paraCode\":547},\n" +
            "  {\"name\":\"Pol_in_Cane_on_Date\",\"paraCode\":881},\n" +
            "  {\"name\":\"Pol_in_Cane_to_Date\",\"paraCode\":881},\n" +
            "  {\"name\":\"Mill_Extraction_on_Date\",\"paraCode\":16},\n" +
            "  {\"name\":\"Mill_Extraction_to_Date\",\"paraCode\":16},\n" +
            "  {\"name\":\"Molasses_Purity_on_Date\",\"paraCode\":420},\n" +
            "  {\"name\":\"Molasses_Purity_to_Date\",\"paraCode\":420},\n" +
            "  {\"name\":\"Molasses_B_Heavy_on_Date\",\"paraCode\":20},\n" +
            "  {\"name\":\"Molasses_B_Heavy_to_Date\",\"paraCode\":20},\n" +
            "  {\"name\":\"Molasses_C_Heavy_on_Date\",\"paraCode\":18},\n" +
            "  {\"name\":\"Molasses_C_Heavy_to_Date\",\"paraCode\":18},\n" +
            "  {\"name\":\"P_J_Brix_on_Date\",\"paraCode\":362},\n" +
            "  {\"name\":\"P_J_Brix_to_Date\",\"paraCode\":362},\n" +
            "  {\"name\":\"P_J_Purity_on_Date\",\"paraCode\":364},\n" +
            "  {\"name\":\"P_J_Purity_to_Date\",\"paraCode\":364},\n" +
            "  {\"name\":\"L_J__Brix_on_Date\",\"paraCode\":386},\n" +
            "  {\"name\":\"L_J__Brix_to_Date\",\"paraCode\":386},\n" +
            "  {\"name\":\"M_J__Brix_on_Date\",\"paraCode\":378},\n" +
            "  {\"name\":\"M_J__Brix_to_Date\",\"paraCode\":378},\n" +
            "  {\"name\":\"M_J__Purity_on_Date\",\"paraCode\":380},\n" +
            "  {\"name\":\"M_J__Purity_to_Date\",\"paraCode\":380},\n" +
            "  {\"name\":\"C_J_Brix_on_Date\",\"paraCode\":394},\n" +
            "  {\"name\":\"C_J_Brix_to_Date\",\"paraCode\":394},\n" +
            "  {\"name\":\"C_J_Purity_on_Date\",\"paraCode\":396},\n" +
            "  {\"name\":\"C_J_Purity_to_Date\",\"paraCode\":396},\n" +
            "  {\"name\":\"Syrup_Brix_on_Date\",\"paraCode\":\"default1\"},\n" +
            "  {\"name\":\"Syrup_Brix_to_Date\",\"paraCode\":\"default1\"},\n" +
            "  {\"name\":\"Fiber_on_Date\",\"paraCode\":14},\n" +
            "  {\"name\":\"Fiber_to_Date\",\"paraCode\":14},\n" +
            "  {\"name\":\"Total_Losses_on_Date\",\"paraCode\":10},\n" +
            "  {\"name\":\"Total_Losses_to_Date\",\"paraCode\":10},\n" +
            "  {\"name\":\"Sugar_Colour_on_Date\",\"paraCode\":339},\n" +
            "  {\"name\":\"Sugar_Colour_to_Date\",\"paraCode\":339},\n" +
            "  {\"name\":\"Mud_Pol_on_Date\",\"paraCode\":547},\n" +
            "  {\"name\":\"Mud_Pol_to_Date\",\"paraCode\":547},\n" +
            "  {\"name\":\"Sugar_in_Process_Inhouse_Stock_on_Date\",\"paraCode\":271},\n" +
            "  {\"name\":\"Sugar_in_Process_Inhouse_Stock_to_Date\",\"paraCode\":271},\n" +
            "  {\"name\":\"Breakdown_Duration_on_Date\",\"paraCode\":156},\n" +
            "  {\"name\":\"Breakdown_Duration_to_Date\",\"paraCode\":156},\n" +
            "  {\"name\":\"Stoppage_Cane_on_Date\",\"paraCode\":138},\n" +
            "  {\"name\":\"Stoppage_Cane_to_Date\",\"paraCode\":138},\n" +
            "  {\"name\":\"Stoppage_Mechanical_on_Date\",\"paraCode\":140},\n" +
            "  {\"name\":\"Stoppage_Mechanical_to_Date\",\"paraCode\":140},\n" +
            "  {\"name\":\"Stoppage_Boiler_Turbine_on_Date\",\"paraCode\":142},\n" +
            "  {\"name\":\"Stoppage_Boiler_Turbine_to_Date\",\"paraCode\":142},\n" +
            "  {\"name\":\"Stoppage_Electrical_on_Date\",\"paraCode\":144},\n" +
            "  {\"name\":\"Stoppage_Electrical_to_Date\",\"paraCode\":144},\n" +
            "  {\"name\":\"Stoppage_Instrumentation_on_Date\",\"paraCode\":146},\n" +
            "  {\"name\":\"Stoppage_Instrumentation_to_Date\",\"paraCode\":146},\n" +
            "  {\"name\":\"Stoppage_Process_on_Date\",\"paraCode\":148},\n" +
            "  {\"name\":\"Stoppage_Process_to_Date\",\"paraCode\":148},\n" +
            "  {\"name\":\"Stoppage_General_Cleaning_on_Date\",\"paraCode\":150},\n" +
            "  {\"name\":\"Stoppage_General_Cleaning_to_Date\",\"paraCode\":150},\n" +
            "  {\"name\":\"Stoppage_Misc_on_Date\",\"paraCode\":152},\n" +
            "  {\"name\":\"Stoppage_Misc_to_Date\",\"paraCode\":152},\n" +
            "  {\"name\":\"Stoppage_Stoppage_Wthr_Lb_GrSt_on_Date\",\"paraCode\":154},\n" +
            "  {\"name\":\"Stoppage_Stoppage_Wthr_Lb_GrSt_to_Date\",\"paraCode\":154},\n" +
            "  {\"name\":\"Syrup_Transferred_to_Distillery_on_Date\",\"paraCode\":130},\n" +
            "  {\"name\":\"Syrup_Transferred_to_Distillery_to_Date\",\"paraCode\":130},\n" +
            "  {\"name\":\"C_Heavy_Estimated_on_Date\",\"paraCode\":18},\n" +
            "  {\"name\":\"C_Heavy_Estimated_to_Date\",\"paraCode\":18},\n" +
            "  {\"name\":\"B_Heavy_Estimated_on_Date\",\"paraCode\":20},\n" +
            "  {\"name\":\"B_Heavy_Estimated_to_Date\",\"paraCode\":20},\n" +
            "\n" +
            "]";


}
