package com.hanson.service;

import com.hanson.dto.*;
import com.hanson.pojo.Order;
import org.springframework.stereotype.Service;

/**
 * @program: DreamMall
 * @description: 订单service层接口
 * @param:
 * @author: Hanson
 * @create: 2020-04-24 22:25
 **/
@Service
public interface OrderService {

    /*
    * @description: 分页模糊查询订单信息
    * @params: [pageSize, currentPage, key] pageSize：分页大小, currentPage：当前页数, key：搜索关键字
    * @return: com.hanson.dto.PageBean<com.hanson.dto.OrderTable> 订单数据，封装为pageBean对象
    * @Date: 2020/4/24
    */
    public PageBean<OrderTable> getOrderSplit(Integer pageSize,Integer currentPage,String key);



    /*
     * @description: 分页模糊查询回收站订单信息
     * @params: [pageSize, currentPage, key] pageSize：分页大小, currentPage：当前页数, key：搜索关键字
     * @return: com.hanson.dto.PageBean<com.hanson.dto.OrderTable> 订单数据，封装为pageBean对象
     * @Date: 2020/4/24
     */
    public PageBean<OrderTable> getOrderSplitTrash(Integer pageSize,Integer currentPage,String key);


    /*
    * @description: 根据订单id修改订单状态、回收订单操作
    * @params: [oid, state] oid：订单id, state：订单状态
    * @return: com.hanson.dto.Message 返回处理消息
    * @Date: 2020/4/25
    */
    public Message transferOrderState(String oid, Integer state);



    /*
    * @description: 获取订单信息
    * @params: [oid] 订单id
    * @return: com.hanson.dto.OrderTable
    * @Date: 2020/4/25
    */
    public OrderTable getOrderInfo(String oid);



    /*
    * @description: 根据订单id修改订单备注
    * @params: [oid, notes] oid：订单id, notes：备注内容
    * @return: com.hanson.dto.Message 返回处理消息
    * @Date: 2020/4/25
    */
    public Message updateOrderNotes(String oid,String notes);



    /*
    * @description: 查询订单信息以及物流公司信息
    * @params: [oid] 订单号
    * @return: com.hanson.dto.DeliverTable 前端发货表对象
    * @Date: 2020/4/25
    */
    public DeliverTable getOrderLogisticsInfo(String oid);



    /*
    * @description: 更新订单的物流信息
    * @params: [deliver] 发货对象
    * @return: com.hanson.dto.Message 返回处理结果
    * @Date: 2020/4/25
    */
    public Message updateOrderLogistics(Deliver deliver);



    /*
    * @description: 批量恢复订单
    * @params: [ids] 需要恢复的订单id字符串，需要格式化为set集合
    * @return: com.hanson.dto.Message 返回处理消息
    * @Date: 2020/4/25
    */
    public Message batchRecoveryOrders(String ids);


    /*
    * @description:  批量删除订单
    * @params: [ids] 需要删除的订单id字符串，需要格式化为set集合
    * @return: com.hanson.dto.Message 返回处理消息
    * @throws: Exception
    * @Date: 2020/4/25
    */
    public Message batchDeleteOrders(String ids);


    /*
    * @description: 新增物流公司信息
    * @params: [name] 物流名称
    * @return: com.hanson.dto.Message 返回处理结果
    * @Date: 2020/4/25
    */
    public Message increaseLogistics(String name);



    /*
    * @description: 获取订单详细信息
    * @params: [oid] 订单id
    * @return: com.hanson.dto.OrderDetailsTable 订单打印表类
    * @Date: 2020/4/25
    */
    public OrderDetailsTable getOrderDetailsForPrint(String oid);
}
