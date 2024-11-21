package org.niiish32x.fxdemo.http;

import lombok.Data;

@Data
public class HttpResponseData {
    private int statusCode;
    private String responseText;
}
