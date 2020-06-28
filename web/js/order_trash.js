/*订单回收站业务处理js文件*/

$(function () {
    pageOrderTrash();//分页
    document.getElementById("page-select").addEventListener("change",initOrderSelectTrash);
    document.getElementById("select-all-trash").addEventListener("click",selectAllTrash);
})
//分页订单用户
function pageOrderTrash() {
    let url = "/order/trash";
    pageSplitTrash(url,"null");
}
//分页函数
function pageSplitTrash(url,key) {
    let pageSize = document.getElementById("page-select").value;
    $.ajax({ //去后台查询第一页数据
        type : "get",
        url : url,
        contentType :  'application/json;charset=UTF-8',
        dataType : "json",
        data : {pageSize : pageSize,currentPage : 1,searchKey : key}, //参数：当前页为1
        success : function(result) {

            appendHtml(result.list)//处理第一页的数据
            let start;
            let end;
            if (result.total == 0){
                start = 0;
                end = 0;
            }else {
                start = (result.currentPage - 1) * result.pageSize + 1;
                end = start + result.size - 1;
            }
            tipsChange(start,end,result.total);//数据提示信息
            initStateTrash();
            let options = {//根据后台返回的分页相关信息，设置插件参数
                size:"small",//分页模型选择小
                bootstrapMajorVersion : 3, //如果是bootstrap3版本需要加此标识，并且设置包含分页内容的DOM元素为UL,如果是bootstrap2版本，则DOM包含元素是DIV
                currentPage : result.currentPage, //当前页数
                totalPages : result.totalPage, //总页数
                numberOfPages : result.size,//每页记录数
                onPageClicked : function(event, originalEvent, type, page) {//分页按钮点击事件
                    loading();//加载时提示样式
                    $.ajax({//根据page去后台加载数据
                        url : url,
                        type : "get",
                        dataType : "json",
                        data : {"currentPage" : page,"pageSize" : pageSize,"searchKey" : key},
                        success : function(result) {
                            appendHtml(result.list);//处理数据
                            let start = (result.currentPage - 1) * result.pageSize + 1;
                            let end = start + result.size - 1;
                            tipsChange(start,end,result.total);
                            loaded();//恢复提示样式
                            initStateTrash();
                        }
                    });
                }
            };
            $('#order-split').bootstrapPaginator(options);//设置分页
        }
    });
}
//数据展示页面渲染
function appendHtml(list) {
    let tableTbody = '';
    for (let i = 0; i < list.length; i++) {
        tableTbody += '<tr style="word-wrap: break-word">';
        tableTbody += '<td><input type="checkbox" id="select-' + i +'" style="width: 20px" value="' + list[i].orderId + '" onclick="selectTrashThis(this)"></td>';
        tableTbody += '<td><span style="width: 50px;">' + list[i].orderId + '</span></td>';
        tableTbody += '<td><span style="width: 50px;">' + list[i].userId + '</span></td>';
        tableTbody += '<td><span style="width: 50px;">' + list[i].logisticsId + '</span></td>';
        tableTbody += '<td><span style="width: 130px;">' + list[i].notes + '</span></td>';
        tableTbody += '<td><span style="width: 100px;">'+ list[i].createTime + '</span></td>';
        tableTbody += '<td><span style="width: 100px;">'+ list[i].payTime + '</span></td>';
        tableTbody += '<td><span style="width: 100px;">'+ list[i].closeTime + '</span></td>';
        tableTbody += '<td><span style="width: 100px;">'+ list[i].finishTime + '</span></td>';
        tableTbody += '<td><span style="width: 70px;">￥'+ list[i].paymentAmount + '</span></td>';
        tableTbody += '<td><span style="width: 50px;" class="state-trash">'+ list[i].orderState + '</span></td>';
        tableTbody += '<td><div style="padding-top: 0">';
        tableTbody += '<a href="#" aria-label="Save" style="color: greenyellow;text-decoration: none" data-toggle="tooltip" data-placement="bottom" title="恢复"'
            + ' data-modal="message-window" onclick="saveThis(this)" id="save-' + list[i].orderId + '">'
            + '<span class="glyphicon glyphicon-save" aria-hidden="true"></span>&nbsp;|&nbsp;</a>'
            + '<a class="md-trigger" href="#" aria-label="Trash" style="color: red" data-toggle="tooltip" data-placement="bottom" title="彻底删除"'
            + ' data-modal="message-window" onclick="deleteThis(this)" id="delete-' + list[i].orderId + '">'
            + '<span class="glyphicon glyphicon-trash" aria-hidden="true"></span></a></div></td>';
        tableTbody += '</tr>';
    }
    $('#order-trash-table tbody').html(tableTbody);
}
/*选择列表内容改变时执行start*/
function initOrderSelectTrash() {
    initSelect(pageOrderTrash);//重新请求分页
}
/*选择列表内容改变时执行end*/

