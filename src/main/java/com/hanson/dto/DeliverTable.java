package com.hanson.dto;

import com.hanson.pojo.Logistics;
import com.hanson.pojo.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: DreamMall
 * @description: 前端发货表，用于返回给前端，进行发货选择
 * @param:
 * @author: Hanson
 * @create: 2020-04-25 12:54
 **/
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliverTable {
    private String orderId;     //需要发货的订单id
    private String userId;      //该订单的用户id
    private List<Logistics> list; //可选择的物流公司列表
    private String logisticsId;     //该订单的物流单号，如果没有，默认为0
    public DeliverTable(Order order,List<Logistics> list){//order为订单对象，list为物流公司列表对象
        if (order.getOrderId() == null){
            this.orderId = "0";
        }else {
            this.orderId = order.getOrderId();
        }
        if (order.getUserId() == null){
            this.userId = "0";
        }else {
            this.userId = order.getUserId();
        }
        if (order.getLogisticsId() == null){
            this.logisticsId = "0";
        }else {
            this.logisticsId = order.getLogisticsId();
        }
        if (list.size() > 0){
            this.list = list;
        }else {
            this.list = null;
        }
    }
}
