package com.hanson.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @program: DreamMall
 * @description: 订单的商品列表信息
 * @param:
 * @author: Hanson
 * @create: 2020-04-25 19:37
 **/
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderGoodsTable {
    private String productName;     //商品名称
    private String productId;       //商品id
    private Double price;           //商品价格
    private Integer quantity;       //商品购买数量
    private Double subTotal;        //商品总价
}
