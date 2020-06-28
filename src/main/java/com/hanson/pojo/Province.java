package com.hanson.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @program: DreamMall
 * @description: 省份表实体类
 * @param:
 * @author: Hanson
 * @create: 2020-04-25 20:52
 **/
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Province {
    private Integer id;     //主键id
    private String provinceId;  //省份id
    private String provinceName;    //省份名称
}
