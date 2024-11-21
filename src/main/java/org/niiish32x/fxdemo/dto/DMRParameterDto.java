package org.niiish32x.fxdemo.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

@Data
public class DMRParameterDto {
    @JSONField(name="Error_Flag")
    String errorFlag;

    @JSONField(name = "Error_Message")
    String errorMessage;

    @JSONField(name = "Response")
    DMRResponseDto responseDto;
}
