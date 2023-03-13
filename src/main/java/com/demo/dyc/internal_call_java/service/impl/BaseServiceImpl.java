package com.demo.dyc.internal_call_java.service.impl;


import com.demo.dyc.internal_call_java.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public class BaseServiceImpl implements BaseService {
    public int GetCount(int num1, int num2) {
        System.out.printf("return: %d", num1 + num2);
        return num1+num2;
    }
}
