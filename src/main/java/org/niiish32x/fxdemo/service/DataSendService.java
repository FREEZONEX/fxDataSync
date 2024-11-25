package org.niiish32x.fxdemo.service;

import java.io.IOException;

public interface DataSendService {



    void postToSuposTest(String jsonData, String prefix) throws IOException, InterruptedException;

    public void postToSuposProd(String jsonData) ;
}
