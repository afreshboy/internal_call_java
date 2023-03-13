package com.demo.dyc.internal_call_java.service.impl;


import com.demo.dyc.internal_call_java.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public class BaseServiceImpl implements BaseService {
    public int GetCount(int num) {
        System.out.printf("GetCount: %d, return: %d", num, num + 1);
        return num + 1;
    }
}
