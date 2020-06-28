package com.hanson.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @program: DreamMall
 * @description: 订单表实体类
 * @param:
 * @author: Hanson
 * @create: 2020-04-24 15:42
 **/
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private String orderId;     //订单号id
    private String userId;      //用户id
    private String logisticsId;     //物流号
    private Double paymentAmount;   //订单的支付金额
    private String notes;           //订单备注
    private Date createTime;        //订单创建时间
    private Date payTime;           //订单的支付时间
    private Date closeTime;         //订单的关闭时间
    private Date finishTime;        //订单交付完成时间
    private Integer orderState;     //订单的状态
}
