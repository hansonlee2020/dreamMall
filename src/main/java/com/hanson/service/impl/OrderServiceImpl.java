package com.hanson.service.impl;

import com.hanson.dao.*;
import com.hanson.dto.*;
import com.hanson.pojo.*;
import com.hanson.service.OrderService;
import com.hanson.util.HandleStringUtils;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @program: DreamMall
 * @description: 订单service层接口实现类
 * @param:
 * @author: Hanson
 * @create: 2020-04-24 22:31
 **/
@Service
public class OrderServiceImpl implements OrderService {
    private OrderMapper orderMapper;
    @Autowired
    public void setOrderMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    private MemberOrderMapper memberOrderMapper;
    @Autowired
    public void setMemberOrderMapper(MemberOrderMapper memberOrderMapper) {
        this.memberOrderMapper = memberOrderMapper;
    }

    private MemberMapper memberMapper;
    @Autowired
    public void setMemberMapper(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    private GoodsMapper goodsMapper;
    @Autowired
    public void setGoodsMapper(GoodsMapper goodsMapper) {
        this.goodsMapper = goodsMapper;
    }

    private LogisticsRecordMapper logisticsRecordMapper;
    @Autowired
    public void setLogisticsRecordMapper(LogisticsRecordMapper logisticsRecordMapper) {
        this.logisticsRecordMapper = logisticsRecordMapper;
    }

    private LogisticsMapper logisticsMapper;
    @Autowired
    public void setLogisticsMapper(LogisticsMapper logisticsMapper) {
        this.logisticsMapper = logisticsMapper;
    }

    private LocationMapper locationMapper;
    @Autowired
    public void setLocationMapper(LocationMapper locationMapper) {
        this.locationMapper = locationMapper;
    }

    private ProvinceMapper provinceMapper;
    @Autowired
    public void setProvinceMapper(ProvinceMapper provinceMapper) {
        this.provinceMapper = provinceMapper;
    }

    private CityMapper cityMapper;
    @Autowired
    public void setCityMapper(CityMapper cityMapper) {
        this.cityMapper = cityMapper;
    }

    private AreaMapper areaMapper;
    @Autowired
    public void setAreaMapper(AreaMapper areaMapper) {
        this.areaMapper = areaMapper;
    }

    @Override
    public PageBean<OrderTable> getOrderSplit(Integer pageSize, Integer currentPage, String key) {
        String newKey;//保存格式后的key
        Integer total;
        Integer pages;
        if ("null".equals(key)){
            newKey = null;
        }else {
            newKey = key.replaceAll(" ","");
        }
        try {
            total = orderMapper.doSearchOrder(newKey).size();
            Integer startIndex = (currentPage - 1) * pageSize;
            List<Order> orders = orderMapper.doGetOrderSplit(startIndex, pageSize, newKey);
            if (total % pageSize != 0){
                pages = (int)(((double)total/pageSize) + 1);//总页数,有余数，+1再取整
            }else {
                pages = (int)(((double)total/pageSize));//总页数,没有余数，直接取整
            }
            List<OrderTable> orderTables = new ArrayList<>();
            Iterator<Order> iterator = orders.iterator();
            while (iterator.hasNext()){
                Order next = iterator.next();
                OrderTable orderTable = new OrderTable(next);//转换为前端订单表类
                Member member = memberMapper.doGetMemberById(orderTable.getUserId());
                orderTable.setUserId(member.getMemberName());//使用orderTable的userId字段保存用户名，偷梁换柱！
                orderTables.add(orderTable);
            }
            return new PageBean<>(
                    (long) total,
                    currentPage,
                    pageSize,
                    pages,
                    orders.size(),
                    orderTables
            );
        }catch (Exception e){
            System.err.println("error：" + e);
            return new PageBean<>((long)0,1,10,0,0,new ArrayList<>());
        }
    }

    @Override
    public PageBean<OrderTable> getOrderSplitTrash(Integer pageSize, Integer currentPage, String key) {
        String newKey;//保存格式后的key
        Integer total;
        Integer pages;
        if ("null".equals(key)){
            newKey = null;
        }else {
            newKey = key.replaceAll(" ","");
        }
        try {
            total = orderMapper.doSearchOrderTrash(newKey).size();
            Integer startIndex = (currentPage - 1) * pageSize;
            List<Order> orders = orderMapper.doGetOrderSplitTrash(startIndex, pageSize, newKey);
            if (total % pageSize != 0){
                pages = (int)(((double)total/pageSize) + 1);//总页数,有余数，+1再取整
            }else {
                pages = (int)(((double)total/pageSize));//总页数,没有余数，直接取整
            }
            List<OrderTable> orderTables = new ArrayList<>();
            Iterator<Order> iterator = orders.iterator();
            while (iterator.hasNext()){
                Order next = iterator.next();
                OrderTable orderTable = new OrderTable(next);//转换为前端订单表类
                Member member = memberMapper.doGetMemberById(orderTable.getUserId());
                orderTable.setUserId(member.getMemberName());//使用orderTable的userId字段保存用户名，偷梁换柱！
                orderTables.add(orderTable);
            }
            return new PageBean<>(
                    (long) total,
                    currentPage,
                    pageSize,
                    pages,
                    orders.size(),
                    orderTables
            );
        }catch (Exception e){
            System.err.println("error：" + e);
            return new PageBean<>((long)0,1,10,0,0,new ArrayList<>());
        }
    }

    @Override
    public Message transferOrderState(String oid, Integer state) {
        Message message;
        Integer result = orderMapper.doUpdateOrder(oid,state);
        if (result == 1){
                message = new Message("系统消息","订单" + oid + "已回收！","success");
        }else {
                message = new Message("系统消息","回收失败，订单不存在！","error");
        }
        return message;
    }

    @Override
    public OrderTable getOrderInfo(String oid) {
        Order order = orderMapper.doGetOrderInfoByoid(oid);
        if (order != null){
            OrderTable orderTable = new OrderTable(order);
            Member member = memberMapper.doGetMemberById(order.getUserId());
            orderTable.setUserId(member.getMemberName());
            return orderTable;
        }
        return new OrderTable("","","",0.0,"",null,null,null,null,null);
    }

    @Override
    public Message updateOrderNotes(String oid, String notes) {
        Message message;
        Integer result = orderMapper.doUpdateOrderNotes(oid, notes);
        if (result == 1){
            message = new Message("系统消息","订单" + oid + "备注已修改！","success");
        }else {
            message = new Message("系统消息","订单" + oid + "备注失败！","error");
        }
        return message;
    }

    @Override
    public DeliverTable getOrderLogisticsInfo(String oid) {
        Order order = orderMapper.doGetOrderInfoByoid(oid);
        List<Logistics> logisticsList = logisticsMapper.doGetAllLogistics();
        if (order != null && logisticsList.size() > 0){
            Member member = memberMapper.doGetMemberById(order.getUserId());
            order.setUserId(member.getMemberName());
            return new DeliverTable(order, logisticsList);
        }
        return new DeliverTable("0","0",null,"0");
    }

    @Override
    public Message updateOrderLogistics(Deliver deliver) {
        Message message;
        Order order = orderMapper.doGetOrderInfoByoid(deliver.getOrderId());
        if (order == null){
            return new Message("错误","订单不存在！","error");
        }
        Integer result = orderMapper.doUpdateOrderLogistics(deliver.getOrderId(), deliver.getLogisticsId());
        if (result == 1){
            LogisticsRecord logisticsRecord = new LogisticsRecord(null, deliver.getLogisticsId(),deliver.getLogisticsName());
            Integer logResult = logisticsRecordMapper.doCreateLogisticsRecord(logisticsRecord);
            if (logResult == 1){
                message = new Message("系统消息","发货信息录入成功，即将发货！","success");
            }else {
                message = new Message("错误","物流信息录入失败！请重试","error");
            }
        }else {
            message = new Message("错误","订单信息录入失败！请重试","error");
        }
        return message;
    }

    @Override
    public Message batchRecoveryOrders(String ids) {
        Message message;
        Set<String> newIds = HandleStringUtils.formatToSet(ids);//处理字符串格式
        if (newIds.size() == 0){
            return new Message("错误","需要恢复的数据id为空！","error");
        }else {
            Integer num = orderMapper.doBatchUpdateOrder(newIds);//执行批量恢复
            if (num != 0){
                message = new Message("系统消息","已恢复 " + num + " 条数据！","success");
                return message;
            }else {
                message = new Message("错误","订单不存在，恢复失败！","error");
            }
            return message;
        }
    }

    @Override
    public Message batchDeleteOrders(String ids) {
        Message message;
        Set<String> newIds = HandleStringUtils.formatToSet(ids);//处理字符串格式
        if (newIds.size() == 0){
            return new Message("错误","需要删除的数据id为空！","error");
        }else {
            Integer num = orderMapper.doBatchDeleteOrder(newIds);//执行批量删除
            if (num != 0){
                message = new Message("系统消息","已删除 " + num + " 条数据！","success");
                return message;
            }else {
                message = new Message("系统消息", "订单不存在，删除失败！", "error");
            }
            return message;
        }
    }

    @Override
    public Message increaseLogistics(String name) {
        Message message;
        if (name.length() > 20 ){
            return new Message("系统消息", "物流公司名称格式不符合要求！", "error");
        }else {
            try {
                Integer result = logisticsMapper.doCreateLogistics(new Logistics(null, name.replaceAll(" ", "")));
                if (result == 1 ){
                    message = new Message("系统消息","已添加名称为：" + name.replaceAll(" ","") +  "的物流","success");
                    return message;
                }else {
                    message = new Message("系统消息", "添加物流失败！", "error");
                }
                return message;
            }catch (Exception e){
                System.err.println("error：" + e);
                return new Message("错误", "物流已存在！", "error");
            }
        }
    }

    @Override
    public OrderDetailsTable getOrderDetailsForPrint(String oid) {
        Order order = orderMapper.doGetOrderById(oid);//获取订单信息
        OrderTable orderTable = new OrderTable(order);//转换为订单表格式,格式化时间数据
        String userId = order.getUserId();
        Double subTotal = 0.0;//小计
        Double sum = 0.0;//总计
        String notes;//备注
        if (order.getNotes() == null){
            notes = "";
        }else {
            notes = order.getNotes();
        }
        //获取订单的商品列表
        List<MemberOrder> memberOrders = memberOrderMapper.doGetMemberOrderByoid(oid);//订单的商品列表
        Member member = memberMapper.doGetMemberById(userId);//获取用户信息
        List<OrderGoodsTable> orderGoodsTables = new ArrayList<>();//保存订单的商品列表信息
        for (MemberOrder memberOrder : memberOrders) {
            String productId = memberOrder.getProductId();
            Integer quantity = memberOrder.getQuantity();
            Goods goods = goodsMapper.doGetGoodsById(productId);
            Double price = goods.getPrice();
            String productName = goods.getProductName();
            subTotal = quantity*price;
            OrderGoodsTable orderGoodsTable = new OrderGoodsTable(productName, productId, price, quantity, subTotal);
            orderGoodsTables.add(orderGoodsTable);//添加到商品列表
            sum += subTotal;
        }
        //获取用户地址信息
        String address;
        Location location = locationMapper.doGetLocationById(member.getLocationId());
        if (location == null){
            address = "广东省广州市梦想购物中心测试团队";
        }else {
            Province province = provinceMapper.doGetProvinceById(location.getProvinceId());
            City city = cityMapper.doGetCityById(location.getCityId());
            Area area = areaMapper.doGetAreaById(location.getAreaId());
            String addressDetails = location.getAddress();
            //拼接地址
            address = province.getProvinceName() + city.getCityName() + area.getAreaName() + addressDetails;
        }
        return new OrderDetailsTable(
                member.getMemberName(),
                orderTable.getCreateTime(),
                order.getOrderId(),
                "在线支付",
                orderTable.getPayTime(),
                "",
                orderTable.getLogisticsId(),
                "测试团队",
                member.getMobilePhone(),
                address,
                orderGoodsTables,
                sum,
                notes
        );
    }
}
