package com.hanson.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hanson.dto.Deliver;
import com.hanson.dto.Message;
import com.hanson.service.impl.OrderServiceImpl;
import com.sun.jdi.VoidType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: DreamMall
 * @description: 订单业务控制层接口，可以处理订单信息的查看、检索、新增、更改、删除、恢复、
 * 更新物流、添加订单备注、打印订单业务
 * @param:
 * @author: Hanson
 * @create: 2020-04-24 14:44
 **/
@Controller
@RequestMapping("/order")
public class OrderController {
    private OrderServiceImpl orderService;
    @Autowired
    public void setOrderService(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    //跳转到订单列表
    @RequestMapping("/toList")
    public String toOrderList(){
        return "order_list";
    }

    //分页模糊查询订单、检索订单
    @RequestMapping("/list")
    @ResponseBody
    public JSONObject orderListSplit(@RequestParam("pageSize") Integer pageSize ,
                                     @RequestParam("currentPage") Integer currentPage,
                                     @RequestParam("searchKey") String key){
        return (JSONObject) JSON.toJSON(orderService.getOrderSplit(pageSize,currentPage,key));
    }
    //回收订单
    @RequestMapping("/transfer")
    @ResponseBody
    public JSONObject orderRecycle(@RequestParam("orderId") String id ,
                                   @RequestParam("state") Integer state){
//        Message message = new Message("test","111111","success");
        return (JSONObject) JSON.toJSON(orderService.transferOrderState(id,state));
    }
    //查询订单信息
    @RequestMapping("/query")
    @ResponseBody
    public JSONObject orderInfo(@RequestParam("orderId") String oid){
//        Message message = new Message("test","111111","success");
        return (JSONObject) JSON.toJSON(orderService.getOrderInfo(oid));
    }
    //备注订单
    @RequestMapping("/update")
    @ResponseBody
    public JSONObject orderUpdateNotes(@RequestParam("orderId") String oid,@RequestParam("notes")String text){
        return (JSONObject) JSON.toJSON(orderService.updateOrderNotes(oid,text));
    }
    //查询订单和物流信息
    @RequestMapping("/logistics/info")
    @ResponseBody
    public JSONObject orderLogisticsInfo(@RequestParam("orderId") String oid){
        return (JSONObject) JSON.toJSON(orderService.getOrderLogisticsInfo(oid));
    }
    //发货
    @RequestMapping("/deliver")
    @ResponseBody
    public JSONObject orderDeliver(Deliver deliver){
//        Message message = new Message("test","111111","success");
        return (JSONObject) JSON.toJSON(orderService.updateOrderLogistics(deliver));
    }

    //跳转订单回收站
    @RequestMapping("/toTrash")
    public String toOrderTrash(){
        return "order_trash";
    }
    //分页模糊查询回收站订单、检索订单
    @RequestMapping("/trash")
    @ResponseBody
    public JSONObject orderListSplitTrash(@RequestParam("pageSize") Integer pageSize ,
                                     @RequestParam("currentPage") Integer currentPage,
                                     @RequestParam("searchKey") String key){
        return (JSONObject) JSON.toJSON(orderService.getOrderSplitTrash(pageSize, currentPage, key));
    }
    //删除订单
    @RequestMapping("/delete")
    @ResponseBody
    public JSONObject orderDelete(@RequestParam("deleteId") String id){
//        Message message = new Message("test","111111","success");

        return (JSONObject) JSON.toJSON(orderService.batchDeleteOrders(id));
    }
    //恢复订单
    @RequestMapping("/recovery")
    @ResponseBody
    public JSONObject orderRecovery(@RequestParam("orderId") String id){
        return (JSONObject) JSON.toJSON(orderService.batchRecoveryOrders(id));
    }
    //批量删除订单
    @RequestMapping("/deletes")
    @ResponseBody
    public JSONObject orderBatchDelete(@RequestParam("ids") String ids){
        return (JSONObject) JSON.toJSON(orderService.batchDeleteOrders(ids));
    }
    //批量恢复订单
    @RequestMapping("/recoveries")
    @ResponseBody
    public JSONObject orderBatchRecovery(@RequestParam("ids") String ids){
        return (JSONObject) JSON.toJSON(orderService.batchRecoveryOrders(ids));
    }
    //添加物流
    @RequestMapping("/add/logistics")
    @ResponseBody
    public JSONObject orderIncreaseLogistics(@RequestParam("logisticsName") String name){
        return (JSONObject) JSON.toJSON(orderService.increaseLogistics(name));
    }
    //跳转订单打印
    @RequestMapping("/toPrint/{orderId}")
    public String toOrderPrint(@PathVariable("orderId") String oid , Model model){
        String orderId = orderService.getOrderInfo(oid).getOrderId();
        model.addAttribute("orderId",orderId);
        return "order_print";
    }
    //订单详情
    @RequestMapping("/print/details")
    @ResponseBody
    public JSONObject orderDetailsPrint(@RequestParam("orderId") String oid){
        return (JSONObject) JSON.toJSON(orderService.getOrderDetailsForPrint(oid));
    }
}