/*初始化状态栏start*/
function initStateTrash() {
    let stateElements = document.getElementsByClassName("state-trash");
    for(let i = 0; i < stateElements.length ; i ++){
        stateElements[i].style.color = "red";
    }
}
/*初始化状态栏end*/

/*全选start*/
function selectAllTrash() {
    selectAll("select-all-trash","ids-to-do-trash");
}
/*全选end*/
/*单选start*/
function selectTrashThis(obj) {
    selectByOne(obj,"ids-to-do-trash");
}
/*单选end*/

/*操作start*/
//恢复订单
//恢复用户
function saveThis(obj) {
    //id形式为save-id
    let orderId = obj.id;
    let id = orderId.substring(5);
    let text = "确定要恢复id：" + id + "的订单吗？";
    operationTransfer(obj,id,text,saveOrder);
}
function saveOrder() {
    let id = document.getElementById("id").value;
    let url = "/order/recovery";
    let info = '{"orderId":"' + id + '"}';
    let data = JSON.parse(info);
    startOperationTransfer(url,data,saveOrder,pageOrderTrash);
}

//彻底删除订单
function deleteThis(obj) {
    //元素id形式为delete-id
    let elementId = obj.id;
    let orderId = elementId.substring(7);//截取id信息
    let text = "确定要删除id：" + orderId + "的订单吗？";
    operation(obj,orderId,text,deleteTrash);
}
function deleteTrash() {
    let value = document.getElementById("id").value;
    let url = "/order/delete";
    let info = '{' + '"deleteId":"' + value + '"}';
    let data = JSON.parse(info);
    startOperation(url,data,deleteTrash,pageOrderTrash);
}
//批量删除订单
function batchDeleteThis(obj) {
    let rightText = "彻底删除后数据不可恢复，确定要彻底删除这些订单吗？";
    let wrongText = "你还没勾选任何数据！";
    batch(obj,"ids-to-do-trash",rightText,wrongText,batchDeleteOrder);
}
function batchDeleteOrder() {
    let url = "/order/deletes";
    let ids = document.getElementById("ids-to-do-trash").value;
    batchConfirm(url,ids,batchDeleteOrder,pageOrderTrash);
}
//批量恢复订单
function batchRecoveryThis(obj) {
    let rightText = "订单将恢复为关闭订单，确定要恢复这些订单吗？";
    let wrongText = "你还没勾选任何数据！";
    batch(obj,"ids-to-do-trash",rightText,wrongText,batchRecoveryOrder);
}
function batchRecoveryOrder() {
    let url = "/order/recoveries";
    let ids = document.getElementById("ids-to-do-trash").value;
    batchConfirm(url,ids,batchRecoveryOrder,pageOrderTrash);
}
//搜索回收站订单
function searchOrderTrash() {
    let url = "/order/trash";
    search(url,pageSplitTrash,"search-key");
}
//初始化检索提示
function initToOriTips() {
    initToOri("error-msg");
}
/*操作end*/