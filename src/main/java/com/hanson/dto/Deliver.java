package com.hanson.dto;

import com.hanson.pojo.Logistics;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: DreamMall
 * @description: 用于接收前端的发货对象
 * @param:
 * @author: Hanson
 * @create: 2020-04-25 13:05
 **/
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Deliver {
    private String orderId;     //要发货的订单id
    private String userId;      //要发货的订单的用户id
    private String logisticsName;   //物流公司名
    private String logisticsId;     //物流单id号
}
