package com.hanson.dao;

import com.hanson.pojo.MemberOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: DreamMall
 * @description: 用户订单表接口
 * @param:
 * @author: Hanson
 * @create: 2020-04-24 17:36
 **/
@Repository
public interface MemberOrderMapper {

    /*
    * @description: 获取全部订单
    * @params: []
    * @return: java.util.List<com.hanson.pojo.MemberOrder>
    * @Date: 2020/4/24
    */
    public List<MemberOrder> doGetMemberOrders();


    /*
    * @description: 根据订单id查询用户订单所含商品信息
    * @params: [oid] 订单id
    * @return: java.util.List<com.hanson.pojo.MemberOrder> 用户订单的信息集合
    * @Date: 2020/4/24
    */
    public List<MemberOrder> doGetMemberOrderByoid(@Param("orderId") String oid);


    /*
    * @description: 根据用户id查询用户订单所含商品信息
    * @params: [uid] 用户id
    * @return: java.util.List<com.hanson.pojo.MemberOrder> 用户订单的信息集合
    * @Date: 2020/4/24
    */
    public List<MemberOrder> doGetMemberOrderByuid(@Param("userId") String uid);


    /*
    * @description: 新增用户订单
    * @params: [memberOrder] 用户订单对象
    * @return: java.lang.Integer 新增成功返回1.否则返回0
    * @Date: 2020/4/24
    */
    public Integer doCreateMemberOrder(MemberOrder memberOrder);


    /*
    * @description: 初始化用户订单
    * @params: []
    * @return: java.lang.Integer
    * @Date: 2020/5/2
    */
    public Integer doInitMemberOrder();


}
