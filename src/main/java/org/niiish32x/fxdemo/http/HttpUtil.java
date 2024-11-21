package org.niiish32x.fxdemo.http;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;


import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Slf4j
public class HttpUtil {
    /**
     * 测试环境URL
     */
    public static final String TEST_URL = "http://192.168.31.223:8080/open-api/supos/oodm/v2/attributes/current";

    /**
     * 生成环境URL
     */
    public static final String PROD_URL = "http://192.168.31.223:8080/open-api/supos/oodm/v2/attributes/current";

    public static final String PROD_PREFIX = "sugarapi_sugarapi.CMS.CANE_MANAGEMENT_SYSTEM_SUGAR.sugarapi_sugarapi.";

    public static final String TEST_PREFIX = "sugarplant_sugarplant.CMS.CANE_MANAGEMENT_SYSTEM_SUGAR.sugarplant_sugarplant.";


    private static final String DEFAULT_CHARSET = "UTF-8";

    public static String getHttpRequestUrl(String host, String path, int port) {
        StringBuilder sb = new StringBuilder();
        sb.append("http://");
        sb.append(host);
        sb.append(":");
        sb.append(port);
        sb.append(path);
        return sb.toString();
    }

    public static String buildHttpRequestUrlWithParam(String httpRequestUrl,
                                                      Map<String, Object> paramMap) {
        if (MapUtils.isEmpty(paramMap)) {
            return httpRequestUrl;
        }
        StringBuilder stringBuilder = new StringBuilder(httpRequestUrl);
        List<NameValuePair> nvp = genNameValuePairsFromMap(paramMap);
        String paramUrl = URLEncodedUtils.format(nvp, DEFAULT_CHARSET);
        if (StringUtils.isNotBlank(paramUrl)) {
            stringBuilder.append("?");
            stringBuilder.append(paramUrl);
        }
        return stringBuilder.toString();
    }

    public static String httpGet(String url) throws IOException {
        CloseableHttpClient httpclient = HttpClientUtil.getHttpClient();
        HttpGet httpget = new HttpGet(url);
        try (CloseableHttpResponse response = httpclient.execute(httpget)) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                return EntityUtils.toString(entity);
            }
        }
        return null;
    }

    private static HttpGet buildHttpGet(String url, int timeout) {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("User-Agent",
                "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.0.3) Gecko/2008092417 Firefox/3.0.3");
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout)
                .setConnectTimeout(timeout).setConnectionRequestTimeout(5000).build();
        httpGet.setConfig(requestConfig);
        return httpGet;
    }


//    public static HttpResponseData executeHttpGetWithParam(String url, Map<String, Object> params,
//                                                           int timeout) {
//        List<NameValuePair> nvp = genNameValuePairsFromMap(params);
//        String paramUrl = URLEncodedUtils.format(nvp, DEFAULT_CHARSET);
//        if (StringUtils.isNotBlank(paramUrl)) {
//            url += ("?" + paramUrl);
//        }
//        HttpGet httpGet = buildHttpGet(url, timeout);
//        CloseableHttpClient httpClient = MusicHttpClient.getHttpClient();
//        try {
//            CloseableHttpResponse response = httpClient.execute(httpGet);
//            HttpResponseData responseData = parseHttpResponse(url, response);
//            if (responseData == null) {
//                log.error("executeHttpGetWithParam.parseHttpResponse error,url:{}", url);
//            }
//            return responseData;
//        } catch (Exception e) {
//            log.error("executeHttpGetWithParam error,url: " + url, e);
//        }
//        return null;
//    }

//    public static HttpResponseData executeHttpGet(String url, int timeout) {
//        CloseableHttpClient httpClient = MusicHttpClient.getHttpClient();
//        HttpGet httpGet = buildHttpGet(url, timeout);
//        try {
//            CloseableHttpResponse response = httpClient.execute(httpGet);
//            HttpResponseData responseData = parseHttpResponse(url, response);
//            if (responseData == null) {
//                log.error("executeHttpGet.parseHttpResponse error,url:{}", url);
//            }
//            return responseData;
//        } catch (Exception e) {
//            log.error("executeHttpGet error,url: " + url, e);
//        }
//        return null;
//    }

