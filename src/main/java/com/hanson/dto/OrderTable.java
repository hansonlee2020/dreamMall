package com.hanson.dto;

import com.hanson.pojo.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: DreamMall
 * @description: 订单表实体类，用于回显订单信息给展示页面
 * @param:
 * @author: Hanson
 * @create: 2020-04-24 17:31
 **/
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderTable {
    //格式化日期为2020-01-01 00:00:00格式
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private final static String SUCCESS="交易成功";//订单状态为1
    private final static String PAID="已支付";//订单状态为2
    private final static String TO_BE_PAID="待支付";//订单状态为3
    private final static String CLOSE="交易关闭";//订单状态为4
    private final static String UNRECOGNIZED="未识别";//订单状态为5
    private String orderId;     //订单id
    private String userId;      //该订单的用户id
    private String logisticsId; //物流单号id
    private Double paymentAmount;//订单支付金额
    private String notes;       //订单的备注
    private String createTime;  //订单创建时间
    private String payTime;     //订单支付时间
    private String closeTime;   //订单关闭时间
    private String finishTime;  //订单交付完成时间
    private String orderState;  //订单的状态
    public OrderTable(String orderId,
                 String userId,
                 String logisticsId,
                 Double paymentAmount,
                 String notes,
                 Date createTime,
                 Date payTime,
                 Date closeTime,
                 Date finishTime,
                 Integer orderState){
        this.orderId = orderId;
        this.userId = userId;
        this.logisticsId = logisticsId;
        this.paymentAmount = paymentAmount;
        this.notes = notes;
        if (createTime == null){
            this.createTime = "";
        }else {
            this.createTime = sdf.format(createTime);
        }
        if (payTime == null){
            this.payTime = "";
        }else {
            this.payTime = sdf.format(payTime);
        }
        if (closeTime == null){
            this.closeTime = "";
        }else {
            this.closeTime = sdf.format(closeTime);
        }
        if (finishTime == null){
            this.finishTime ="";
        }else {
            this.finishTime = sdf.format(finishTime);
        }
        if (orderState == 1){
            this.orderState = SUCCESS;
        }else if (orderState == 2){
            this.orderState = PAID;
        }else if (orderState == 3){
            this.orderState = TO_BE_PAID;
        }else if (orderState == 4){
            this.orderState = CLOSE;
        }else {
            this.orderState = UNRECOGNIZED;
        }
    }
    public OrderTable(Order order){
        this.orderId = order.getOrderId();
        this.userId = order.getUserId();
        if (order.getLogisticsId() == null){
            this.logisticsId = "";
        }else {
            this.logisticsId = order.getLogisticsId();
        }
        if (order.getPaymentAmount() == null){
            this.paymentAmount = 0.0;
        }else {
            this.paymentAmount = order.getPaymentAmount();
        }
        if (order.getNotes() == null){
            this.notes = "";
        }else {
            this.notes = order.getNotes();
        }
        if (order.getCreateTime() == null){
            this.createTime = "";
        }else {
            this.createTime = sdf.format(order.getCreateTime());
        }
        if (order.getPayTime() == null){
            this.payTime = "";
        }else {
            this.payTime = sdf.format(order.getPayTime());
        }
        if (order.getCloseTime() == null){
            this.closeTime = "";
        }else {
            this.closeTime = sdf.format(order.getCloseTime());
        }
        if (order.getFinishTime() == null){
            this.finishTime ="";
        }else {
            this.finishTime = sdf.format(order.getFinishTime());
        }
        if (order.getOrderState() == null){
            this.orderState = "";
        }else if (order.getOrderState() == 1){
            this.orderState = SUCCESS;
        }else if (order.getOrderState() == 2){
            this.orderState = PAID;
        }else if (order.getOrderState() == 3){
            this.orderState = TO_BE_PAID;
        }else if (order.getOrderState() == 4){
            this.orderState = CLOSE;
        }else {
            this.orderState = UNRECOGNIZED;
        }
    }
}
