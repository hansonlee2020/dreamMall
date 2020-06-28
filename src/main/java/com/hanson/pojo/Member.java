package com.hanson.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @program: DreamMall
 * @description: 前台系统用户表对应的用户类，实现了比较接口
 * @param:
 * @author: Hanson
 * @create: 2020-04-20 20:24
 **/
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member implements Comparable<Member> {
    private String memberId;    //用户id
    private String memberName;  //用户名
    private String memberPwd;   //用户密码
    private String encryptionSalt;  //加密盐
    private String sex;         //性别
    private String mobilePhone; //用户手机
    private String email;       //用户邮箱
    private Integer locationId; //用户地址
    private Integer memberState;    //用户状态
    private Date createTime;    //用户创建日期

    /**用于用户信息排序，排序规则为用户id首位升序
     * @param o 要比较的用户对象
     * @return 返回结果为正数表示当前对象排靠后，返回结果为负数，表示比较的用户对象排靠后，
     *      * 返回结果为0，表示两对象并排
     */
    @Override
    public int compareTo(Member o) {
        return this.memberId.charAt(1) - o.memberId.charAt(1);//默认按照id的首位升序排序
    }
}
