package org.niiish32x.fxdemo.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.niiish32x.fxdemo.dto.item.ItemDto;

import java.util.List;

public class ReadJsonTool {
    public final static List <String> RULE_COLLECTION
            = Lists.newArrayList(ReadJsonTool.lmsSugarJsonData,
                                ReadJsonTool.lmsPowerJsonData,
                                ReadJsonTool.lmsGrainJsonData,
                                ReadJsonTool.lmsDistilleryJsonData) ;

    public static List<ItemDto> readDataFromJson(String JsonData) {
        List<ItemDto> itemDtos = JSON.parseArray(JsonData, ItemDto.class);

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

    public static final String lmsDistilleryJsonData = "[\n" +
            "{\"name\":\"RS_Production_KL_CH_on_Date\",\"paraCode\":2001},\n" +
            "{\"name\":\"Ethanol_Production__KL_CH_on_Date\",\"paraCode\":2003},\n" +
            "{\"name\":\"ENA_Production_KL_CH_on_Date\",\"paraCode\":2005},\n" +
            "{\"name\":\"Yield_in_BL_on_Date\",\"paraCode\":2008},\n" +
            "{\"name\":\"Yield_in_BL_AL_on_Date\",\"paraCode\":2009},\n" +
            "{\"name\":\"Recovery_in_Terms_of_R_S__on__CH_Molasses_on_Date\",\"paraCode\":80},\n" +
            "{\"name\":\"Recovery_Factor__on_Ethanol_on_Date\",\"paraCode\":2010},\n" +
            "{\"name\":\"TRS_on_Date\",\"paraCode\":2011},\n" +
            "{\"name\":\"UFS_Unfermented_Sugar_on_Date\",\"paraCode\":2012},\n" +
            "{\"name\":\"Rs_in_Wash_on_Date\",\"paraCode\":2013},\n" +
            "{\"name\":\"FE_on_Date\",\"paraCode\":2014},\n" +
            "{\"name\":\"DE_on_Date\",\"paraCode\":2015},\n" +
            "{\"name\":\"Alcohol_in_Wash_on_Date\",\"paraCode\":2016},\n" +
            "{\"name\":\"Alcohol_in_Fermenters_Average_on_Date\",\"paraCode\":2017},\n" +
            "{\"name\":\"Spent_wash_Gen_on_Date\",\"paraCode\":2018},\n" +
            "{\"name\":\"Slop_Cons_in_Boiler_on_Date\",\"paraCode\":2019},\n" +
            "{\"name\":\"Slop_Brix_on_Date\",\"paraCode\":2020},\n" +
            "{\"name\":\"Slope_Gen_55_0_Brix_on_Date\",\"paraCode\":2021},\n" +
            "{\"name\":\"RS_Production_BH_on_Date\",\"paraCode\":2027},\n" +
            "{\"name\":\"Ethanol_Production_BH_on_Date\",\"paraCode\":2029},\n" +
            "{\"name\":\"ENA_Production_BH_on_Date\",\"paraCode\":2031},\n" +
            "{\"name\":\"Recovery_in_Terms_of_RS_BH_on_Date\",\"paraCode\":81},\n" +
            "{\"name\":\"Recovery_Factor_on_Ethanol_BH_on_Date\",\"paraCode\":2010},\n" +
            "{\"name\":\"RS_Production_Syrup_on_Date\",\"paraCode\":2052},\n" +
            "{\"name\":\"Ethanol_Production_Syrup_on_Date\",\"paraCode\":2054},\n" +
            "{\"name\":\"ENA_Production_Syrup_on_Date\",\"paraCode\":2056},\n" +
            "{\"name\":\"Recovery_in_Terms_of_RS_Syrup_on_Date\",\"paraCode\":82},\n" +
            "{\"name\":\"Sugar_Cane_Syrup_on_Date\",\"paraCode\":2076},\n" +
            "{\"name\":\"Absolute_Alcohol_on_Date\",\"paraCode\":2079},\n" +
            "{\"name\":\"Yield_on_Date\",\"paraCode\":2084},\n" +
            "{\"name\":\"Yield_Ton_Abs_on_Date\",\"paraCode\":2085},\n" +
            "{\"name\":\"RS_Production_Grain_Maize_Rice_on_Date\",\"paraCode\":2177},\n" +
            "{\"name\":\"Absolute_Alcohol_Production_on_Date\",\"paraCode\":2179},\n" +
            "{\"name\":\"R_Starch_on_Date\",\"paraCode\":2089},\n" +
            "{\"name\":\"Starch_Grain_Maize_Rice_on_Date\",\"paraCode\":2088},\n" +
            "{\"name\":\"FE_Grain_Maize_Rice_on_Date\",\"paraCode\":2091},\n" +
            "{\"name\":\"DE_Grain_Maize_Rice_on_Date\",\"paraCode\":2092},\n" +
            "{\"name\":\"Alcohol_in_Wash_Grain_Maize_Rice_on_Date\",\"paraCode\":2093},\n" +
            "{\"name\":\"Alcohol_in_Fermenters_Average_Grain_Maize_Rice_on_Date\",\"paraCode\":2094},\n" +
            "{\"name\":\"Energy_Consumption_Grain_Maize_Rice_on_Date\",\"paraCode\":2096},\n" +
            "{\"name\":\"Steam_consumption_on_RS_Grain_Maize_Rice_on_Date\",\"paraCode\":2090},\n" +
            "{\"name\":\"Power_Consumption_on_Ethanol_Grain_Maize_Rice_on_Date\",\"paraCode\":2099},\n" +
            "{\"name\":\"Total_Steam_Consumption_on_Date\",\"paraCode\":2097},\n" +
            "{\"name\":\"CO2_Production_on_Date\",\"paraCode\":2100},\n" +
            "{\"name\":\"Ethyl_Acetate_Production_New_Plant_on_Date\",\"paraCode\":2103},\n" +
            "{\"name\":\"Ethyl_Acetate_Production_Old_Plant_on_Date\",\"paraCode\":2104},\n" +
            "{\"name\":\"Ethyl_acetate_Total_Prod_on_Date\",\"paraCode\":2105},\n" +
            "{\"name\":\"Acetic_Acid_on_Date\",\"paraCode\":2106},\n" +
            "{\"name\":\"Acid_Factor99_60_Ton_E_A_on_Date\",\"paraCode\":2109},\n" +
            "{\"name\":\"SDS_factor99_Ton_EA_on_Date\",\"paraCode\":2108},\n" +
            "{\"name\":\"SDS_Imported_on_Date\",\"paraCode\":2107},\n" +
            "{\"name\":\"E_A_Steam_consum_4_5_Kg_cm2_in_Ton_Ton_of_EA_on_Date\",\"paraCode\":2023},\n" +
            "{\"name\":\"E_A_Power_Consum_In_MWH_Per_ton_of_EA_on_Date\",\"paraCode\":2024},\n" +
            "{\"name\":\"RS_Production_KL_CH_to_Date\",\"paraCode\":2001},\n" +
            "{\"name\":\"Ethanol_Production_KL_CH_to_Date\",\"paraCode\":2003},\n" +
            "{\"name\":\"ENA_Production_KL_CH_to_Date\",\"paraCode\":2005},\n" +
            "{\"name\":\"Yield_in_BL_to_Date\",\"paraCode\":2008},\n" +
            "{\"name\":\"Yield_in_BL_AL_to_Date\",\"paraCode\":2009},\n" +
            "{\"name\":\"Recovery_in_Terms_of_R_S_on_CH_Molasses_to_Date\",\"paraCode\":80},\n" +
            "{\"name\":\"Recovery_Factor_on_Ethanol_to_Date\",\"paraCode\":2010},\n" +
            "{\"name\":\"TRS_to_Date\",\"paraCode\":2011},\n" +
            "{\"name\":\"UFS_Unfermented_Sugar_to_Date\",\"paraCode\":2012},\n" +
            "{\"name\":\"Rs_in_Wash_to_Date\",\"paraCode\":2013},\n" +
            "{\"name\":\"FE_to_Date\",\"paraCode\":2014},\n" +
            "{\"name\":\"DE_to_Date\",\"paraCode\":2015},\n" +
            "{\"name\":\"Alcohol_in_Wash_to_Date\",\"paraCode\":2016},\n" +
            "{\"name\":\"Alcohol_in_Fermenters_Average_to_Date\",\"paraCode\":2017},\n" +
            "{\"name\":\"Spent_wash_Gen_to_Date\",\"paraCode\":2018},\n" +
            "{\"name\":\"Slop_Cons_in_Boiler_to_Date\",\"paraCode\":2019},\n" +
            "{\"name\":\"Slop_Brix_to_Date\",\"paraCode\":2020},\n" +
            "{\"name\":\"Slope_Gen_55_0_Brix_to_Date\",\"paraCode\":2021},\n" +
            "{\"name\":\"RS_Production_BH_to_Date\",\"paraCode\":2027},\n" +
            "{\"name\":\"Ethanol_Production_BH_to_Date\",\"paraCode\":2029},\n" +
            "{\"name\":\"ENA_Production_BH_to_Date\",\"paraCode\":2031},\n" +
            "{\"name\":\"Recovery_in_Terms_of_RS_BH_to_Date\",\"paraCode\":81},\n" +
            "{\"name\":\"Recovery_Factor_on_Ethanol__BH_to_Date\",\"paraCode\":2010},\n" +
            "{\"name\":\"RS_Production_Syrup_to_Date\",\"paraCode\":2052},\n" +
            "{\"name\":\"Ethanol_Production_Syrup_to_Date\",\"paraCode\":2054},\n" +
            "{\"name\":\"ENA_Production_Syrup_to_Date\",\"paraCode\":2056},\n" +
            "{\"name\":\"Recovery_in_Terms_of_RS_Syrup_to_Date\",\"paraCode\":82},\n" +
            "{\"name\":\"Sugar_Cane_Syrup_to_Date\",\"paraCode\":2076},\n" +
            "{\"name\":\"Absolute_Alcohol_to_Date\",\"paraCode\":2079},\n" +
            "{\"name\":\"Yield_to_Date\",\"paraCode\":2084},\n" +
            "{\"name\":\"Yield_Ton_Abs_to_Date\",\"paraCode\":2085},\n" +
            "{\"name\":\"RS_Production_Grain_Maize_Rice_to_Date\",\"paraCode\":2177},\n" +
            "{\"name\":\"Absolute_Alcohol_Production_to_Date\",\"paraCode\":2179},\n" +
            "{\"name\":\"R_Starch_to_Date\",\"paraCode\":2089},\n" +
            "{\"name\":\"Starch_Grain_Maize_Rice_to_Date\",\"paraCode\":2088},\n" +
            "{\"name\":\"FE_Grain_Maize_Rice_to_Date\",\"paraCode\":2091},\n" +
            "{\"name\":\"DE_Grain_Maize_Rice_to_Date\",\"paraCode\":2092},\n" +
            "{\"name\":\"Alcohol_in_Wash_Grain_Maize_Rice_to_Date\",\"paraCode\":2093},\n" +
            "{\"name\":\"Alcohol_in_Fermenters_Average_Grain_Maize_Rice_to_Date\",\"paraCode\":2094},\n" +
            "{\"name\":\"Energy_Consumption_Grain_Maize_Rice_to_Date\",\"paraCode\":2096},\n" +
            "{\"name\":\"Steam_consumption_on_RS_Grain_Maize_Rice_to_Date\",\"paraCode\":2090},\n" +
            "{\"name\":\"Power_Consumption_on_Ethanol_Grain_Maize_Rice_to_Date\",\"paraCode\":2099},\n" +
            "{\"name\":\"Total_Steam_Consumption_to_Date\",\"paraCode\":2097},\n" +
            "{\"name\":\"CO2_Production_to_Date\",\"paraCode\":2100},\n" +
            "{\"name\":\"Ethyl_Acetate_Production_New_Plant_to_Date\",\"paraCode\":2103},\n" +
            "{\"name\":\"Ethyl_Acetate_Production_Old_Plant_to_Date\",\"paraCode\":2104},\n" +
            "{\"name\":\"Ethyl_acetate_Total_Prod_to_Date\",\"paraCode\":2105},\n" +
            "{\"name\":\"Acetic_Acid_to_Date\",\"paraCode\":2106},\n" +
            "{\"name\":\"Acid_Factor99_60_Ton_E_A_to_Date\",\"paraCode\":2109},\n" +
            "{\"name\":\"SDS_factor99_Ton_EA_to_Date\",\"paraCode\":2108},\n" +
            "{\"name\":\"SDS_Imported_to_Date\",\"paraCode\":2107},\n" +
            "{\"name\":\"E_A_Steam_consum_4_5_Kg_cm2_in_Ton_Ton_of_EA_to_Date\",\"paraCode\":2023},\n" +
            "{\"name\":\"E_A_Power_Consum_In_MWH_Per_ton_of_EA_to_Date\",\"paraCode\":2024},\n" +
            "\n" +
            "]";

    public static final String lmsGrainJsonData = "[\n" +
            "{\"name\":\"Steam_Fuel_Ratio_P1_on_Date\",\"paraCode\":83},\n" +
            "{\"name\":\"Steam_Fuel_Ratio_P2_on_Date\",\"paraCode\":84},\n" +
            "{\"name\":\"Steam_Fuel_Ratio_75_TPH_on_Date\",\"paraCode\":86},\n" +
            "{\"name\":\"Steam_Fuel_Ratio_55_TPH_on_Date\",\"paraCode\":85},\n" +
            "{\"name\":\"Coal_Consumed_P1_on_Date\",\"paraCode\":87},\n" +
            "{\"name\":\"Coal_Consumed_P2_on_Date\",\"paraCode\":88},\n" +
            "{\"name\":\"Coal_Consumed_55_TPH_on_Date\",\"paraCode\":89},\n" +
            "{\"name\":\"Coal_Consumed_75_TPH_on_Date\",\"paraCode\":90},\n" +
            "{\"name\":\"Condensate_Return_on_Date\",\"paraCode\":192},\n" +
            "{\"name\":\"Steam_Losses_P1_on_Date\",\"paraCode\":91},\n" +
            "{\"name\":\"Steam_Losses_P2_on_Date\",\"paraCode\":92},\n" +
            "{\"name\":\"Steam_Losses_55_TPH_on_Date\",\"paraCode\":93},\n" +
            "{\"name\":\"Steam_Losses_75_TPH_on_Date\",\"paraCode\":94},\n" +
            "{\"name\":\"Fuel_Losses_P1_on_Date\",\"paraCode\":95},\n" +
            "{\"name\":\"Fuel_Losses_P2_on_Date\",\"paraCode\":96},\n" +
            "{\"name\":\"Fuel_Losses_55_TPH_on_Date\",\"paraCode\":97},\n" +
            "{\"name\":\"Fuel_Losses_75_TPH_on_Date\",\"paraCode\":98},\n" +
            "{\"name\":\"Steam_Fuel_Ratio_P1_to_Date\",\"paraCode\":83},\n" +
            "{\"name\":\"Steam_Fuel_Ratio_P2_to_Date\",\"paraCode\":84},\n" +
            "{\"name\":\"Steam_Fuel_Ratio_75_TPH_to_Date\",\"paraCode\":86},\n" +
            "{\"name\":\"Steam_Fuel_Ratio_55_TPH_to_Date\",\"paraCode\":85},\n" +
            "{\"name\":\"Coal_Consumed_P1_to_Date\",\"paraCode\":87},\n" +
            "{\"name\":\"Coal_Consumed_P2_to_Date\",\"paraCode\":88},\n" +
            "{\"name\":\"Coal_Consumed_55_TPH_to_Date\",\"paraCode\":89},\n" +
            "{\"name\":\"Coal_Consumed_75_TPH_to_Date\",\"paraCode\":90},\n" +
            "{\"name\":\"Condensate_Return_to_Date\",\"paraCode\":192},\n" +
            "{\"name\":\"Steam_Losses_P1_to_Date\",\"paraCode\":91},\n" +
            "{\"name\":\"Steam_Losses_P2_to_Date\",\"paraCode\":92},\n" +
            "{\"name\":\"Steam_Losses_55_TPH_to_Date\",\"paraCode\":93},\n" +
            "{\"name\":\"Steam_Losses_75_TPH_to_Date\",\"paraCode\":94},\n" +
            "{\"name\":\"Fuel_Losses_P1_to_Date\",\"paraCode\":95},\n" +
            "{\"name\":\"Fuel_Losses_P2_to_Date\",\"paraCode\":96},\n" +
            "{\"name\":\"Fuel_Losses_55_TPH_to_Date\",\"paraCode\":97},\n" +
            "{\"name\":\"Fuel_Losses_75_TPH_to_Date\",\"paraCode\":98},\n" +
            "\n" +
            "]";





    public static final String lmsPowerJsonData = "[\n" +
            "{\"name\":\"Steam_Fuel_Ratio_P1_on_Date\",\"paraCode\":83},\n" +
            "{\"name\":\"Steam_Fuel_Ratio_P2_on_Date\",\"paraCode\":84},\n" +
            "{\"name\":\"Steam_Fuel_Ratio_75_TPH_on_Date\",\"paraCode\":86},\n" +
            "{\"name\":\"Steam_Fuel_Ratio_55_TPH_on_Date\",\"paraCode\":85},\n" +
            "{\"name\":\"Coal_Consumed_P1_on_Date\",\"paraCode\":87},\n" +
            "{\"name\":\"Coal_Consumed_P2_on_Date\",\"paraCode\":88},\n" +
            "{\"name\":\"Coal_Consumed_55_TPH_on_Date\",\"paraCode\":89},\n" +
            "{\"name\":\"Coal_Consumed_75_TPH_on_Date\",\"paraCode\":90},\n" +
            "{\"name\":\"Condensate_Return_on_Date\",\"paraCode\":192},\n" +
            "{\"name\":\"Steam_Losses_P1_on_Date\",\"paraCode\":91},\n" +
            "{\"name\":\"Steam_Losses_P2_on_Date\",\"paraCode\":92},\n" +
            "{\"name\":\"Steam_Losses_55_TPH_on_Date\",\"paraCode\":93},\n" +
            "{\"name\":\"Steam_Losses_75_TPH_on_Date\",\"paraCode\":94},\n" +
            "{\"name\":\"Fuel_Losses_P1_on_Date\",\"paraCode\":95},\n" +
            "{\"name\":\"Fuel_Losses_P2_on_Date\",\"paraCode\":96},\n" +
            "{\"name\":\"Fuel_Losses_55_TPH_on_Date\",\"paraCode\":97},\n" +
            "{\"name\":\"Fuel_Losses_75_TPH_on_Date\",\"paraCode\":98},\n" +
            "{\"name\":\"Steam_Fuel_Ratio_P1_to_Date\",\"paraCode\":83},\n" +
            "{\"name\":\"Steam_Fuel_Ratio_P2_to_Date\",\"paraCode\":84},\n" +
            "{\"name\":\"Steam_Fuel_Ratio_75_TPH_to_Date\",\"paraCode\":86},\n" +
            "{\"name\":\"Steam_Fuel_Ratio_55_TPH_to_Date\",\"paraCode\":85},\n" +
            "{\"name\":\"Coal_Consumed_P1_to_Date\",\"paraCode\":87},\n" +
            "{\"name\":\"Coal_Consumed_P2_to_Date\",\"paraCode\":88},\n" +
            "{\"name\":\"Coal_Consumed_55_TPH_to_Date\",\"paraCode\":89},\n" +
            "{\"name\":\"Coal_Consumed_75_TPH_to_Date\",\"paraCode\":90},\n" +
            "{\"name\":\"Condensate_Return_to_Date\",\"paraCode\":192},\n" +
            "{\"name\":\"Steam_Losses_P1_to_Date\",\"paraCode\":91},\n" +
            "{\"name\":\"Steam_Losses_P2_to_Date\",\"paraCode\":92},\n" +
            "{\"name\":\"Steam_Losses_55_TPH_to_Date\",\"paraCode\":93},\n" +
            "{\"name\":\"Steam_Losses_75_TPH_to_Date\",\"paraCode\":94},\n" +
            "{\"name\":\"Fuel_Losses_P1_to_Date\",\"paraCode\":95},\n" +
            "{\"name\":\"Fuel_Losses_P2_to_Date\",\"paraCode\":96},\n" +
            "{\"name\":\"Fuel_Losses_55_TPH_to_Date\",\"paraCode\":97},\n" +
            "{\"name\":\"Fuel_Losses_75_TPH_to_Date\",\"paraCode\":98},\n" +
            "\n" +
            "]";


    public static final  String lmsSugarJsonData = "[\n" +
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
