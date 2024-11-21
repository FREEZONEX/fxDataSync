package org.niiish32x.fxdemo.service;

import org.niiish32x.fxdemo.dto.item.ItemDto;
import org.niiish32x.fxdemo.dto.item.ItemReqDto;

import java.util.List;

public interface DataProcessService {
    List<ItemReqDto> processSugarData();

    List<ItemReqDto> processData(List<ItemDto>  dataFromJson);

    List<ItemDto> readSugarDataFromJson();
}
