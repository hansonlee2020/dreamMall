package com.hanson.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @program: DreamMall
 * @description: 物流公司表实体类
 * @param:
 * @author: Hanson
 * @create: 2020-04-25 12:32
 **/
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Logistics {
    private Integer id;     //物流公司id
    private String logisticsName;   //物流公司名称
}
