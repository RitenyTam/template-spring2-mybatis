package com.riteny.httpclient;

import com.alibaba.fastjson2.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Set;

public class CommonHttpRestTemplate {

    private static final Logger logger = LoggerFactory.getLogger(CommonHttpRestTemplate.class);

    private String url;

    private HttpHeaders headers = new HttpHeaders();

    private Map<String, Object> requestJson = new JSONObject();

    private Integer connectionTimeout = 10000;

    private Integer readTimeout = 100000;

    private CommonHttpRestTemplate() {
    }

    public static CommonHttpRestTemplate getInstance() {
        return new CommonHttpRestTemplate();
    }

    private RestTemplate getRestTemplate() {

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(connectionTimeout);
        requestFactory.setReadTimeout(readTimeout);

        return new RestTemplate(requestFactory);
    }

    public CommonHttpRestTemplate setUrl(String url) {
        this.url = url;
        return this;
    }

    public CommonHttpRestTemplate setBasicAuth(String username, String password) {
        headers.setBasicAuth(username, password);
        return this;
    }

    public CommonHttpRestTemplate setConnectionTimeout(Integer connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
        return this;
    }

    public CommonHttpRestTemplate setReadTimeout(Integer readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    public CommonHttpRestTemplate setParams(JSONObject params) {
        this.requestJson = params;
        return this;
    }

    public CommonHttpRestTemplate addParam(String key, String value) {
        requestJson.put(key, value);
        return this;
    }

    public CommonHttpRestTemplate addParam(String key, Integer value) {
        requestJson.put(key, value);
        return this;
    }

    public CommonHttpRestTemplate addParam(String key, Long value) {
        requestJson.put(key, value);
        return this;
    }

    public CommonHttpRestTemplate addParam(String key, Boolean value) {
        requestJson.put(key, value);
        return this;
    }

    public CommonHttpRestTemplate addParam(String key, JSONObject value) {
        requestJson.put(key, value);
        return this;
    }

    public CommonHttpRestTemplate addHeader(String key, String value) {
        headers.set(key, value);
        return this;
    }

    public JSONObject executeGet() {

        long startTime = System.currentTimeMillis();
        logger.info("#Request : " + url + " #Method : GET #Param : ");

        try {
            //发送请求
            HttpEntity<String> ans = getRestTemplate(connectionTimeout, readTimeout).exchange(url, HttpMethod.POST, new HttpEntity<>(null, headers), String.class);

            JSONObject responseBody = JSONObject.parseObject(ans.getBody());

            long useTime = System.currentTimeMillis() - startTime;
            logger.info("#Response : " + url + " #Method : GET  #use time(ms) : " + useTime + " #Result : " + responseBody);

            return responseBody;
        } catch (Exception e) {
            long useTime = System.currentTimeMillis() - startTime;
            logger.error("#Response : " + url + " #Method : GET  #use time(ms) : " + useTime + " #Result : " + e.getMessage());
            throw e;
        }
    }

    public JSONObject executePost() {

        long startTime = System.currentTimeMillis();
        logger.info("#Request : " + url + " #Method : POST #Param : " + requestJson);

        try {
            //发送请求
            HttpEntity<String> ans = getRestTemplate(connectionTimeout, readTimeout).exchange(url, HttpMethod.POST, new HttpEntity<>(requestJson, headers), String.class);

            JSONObject responseBody = JSONObject.parseObject(ans.getBody());

            long useTime = System.currentTimeMillis() - startTime;
            logger.info("#Response : " + url + " #Method : POST  #use time(ms) : " + useTime + " #Result : " + responseBody);

            return responseBody;
        } catch (Exception e) {
            long useTime = System.currentTimeMillis() - startTime;
            logger.error("#Response : " + url + " #Method : POST  #use time(ms) : " + useTime + " #Result : " + e.getMessage());
            throw e;
        }
    }

    public JSONObject executePut() {

        long startTime = System.currentTimeMillis();
        logger.info("#Request : " + url + " #Method : PUT #Param : " + requestJson);

        try {
            //发送请求
            HttpEntity<String> ans = getRestTemplate(connectionTimeout, readTimeout).exchange(url, HttpMethod.PUT, new HttpEntity<>(requestJson, headers), String.class);

            JSONObject responseBody = JSONObject.parseObject(ans.getBody());

            long useTime = System.currentTimeMillis() - startTime;
            logger.info("#Response : " + url + " #Method : PUT  #use time(ms) : " + useTime + " #Result : " + responseBody);

            return responseBody;
        } catch (Exception e) {
            long useTime = System.currentTimeMillis() - startTime;
            logger.error("#Response : " + url + " #Method : PUT  #use time(ms) : " + useTime + " #Result : " + e.getMessage());
            throw e;
        }
    }

    public JSONObject executeDel() {
        long startTime = System.currentTimeMillis();
        logger.info("#Request : " + url + " #Method : DEL #Param : " + requestJson);

        try {
            //发送请求
            HttpEntity<String> ans = getRestTemplate(connectionTimeout, readTimeout).exchange(url, HttpMethod.DELETE, new HttpEntity<>(requestJson, headers), String.class);

            JSONObject responseBody = JSONObject.parseObject(ans.getBody());

            long useTime = System.currentTimeMillis() - startTime;
            logger.info("#Response : " + url + " #Method : DEL  #use time(ms) : " + useTime + " #Result : " + responseBody);

            return responseBody;
        } catch (Exception e) {
            long useTime = System.currentTimeMillis() - startTime;
            logger.error("#Response : " + url + " #Method : DEL  #use time(ms) : " + useTime + " #Result : " + e.getMessage());
            throw e;
        }
    }

    public static <T> T executeGetMethod(String url, Map<String, Object> params, HttpHeaders headers, Class<T> responseType, Integer connTimeout, Integer readTimeout) {

        long startTime = System.currentTimeMillis();
        logger.info("#Request : " + url + " #Method : GET #Param : " + params);

        if (params != null) {

            Set<String> keySet = params.keySet();

            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("?");

            for (String key : keySet) {
                if (params.get(key) != null) {
                    stringBuilder.append(key).append("=").append(params.get(key)).append("&");
                }
            }

            url += stringBuilder.substring(0, stringBuilder.length() - 1);
        }

        try {
            HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(params, headers);
            HttpEntity<T> response = getRestTemplate(connTimeout, readTimeout)
                    .exchange(url, HttpMethod.GET, httpEntity, responseType);

            long useTime = System.currentTimeMillis() - startTime;
            logger.info("#Response : " + url + " #Method : GET  #use time(ms) : " + useTime + " #Result : " + response.getBody());

            return response.getBody();
        } catch (Exception e) {
            long useTime = System.currentTimeMillis() - startTime;
            logger.error("#Response : " + url + " #Method : GET  #use time(ms) : " + useTime + " #Result : " + e.getMessage());
            throw e;
        }
    }

    public static <T> T executeGetMethod(String url, Map<String, Object> params, HttpHeaders headers, Class<T> responseType) {
        return executeGetMethod(url, params, headers, responseType, 10000, 4000);
    }

    public static <T> T executeGetMethod(String url, Map<String, Object> params, Map<String, Object> headers, Class<T> responseType, Integer connTimeout, Integer readTimeout) {

        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach((key, value) -> httpHeaders.add(key, value.toString()));

        return executeGetMethod(url, params, httpHeaders, responseType, connTimeout, readTimeout);
    }

    public static <T> T executeGetMethod(String url, Map<String, Object> params, Map<String, Object> headers, Class<T> responseType) {

        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach((key, value) -> httpHeaders.add(key, value.toString()));

        return executeGetMethod(url, params, httpHeaders, responseType, 10000, 4000);
    }

    public static <T> T executeBasicAuthGetMethod(String url, Map<String, Object> params, Map<String, Object> headers, String basicAuthUserName, String basicAuthPassword, Class<T> responseType, Integer connTimeout, Integer readTimeout) {

        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach((key, value) -> httpHeaders.add(key, value.toString()));
        httpHeaders.setBasicAuth(basicAuthUserName, basicAuthPassword);

        return executeGetMethod(url, params, httpHeaders, responseType, connTimeout, readTimeout);
    }

    public static <T> T executeBasicAuthGetMethod(String url, Map<String, Object> params, Map<String, Object> headers, String basicAuthUserName, String basicAuthPassword, Class<T> responseType) {
        return executeBasicAuthGetMethod(url, params, headers, basicAuthUserName, basicAuthPassword, responseType, 10000, 4000);
    }

    public static <T> T executeBearerTokenGetMethod(String url, Map<String, Object> params, Map<String, Object> headers, String bearerToken, Class<T> responseType, Integer connTimeout, Integer readTimeout) {

        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach((key, value) -> httpHeaders.add(key, value.toString()));
        httpHeaders.setBearerAuth(bearerToken);

        return executeGetMethod(url, params, httpHeaders, responseType, connTimeout, readTimeout);
    }

    public static <T> T executeBearerTokenGetMethod(String url, Map<String, Object> params, Map<String, Object> headers, String bearerToken, Class<T> responseType) {
        return executeBearerTokenGetMethod(url, params, headers, bearerToken, responseType, 10000, 4000);
    }

    public static <T> T executeGetMethod(String url, Map<String, Object> params, Class<T> responseType, Integer connTimeout, Integer readTimeout) {
        return executeGetMethod(url, params, new HttpHeaders(), responseType, connTimeout, readTimeout);
    }

    public static <T> T executeGetMethod(String url, Map<String, Object> params, Class<T> responseType) {
        return executeGetMethod(url, params, new HttpHeaders(), responseType, 10000, 4000);
    }

    public static <T> T executeBasicAuthGetMethod(String url, Map<String, Object> params, String basicAuthUserName, String basicAuthPassword, Class<T> responseType, Integer connTimeout, Integer readTimeout) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth(basicAuthUserName, basicAuthPassword);

        return executeGetMethod(url, params, httpHeaders, responseType, connTimeout, readTimeout);
    }

