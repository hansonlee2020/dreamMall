/*订单打印业务处理js文件*/

$(function () {
    getOrder();
    // backOff();
})
//开始请求订单信息
function getOrder() {
    let orderId = document.getElementById("order-id").value;
    let url = "/order/print/details";
    let info = '{"orderId":"' + orderId + '"}';
    let data = JSON.parse(info);
    sendOrder(url,data);
}
//请求订单详情
function sendOrder(url,data) {//url：请求路径，data：要发送的数据
    $.ajax({ //发送
        type : "get",
        url : url,
        contentType :  'application/json;charset=UTF-8',
        dataType : "json",
        data : data,
        success : function(result) {//后台处理结果信息回显，result：处理结果
            //渲染订单数据
            appendHtmlOrder(result);
            appendHtmlGoodsList(result.list);
            BottomInformation(result);
        }
    });
}
//渲染订单数据
function appendHtmlOrder(data) {
    let li = '';
    li += '<li><label>会员名：</label><span>' + data.userName + '</span></li>';
    li += '<li><label>下单时间：</label><span>' + data.createTime + '</span></li>';
    li += '<li><label>订单编号：</label><span>' + data.orderId + '</span></li>';
    li += '<li><label>支付方式：</label><span>' + data.payWay + '</span></li>';
    li += '<li><label>支付时间：</label><span>' + data.payTime + '</span></li>';
    li += '<li><label>发货时间：</label><span>' + data.deliveryTime + '</span></li>';
    li += '<li><label>物流单号：</label><span>' + data.logisticsId + '</span></li>';
    li += '<li><label>收货人：</label><span>' + data.receiver + '</span></li>';
    li += '<li><label>手机号码：</label><span>' + data.phone + '</span></li>';
    li += '<li style="width: 735px"><label>收货地址：</label><span>' + data.address + '</span></li>';
    $('#order-info ul').html(li);
}
//渲染订单商品列表数据
function appendHtmlGoodsList(list) {
    let tableTbody = '';
    for (let i = 0 ; i < list.length ; i ++){
        tableTbody += '<tr style="word-wrap: break-word">';
        tableTbody += '<td><span style="width: 200px;">' + list[i].productName + '</span></td>';
        tableTbody += '<td><span style="width: 300px;">' + list[i].productId + '</span></td>';
        tableTbody += '<td><span style="width: 80px;">￥' + list[i].price + '</span></td>';
        tableTbody += '<td><span style="width: 80px;">'+ list[i].quantity + '</span></td>';
        tableTbody += '<td><span style="width: 100px;">￥'+ list[i].subTotal + '</span></td>';
        tableTbody += '</tr>'
    }
    $('#order-table tbody').html(tableTbody);
}
//渲染底部信息
function BottomInformation(data) {
    let totalDiv = '';
    let notesDiv = '';
    totalDiv += '<label>总金额：</label><span>￥' + data.sumPrice + '</span>';
    notesDiv += '<label style="width: unset">订单备注：</label>'
        + '<span style="margin-inline-start: 60px">' + data.notes + '</span>';
    $('#total-price').html(totalDiv);
    $('#notes').html(notesDiv);
}
//返回
function backOff() {
    let element = document.getElementById("back-off");
    element.href = "/order/toList";
}
//隐藏提示
function hideTips() {
    document.getElementById("tips-print").hidden = true;
    document.getElementById("back-off").hidden = true;
    document.getElementById("print-order").hidden = true;
}
//恢复提示
function initHide() {
    document.getElementById("tips-print").hidden = false;
    document.getElementById("back-off").hidden = false;
    document.getElementById("print-order").hidden = false;
}
//打印
function myPrint() {
    hideTips();
    window.print();
    initHide();
}