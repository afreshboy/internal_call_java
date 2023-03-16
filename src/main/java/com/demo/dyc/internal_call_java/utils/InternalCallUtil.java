package com.demo.dyc.internal_call_java.utils;


import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InternalCallUtil {
    public static String InternalCallGet(String uri, String toServiceID, HashMap<String, String> paramMap, HashMap<String, String> headers) {
        String fromServiceID = System.getenv("SERVICE_ID");
        String url = String.format("http://%s-%s.dycloud.service%s", fromServiceID, toServiceID, uri);

        HttpGet httpGet = new HttpGet(url);
        // 表单参数
        List<NameValuePair> nvps = new ArrayList<>();
        // GET 请求参数
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }

        // 将参数增加到请求 URL 中
        try {
            URI uriVariable = new URIBuilder(new URI(url))
                    .addParameters(nvps)
                    .build();
            httpGet.setURI(uriVariable);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        // 添加header
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            httpGet.addHeader(entry.getKey(), entry.getValue());
        }

        System.out.printf("req: %s", httpGet);

        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
                System.out.printf("response: %s", response);

                String content = EntityUtils.toString(response.getEntity());
                System.out.printf("content: %s", content);
                response.close();
                return content;
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String InternalCallPost(String uri, String toServiceID, String body, HashMap<String, String> headers) {
        String fromServiceID = System.getenv("SERVICE_ID");
        String url = String.format("http://%s-%s.dycloud.service%s", fromServiceID, toServiceID, uri);

        HttpPost httpPost = new HttpPost(url);

        httpPost.setEntity(new StringEntity(body, ContentType.APPLICATION_JSON));

        System.out.printf("req: %s", body);

        // 添加header
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            httpPost.addHeader(entry.getKey(), entry.getValue());
        }

        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
                String content = EntityUtils.toString(response.getEntity());
                System.out.printf("content: %s", content);
                return content;
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
}
