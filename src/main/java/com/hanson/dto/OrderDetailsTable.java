package com.hanson.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: DreamMall
 * @description: 订单全部信息表，用于前端打印订单
 * @param:
 * @author: Hanson
 * @create: 2020-04-25 19:31
 **/
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsTable {
    private String userName;        //订单的用户名
    private String createTime;      //订单创建时间
    private String orderId;         //订单的id
    private String payWay;          //订单的支付方式
    private String payTime;         //订单的支付时间
    private String deliveryTime;    //订单交付完成的时间
    private String logisticsId;     //订单的物流单号id
    private String receiver;        //收货人
    private String phone;           //收货人手机号
    private String address;         //收货人地址
    private List<OrderGoodsTable> list;     //订单的已购买商品列表
    private Double sumPrice;        //订单的总支付金额
    private String notes;           //订单备注
}
