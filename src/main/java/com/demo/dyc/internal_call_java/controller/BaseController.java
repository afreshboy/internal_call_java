package com.demo.dyc.internal_call_java.controller;


import com.demo.dyc.internal_call_java.domain.BaseReq;
import com.demo.dyc.internal_call_java.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class BaseController {

    @Autowired
    private BaseService baseService;

    @RequestMapping(value = "/api/v1/get_count", method = RequestMethod.GET)
    public int get_count(@RequestParam int num) {
        return baseService.GetCount(num);
    }

    @RequestMapping(value = "/api/v1/post_count", method = RequestMethod.POST)
    public int post_count(@RequestBody BaseReq baseReq) {
        return baseService.GetCount(baseReq.getNum());
    }
}
