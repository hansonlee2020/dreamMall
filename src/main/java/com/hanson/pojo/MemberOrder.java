package com.hanson.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: DreamMall
 * @description: 用户订单表实体类
 * @param:
 * @author: Hanson
 * @create: 2020-04-24 16:00
 **/
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberOrder {
    private Integer id;     //主键id
    private String orderId;     //用户订单的id
    private String userId;      //该订单的用户id
    private String productId;   //订单的每个商品id
    private Integer quantity;   //订单的每个商品对应的商品数量
}
