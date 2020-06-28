package com.hanson.dao;

import com.hanson.pojo.LogisticsRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: DreamMall
 * @description: 订单物流信息接口
 * @param:
 * @author: Hanson
 * @create: 2020-04-25 13:25
 **/
@Repository
public interface LogisticsRecordMapper {
    /*
    * @description: 获取全部订单物流信息
    * @params: []
    * @return: java.util.List<com.hanson.pojo.LogisticsRecord>
    * @Date: 2020/4/25
    */
    public List<LogisticsRecord> doGetALlLogisticsRecords();


    /*
    * @description: 根据物流号查询物流信息
    * @params: [logisticsId] 物流号
    * @return: com.hanson.pojo.LogisticsRecord
    * @Date: 2020/4/25
    */
    public LogisticsRecord doGetLogisticsRecordById(@Param("logisticsId") String logisticsId);



    /*
    * @description: 新建物流信息
    * @params: [logisticsRecord] 物流信息对象
    * @return: java.lang.Integer 创建成功返回1，否则抛出异常信息
    * @throws: Exception SQL执行异常，违反唯一约束
    * @Date: 2020/4/25
    */
    public Integer doCreateLogisticsRecord(LogisticsRecord logisticsRecord);
}
