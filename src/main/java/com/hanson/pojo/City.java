package com.hanson.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @program: DreamMall
 * @description: 市表实体类
 * @param:
 * @author: Hanson
 * @create: 2020-04-25 21:07
 **/
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class City {
    private Integer id;     //主键id
    private String cityId;  //城市id
    private String cityName;    //城市名称
    private String provinceId;  //城市所属省份id
}
