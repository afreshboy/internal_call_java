package com.demo.dyc.internal_call_java.controller;

import com.demo.dyc.internal_call_java.domain.BaseReq;
import com.demo.dyc.internal_call_java.service.InternalCallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class InternalCallController {
    @Autowired
    private InternalCallService internalCallService;
    @RequestMapping(value = "/api/v1/internal_call", method = RequestMethod.GET)
    public String InternalCall(@RequestHeader HttpHeaders headers) {
        String serviceID = headers.getFirst("X-SERVICE-ID");
        String method = headers.getFirst("X-SERVICE-METHOD");
        String uri = headers.getFirst("X-SERVICE-URI");
        String value1 = headers.getFirst("X-SERVICE-VALUE1");
        String value2 = headers.getFirst("X-SERVICE-VALUE2");
        if (method == "GET") {
            return internalCallService.InternalCallServiceGet(uri, serviceID, new HashMap<String, String>() {
                {
                    put("num1", value1);
                    put("num2", value2);
                }
            }, new HashMap<String, String>() {
                {
                    put("TEST_HEADER1", "test1");
                }
            });
        } else if (method == "POST") {
            BaseReq baseReq = new BaseReq();
            if (value1 == null || value2 == null) {
                return String.format("invalid param, num1: %s, num2: %s", value1, value2);
            }
            baseReq.setNum1(Integer.parseInt(value1));
            baseReq.setNum2(Integer.parseInt(value2));
            String body = baseReq.toString();
            return internalCallService.InternalCallServicePost(uri, serviceID, body, new HashMap<String, String>() {
                {
                    put("TEST_HEADER1", "test1");
                }
            });
        }
        return String.format("invalid method: %s", method);
    }
}
