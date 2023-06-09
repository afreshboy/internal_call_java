package com.demo.dyc.internal_call_java.service.impl;

import com.demo.dyc.internal_call_java.service.InternalCallService;
import com.demo.dyc.internal_call_java.utils.InternalCallUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;


@Service
public class InternalCallServiceImpl implements InternalCallService {
    public String InternalCallServiceGet(String uri, String toServiceID, HashMap<String, String> paramMap, HashMap<String, String> headers) {
        String content;
        try {
            content = InternalCallUtil.InternalCallGet(uri, toServiceID, paramMap, new HashMap<String, String>() {
                {
                    put("TEST_HEADER1", "test1");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            content = "InternalCallServiceErr";
        }

        return content;
    }

    public String InternalCallServicePost(String uri, String toServiceID, String body, HashMap<String, String> headers) {
        String content;
        try {
            content = InternalCallUtil.InternalCallPost(uri, toServiceID, body, new HashMap<String, String>() {
                {
                    put("TEST_HEADER1", "test1");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            content = "InternalCallServiceErr";
        }
        return content;
    }
}
