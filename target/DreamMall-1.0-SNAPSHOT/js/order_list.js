/*订单业务处理js文件*/

$(function () {
    pageOrder();//分页
    document.getElementById("page-select").addEventListener("change",initOrderSelect);
    document.getElementById("logistics-id").addEventListener('keyup',verifyLogistics);
    document.getElementById("logistics-name").addEventListener('keyup',verifyLogName);
})
//分页订单用户
function pageOrder() {
    let url = "/order/list";
    pageSplit(url,"null");
}
//分页函数
function pageSplit(url,key) {
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
            if (url == "/order/list"){
                initState();//初始化状态
            }
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
                            if (url == "/order/list"){
                                initState();//初始化状态
                            }
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
        tableTbody += '<td><input type="checkbox" id="select-' + i +'" style="width: 20px" value="' + list[i].orderId + '" onclick="selectThis(this)"></td>';
        tableTbody += '<td><span style="width: 50px;">' + list[i].orderId + '</span></td>';
        tableTbody += '<td><span style="width: 50px;">' + list[i].userId + '</span></td>';
        tableTbody += '<td><span style="width: 50px;">' + list[i].logisticsId + '</span></td>';
        tableTbody += '<td><span style="width: 130px;">' + list[i].notes + '</span></td>';
        tableTbody += '<td><span style="width: 100px;">'+ list[i].createTime + '</span></td>';
        tableTbody += '<td><span style="width: 100px;">'+ list[i].payTime + '</span></td>';
        tableTbody += '<td><span style="width: 100px;">'+ list[i].closeTime + '</span></td>';
        tableTbody += '<td><span style="width: 100px;">'+ list[i].finishTime + '</span></td>';
        tableTbody += '<td><span style="width: 70px;">￥'+ list[i].paymentAmount + '</span></td>';
        tableTbody += '<td><span style="width: 50px;" class="state">'+ list[i].orderState + '</span></td>';
        tableTbody += '<td><div style="padding-top: 0">';
        if (list[i].orderState == '已支付') {
            tableTbody += '<a href="#" aria-label="Send" style="color: greenyellow;text-decoration: none" data-toggle="tooltip" data-placement="bottom" title="发货" data-modal="message-window" onclick="deliverThis(this)" id="deliver-' + list[i].orderId + '">'
                + '<span class="glyphicon glyphicon-send" aria-hidden="true"></span>&nbsp;|&nbsp;</a>';
        }else {
            tableTbody += '<a href="#" aria-label="Send" style="color: gray;text-decoration: none" data-toggle="tooltip" data-placement="bottom" title="不可用" data-modal="message-window" onclick="banThis(this)" id="ban-' + list[i].orderId + '" disabled="disabled">'
                + '<span class="glyphicon glyphicon-send" aria-hidden="true"></span>&nbsp;|&nbsp;</a>';
        }
        tableTbody += '<a href="#" aria-label="Edit" style="color: mediumpurple;text-decoration: none" data-toggle="tooltip" data-placement="bottom" title="编辑"'
            + ' onclick="editThis(this)" id="edit-' +list[i].orderId +  '">'
            + '<span class="glyphicon glyphicon-edit" aria-hidden="true"></span>&nbsp;|&nbsp;</a>'
            + '<a class="md-trigger" href="#" aria-label="Trash" style="color: red" data-toggle="tooltip" data-placement="bottom" title="回收"'
            + ' data-modal="message-window" onclick="recycleThis(this)" id="recycle-' + list[i].orderId + '">'
            + '<span class="glyphicon glyphicon-trash" aria-hidden="true"></span></a></div></td>';
        tableTbody += '</tr>'
    }
    $('#order-table tbody').html(tableTbody);
}
/*选择列表内容改变时执行start*/
function initOrderSelect() {
    initSelect(pageOrder);//重新请求分页
}
/*选择列表内容改变时执行end*/
/*初始化状态栏start*/
function initState() {
    let stateElements = document.getElementsByClassName("state");
    for(let i = 0; i < stateElements.length ; i ++){
        if (stateElements[i].innerText == "已支付"){
            stateElements[i].style.color = "orange";
        }else if (stateElements[i].innerText == "交易成功"){
            stateElements[i].style.color = "yellowgreen";
        }else if (stateElements[i].innerText == "待支付"){
            stateElements[i].style.color = "gray";
        }
        else {
            stateElements[i].style.color = "red";
        }
    }
}
/*初始化状态栏end*/

