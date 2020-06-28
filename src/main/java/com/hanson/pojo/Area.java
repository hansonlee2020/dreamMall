package com.hanson.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @program: DreamMall
 * @description: 区表实体类
 * @param:
 * @author: Hanson
 * @create: 2020-04-25 21:08
 **/
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Area {
    private Integer id;     //主键id
    private String areaId;  //区id
    private String areaName;    //区名
    private String cityId;      //区所属城市id
}
