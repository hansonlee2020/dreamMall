package com.hanson.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @program: DreamMall
 * @description: 返回信息类，用于包装处理各种请求访问服务器后返回执行结果的提示信息处理
 * @param:
 * @author: Hanson
 * @create: 2020-04-05 19:22
 **/
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private String msgTitle;    //消息的标题
    private String msgContent;  //消息的内容
    private String msgType;     //消息的类型
}