/*操作start*/
//回收订单
function recycleThis(obj) {
    //id形式为：recycle-
    let orderId = obj.id;
    let id = orderId.substring(8);
    let text = "确定回收id：" + id + "的订单吗？";
    operationTransfer(obj,id,text,recycle);
}
function recycle() {
    let id = document.getElementById("id").value;
    let url = "/order/transfer";
    let info = '{"orderId":"' + id + '","state":' + 5 + '}';
    let data = JSON.parse(info);
    startOperationTransfer(url,data,recycle,pageOrder);
}
/*编辑订单start*/
function editThis(obj) {
    //id为edit-id的形式，需要字符串截取
    showEdit();
    document.getElementById("add-btn").type = "hidden";
    let element = document.getElementById(obj.id);
    let orderId = obj.id;
    let value = "remark-info";
    createAttribute(element,value);//创建属性
    document.getElementById(obj.id).addEventListener('click',setOrderContent);//添加设置内容事件
    setOrderContent(orderId.substring(5));
}
//设置订单内容
function setOrderContent(orderId) {
    let url = "/order/query";
    let id = '{"orderId":"' + orderId +  '"}';
    let data = JSON.parse(id);
    sendEditData(url,data);//调用请求订单信息
}
function setResultContent(oid,uid,notes) {
    document.getElementById("order-id").value = oid;
    document.getElementById("user-id").value = uid;
    document.getElementById("order-remark").value = notes;
    let element = document.getElementById("edit-" + oid);
    if(element != null){
        element.removeEventListener('click',setOrderContent);//移除设置内容事件
    }
    document.getElementById("put-btn").type = "hidden";
    document.getElementById("edit-btn").type = "button";
    removeAttribute("编辑");//移除属性
}
//请求订单信息
function sendEditData(url,data) {
    $.ajax({
        type : "get",
        url : url,
        dataType : "json",
        data : data,
        success(result){
            setResultContent(result.orderId,result.userId,result.notes);
        }
    });
}
//进入编辑
function edit(obj) {
    initResult();//初始化结果信息
    if (!isInfoEmpty()){
        initMessage();
        let text = "确定要备注该订单吗？";
        inOperation(obj,text,editStart,reset);
    }else {
        let title = "警告";
        let text = "备注不能为空！请检查！"
        eject(obj);
        showMsgAll(title,text);
        document.getElementById("confirm").type = "hidden";
    }
}
//开始编辑
function editStart() {
    let url = "/order/update";
    let id = document.getElementById("order-id").value;
    let text = document.getElementById("order-remark").value;
    let info = '{' + '"orderId":' + id + ',"notes":"' + text  + '"}';
    let data = JSON.parse(info);
    putOperation(url,data,editStart,reset,pageOrder);
}
/*编辑end*/

