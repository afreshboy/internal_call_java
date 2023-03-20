package com.demo.dyc.internal_call_java.controller;

import com.alibaba.fastjson.JSON;
import com.demo.dyc.internal_call_java.domain.BaseReq;
import com.demo.dyc.internal_call_java.service.InternalCallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class InternalCallController {
    @Autowired
    private InternalCallService internalCallService;
    @RequestMapping(value = "/api/v1/internal_call", method = RequestMethod.GET)
    public ResponseEntity<String> InternalCall(@RequestHeader HttpHeaders headers) {
        String serviceID = headers.getFirst("X-SERVICE-ID");
        String method = headers.getFirst("X-SERVICE-METHOD");
        String uri = headers.getFirst("X-SERVICE-URI");
        String value1 = headers.getFirst("X-SERVICE-VALUE1");
        String value2 = headers.getFirst("X-SERVICE-VALUE2");
        String result = "";
        if (method != null && method.equals("GET")) {
            result = internalCallService.InternalCallServiceGet(uri, serviceID, new HashMap<String, String>() {
                {
                    put("num1", value1);
                    put("num2", value2);
                }
            }, new HashMap<String, String>() {
                {
                    put("TEST_HEADER1", "test1");
                }
            });
        } else if (method != null && method.equals("POST")) {
            BaseReq baseReq = new BaseReq();
            if (value1 == null || value2 == null) {
               System.out.println(String.format("invalid param, num1: %s, num2: %s", value1, value2));
               return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            baseReq.setNum1(Integer.parseInt(value1));
            baseReq.setNum2(Integer.parseInt(value2));
            String body = JSON.toJSONString(baseReq);
            System.out.printf("body: %s\n", body);
            result = internalCallService.InternalCallServicePost(uri, serviceID, body, new HashMap<String, String>() {
                {
                    put("TEST_HEADER1", "test1");
                }
            });
        } else {
            System.out.println(String.format("invalid method: %s", method));
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if (result.equals("InternalCallServiceErr")) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);

    }
}
