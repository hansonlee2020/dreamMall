package com.hanson.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @program: DreamMall
 * @description: 订单物流信息表
 * @param:
 * @author: Hanson
 * @create: 2020-04-25 13:21
 **/
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogisticsRecord {
    private Integer id;         //物流信息id
    private String logisticsId;     //物流单号id
    private String logisticsName;   //物流公司名称
}