//    public static HttpResponseData executeHttpGetAddHeader(String url, int timeout, Header header) {
//        CloseableHttpClient httpClient = MusicHttpClient.getHttpClient();
//        HttpGet httpGet = buildHttpGet(url, timeout);
//        httpGet.addHeader(header);
//        try {
//            CloseableHttpResponse response = httpClient.execute(httpGet);
//            HttpResponseData responseData = parseHttpResponse(url, response);
//            if (responseData == null) {
//                log.error("executeHttpGet.parseHttpResponse error,url:{}", url);
//            }
//            return responseData;
//        } catch (Exception e) {
//            log.error("executeHttpGet error,url: " + url, e);
//        }
//        return null;
//    }

//    public static HttpResponseData executeHttpGetWithException(String url, int timeout)
//            throws Exception {
//        CloseableHttpClient httpClient = MusicHttpClient.getHttpClient();
//        HttpGet httpGet = buildHttpGet(url, timeout);
//        CloseableHttpResponse response = httpClient.execute(httpGet);
//        HttpResponseData responseData = parseHttpResponse(url, response);
//        if (responseData == null) {
//            log.error("executeHttpGetWithException.parseHttpResponse error,url:{}", url);
//        }
//        return responseData;
//    }

    private static HttpPost buildHttpPost(String url, Map<String, Object> params, int timeout) {
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout)
                .setConnectTimeout(3000).setConnectionRequestTimeout(5000).build();
        httpPost.setConfig(requestConfig);
        httpPost.setHeader("Connection", "close");
        String paramJson = JSON.toJSONString(params);
        StringEntity postEntity = new StringEntity(paramJson, ContentType.APPLICATION_JSON);
        httpPost.setEntity(postEntity);
        return httpPost;
    }




    public static HttpResponseData executeHttpPost(String url, Map<String, Object> params,
                                                   int timeout) {
        CloseableHttpClient httpClient = HttpClientUtil.getHttpClient();
        HttpPost httpPost = buildHttpPost(url, params, timeout);
        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpResponseData responseData = parseHttpResponse(url, response);
            if (responseData == null) {
                log.error("parseHttpResponse error,url:{},params:{}", url, params);
            }
            return responseData;
        } catch (Exception e) {
            log.error(String.format("executeHttpPost error,url: %s,params:%s", url, params), e);
        }
        return null;
    }

    public static HttpResponseData executeHttpPostWithBody(String url, String bodyStr,
                                                           int timeout) {
        CloseableHttpClient httpClient = HttpClientUtil.getHttpClient();
        HttpPost httpPost = buildHttpPostWithBody(url, bodyStr, timeout);
        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpResponseData responseData = parseHttpResponse(url, response);
            if (responseData == null) {
                log.error("executeHttpPostWithBody error,url:{},bodyStr:{}", url, bodyStr);
            }
            return responseData;
        } catch (Exception e) {
            log.error(
                    String.format("executeHttpPostWithBody error,url:%s,bodyStr:%s", url, bodyStr),
                    e);
        }
        return null;
    }

    private static HttpPost buildHttpPostWithBody(String url, String bodyStr, int timeout) {
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout)
                .setConnectTimeout(3000).setConnectionRequestTimeout(5000).build();
        httpPost.setConfig(requestConfig);
        StringEntity postEntity = new StringEntity(bodyStr, ContentType.APPLICATION_JSON);
        httpPost.setEntity(postEntity);
        return httpPost;
    }

    public static HttpResponseData executeHttpPostWithHeaderMap(String url,
                                                                Map<String, Object> params, Map<String, String> headerMap) {
        CloseableHttpClient httpClient = HttpClientUtil.getHttpClient();
        HttpPost httpPost = buildHttpPost(url, params, 30000);
        headerMap.forEach((header, value) -> httpPost.addHeader(header, value));
        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpResponseData responseData = parseHttpResponse(url, response);
            if (responseData == null) {
                log.error("executeHttpPostWithHeaderMap error,url:{},params:{}", url, params);
            }
            return responseData;
        } catch (Exception e) {
            log.error(String.format("executeHttpPostWithHeaderMap error,url: %s,params:%s", url,
                    params), e);
        }
        return null;
    }

    private static HttpPut buildHttpPut(String url, Map<String, Object> params, int timeout) {
        HttpPut httpPut = new HttpPut(url);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeout)
                .setConnectTimeout(3000).setConnectionRequestTimeout(5000).build();
        httpPut.setConfig(requestConfig);
        httpPut.setHeader("Connection", "close");
        String paramJson = JSON.toJSONString(params);
        StringEntity postEntity = new StringEntity(paramJson, ContentType.APPLICATION_JSON);
        httpPut.setEntity(postEntity);
        return httpPut;
    }


    public static HttpResponseData executeHttpPutWithHeaderMap(String url,
                                                               Map<String, Object> params, Map<String, String> headerMap) {
        CloseableHttpClient httpClient = HttpClientUtil.getHttpClient();
        HttpPut httpPut = buildHttpPut(url, params, 30000);
        headerMap.forEach((header, value) -> httpPut.addHeader(header, value));
        try {
            CloseableHttpResponse response = httpClient.execute(httpPut);
            HttpResponseData responseData = parseHttpResponse(url, response);
            if (responseData == null) {
                log.error("executeHttpPostWithHeaderMap error,url:{},params:{}", url, params);
            }
            return responseData;
        } catch (Exception e) {
            log.error(String.format("executeHttpPostWithHeaderMap error,url: %s,params:%s", url,
                    params), e);
        }
        return null;
    }


    private static HttpResponseData parseHttpResponse(String requestUrl,
                                                      CloseableHttpResponse response)
            throws Exception {
        try {
            HttpResponseData metaData = new HttpResponseData();
            int statusCode = response.getStatusLine().getStatusCode();
            metaData.setStatusCode(statusCode);
            if (HttpStatus.SC_OK == statusCode
                    || HttpStatus.SC_CREATED == statusCode
                    || HttpStatus.SC_CREATED == statusCode
                    || 403 == statusCode) {
                HttpEntity entity = response.getEntity();
                String content = EntityUtils.toString(entity, "UTF-8");
                metaData.setResponseText(content);
                return metaData;
            } else {
                log.error("parseHttpResponse error, url:{},responseCode:{}", requestUrl,
                        statusCode);
            }
            return metaData;
        } catch (Exception e) {
            log.error("parseHttpResponse error,url: " + requestUrl, e);
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return null;

    }

    private static List<NameValuePair> genNameValuePairsFromMap(Map<String, Object> params) {
        List<NameValuePair> nvp = new LinkedList<>();
        if (params != null && params.size() > 0) {
            for (String key : params.keySet()) {
                nvp.add(new BasicNameValuePair(key, String.valueOf(params.get(key))));
            }
        }
        return nvp;
    }

//    public static JSONObject httpGet(String url, int timeout) {
//        HttpResponseData httpResponseData = executeHttpGet(url, timeout);
//        if (null == httpResponseData) {
//            return null;
//        }
//        if (httpResponseData.getStatusCode() == 200) {
//            String responseTxt = httpResponseData.getResponseText();
//            if (StringUtils.isNotBlank(responseTxt)) {
//                int realCode = JsonUtils.path(responseTxt, "code").asInt();
//                if (realCode == 200) {
//                    return JSONObject.parseObject(responseTxt);
//                } else {
//                    log.error("send http failed, respText:{}", responseTxt);
//                }
//            }
//        } else {
//            log.error("send http failed, with http status code: {}, respText:{}",
//                    httpResponseData.getStatusCode(), httpResponseData.getResponseText());
//        }
//        return null;
//    }

    public static HttpResponseData executeHttpGetWithHeaderMap(String url,
                                                               Map<String, Object> params, Map<String, String> headerMap) {
        CloseableHttpClient httpClient = HttpClientUtil.getHttpClient();
        String httpRequestUrl = buildHttpRequestUrlWithParam(url, params);
        HttpGet httpGet = buildHttpGet(httpRequestUrl, 2000);
        headerMap.forEach((header, value) -> httpGet.addHeader(header, value));
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpResponseData responseData = parseHttpResponse(url, response);
            if (responseData == null) {
                log.error("executeHttpGetWithHeaderMap error,url:{},params:{}", url, params);
            }
            return responseData;
        } catch (Exception e) {
            log.error(String.format("executeHttpGetWithHeaderMap error,url: %s,params:%s", url,
                    params), e);
        }
        return null;
    }

    public static HttpResponseData executeHttpGetWithHeaderMap(String url, Map<String, String> headerMap) {
        CloseableHttpClient httpClient = HttpClientUtil.getHttpClient();
        HttpGet httpGet = buildHttpGet(url, 2000);
        headerMap.forEach((header, value) -> httpGet.addHeader(header, value));
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpResponseData responseData = parseHttpResponse(url, response);
            if (responseData == null) {
                log.error("executeHttpGetWithHeaderMap error,url:{}", url);
            }
            return responseData;
        } catch (Exception e) {
            log.error(String.format("executeHttpGetWithHeaderMap error,url: %s", url), e);
        }
        return null;
    }

}