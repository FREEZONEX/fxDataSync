package org.niiish32x.fxdemo.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.List;

@Data
public class DMRResponseDto {
    @JSONField(name = "DMRReturn")
    List<DMRDto>  dto;
}
