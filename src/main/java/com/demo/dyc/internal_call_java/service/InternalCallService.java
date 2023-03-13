package com.demo.dyc.internal_call_java.service;


import java.util.HashMap;

public interface InternalCallService {
    public String InternalCallServiceGet(String uri, String toServiceID, HashMap<String, String> paramMap, HashMap<String, String> headers);
    public String InternalCallServicePost(String uri, String toServiceID, String body, HashMap<String, String> headers);
}
