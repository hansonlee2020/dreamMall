package com.hanson.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @program: DreamMall
 * @description: 商品表，用于返回商品对象的具体信息，进行商品数据回显展示，实现了序列化接口
 * 和比较接口
 * @param:
 * @author: Hanson
 * @create: 2020-04-12 14:37
 **/
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsTable implements Serializable,Comparable<GoodsTable> {
    private static final String AUDITING = "审核中";//状态为0
    private static final String RELEASED = "已发布";//状态为1
    private static final String OFF_SHELF = "已下架";//状态为2
    private String productId;       //商品id
    private String productName;     //商品名称
    private String briefInfo;       //商品简介
    private Double price;           //商品价格
    private Integer stock;          //商品库存
    private Integer limitNum;       //商品限购数量
    private String thirdCategory;     //三级分类名
    private String releaseState;     //商品发布状态
    private String imageSrc;           //商品图片路径，只保存一张
    private String productDetails;     //商品详情

    public GoodsTable(String productId,String productName,String briefInfo,Double price,Integer stock,Integer limitNum,String thirdCategory,Integer state,String imageSrc,String productDetails){
        this.productId = productId;
        this.productName = productName;
        this.briefInfo = briefInfo;
        this.price = price;
        this.stock = stock;
        this.limitNum = limitNum;
        this.thirdCategory = thirdCategory;
        if (state == 0){
            this.releaseState = AUDITING;
        }else if (state ==1){
            this.releaseState = RELEASED;
        }else {
            this.releaseState = OFF_SHELF;
        }
        this.imageSrc = imageSrc;
        this.productDetails = productDetails;
    }

    /**进行商品排序，排序规则按照比较商品id的首位，进行升序排序
     * @param o 需要比较的商品对象
     * @return 返回比较结果，结果为负数表示商品对象o排靠后，正数表示商品对象o排靠前，
     * 等于0表示两个商品对象并列，排序结果按照先到的对象排前面，后来的对象排后面
     */
    @Override
    public int compareTo(GoodsTable o) {
        return this.productId.charAt(1) - o.productId.charAt(1);//默认按照id的首位升序排序
    }
}
