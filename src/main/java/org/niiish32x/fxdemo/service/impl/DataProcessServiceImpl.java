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


    private final String  DMRJSON = "{\n" +
            "    \"Error_Flag\": \"0\",\n" +
            "    \"Error_Message\": \"\",\n" +
            "    \"Message\": \"Successful\",\n" +
            "    \"Response\": {\n" +
            "        \"DMRReturn\": [\n" +
            "            {\n" +
            "                \"Para_Code\": 1,\n" +
            "                \"Para_Name\": \"Cane Crushed\",\n" +
            "                \"OnDate\": 119200.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 566600.0\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2,\n" +
            "                \"Para_Name\": \"Crush Rate/Avg.Crush\",\n" +
            "                \"OnDate\": 119613.66,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 102243.61\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 3,\n" +
            "                \"Para_Name\": \"Recovery\",\n" +
            "                \"OnDate\": 9.09,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 9.07\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 4,\n" +
            "                \"Para_Name\": \"Sugar Production\",\n" +
            "                \"OnDate\": 11150.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 26050.0\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 10,\n" +
            "                \"Para_Name\": \"Total losses\",\n" +
            "                \"OnDate\": 2.4,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 2.3\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 11,\n" +
            "                \"Para_Name\": \"Added water\",\n" +
            "                \"OnDate\": 39.67,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 39.73\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 14,\n" +
            "                \"Para_Name\": \"Fiber\",\n" +
            "                \"OnDate\": 13.38,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 13.4\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 16,\n" +
            "                \"Para_Name\": \"Mill Extraction\",\n" +
            "                \"OnDate\": 96.73,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 96.77\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 18,\n" +
            "                \"Para_Name\": \"C Heavy Estimated\",\n" +
            "                \"OnDate\": 5.58,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 5.52\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 20,\n" +
            "                \"Para_Name\": \"B Heavy Estimated\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 0.0\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 80,\n" +
            "                \"Para_Name\": \"Recovery in terms of R.S. on CH Molasses\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 0.0\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 81,\n" +
            "                \"Para_Name\": \"Recovery in terms of RS\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 0.0\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 82,\n" +
            "                \"Para_Name\": \"Recovery in terms of RS Syrup\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 0.0\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 83,\n" +
            "                \"Para_Name\": \"Steam Fuel Ratio P1\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 0.0\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 84,\n" +
            "                \"Para_Name\": \"Steam Fuel Ratio P2\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 0.0\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 85,\n" +
            "                \"Para_Name\": \"Steam Fuel Ratio 55 TPH\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 0.0\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 86,\n" +
            "                \"Para_Name\": \"Steam Fuel Ratio 75 TPH\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 0.0\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 87,\n" +
            "                \"Para_Name\": \"Coal Consumed P1\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 0.0\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 88,\n" +
            "                \"Para_Name\": \"Coal Consumed P2\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 0.0\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 89,\n" +
            "                \"Para_Name\": \"Coal Consumed 55 TPH\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 0.0\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 90,\n" +
            "                \"Para_Name\": \"Coal Consumed 75 TPH\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 0.0\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 91,\n" +
            "                \"Para_Name\": \"Steam Losses P1\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 0.0\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 92,\n" +
            "                \"Para_Name\": \"Steam Losses P2\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 0.0\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 93,\n" +
            "                \"Para_Name\": \"Steam Losses 55 TPH\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 0.0\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 94,\n" +
            "                \"Para_Name\": \"Steam Losses 75 TPH\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 0.0\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 95,\n" +
            "                \"Para_Name\": \"Fuel Losses P1\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 0.0\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 96,\n" +
            "                \"Para_Name\": \"Fuel Losses P2\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 0.0\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 97,\n" +
            "                \"Para_Name\": \"Fuel Losses 55 TPH\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 0.0\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 98,\n" +
            "                \"Para_Name\": \"Fuel Losses 75 TPH\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 0.0\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 130,\n" +
            "                \"Para_Name\": \"Syrup Transfered\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 0.0\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 138,\n" +
            "                \"Para_Name\": \"Stoppage Cane\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 0.0\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 140,\n" +
            "                \"Para_Name\": \"Stoppage Mechanical\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 0.23\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 142,\n" +
            "                \"Para_Name\": \"Stoppage Boiler & Turb.\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 0.0\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 144,\n" +
            "                \"Para_Name\": \"Stoppage Electrical\",\n" +
            "                \"OnDate\": 0.083,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 0.97\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 146,\n" +
            "                \"Para_Name\": \"Stoppage Instrumentation\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 0.4\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 148,\n" +
            "                \"Para_Name\": \"Stoppage Process\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 0.0\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 150,\n" +
            "                \"Para_Name\": \"Stoppage Gen. Cleaning\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 0.0\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 152,\n" +
            "                \"Para_Name\": \"Stoppage Misc.\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 0.08\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 154,\n" +
            "                \"Para_Name\": \"Stoppage Wthr./Lb/Gr.St.\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 0.0\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 156,\n" +
            "                \"Para_Name\": \"Stoppage Total Stopages\",\n" +
            "                \"OnDate\": 0.083,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 1.68\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 158,\n" +
            "                \"Para_Name\": \"Stoppage Hrs. Worked\",\n" +
            "                \"OnDate\": 23.917,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 131.32\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 160,\n" +
            "                \"Para_Name\": \"Stoppage Hrs. Available\",\n" +
            "                \"OnDate\": 24.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 133.0\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 192,\n" +
            "                \"Para_Name\": \"Condensate Return\",\n" +
            "                \"OnDate\": 92.13,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 85.57\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 271,\n" +
            "                \"Para_Name\": \"Sugar in Process\",\n" +
            "                \"OnDate\": -312.42,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 25313.79\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 339,\n" +
            "                \"Para_Name\": \"Sugar Colour IU/8\",\n" +
            "                \"OnDate\": 92.1667,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 86.78\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 362,\n" +
            "                \"Para_Name\": \"Primary Juice Brix\",\n" +
            "                \"OnDate\": 17.31,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 17.27\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 364,\n" +
            "                \"Para_Name\": \"Primary Juice Purity\",\n" +
            "                \"OnDate\": 80.13,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 80.02\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 378,\n" +
            "                \"Para_Name\": \"Mixed Juice Brix\",\n" +
            "                \"OnDate\": 12.57,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 12.46\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 380,\n" +
            "                \"Para_Name\": \"Mixed Juice Purity\",\n" +
            "                \"OnDate\": 78.68,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 78.49\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 386,\n" +
            "                \"Para_Name\": \"Last Mill Juice Brix\",\n" +
            "                \"OnDate\": 1.1,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 1.12\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 394,\n" +
            "                \"Para_Name\": \"Clear Juice Brix\",\n" +
            "                \"OnDate\": 12.72,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 12.63\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 396,\n" +
            "                \"Para_Name\": \"Clear Juice Purity\",\n" +
            "                \"OnDate\": 77.91,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 77.59\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 420,\n" +
            "                \"Para_Name\": \"Final Molasses Purity\",\n" +
            "                \"OnDate\": 37.4527,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 35.51\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 539,\n" +
            "                \"Para_Name\": \"Bagasse Pol\",\n" +
            "                \"OnDate\": 1.4,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 1.4\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 547,\n" +
            "                \"Para_Name\": \"Filter Cake Pol \",\n" +
            "                \"OnDate\": 1.32,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 1.33\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 881,\n" +
            "                \"Para_Name\": \"Pol In Cane %\",\n" +
            "                \"OnDate\": 11.5,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 11.36\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 884,\n" +
            "                \"Para_Name\": \"Moist in Baggasse %\",\n" +
            "                \"OnDate\": 47.58,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 47.73\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 1001,\n" +
            "                \"Para_Name\": \"Biocide Percent\",\n" +
            "                \"OnDate\": 0.059,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 0.07\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 1002,\n" +
            "                \"Para_Name\": \"Lime Percent\",\n" +
            "                \"OnDate\": 0.193,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 0.21\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 1004,\n" +
            "                \"Para_Name\": \"Colour Ppt. Percent\",\n" +
            "                \"OnDate\": 0.071,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 0.06\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 1005,\n" +
            "                \"Para_Name\": \"Sulphur Percent\",\n" +
            "                \"OnDate\": 0.065,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 0.08\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 1006,\n" +
            "                \"Para_Name\": \"Phos.Acid Percent\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 0.0\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2001,\n" +
            "                \"Para_Name\": \"RS from CH\",\n" +
            "                \"OnDate\": 134.03,\n" +
            "                \"MonthToDate\": 309.852,\n" +
            "                \"ToDate\": 16304.88\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2003,\n" +
            "                \"Para_Name\": \"Et-OH from CH\",\n" +
            "                \"OnDate\": 127.584,\n" +
            "                \"MonthToDate\": 294.95,\n" +
            "                \"ToDate\": 8621.31\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2005,\n" +
            "                \"Para_Name\": \"ENA from CH\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 7315.2\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2008,\n" +
            "                \"Para_Name\": \"Yield in BL\",\n" +
            "                \"OnDate\": 226.1783,\n" +
            "                \"MonthToDate\": 222.852,\n" +
            "                \"ToDate\": 247.95\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2009,\n" +
            "                \"Para_Name\": \"Yield in AL\",\n" +
            "                \"OnDate\": 215.3,\n" +
            "                \"MonthToDate\": 212.133,\n" +
            "                \"ToDate\": 236.02\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2010,\n" +
            "                \"Para_Name\": \"Recovery BL/Unit TRS\",\n" +
            "                \"OnDate\": 4.87,\n" +
            "                \"MonthToDate\": 4.8,\n" +
            "                \"ToDate\": 5.03\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2011,\n" +
            "                \"Para_Name\": \"TRS\",\n" +
            "                \"OnDate\": 44.24,\n" +
            "                \"MonthToDate\": 44.2,\n" +
            "                \"ToDate\": 46.27\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2012,\n" +
            "                \"Para_Name\": \"UFS\",\n" +
            "                \"OnDate\": 5.76,\n" +
            "                \"MonthToDate\": 5.66,\n" +
            "                \"ToDate\": 5.59\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2013,\n" +
            "                \"Para_Name\": \"R.S % In Wash\",\n" +
            "                \"OnDate\": 44.24,\n" +
            "                \"MonthToDate\": 6.95,\n" +
            "                \"ToDate\": 120.9\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2014,\n" +
            "                \"Para_Name\": \"FE\",\n" +
            "                \"OnDate\": 88.73,\n" +
            "                \"MonthToDate\": 88.617,\n" +
            "                \"ToDate\": 89.59\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2015,\n" +
            "                \"Para_Name\": \"DE\",\n" +
            "                \"OnDate\": 97.74,\n" +
            "                \"MonthToDate\": 96.257,\n" +
            "                \"ToDate\": 97.62\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2016,\n" +
            "                \"Para_Name\": \"Wash Alco.\",\n" +
            "                \"OnDate\": 8.34,\n" +
            "                \"MonthToDate\": 7.907,\n" +
            "                \"ToDate\": 8.73\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2017,\n" +
            "                \"Para_Name\": \"Ferm Alco.\",\n" +
            "                \"OnDate\": 9.26,\n" +
            "                \"MonthToDate\": 8.94,\n" +
            "                \"ToDate\": 9.5\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2018,\n" +
            "                \"Para_Name\": \"Spent Wash Generation\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 153468.0\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2019,\n" +
            "                \"Para_Name\": \"Slop Cons MT\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 46286.0\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2020,\n" +
            "                \"Para_Name\": \"SLOP Brix\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 4212.67\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2021,\n" +
            "                \"Para_Name\": \"SLOP Production\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 44453.8\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2023,\n" +
            "                \"Para_Name\": \"E.A Steam consum./Ton.\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 18.08,\n" +
            "                \"ToDate\": 829.15\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2024,\n" +
            "                \"Para_Name\": \"E.A Power consum./Ton.\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.646,\n" +
            "                \"ToDate\": 44.19\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2026,\n" +
            "                \"Para_Name\": \"C-Heavy Molasses\",\n" +
            "                \"OnDate\": 6050.0,\n" +
            "                \"MonthToDate\": 20800.0,\n" +
            "                \"ToDate\": 273323.5\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2027,\n" +
            "                \"Para_Name\": \"RS from BH\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 22800.62\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2029,\n" +
            "                \"Para_Name\": \"Et-OH from BH\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 20623.02\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2031,\n" +
            "                \"Para_Name\": \"ENA from BH\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 265.69\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2051,\n" +
            "                \"Para_Name\": \"B- Heavy Molasses\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 122130.0\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2052,\n" +
            "                \"Para_Name\": \"RS from Syrup\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 3209.35\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2054,\n" +
            "                \"Para_Name\": \"Et-OH from Syrup\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 2598.09\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2055,\n" +
            "                \"Para_Name\": \"R.S.\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 32.48\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2056,\n" +
            "                \"Para_Name\": \"ENA from Syrup\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 472.28\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2076,\n" +
            "                \"Para_Name\": \"Sugar Cane Syrup\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 0.0\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2077,\n" +
            "                \"Para_Name\": \"RS\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 29803.48\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2079,\n" +
            "                \"Para_Name\": \"Absolute Alco\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 27594.96\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2081,\n" +
            "                \"Para_Name\": \"ENA\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 0.0\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2084,\n" +
            "                \"Para_Name\": \"Yield\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 628.19\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2085,\n" +
            "                \"Para_Name\": \"Yield/Ton @/ Abs.\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 597.98\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2088,\n" +
            "                \"Para_Name\": \"Starch Grain\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 60.16\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2089,\n" +
            "                \"Para_Name\": \"R.Starch\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 21.71\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2090,\n" +
            "                \"Para_Name\": \"Steam Cons./KL RS\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 16.54\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2091,\n" +
            "                \"Para_Name\": \"FE%\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 140.7\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2092,\n" +
            "                \"Para_Name\": \"DE%\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 155.29\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2093,\n" +
            "                \"Para_Name\": \"Alcohol in Wash\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 12.11\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2094,\n" +
            "                \"Para_Name\": \"Alcohol % in Fermenters Avergae\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 12.11\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2096,\n" +
            "                \"Para_Name\": \"Power Consumption\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 8862.5\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2097,\n" +
            "                \"Para_Name\": \"Steam Consumption\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 97952.11\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2099,\n" +
            "                \"Para_Name\": \"Power Cons MW/KL RS\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 53314.72\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2100,\n" +
            "                \"Para_Name\": \"CO2\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 7058.65\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2103,\n" +
            "                \"Para_Name\": \"Ethyl Acetate Production ondate (MT) New Plant\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 0.0\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2104,\n" +
            "                \"Para_Name\": \"Ethyl Acetate Production ondate (MT) Old Plant\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 372.5,\n" +
            "                \"ToDate\": 29119.0\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2105,\n" +
            "                \"Para_Name\": \"E.A (MT)\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 372.5,\n" +
            "                \"ToDate\": 29119.0\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2106,\n" +
            "                \"Para_Name\": \"Acetic Acid\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 708.5,\n" +
            "                \"ToDate\": 1403.96\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2107,\n" +
            "                \"Para_Name\": \"SDS Imported\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 696.0,\n" +
            "                \"ToDate\": 698.92\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2108,\n" +
            "                \"Para_Name\": \"SDS factor@99.2 %/ Ton EA\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 696.0,\n" +
            "                \"ToDate\": 698.92\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2109,\n" +
            "                \"Para_Name\": \"Acid Factor@99.65%/ Ton E.A\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 708.5,\n" +
            "                \"ToDate\": 1403.96\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2113,\n" +
            "                \"Para_Name\": \"Protein In DDGs on Maize\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 4539.87\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2117,\n" +
            "                \"Para_Name\": \"Starch %\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 60.16\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2120,\n" +
            "                \"Para_Name\": \"D.DGs Stock  Qtl. On Maize max.\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 634453.7\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2121,\n" +
            "                \"Para_Name\": \"D.D.Gs Prod. Qtl on Maize\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 150347.0\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2127,\n" +
            "                \"Para_Name\": \"Grain Cons.( Corn)\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 426937.0\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2177,\n" +
            "                \"Para_Name\": \"R.S Prod.(KL)\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 29803.48\n" +
            "            },\n" +
            "            {\n" +
            "                \"Para_Code\": 2179,\n" +
            "                \"Para_Name\": \"Absolute Alco Prod (KL)\",\n" +
            "                \"OnDate\": 0.0,\n" +
            "                \"MonthToDate\": 0.0,\n" +
            "                \"ToDate\": 27594.96\n" +
            "            }\n" +
            "        ]\n" +
            "    }\n" +
            "}";
}