    public static <T> T executeBasicAuthGetMethod(String url, Map<String, Object> params, String basicAuthUserName, String basicAuthPassword, Class<T> responseType) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth(basicAuthUserName, basicAuthPassword);

        return executeGetMethod(url, params, httpHeaders, responseType, 10000, 4000);
    }

    public static <T> T executeBearerTokenGetMethod(String url, Map<String, Object> params, String bearerToken, Class<T> responseType, Integer connTimeout, Integer readTimeout) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(bearerToken);

        return executeGetMethod(url, params, httpHeaders, responseType, connTimeout, readTimeout);
    }

    public static <T> T executeBearerTokenGetMethod(String url, Map<String, Object> params, String bearerToken, Class<T> responseType) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(bearerToken);

        return executeGetMethod(url, params, httpHeaders, responseType, 10000, 4000);
    }

    public static <T> T executeGetMethod(String url, Class<T> responseType, Integer connTimeout, Integer readTimeout) {
        return executeGetMethod(url, null, new HttpHeaders(), responseType, connTimeout, readTimeout);
    }

    public static <T> T executeGetMethod(String url, Class<T> responseType) {
        return executeGetMethod(url, null, new HttpHeaders(), responseType, 10000, 4000);
    }

    public static <T> T executeBasicAuthGetMethod(String url, String basicAuthUserName, String basicAuthPassword, Class<T> responseType, Integer connTimeout, Integer readTimeout) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth(basicAuthUserName, basicAuthPassword);

        return executeGetMethod(url, null, httpHeaders, responseType, connTimeout, readTimeout);
    }

    public static <T> T executeBasicAuthGetMethod(String url, String basicAuthUserName, String basicAuthPassword, Class<T> responseType) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth(basicAuthUserName, basicAuthPassword);

        return executeGetMethod(url, null, httpHeaders, responseType, 10000, 4000);
    }

    public static <T> T executeBearerTokenGetMethod(String url, String bearerToken, Class<T> responseType, Integer connTimeout, Integer readTimeout) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(bearerToken);

        return executeGetMethod(url, null, httpHeaders, responseType, connTimeout, readTimeout);
    }

    public static <T> T executeBearerTokenGetMethod(String url, String bearerToken, Class<T> responseType) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(bearerToken);

        return executeGetMethod(url, null, httpHeaders, responseType, 10000, 4000);
    }

    public static JSONObject executeGetMethod(String url, Map<String, Object> params, Map<String, Object> headers, Integer connTimeout, Integer readTimeout) {

        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach((key, value) -> httpHeaders.add(key, value.toString()));

        return executeGetMethod(url, params, httpHeaders, JSONObject.class, connTimeout, readTimeout);
    }

    public static JSONObject executeGetMethod(String url, Map<String, Object> params, Map<String, Object> headers) {

        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach((key, value) -> httpHeaders.add(key, value.toString()));

        return executeGetMethod(url, params, httpHeaders, JSONObject.class, 10000, 4000);
    }

    public static JSONObject executeBasicAuthGetMethod(String url, Map<String, Object> params, Map<String, Object> headers, String basicAuthUserName, String basicAuthPassword, Integer connTimeout, Integer readTimeout) {

        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach((key, value) -> httpHeaders.add(key, value.toString()));
        httpHeaders.setBasicAuth(basicAuthUserName, basicAuthPassword);

        return executeGetMethod(url, params, httpHeaders, JSONObject.class, connTimeout, readTimeout);
    }

    public static JSONObject executeBasicAuthGetMethod(String url, Map<String, Object> params, Map<String, Object> headers, String basicAuthUserName, String basicAuthPassword) {
        return executeBasicAuthGetMethod(url, params, headers, basicAuthUserName, basicAuthPassword, JSONObject.class, 10000, 4000);
    }

    public static JSONObject executeBearerTokenGetMethod(String url, Map<String, Object> params, Map<String, Object> headers, String bearerToken, Integer connTimeout, Integer readTimeout) {

        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach((key, value) -> httpHeaders.add(key, value.toString()));
        httpHeaders.setBearerAuth(bearerToken);

        return executeGetMethod(url, params, httpHeaders, JSONObject.class, connTimeout, readTimeout);
    }

    public static JSONObject executeBearerTokenGetMethod(String url, Map<String, Object> params, Map<String, Object> headers, String bearerToken) {
        return executeBearerTokenGetMethod(url, params, headers, bearerToken, JSONObject.class, 10000, 4000);
    }

    public static JSONObject executeGetMethod(String url, Map<String, Object> params, Integer connTimeout, Integer readTimeout) {
        return executeGetMethod(url, params, new HttpHeaders(), JSONObject.class, connTimeout, readTimeout);
    }

    public static JSONObject executeGetMethod(String url, Map<String, Object> params) {
        return executeGetMethod(url, params, new HttpHeaders(), JSONObject.class, 10000, 4000);
    }

    public static JSONObject executeBasicAuthGetMethod(String url, Map<String, Object> params, String basicAuthUserName, String basicAuthPassword, Integer connTimeout, Integer readTimeout) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth(basicAuthUserName, basicAuthPassword);

        return executeGetMethod(url, params, httpHeaders, JSONObject.class, connTimeout, readTimeout);
    }

    public static JSONObject executeBasicAuthGetMethod(String url, Map<String, Object> params, String basicAuthUserName, String basicAuthPassword) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth(basicAuthUserName, basicAuthPassword);

        return executeGetMethod(url, params, httpHeaders, JSONObject.class, 10000, 4000);
    }

    public static JSONObject executeBearerTokenGetMethod(String url, Map<String, Object> params, String bearerToken, Integer connTimeout, Integer readTimeout) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(bearerToken);

        return executeGetMethod(url, params, httpHeaders, JSONObject.class, connTimeout, readTimeout);
    }

    public static JSONObject executeBearerTokenGetMethod(String url, Map<String, Object> params, String bearerToken) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(bearerToken);

        return executeGetMethod(url, params, httpHeaders, JSONObject.class, 10000, 4000);
    }

    public static JSONObject executeGetMethod(String url, Integer connTimeout, Integer readTimeout) {
        return executeGetMethod(url, null, new HttpHeaders(), JSONObject.class, connTimeout, readTimeout);
    }

    public static JSONObject executeGetMethod(String url) {
        return executeGetMethod(url, null, new HttpHeaders(), JSONObject.class, 10000, 4000);
    }

    public static JSONObject executeBasicAuthGetMethod(String url, String basicAuthUserName, String basicAuthPassword, Integer connTimeout, Integer readTimeout) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth(basicAuthUserName, basicAuthPassword);

        return executeGetMethod(url, null, httpHeaders, JSONObject.class, connTimeout, readTimeout);
    }

    public static JSONObject executeBasicAuthGetMethod(String url, String basicAuthUserName, String basicAuthPassword) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth(basicAuthUserName, basicAuthPassword);

        return executeGetMethod(url, null, httpHeaders, JSONObject.class, 10000, 4000);
    }

    public static JSONObject executeBearerTokenGetMethod(String url, String bearerToken, Integer connTimeout, Integer readTimeout) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(bearerToken);

        return executeGetMethod(url, null, httpHeaders, JSONObject.class, connTimeout, readTimeout);
    }

    public static JSONObject executeBearerTokenGetMethod(String url, String bearerToken) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(bearerToken);

        return executeGetMethod(url, null, httpHeaders, JSONObject.class, 10000, 4000);
    }


    public static <T> T executePostMethod(String url, Map<String, Object> params, HttpHeaders headers, Class<T> responseType, Integer connTimeout, Integer readTimeout) {

        long startTime = System.currentTimeMillis();
        try {
            logger.info("#Request : " + url + " #Method : POST #Param : " + params);

            HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(params, headers);

            HttpEntity<T> response = getRestTemplate(connTimeout, readTimeout).exchange(url, HttpMethod.POST, httpEntity, responseType);

            long useTime = System.currentTimeMillis() - startTime;
            logger.info("#Response : " + url + " #Method : POST  #use time(ms) : " + useTime + " #Result : " + response.getBody());

            return response.getBody();
        } catch (Exception e) {
            long useTime = System.currentTimeMillis() - startTime;
            logger.error("#Response : " + url + " #Method : POST  #use time(ms) : " + useTime + " #Result : " + e.getMessage());
            throw e;
        }
    }

    public static <T> T executePostMethod(String url, Map<String, Object> params, HttpHeaders headers, Class<T> responseType) {
        return executePostMethod(url, params, headers, responseType, 10000, 4000);
    }

    public static <T> T executePostMethod(String url, Map<String, Object> params, Map<String, Object> headers, Class<T> responseType, Integer connTimeout, Integer readTimeout) {
        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach((key, value) -> httpHeaders.add(key, value.toString()));

        return executePostMethod(url, params, httpHeaders, responseType, connTimeout, readTimeout);
    }

    public static <T> T executePostMethod(String url, Map<String, Object> params, Map<String, Object> headers, Class<T> responseType) {
        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach((key, value) -> httpHeaders.add(key, value.toString()));

        return executePostMethod(url, params, httpHeaders, responseType, 10000, 4000);
    }

    public static <T> T executeBasicAuthPostMethod(String url, Map<String, Object> params, Map<String, Object> headers, String basicAuthUserName, String basicAuthPassword, Class<T> responseType, Integer connTimeout, Integer readTimeout) {

        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach((key, value) -> httpHeaders.add(key, value.toString()));
        httpHeaders.setBasicAuth(basicAuthUserName, basicAuthPassword);

        return executePostMethod(url, params, httpHeaders, responseType, connTimeout, readTimeout);
    }

    public static <T> T executeBasicAuthPostMethod(String url, Map<String, Object> params, Map<String, Object> headers, String basicAuthUserName, String basicAuthPassword, Class<T> responseType) {
        return executeBasicAuthPostMethod(url, params, headers, basicAuthUserName, basicAuthPassword, responseType, 10000, 4000);
    }

    public static <T> T executeBearerTokenPostMethod(String url, Map<String, Object> params, Map<String, Object> headers, String bearerToken, Class<T> responseType, Integer connTimeout, Integer readTimeout) {

        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach((key, value) -> httpHeaders.add(key, value.toString()));
        httpHeaders.setBearerAuth(bearerToken);

        return executePostMethod(url, params, httpHeaders, responseType, connTimeout, readTimeout);
    }

    public static <T> T executeBearerTokenPostMethod(String url, Map<String, Object> params, Map<String, Object> headers, String bearerToken, Class<T> responseType) {
        return executeBearerTokenPostMethod(url, params, headers, bearerToken, responseType, 10000, 4000);
    }

    public static <T> T executePostMethod(String url, Map<String, Object> params, Class<T> responseType, Integer connTimeout, Integer readTimeout) {

        HttpHeaders httpHeaders = new HttpHeaders();
        return executePostMethod(url, params, httpHeaders, responseType, connTimeout, readTimeout);
    }

    public static <T> T executePostMethod(String url, Map<String, Object> params, Class<T> responseType) {

        HttpHeaders httpHeaders = new HttpHeaders();
        return executePostMethod(url, params, httpHeaders, responseType, 10000, 4000);
    }

    public static <T> T executeBasicAuthPostMethod(String url, Map<String, Object> params, String basicAuthUserName, String basicAuthPassword, Class<T> responseType, Integer connTimeout, Integer readTimeout) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth(basicAuthUserName, basicAuthPassword);

        return executePostMethod(url, params, httpHeaders, responseType, connTimeout, readTimeout);
    }

    public static <T> T executeBasicAuthPostMethod(String url, Map<String, Object> params, String basicAuthUserName, String basicAuthPassword, Class<T> responseType) {
        return executeBasicAuthPostMethod(url, params, basicAuthUserName, basicAuthPassword, responseType, 10000, 4000);
    }

    public static <T> T executeBearerTokenPostMethod(String url, Map<String, Object> params, String bearerToken, Class<T> responseType, Integer connTimeout, Integer readTimeout) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(bearerToken);

        return executePostMethod(url, params, httpHeaders, responseType, connTimeout, readTimeout);
    }

    public static <T> T executeBearerTokenPostMethod(String url, Map<String, Object> params, String bearerToken, Class<T> responseType) {
        return executeBearerTokenPostMethod(url, params, bearerToken, responseType, 10000, 4000);
    }

    public static JSONObject executePostMethod(String url, Map<String, Object> params, Map<String, Object> headers, Integer connTimeout, Integer readTimeout) {
        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach((key, value) -> httpHeaders.add(key, value.toString()));

        return executePostMethod(url, params, httpHeaders, JSONObject.class, connTimeout, readTimeout);
    }

    public static JSONObject executePostMethod(String url, Map<String, Object> params, Map<String, Object> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach((key, value) -> httpHeaders.add(key, value.toString()));

        return executePostMethod(url, params, httpHeaders, JSONObject.class, 10000, 4000);
    }

    public static JSONObject executeBasicAuthPostMethod(String url, Map<String, Object> params, Map<String, Object> headers, String basicAuthUserName, String basicAuthPassword, Integer connTimeout, Integer readTimeout) {

        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach((key, value) -> httpHeaders.add(key, value.toString()));
        httpHeaders.setBasicAuth(basicAuthUserName, basicAuthPassword);

        return executePostMethod(url, params, httpHeaders, JSONObject.class, connTimeout, readTimeout);
    }

    public static JSONObject executeBasicAuthPostMethod(String url, Map<String, Object> params, Map<String, Object> headers, String basicAuthUserName, String basicAuthPassword) {
        return executeBasicAuthPostMethod(url, params, headers, basicAuthUserName, basicAuthPassword, JSONObject.class, 10000, 4000);
    }

    public static JSONObject executeBearerTokenPostMethod(String url, Map<String, Object> params, Map<String, Object> headers, String bearerToken, Integer connTimeout, Integer readTimeout) {

        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach((key, value) -> httpHeaders.add(key, value.toString()));
        httpHeaders.setBearerAuth(bearerToken);

        return executePostMethod(url, params, httpHeaders, JSONObject.class, connTimeout, readTimeout);
    }

    public static JSONObject executeBearerTokenPostMethod(String url, Map<String, Object> params, Map<String, Object> headers, String bearerToken) {
        return executeBearerTokenPostMethod(url, params, headers, bearerToken, JSONObject.class, 10000, 4000);
    }

    public static JSONObject executePostMethod(String url, Map<String, Object> params, Integer connTimeout, Integer readTimeout) {

        HttpHeaders httpHeaders = new HttpHeaders();
        return executePostMethod(url, params, httpHeaders, JSONObject.class, connTimeout, readTimeout);
    }

    public static JSONObject executePostMethod(String url, Map<String, Object> params) {

        HttpHeaders httpHeaders = new HttpHeaders();
        return executePostMethod(url, params, httpHeaders, JSONObject.class, 10000, 4000);
    }

    public static JSONObject executeBasicAuthPostMethod(String url, Map<String, Object> params, String basicAuthUserName, String basicAuthPassword, Integer connTimeout, Integer readTimeout) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth(basicAuthUserName, basicAuthPassword);

        return executePostMethod(url, params, httpHeaders, JSONObject.class, connTimeout, readTimeout);
    }

    public static JSONObject executeBasicAuthPostMethod(String url, Map<String, Object> params, String basicAuthUserName, String basicAuthPassword) {
        return executeBasicAuthPostMethod(url, params, basicAuthUserName, basicAuthPassword, JSONObject.class, 10000, 4000);
    }

    public static JSONObject executeBearerTokenPostMethod(String url, Map<String, Object> params, String bearerToken, Integer connTimeout, Integer readTimeout) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(bearerToken);

        return executePostMethod(url, params, httpHeaders, JSONObject.class, connTimeout, readTimeout);
    }

    public static JSONObject executeBearerTokenPostMethod(String url, Map<String, Object> params, String bearerToken) {
        return executeBearerTokenPostMethod(url, params, bearerToken, JSONObject.class, 10000, 4000);
    }


    public static <T> T executePutMethod(String url, Map<String, Object> params, HttpHeaders headers, Class<T> responseType, Integer connTimeout, Integer readTimeout) {

        long startTime = System.currentTimeMillis();
        try {
            logger.info("#Request : " + url + " #Method : PUT #Param : " + params);

            HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(params, headers);

            HttpEntity<T> response = getRestTemplate(connTimeout, readTimeout).exchange(url, HttpMethod.PUT, httpEntity, responseType);

            long useTime = System.currentTimeMillis() - startTime;
            logger.info("#Response : " + url + " #Method : PUT  #use time(ms) : " + useTime + " #Result : " + response.getBody());

            return response.getBody();
        } catch (Exception e) {
            long useTime = System.currentTimeMillis() - startTime;
            logger.error("#Response : " + url + " #Method : PUT  #use time(ms) : " + useTime + " #Result : " + e.getMessage());
            throw e;
        }
    }

    public static <T> T executePutMethod(String url, Map<String, Object> params, HttpHeaders headers, Class<T> responseType) {
        return executePutMethod(url, params, headers, responseType, 10000, 4000);
    }

    public static <T> T executePutMethod(String url, Map<String, Object> params, Map<String, Object> headers, Class<T> responseType, Integer connTimeout, Integer readTimeout) {
        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach((key, value) -> httpHeaders.add(key, value.toString()));

        return executePutMethod(url, params, httpHeaders, responseType, connTimeout, readTimeout);
    }

    public static <T> T executePutMethod(String url, Map<String, Object> params, Map<String, Object> headers, Class<T> responseType) {
        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach((key, value) -> httpHeaders.add(key, value.toString()));

        return executePutMethod(url, params, httpHeaders, responseType, 10000, 4000);
    }

    public static <T> T executeBasicAuthPutMethod(String url, Map<String, Object> params, Map<String, Object> headers, String basicAuthUserName, String basicAuthPassword, Class<T> responseType, Integer connTimeout, Integer readTimeout) {

        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach((key, value) -> httpHeaders.add(key, value.toString()));
        httpHeaders.setBasicAuth(basicAuthUserName, basicAuthPassword);

        return executePutMethod(url, params, httpHeaders, responseType, connTimeout, readTimeout);
    }

    public static <T> T executeBasicAuthPutMethod(String url, Map<String, Object> params, Map<String, Object> headers, String basicAuthUserName, String basicAuthPassword, Class<T> responseType) {
        return executeBasicAuthPutMethod(url, params, headers, basicAuthUserName, basicAuthPassword, responseType, 10000, 4000);
    }

    public static <T> T executeBearerTokenPutMethod(String url, Map<String, Object> params, Map<String, Object> headers, String bearerToken, Class<T> responseType, Integer connTimeout, Integer readTimeout) {

        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach((key, value) -> httpHeaders.add(key, value.toString()));
        httpHeaders.setBearerAuth(bearerToken);

        return executePutMethod(url, params, httpHeaders, responseType, connTimeout, readTimeout);
    }

    public static <T> T executeBearerTokenPutMethod(String url, Map<String, Object> params, Map<String, Object> headers, String bearerToken, Class<T> responseType) {
        return executeBearerTokenPutMethod(url, params, headers, bearerToken, responseType, 10000, 4000);
    }

    public static <T> T executePutMethod(String url, Map<String, Object> params, Class<T> responseType, Integer connTimeout, Integer readTimeout) {

        HttpHeaders httpHeaders = new HttpHeaders();
        return executePutMethod(url, params, httpHeaders, responseType, connTimeout, readTimeout);
    }

    public static <T> T executePutMethod(String url, Map<String, Object> params, Class<T> responseType) {

        HttpHeaders httpHeaders = new HttpHeaders();
        return executePutMethod(url, params, httpHeaders, responseType, 10000, 4000);
    }

    public static <T> T executeBasicAuthPutMethod(String url, Map<String, Object> params, String basicAuthUserName, String basicAuthPassword, Class<T> responseType, Integer connTimeout, Integer readTimeout) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth(basicAuthUserName, basicAuthPassword);

        return executePutMethod(url, params, httpHeaders, responseType, connTimeout, readTimeout);
    }

    public static <T> T executeBasicAuthPutMethod(String url, Map<String, Object> params, String basicAuthUserName, String basicAuthPassword, Class<T> responseType) {
        return executeBasicAuthPutMethod(url, params, basicAuthUserName, basicAuthPassword, responseType, 10000, 4000);
    }

    public static <T> T executeBearerTokenPutMethod(String url, Map<String, Object> params, String bearerToken, Class<T> responseType, Integer connTimeout, Integer readTimeout) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(bearerToken);

        return executePutMethod(url, params, httpHeaders, responseType, connTimeout, readTimeout);
    }

    public static <T> T executeBearerTokenPutMethod(String url, Map<String, Object> params, String bearerToken, Class<T> responseType) {
        return executeBearerTokenPutMethod(url, params, bearerToken, responseType, 10000, 4000);
    }

    public static JSONObject executePutMethod(String url, Map<String, Object> params, Map<String, Object> headers, Integer connTimeout, Integer readTimeout) {
        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach((key, value) -> httpHeaders.add(key, value.toString()));

        return executePutMethod(url, params, httpHeaders, JSONObject.class, connTimeout, readTimeout);
    }

    public static JSONObject executePutMethod(String url, Map<String, Object> params, Map<String, Object> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach((key, value) -> httpHeaders.add(key, value.toString()));

        return executePutMethod(url, params, httpHeaders, JSONObject.class, 10000, 4000);
    }

    public static JSONObject executeBasicAuthPutMethod(String url, Map<String, Object> params, Map<String, Object> headers, String basicAuthUserName, String basicAuthPassword, Integer connTimeout, Integer readTimeout) {

        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach((key, value) -> httpHeaders.add(key, value.toString()));
        httpHeaders.setBasicAuth(basicAuthUserName, basicAuthPassword);

        return executePutMethod(url, params, httpHeaders, JSONObject.class, connTimeout, readTimeout);
    }

    public static JSONObject executeBasicAuthPutMethod(String url, Map<String, Object> params, Map<String, Object> headers, String basicAuthUserName, String basicAuthPassword) {
        return executeBasicAuthPutMethod(url, params, headers, basicAuthUserName, basicAuthPassword, JSONObject.class, 10000, 4000);
    }

    public static JSONObject executeBearerTokenPutMethod(String url, Map<String, Object> params, Map<String, Object> headers, String bearerToken, Integer connTimeout, Integer readTimeout) {

        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach((key, value) -> httpHeaders.add(key, value.toString()));
        httpHeaders.setBearerAuth(bearerToken);

        return executePutMethod(url, params, httpHeaders, JSONObject.class, connTimeout, readTimeout);
    }

    public static JSONObject executeBearerTokenPutMethod(String url, Map<String, Object> params, Map<String, Object> headers, String bearerToken) {
        return executeBearerTokenPutMethod(url, params, headers, bearerToken, JSONObject.class, 10000, 4000);
    }

    public static JSONObject executePutMethod(String url, Map<String, Object> params, Integer connTimeout, Integer readTimeout) {

        HttpHeaders httpHeaders = new HttpHeaders();
        return executePutMethod(url, params, httpHeaders, JSONObject.class, connTimeout, readTimeout);
    }

    public static JSONObject executePutMethod(String url, Map<String, Object> params) {

        HttpHeaders httpHeaders = new HttpHeaders();
        return executePutMethod(url, params, httpHeaders, JSONObject.class, 10000, 4000);
    }

    public static JSONObject executeBasicAuthPutMethod(String url, Map<String, Object> params, String basicAuthUserName, String basicAuthPassword, Integer connTimeout, Integer readTimeout) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth(basicAuthUserName, basicAuthPassword);

        return executePutMethod(url, params, httpHeaders, JSONObject.class, connTimeout, readTimeout);
    }

    public static JSONObject executeBasicAuthPutMethod(String url, Map<String, Object> params, String basicAuthUserName, String basicAuthPassword) {
        return executeBasicAuthPutMethod(url, params, basicAuthUserName, basicAuthPassword, JSONObject.class, 10000, 4000);
    }

    public static JSONObject executeBearerTokenPutMethod(String url, Map<String, Object> params, String bearerToken, Integer connTimeout, Integer readTimeout) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(bearerToken);

        return executePutMethod(url, params, httpHeaders, JSONObject.class, connTimeout, readTimeout);
    }

    public static JSONObject executeBearerTokenPutMethod(String url, Map<String, Object> params, String bearerToken) {
        return executeBearerTokenPutMethod(url, params, bearerToken, JSONObject.class, 10000, 4000);
    }


    public static <T> T executeDelMethod(String url, Map<String, Object> params, HttpHeaders headers, Class<T> responseType, Integer connTimeout, Integer readTimeout) {

        long startTime = System.currentTimeMillis();
        logger.info("#Request : " + url + " #Method : DEL #Param : " + params);

        if (params != null) {

            Set<String> keySet = params.keySet();

            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("?");

            for (String key : keySet) {
                if (params.get(key) != null) {
                    stringBuilder.append(key).append("=").append(params.get(key)).append("&");
                }
            }

            url += stringBuilder.substring(0, stringBuilder.length() - 1);
        }

        try {
            HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(params, headers);
            HttpEntity<T> response = getRestTemplate(connTimeout, readTimeout)
                    .exchange(url, HttpMethod.DELETE, httpEntity, responseType);

            long useTime = System.currentTimeMillis() - startTime;
            logger.info("#Response : " + url + " #Method : DEL  #use time(ms) : " + useTime + " #Result : " + response.getBody());

            return response.getBody();
        } catch (Exception e) {
            long useTime = System.currentTimeMillis() - startTime;
            logger.error("#Response : " + url + " #Method : GET  #use time(ms) : " + useTime + " #Result : " + e.getMessage());
            throw e;
        }
    }

    public static <T> T executeDelMethod(String url, Map<String, Object> params, HttpHeaders headers, Class<T> responseType) {
        return executeDelMethod(url, params, headers, responseType, 10000, 4000);
    }

    public static <T> T executeDelMethod(String url, Map<String, Object> params, Map<String, Object> headers, Class<T> responseType, Integer connTimeout, Integer readTimeout) {

        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach((key, value) -> httpHeaders.add(key, value.toString()));

        return executeDelMethod(url, params, httpHeaders, responseType, connTimeout, readTimeout);
    }

    public static <T> T executeDelMethod(String url, Map<String, Object> params, Map<String, Object> headers, Class<T> responseType) {

        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach((key, value) -> httpHeaders.add(key, value.toString()));

        return executeDelMethod(url, params, httpHeaders, responseType, 10000, 4000);
    }

    public static <T> T executeBasicAuthDelMethod(String url, Map<String, Object> params, Map<String, Object> headers, String basicAuthUserName, String basicAuthPassword, Class<T> responseType, Integer connTimeout, Integer readTimeout) {

        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach((key, value) -> httpHeaders.add(key, value.toString()));
        httpHeaders.setBasicAuth(basicAuthUserName, basicAuthPassword);

        return executeDelMethod(url, params, httpHeaders, responseType, connTimeout, readTimeout);
    }

    public static <T> T executeBasicAuthDelMethod(String url, Map<String, Object> params, Map<String, Object> headers, String basicAuthUserName, String basicAuthPassword, Class<T> responseType) {
        return executeBasicAuthDelMethod(url, params, headers, basicAuthUserName, basicAuthPassword, responseType, 10000, 4000);
    }

    public static <T> T executeBearerTokenDelMethod(String url, Map<String, Object> params, Map<String, Object> headers, String bearerToken, Class<T> responseType, Integer connTimeout, Integer readTimeout) {

        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach((key, value) -> httpHeaders.add(key, value.toString()));
        httpHeaders.setBearerAuth(bearerToken);

        return executeDelMethod(url, params, httpHeaders, responseType, connTimeout, readTimeout);
    }

    public static <T> T executeBearerTokenDelMethod(String url, Map<String, Object> params, Map<String, Object> headers, String bearerToken, Class<T> responseType) {
        return executeBearerTokenDelMethod(url, params, headers, bearerToken, responseType, 10000, 4000);
    }

    public static <T> T executeDelMethod(String url, Map<String, Object> params, Class<T> responseType, Integer connTimeout, Integer readTimeout) {
        return executeDelMethod(url, params, new HttpHeaders(), responseType, connTimeout, readTimeout);
    }

    public static <T> T executeDelMethod(String url, Map<String, Object> params, Class<T> responseType) {
        return executeDelMethod(url, params, new HttpHeaders(), responseType, 10000, 4000);
    }

    public static <T> T executeBasicAuthDelMethod(String url, Map<String, Object> params, String basicAuthUserName, String basicAuthPassword, Class<T> responseType, Integer connTimeout, Integer readTimeout) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth(basicAuthUserName, basicAuthPassword);

        return executeDelMethod(url, params, httpHeaders, responseType, connTimeout, readTimeout);
    }

    public static <T> T executeBasicAuthDelMethod(String url, Map<String, Object> params, String basicAuthUserName, String basicAuthPassword, Class<T> responseType) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth(basicAuthUserName, basicAuthPassword);

        return executeDelMethod(url, params, httpHeaders, responseType, 10000, 4000);
    }

    public static <T> T executeBearerTokenDelMethod(String url, Map<String, Object> params, String bearerToken, Class<T> responseType, Integer connTimeout, Integer readTimeout) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(bearerToken);

        return executeDelMethod(url, params, httpHeaders, responseType, connTimeout, readTimeout);
    }

    public static <T> T executeBearerTokenDelMethod(String url, Map<String, Object> params, String bearerToken, Class<T> responseType) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(bearerToken);

        return executeDelMethod(url, params, httpHeaders, responseType, 10000, 4000);
    }

    public static <T> T executeDelMethod(String url, Class<T> responseType, Integer connTimeout, Integer readTimeout) {
        return executeDelMethod(url, null, new HttpHeaders(), responseType, connTimeout, readTimeout);
    }

    public static <T> T executeDelMethod(String url, Class<T> responseType) {
        return executeDelMethod(url, null, new HttpHeaders(), responseType, 10000, 4000);
    }

    public static <T> T executeBasicAuthDelMethod(String url, String basicAuthUserName, String basicAuthPassword, Class<T> responseType, Integer connTimeout, Integer readTimeout) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth(basicAuthUserName, basicAuthPassword);

        return executeDelMethod(url, null, httpHeaders, responseType, connTimeout, readTimeout);
    }

    public static <T> T executeBasicAuthDelMethod(String url, String basicAuthUserName, String basicAuthPassword, Class<T> responseType) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth(basicAuthUserName, basicAuthPassword);

        return executeDelMethod(url, null, httpHeaders, responseType, 10000, 4000);
    }

    public static <T> T executeBearerTokenDelMethod(String url, String bearerToken, Class<T> responseType, Integer connTimeout, Integer readTimeout) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(bearerToken);

        return executeDelMethod(url, null, httpHeaders, responseType, connTimeout, readTimeout);
    }

    public static <T> T executeBearerTokenDelMethod(String url, String bearerToken, Class<T> responseType) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(bearerToken);

        return executeDelMethod(url, null, httpHeaders, responseType, 10000, 4000);
    }

    public static JSONObject executeDelMethod(String url, Map<String, Object> params, Map<String, Object> headers, Integer connTimeout, Integer readTimeout) {

        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach((key, value) -> httpHeaders.add(key, value.toString()));

        return executeDelMethod(url, params, httpHeaders, JSONObject.class, connTimeout, readTimeout);
    }

    public static JSONObject executeDelMethod(String url, Map<String, Object> params, Map<String, Object> headers) {

        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach((key, value) -> httpHeaders.add(key, value.toString()));

        return executeDelMethod(url, params, httpHeaders, JSONObject.class, 10000, 4000);
    }

    public static JSONObject executeBasicAuthDelMethod(String url, Map<String, Object> params, Map<String, Object> headers, String basicAuthUserName, String basicAuthPassword, Integer connTimeout, Integer readTimeout) {

        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach((key, value) -> httpHeaders.add(key, value.toString()));
        httpHeaders.setBasicAuth(basicAuthUserName, basicAuthPassword);

        return executeDelMethod(url, params, httpHeaders, JSONObject.class, connTimeout, readTimeout);
    }

    public static JSONObject executeBasicAuthDelMethod(String url, Map<String, Object> params, Map<String, Object> headers, String basicAuthUserName, String basicAuthPassword) {
        return executeBasicAuthDelMethod(url, params, headers, basicAuthUserName, basicAuthPassword, JSONObject.class, 10000, 4000);
    }

    public static JSONObject executeBearerTokenDelMethod(String url, Map<String, Object> params, Map<String, Object> headers, String bearerToken, Integer connTimeout, Integer readTimeout) {

        HttpHeaders httpHeaders = new HttpHeaders();
        headers.forEach((key, value) -> httpHeaders.add(key, value.toString()));
        httpHeaders.setBearerAuth(bearerToken);

        return executeDelMethod(url, params, httpHeaders, JSONObject.class, connTimeout, readTimeout);
    }

    public static JSONObject executeBearerTokenDelMethod(String url, Map<String, Object> params, Map<String, Object> headers, String bearerToken) {
        return executeBearerTokenDelMethod(url, params, headers, bearerToken, JSONObject.class, 10000, 4000);
    }

    public static JSONObject executeDelMethod(String url, Map<String, Object> params, Integer connTimeout, Integer readTimeout) {
        return executeDelMethod(url, params, new HttpHeaders(), JSONObject.class, connTimeout, readTimeout);
    }

    public static JSONObject executeDelMethod(String url, Map<String, Object> params) {
        return executeDelMethod(url, params, new HttpHeaders(), JSONObject.class, 10000, 4000);
    }

    public static JSONObject executeBasicAuthDelMethod(String url, Map<String, Object> params, String basicAuthUserName, String basicAuthPassword, Integer connTimeout, Integer readTimeout) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth(basicAuthUserName, basicAuthPassword);

        return executeDelMethod(url, params, httpHeaders, JSONObject.class, connTimeout, readTimeout);
    }

    public static JSONObject executeBasicAuthDelMethod(String url, Map<String, Object> params, String basicAuthUserName, String basicAuthPassword) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth(basicAuthUserName, basicAuthPassword);

        return executeDelMethod(url, params, httpHeaders, JSONObject.class, 10000, 4000);
    }

    public static JSONObject executeBearerTokenDelMethod(String url, Map<String, Object> params, String bearerToken, Integer connTimeout, Integer readTimeout) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(bearerToken);

        return executeDelMethod(url, params, httpHeaders, JSONObject.class, connTimeout, readTimeout);
    }

    public static JSONObject executeBearerTokenDelMethod(String url, Map<String, Object> params, String bearerToken) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(bearerToken);

        return executeDelMethod(url, params, httpHeaders, JSONObject.class, 10000, 4000);
    }

    public static JSONObject executeDelMethod(String url, Integer connTimeout, Integer readTimeout) {
        return executeDelMethod(url, null, new HttpHeaders(), JSONObject.class, connTimeout, readTimeout);
    }

    public static JSONObject executeDelMethod(String url) {
        return executeDelMethod(url, null, new HttpHeaders(), JSONObject.class, 10000, 4000);
    }

    public static JSONObject executeBasicAuthDelMethod(String url, String basicAuthUserName, String basicAuthPassword, Integer connTimeout, Integer readTimeout) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth(basicAuthUserName, basicAuthPassword);

        return executeDelMethod(url, null, httpHeaders, JSONObject.class, connTimeout, readTimeout);
    }

    public static JSONObject executeBasicAuthDelMethod(String url, String basicAuthUserName, String basicAuthPassword) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth(basicAuthUserName, basicAuthPassword);

        return executeDelMethod(url, null, httpHeaders, JSONObject.class, 10000, 4000);
    }

    public static JSONObject executeBearerTokenDelMethod(String url, String bearerToken, Integer connTimeout, Integer readTimeout) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(bearerToken);

        return executeDelMethod(url, null, httpHeaders, JSONObject.class, connTimeout, readTimeout);
    }

    public static JSONObject executeBearerTokenDelMethod(String url, String bearerToken) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(bearerToken);

        return executeDelMethod(url, null, httpHeaders, JSONObject.class, 10000, 4000);
    }


    private static RestTemplate getRestTemplate(Integer connTimeout, Integer readTimeout) {

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(connTimeout);
        requestFactory.setReadTimeout(readTimeout);

        return new RestTemplate(requestFactory);
    }
}