/*发货start*/
function deliverThis(obj) {
//id为deliver-id的形式，需要字符串截取
    hideEdit();
    document.getElementById("add-btn").type = "hidden";
    let element = document.getElementById(obj.id);
    let orderId = obj.id;
    let value = "remark-info";
    createAttribute(element,value);//创建属性
    document.getElementById(obj.id).addEventListener('click',setLogisticsContent);//添加设置内容事件
    setLogisticsContent(orderId.substring(8));
}
//设置订单内容
function setLogisticsContent(orderId) {
    let url = "/order/logistics/info";
    let id = '{"orderId":"' + orderId +  '"}';
    let data = JSON.parse(id);
    sendLogisticsData(url,data);//调用请求订单信息
}
function setResultLogContent(oid,uid,list) {
    document.getElementById("order-id").value = oid;
    document.getElementById("user-id").value = uid;
    showLogisticsChoice(list);//开始渲染物流选择列表
    let element = document.getElementById("deliver-" + oid);
    if(element != null){
        element.removeEventListener('click',setLogisticsContent);//移除设置内容事件
    }
    document.getElementById("put-btn").type = "button";
    document.getElementById("edit-btn").type = "hidden";
    removeAttribute("发货");//移除属性
}
//请求订单信息
function sendLogisticsData(url,data) {
    $.ajax({
        type : "get",
        url : url,
        dataType : "json",
        data : data,
        success(result){
            setResultLogContent(result.orderId,result.userId,result.list);
        }
    });
}
//渲染物流选择列表
function showLogisticsChoice(list) {
    let selectElement = document.getElementById("logistics-choice");
    selectElement.length = 1;
    for(let i = 0 ; i < list.length ; i++){
        let option = document.createElement("option");
        option.setAttribute("value",list[i].logisticsName);
        option.appendChild(document.createTextNode(list[i].logisticsName));
        selectElement.appendChild(option);
    }

}
//进入发货
function send(obj) {
    initResult();//初始化结果信息
    if (!isDeliverEmpty() && verifyLogistics()){
        initMessage();
        let text = "确定开始发货吗？";
        inOperation(obj,text,sendStart,reset);
    }else {
        let title = "警告";
        let text = "";
        if (isDeliverEmpty()){
            text = "必要字段不能为空！请检查！"
        }
        if (verifyLogistics()){
            text = "物流号格式错误！";
        }
        eject(obj);
        showMsgAll(title,text);
        document.getElementById("confirm").type = "hidden";
    }
}
//开始发货
function sendStart() {
    let url = "/order/deliver";
    let id = document.getElementById("order-id").value;
    let uid = document.getElementById("user-id").value;
    let choice = document.getElementById("logistics-choice").value;
    let lid = document.getElementById("logistics-id").value;
    let info = '{' + '"orderId":"' + id + '","userId":"' + uid + '","logisticsName":"' + choice
        + '","logisticsId":"' + lid + '"}';
    let data = JSON.parse(info);
    putOperation(url,data,sendStart,reset,pageOrder);
}
/*发货end*/

//待写功能
function banThis(obj) {
    console.log("小伙计，这还没功能呢！点了没用~");
}
//搜索订单
function searchOrder() {
    let url = "/order/list";
    search(url,pageSplit,"search-key");
}
//初始化检索提示
function initToOriTips() {
    initToOri("error-msg");
}
//单选订单
function selectThis(obj) {
    if (obj.checked){//勾选
        document.getElementById("ids-to-do").value = obj.value;
    }else {//取消勾选
        document.getElementById("ids-to-do").value = "";
    }
}

//添加物流
function addLogistics() {
    hideOthers();
    reset();
    document.getElementById("add-btn").type = "button";
    document.getElementById("put-btn").type = "hidden";
    document.getElementById("edit-btn").type = "hidden";
}
function newAdd(obj) {
    initResult();//初始化结果信息
    let name = document.getElementById("logistics-name").value;
    if (name.length == 0){
        let text = "物流公司不能为空！"
        eject(obj);
        showMsg(text);
        document.getElementById("confirm").type = "hidden";
    }else {
        let text = "确定要添加物流公司吗？";
        initMessage();
        inOperation(obj,text,newAddStart,reset);
    }
}
function newAddStart() {
    let url = "/order/add/logistics";
    let name = document.getElementById("logistics-name").value;
    let info = '{' + '"logisticsName":"' + name + '"}';
    let data = JSON.parse(info);
    putOperation(url,data,newAddStart,reset,pageOrder);
}
//订单打印
function printThis(obj) {
    initPrintLink(obj);
}
function initPrintLink(obj) {
    let value = document.getElementById("ids-to-do").value;
    if (value == ""){
        let text = "未勾选订单！";
        eject(obj);
        showMsg(text);
        document.getElementById("confirm").type = "hidden";
    }else {
        let linkElements = document.getElementsByTagName("a");
        for (let i = 0 ; i < linkElements.length ; i ++){
            if (linkElements[i].title == "订单打印"){
                let orderId = value;
                linkElements[i].href = "/order/toPrint/" + value;
            }
        }
    }
}

/*操作end*/

