package com.hanson.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @program: DreamMall
 * @description: 商品编辑表对象，用于接收需要编辑的商品信息
 * @param:
 * @author: Hanson
 * @create: 2020-04-15 20:06
 **/
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsEditTable {
    private String productId;       //商品id
    private String productName;     //商品名称
    private String briefInfo;       //商品简介
    private Double price;           //商品价格
    private Integer stock;          //商品库存
    private Integer limitNum;       //商品限购数量
    private String category1;       //一级分类
    private String category2;       //二级分类
    private String category3;       //三级分类
    private Integer releaseState;     //商品发布状态
    private String imageSrc;           //商品图片路径，只保存一张
    private String productDetails;     //商品详情
}
