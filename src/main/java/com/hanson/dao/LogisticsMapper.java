package com.hanson.dao;

import com.hanson.pojo.Logistics;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: DreamMall
 * @description: 物流公司表dao接口
 * @param:
 * @author: Hanson
 * @create: 2020-04-25 12:34
 **/
@Repository
public interface LogisticsMapper {
    /*
    * @description: 获取全部物流公司信息
    * @params: []
    * @return: java.util.List<com.hanson.pojo.Logistics>
    * @Date: 2020/4/25
    */
    public List<Logistics> doGetAllLogistics();



    /*
    * @description: 根据物流公司名查询物流表
    * @params: [name] 物流公司名
    * @return: com.hanson.pojo.Logistics
    * @Date: 2020/4/25
    */
    public Logistics doGetLogisticsByName(@Param("name") String name);


    /*
    * @description:  根据物流id查询物流表
    * @params: [id] 物流id
    * @return: com.hanson.pojo.Logistics
    * @Date: 2020/4/25
    */
    public Logistics doGetLogisticsById(@Param("id") Integer id);



    /*
    * @description: 创建物流
    * @params: [logistics] 物流对象
    * @return: java.lang.Integer
    * @Date: 2020/4/25
    */
    public Integer doCreateLogistics(Logistics logistics);

}