/*样式控制start*/
//清空内容栏
function reset() {
    let element = document.getElementsByTagName("order-remark");
    let logElement = document.getElementById("logistics-id");
    let logNameElement = document.getElementById("logistics-name");
    element.value = "";
    logElement.value = "";
    logNameElement.value = "";
    verifyLogistics();
    verifyLogName();
}
//检查必要内容是否为空
function isInfoEmpty() {
    let text = document.getElementById("order-remark").value;
    if (text.length == 0){
        return true;
    }else {
        return false;
    }
}
//检查发货内容是否为空
function isDeliverEmpty() {
    let choice = document.getElementById("logistics-choice").value;
    let lid = document.getElementById("logistics-id").value;
    if (choice == 0 | lid.length == 0){
        return true;
    }
    return false;

}
//初始化结果显示
function initResult() {
    let element = document.getElementById("result-details");
    element.innerText = "你还没选择操作！";
    element.style.color = "black"
}
//隐藏修改页面,显示发货页面
function hideEdit() {
    let remarkElements = document.getElementsByClassName("remark");
    for (let i = 0 ; i < remarkElements.length ; i ++){
        remarkElements[i].hidden = true;
    }
    let logElements = document.getElementsByClassName("logistics");
    for (let i = 0 ; i < logElements.length ; i ++){
        logElements[i].hidden = false;
    }
    let addLogElements = document.getElementsByClassName("add-logistics");
    for (let i = 0 ; i < addLogElements.length ; i ++){
        addLogElements[i].hidden = true;
    }
    let orderElements = document.getElementsByClassName("order-info");
    for (let i = 0 ; i < orderElements.length ; i ++){
        orderElements[i].hidden = false;
    }
    document.getElementById("put-btn").value = "发货";
}
//显示修改页面，隐藏发货页面
function showEdit() {
    let remarkElements = document.getElementsByClassName("remark");
    for (let i = 0 ; i < remarkElements.length ; i ++){
        remarkElements[i].hidden = false;
    }
    let logElements = document.getElementsByClassName("logistics");
    for (let i = 0 ; i < logElements.length ; i ++){
        logElements[i].hidden = true;
    }
    let addLogElements = document.getElementsByClassName("add-logistics");
    for (let i = 0 ; i < addLogElements.length ; i ++){
        addLogElements[i].hidden = true;
    }
    let orderElements = document.getElementsByClassName("order-info");
    for (let i = 0 ; i < orderElements.length ; i ++){
        orderElements[i].hidden = false;
    }
}
//显示添加物流页面，其他隐藏
function hideOthers() {
    let remarkElements = document.getElementsByClassName("remark");
    for (let i = 0 ; i < remarkElements.length ; i ++){
        remarkElements[i].hidden = true;
    }
    let logElements = document.getElementsByClassName("logistics");
    for (let i = 0 ; i < logElements.length ; i ++){
        logElements[i].hidden = true;
    }
    let addLogElements = document.getElementsByClassName("add-logistics");
    for (let i = 0 ; i < addLogElements.length ; i ++){
        addLogElements[i].hidden = false;
    }
    let orderElements = document.getElementsByClassName("order-info");
    for (let i = 0 ; i < orderElements.length ; i ++){
        orderElements[i].hidden = true;
    }
}
//检查物流号是否合法
function verifyLogistics() {
    let obj = document.getElementById("logistics-id");
    let msg = document.getElementById("logistics-id-msg");
    let regex = /^[1-9](\d+)?$/;
    if (obj.value.length != 0){
        if(regex.test(obj.value)){//验证通过
            if(obj.value > 99999999999999999999){
                msg.innerText = "物流号过大！";
                msg.style.color = "red";
                return false;
            }else {
                msg.innerHTML = "<span style='font-size: 12px' class=\"glyphicon glyphicon-ok\" aria-hidden=\"true\"></span>";
                msg.style.color = "lightgreen";
                return true;
            }
        }else {//验证不通过
            msg.innerText = "请确认输入的是数字且符合要求！"
            msg.style.color = "red";
            return false;
        }
    }else {
        msg.innerText = "1~20数字";
        msg.style.color = "#999999";
        return false;
    }

}
//验证名称是否合法
function nameTips(elementId,limitNum,msgRight,msgWrong,msgInit) {//elementId：需要验证的内容的id,limitNum：限制数量,msgRight：正确提示,msgWrong：错误提示,msgInit：初始化提示
    let text = document.getElementById(elementId).value;
    let msg = document.getElementById(elementId + "-msg");
    let len = text.length;
    if(len <= limitNum && len > 0){
        msg.innerText = (limitNum-len) + "/" + limitNum;
        msg.style.color = "#999999";
        return true;
    }else if(len > limitNum) {
        msg.innerText = msgWrong;
        msg.style.color = "red";
        return false;
    }else {
        msg.innerText = msgInit;
        return false;
    }
}
//验证物流公司名称是否合法
function verifyLogName() {
    nameTips("logistics-name",20,"","名称过长！","1~20个字符");
}
/*样式控制end*/