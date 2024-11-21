package org.niiish32x.fxdemo.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

@Data
public class DMRDto {
    @JSONField(name = "Para_Code")
    private int paraCode;
    @JSONField(name = "Para_Name")
    private String paraName;
    @JSONField(name = "OnDate")
    private double onDate;
    @JSONField(name = "MonthToDate")
    private double monthToDate;
    @JSONField(name = "ToDate")
    private double toDate;

}
