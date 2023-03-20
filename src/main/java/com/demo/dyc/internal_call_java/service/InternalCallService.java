package com.demo.dyc.internal_call_java.service;


import java.util.HashMap;

public interface InternalCallService {
    String InternalCallServiceGet(String uri, String toServiceID, HashMap<String, String> paramMap, HashMap<String, String> headers);
    String InternalCallServicePost(String uri, String toServiceID, String body, HashMap<String, String> headers);
}
