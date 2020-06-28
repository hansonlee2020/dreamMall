package com.hanson.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @program: DreamMall
 * @description: 权限分配信息类，保存的是用户所拥有的权限id信息
 * @param:
 * @author: Hanson
 * @create: 2020-03-30 16:08
 **/
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Permission {
    private String userId;      //获得权限分配的用户id
    private Integer authorityId;    //所分配的权限id
}
