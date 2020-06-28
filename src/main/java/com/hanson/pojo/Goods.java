package com.hanson.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: DreamMall
 * @description: 商品类，描述一个商品
 * @param:
 * @author: Hanson
 * @create: 2020-03-27 18:55
 **/
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods {
    private String productId;       //商品id，后台使用UUID类生成，不需要输入
    private String productName;     //商品名称
    private String briefInfo;       //商品简介
    private Double price;           //商品价格
    private Integer stock;          //商品库存
    private Integer limitNum;       //商品限购数量
    private Integer categoryId;     //商品的分类id
    private Integer releaseState;     //商品发布状态
    private String imageSrc;           //商品图片路径
    private String productDetails;     //商品详情
}
