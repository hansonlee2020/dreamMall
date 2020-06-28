package com.hanson.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @program: DreamMall
 * @description: 商品分类的实体类，保存商品分类信息
 * @param:
 * @author: Hanson
 * @create: 2020-04-04 09:37
 **/
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategory {
    private Integer categoryId;     //商品分类id
    private String categoryFirstName;   //商品的一级分类名称
    private String categorySecondName;  //商品的二级分类名称
    private String categoryThirdName;   //商品的三级分类名称
}
