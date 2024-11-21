package org.niiish32x.fxdemo.dto.item;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemToDateDto {
    String name;
    double toDate;
}
