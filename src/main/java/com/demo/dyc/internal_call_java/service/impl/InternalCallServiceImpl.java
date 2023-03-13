package com.demo.dyc.internal_call_java.service.impl;
import com.demo.dyc.internal_call_java.service.InternalCallService;
import com.demo.dyc.internal_call_java.utils.InternalCallUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.springframework.stereotype.Service;
import java.util.HashMap;


@Service
public class InternalCallServiceImpl implements InternalCallService {
    public String InternalCallServiceGet(String uri, String toServiceID, HashMap<String, String> paramMap, HashMap<String, String> headers) {
        CloseableHttpResponse response = InternalCallUtil.InternalCallGet(uri, toServiceID, paramMap, new HashMap<String, String>() {
           {put("TEST_HEADER1", "test1");}
       });
       HttpEntity entity =  response.getEntity();
       if (entity == null) {
           return "err: response is null";
       }

       return entity.toString();
    }

    public String InternalCallServicePost(String uri, String toServiceID, String body, HashMap<String, String> headers) {
        CloseableHttpResponse response = InternalCallUtil.InternalCallPost(uri, toServiceID, body, new HashMap<String, String>() {
            {put("TEST_HEADER1", "test1");}
        });
        HttpEntity entity =  response.getEntity();
        if (entity == null) {
            return "err: response is null";
        }
        return entity.toString();
    }
}
