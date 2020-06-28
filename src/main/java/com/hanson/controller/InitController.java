package com.hanson.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hanson.service.impl.InitServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: DreamMall
 * @description: 初始化（恢复）系统数据的接口
 * @param:
 * @author: Hanson
 * @create: 2020-05-02 20:34
 **/
@Controller
@RequestMapping("/init")
public class InitController {
    private InitServiceImpl initService;
    @Autowired
    public void setInitService(InitServiceImpl initService) {
        this.initService = initService;
    }

    //系统初始化
    @RequestMapping("/start")
    @ResponseBody
    public JSONObject init(){
        return (JSONObject) JSON.toJSON(initService.initSystem());
    }
}
