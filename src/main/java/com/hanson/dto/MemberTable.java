package com.hanson.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @program: DreamMall
 * @description: 前端用户表类，处理隐私数据后返回用户信息给前端展示
 * @param:
 * @author: Hanson
 * @create: 2020-04-20 21:49
 **/
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberTable {
    private final static String ENABLE = "已启用";
    private final static String DISABLE = "已禁用";
    private final static String RECYCLE = "回收";
    private String memberId;    //用户id
    private String memberName;  //用户名
    private String sex;         //性别
    private String memberState;//用户状态
    private String createTime;  //用户创建日期
    public MemberTable(String memberId,String memberName,String sex,Integer memberState,String createTime){
        this.memberId = memberId;
        this.memberName = memberName;
        this.sex = sex;
        if (memberState == 1){
            this.memberState = ENABLE;
        }else if (memberState == 0){
            this.memberState = DISABLE;
        }else {
            this.memberState = RECYCLE;
        }
        this.createTime = createTime;
    }
}
