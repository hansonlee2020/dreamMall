package com.hanson.service;

import com.hanson.dto.Message;
import org.springframework.stereotype.Service;

/**
 * @program: DreamMall
 * @description: 初始化数据库接口
 * @param:
 * @author: Hanson
 * @create: 2020-05-02 20:36
 **/
@Service
public interface InitService {

    /*
    * @description: 初始化系统数据库
    * @params: []
    * @return: com.hanson.dto.Message
    * @Date: 2020/5/2
    */
    public Message initSystem();
}
