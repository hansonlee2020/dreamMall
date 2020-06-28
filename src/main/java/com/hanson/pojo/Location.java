package com.hanson.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @program: DreamMall
 * @description: 位置信息表实体类
 * @param:
 * @author: Hanson
 * @create: 2020-04-25 19:53
 **/
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    private Integer id;     //主键id
    private Integer locationId;     //位置id
    private String provinceId;      //省份id
    private String cityId;      //城市id
    private String areaId;  //区/县id
    private String address;     //详细地址，如xxx镇xxx村或者xxx街道xxx号
}
