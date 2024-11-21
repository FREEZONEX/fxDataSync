package org.niiish32x.fxdemo.dto.item;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

@Data
public class ItemDto {
    @JSONField(name = "name")
    private String name;
    @JSONField(name = "paraCode")
    private String paraCode;

    @JSONField(serialize = false)
    private boolean onDate;
    @JSONField(serialize = false)
    private boolean toDate;

}
