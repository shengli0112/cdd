package com.cdd.gsl.common.util;



import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpClientUtils {
    private final Logger log = LoggerFactory.getLogger(HttpClientUtils.class.getName());
    private final int DEFAULT_TIMEOUT = 10000;

    private static HttpClientUtils ins;

    private HttpClient client;
    private int timeout = DEFAULT_TIMEOUT;

    private HttpClientUtils() {
        if (client == null) {
            client = HttpClients.createDefault();
        }
    }

    public static HttpClientUtils getInstance() {
        if (ins == null) {
            synchronized (HttpClientUtils.class) {
                if (ins == null) {
                    ins = new HttpClientUtils();
                }
            }
        }
        return ins;
    }


    public String doGetWithJsonResult(String uri) {
        String json = null;
        log.debug("========= Call [{}] Start ==========", uri);
        try {
            HttpGet request = new HttpGet(uri);
            RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(timeout)
                    .setConnectTimeout(timeout).setSocketTimeout(timeout).build();
            request.setConfig(config);
            HttpResponse response = client.execute(request);
            log.debug("Response status code: {}", response.getStatusLine().getStatusCode());
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                json = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                log.debug("Payload : {}", json);
            }
        } catch (Exception e) {
            log.error("HttpClient has exception! message: {}", e.getMessage(), e);
            return null;
        }
        log.debug("========= Call [{}] End ==========", uri);
        return json;
    }

    public <T> T doGetWithJsonResult(String uri, Class<T> javaType) {
        StackTraceElement element = null;
        StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
        if (stElements.length >= 2) {
            element = stElements[2];
        }

        T result = null;
        log.debug("========= Call [{}] Start ==========", uri);
        try {
            long startTime = System.currentTimeMillis();
            HttpGet request = new HttpGet(uri);
            RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(timeout)
                    .setConnectTimeout(timeout).setSocketTimeout(timeout).build();
            request.setConfig(config);
            HttpResponse response = client.execute(request);
            log.debug("Response status code: {}", response.getStatusLine().getStatusCode());
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String json = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                log.debug("Payload : {}", json);
                result = JsonUtil.Json2Java(json, javaType);
            } else {
                HttpEntity entityRes = response.getEntity();
                EntityUtils.consume(entityRes);
            }

            log.info("doGetWithJsonResult execute " + (System.currentTimeMillis() - startTime) + " (" + element + ")");
        } catch (Exception e) {
            log.error("HttpClient has exception! message: {}", e.getMessage(), e);
            return null;
        }
        log.debug("========= Call [{}] End ==========", uri);
        return result;
    }

    public String doPostWithJsonResult(String uri, Map<String, String> paramMap) {
        String json = null;
        log.debug("========= Call [{}] Start ==========", uri);
        try {
            HttpPost request = new HttpPost(uri);
            RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(timeout)
                    .setConnectTimeout(timeout).setSocketTimeout(timeout).build();
            request.setConfig(config);
            List<NameValuePair> params = new ArrayList<NameValuePair>(0);
            if (paramMap != null && !paramMap.isEmpty()) {
                for (String key : paramMap.keySet()) {
                    params.add(new BasicNameValuePair(key, paramMap.get(key)));
                }
                request.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            }
            HttpResponse response = client.execute(request);
            log.debug("Response status code: {}", response.getStatusLine().getStatusCode());
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                json = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                log.debug("Payload : {}", json);
            } else {
                HttpEntity entityRes = response.getEntity();
                EntityUtils.consume(entityRes);
            }
        } catch (Exception e) {
            log.error("HttpClient has exception! message: {}", e.getMessage(), e);
            return null;
        }
        log.debug("========= Call [{}] End ==========", uri);
        return json;
    }

    public String doPostFileResult(String uri, Map<String, Object> paramMap, MultipartFile file) {
        String json = null;
        InputStream in = null;
        log.debug("========= Call [{}] Start ==========", uri);
        try {
            HttpPost request = new HttpPost(uri);
            RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(timeout)
                    .setConnectTimeout(timeout).setSocketTimeout(timeout).build();
            request.setConfig(config);
            String fileName = file.getOriginalFilename();
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addBinaryBody("file", file.getInputStream(), ContentType.MULTIPART_FORM_DATA, fileName);// 文件流
            for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
                builder.addTextBody(entry.getKey(), String.valueOf(entry.getValue()));// 类似浏览器表单提交，对应input的name和value
            }
            HttpEntity entity = builder.build();
            request.setEntity(entity);
            HttpResponse response = client.execute(request);
            log.debug("Response status code: {}", response.getStatusLine().getStatusCode());
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                json = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                log.debug("Payload : {}", json);
            } else {
                HttpEntity entityRes = response.getEntity();
                EntityUtils.consume(entityRes);
            }

        } catch (Exception e) {
            log.error("HttpClient has exception! message: {}", e.getMessage(), e);
            return null;
        }
        log.debug("========= Call [{}] End ==========", uri);
        return json;
    }


    public String doPostFileResultNew(String uri, Map<String, Object> paramMap, byte[] file) {
        String json = null;
        log.debug("========= Call [{}] Start ==========", uri);
        try {
            HttpPost request = new HttpPost(uri);
            RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(timeout)
                    .setConnectTimeout(timeout).setSocketTimeout(timeout).build();
            request.setConfig(config);
            String fileName = "a.jpg";
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addBinaryBody("file", file, ContentType.MULTIPART_FORM_DATA, fileName);// 文件流
            for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
                builder.addTextBody(entry.getKey(), String.valueOf(entry.getValue()));// 类似浏览器表单提交，对应input的name和value
            }
            HttpEntity entity = builder.build();
            request.setEntity(entity);
            HttpResponse response = client.execute(request);
            log.debug("Response status code: {}", response.getStatusLine().getStatusCode());
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                json = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                log.debug("Payload : {}", json);
            }
        } catch (Exception e) {
            log.error("HttpClient has exception! message: {}", e.getMessage(), e);
            return null;
        }
        log.debug("========= Call [{}] End ==========", uri);
        return json;
    }

    public String doPost(String uri) {
        String json = null;
        log.debug("========= Call [{}] Start ==========", uri);
        try {
            HttpPost request = new HttpPost(uri);
            RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(timeout)
                    .setConnectTimeout(timeout).setSocketTimeout(timeout).build();
            request.setConfig(config);
            HttpResponse response = client.execute(request);
            log.debug("Response status code: {}", response.getStatusLine().getStatusCode());
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                json = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                log.debug("Payload : {}", json);
            }
        } catch (Exception e) {
            log.error("HttpClient has exception! message: {}", e);
        }
        log.debug("========= Call [{}] End ==========", uri);
        return json;
    }

    public <T> T doPostWithJsonResult(String uri, Map<String, String> paramMap, Class<T> javaType) {
        T result = null;
        log.debug("========= Call [{}] Start ==========", uri);
        try {
            HttpPost request = new HttpPost(uri);
            RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(timeout)
                    .setConnectTimeout(timeout).setSocketTimeout(timeout).build();
            request.setConfig(config);
            List<NameValuePair> params = new ArrayList<NameValuePair>(0);
            if (paramMap != null && !paramMap.isEmpty()) {
                for (String key : paramMap.keySet()) {
                    params.add(new BasicNameValuePair(key, paramMap.get(key)));
                }
                request.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            }
            HttpResponse response = client.execute(request);
            log.debug("Response status code: {}", response.getStatusLine().getStatusCode());
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String json = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                log.debug("Payload : {}", json);
                result = JsonUtil.Json2Java(json, javaType);
            }
        } catch (Exception e) {
            log.error("HttpClient has exception! message: {}", e.getMessage(), e);
            return null;
        }
        log.debug("========= Call [{}] End ==========", uri);
        return result;
    }

    public String doPostWithJsonResult(String uri, String jsonParameters) {
        log.debug("========= Call [{}] Start ==========", uri);
        log.debug("========= Call [{}] Start ==========", jsonParameters);
        HttpPost request = new HttpPost(uri);
        RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(timeout)
                .setConnectTimeout(timeout).setSocketTimeout(timeout).build();
        request.setConfig(config);
        request.setEntity(new StringEntity(jsonParameters, ContentType.APPLICATION_JSON));
        HttpResponse response = null;
        String responseStr = null;
        try {
            response = client.execute(request);
            log.debug("Response status code: {}", response.getStatusLine().getStatusCode());
            responseStr = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            log.debug("Payload : {}", responseStr);
        } catch (Exception e) {
            log.info("http error:" + e.getMessage());
        }
        log.debug("========= Call [{}] End ==========", uri);
        return responseStr;

    }

    public String doPost(String url, String jsonStr) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        return commonPost(url, jsonStr, headers);
    }

    public String commonPost(String url, String jsonStr, Map<String, String> headers) {
        log.debug("========= Call [{}] Start ==========", url);
        URL u = null;
        HttpURLConnection con = null;

        try {
            u = new URL(url);
            con = (HttpURLConnection) u.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            if (headers != null) {
                headers.forEach(con::setRequestProperty);
            }
            if (jsonStr != null && !"".equals(jsonStr)) {
                OutputStreamWriter osw = new OutputStreamWriter(
                        con.getOutputStream(), "UTF-8");
                log.debug("即将发送参数:{}", jsonStr);
                osw.write(jsonStr);
                osw.flush();
                osw.close();
            }
        } catch (Exception e) {
            log.error(e.getMessage(), new IllegalStateException(e));
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
        // 读取返回内容
        StringBuffer buffer = new StringBuffer();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    con.getInputStream(), "UTF-8"));
            String temp = "";
            while ((temp = br.readLine()) != null) {
                buffer.append(temp);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), new IllegalStateException(e));
        }
        String result = buffer.toString();
        log.debug("Payload: {}", result);
        log.debug("========= Call [{}] End ==========", url);
        return result;
    }

    public String doPost(String url, String jsonStr, Map<String, String> headers) {
        return commonPost(url, jsonStr, headers);
    }

    /**
     * @return the timeout
     */
    public int getTimeout() {
        return timeout;
    }

    /**
     * @param timeout the timeout to set
     */
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

}
