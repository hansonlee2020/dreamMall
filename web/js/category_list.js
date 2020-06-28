/*本文件是处理分类信息页面业务的js文件*/

$(function() {
    //绑定事件
    document.getElementById("page-select").addEventListener("change",initCategorySelect);
    document.getElementById("category-id").addEventListener('blur',idTips);
    document.getElementById("category1").addEventListener('keyup',category1Verify);
    document.getElementById("category2").addEventListener('keyup',category2Verify);
    document.getElementById("category3").addEventListener('keyup',category3Verify);
    document.getElementById("select-all").addEventListener("click",selectAllCategory);
    pageCategory();//开始分页
    loadRuleTips();//加载分类规则
});
//分类数据分页
function pageCategory() {
    let url="/category/list";
    initCheckboxCategory();
    pageSplit(url);
}
//此函数用于处理后台返回的数据，来实现页面拼接
function appendHtml(list) {
    let tableTbody = '';
    for (let i = 0; i < list.length; i++) {
        tableTbody += '<tr style="word-wrap: break-word">';
        tableTbody += '<td><input type="checkbox" id="select-' + i +'" style="width: 20px" value="' + list[i].categoryId + '" onclick="selectThis(this)"></td>';
        tableTbody += '<td><span style="width: 75px;">' + list[i].categoryId + '</span></td>';
        tableTbody += '<td><span style="width: 150px;">' + list[i].categoryFirstName + '</span></td>';
        tableTbody += '<td><span style="width: 150px;">' + list[i].categorySecondName + '</span></td>';
        tableTbody += '<td><span style="width: 150px;">'+ list[i].categoryThirdName + '</span></td>';
        tableTbody += '<td><div style="padding-top: 0px">';
        tableTbody += '<a href="#" aria-label="Edit" style="color: mediumpurple;text-decoration: none" data-toggle="tooltip" data-placement="bottom" title="编辑" onclick="editThis(this)" id="' + list[i].categoryId + '">'
            + '<span class="glyphicon glyphicon-edit" aria-hidden="true"></span>&nbsp;|&nbsp;</a>'
            + '<a class="md-trigger" href="#" aria-label="Trash" style="color: red" data-toggle="tooltip" data-placement="bottom" title="删除" data-modal="message-window" onclick="deleteThis(this)" id="' + list[i].categoryId + '">'
            + '<span class="glyphicon glyphicon-trash" aria-hidden="true"></span></a></div></td>';
        tableTbody += '</tr>';
    }
    $('#category-table tbody').html(tableTbody);
}

/*选择列表内容改变时执行start*/
function initCategorySelect() {
    initSelect(pageCategory);
    initCheckboxCategory();
}
/*选择列表内容改变时执行end*/

/*加载分类规则start*/
function loadRuleTips() {
    let text = "本系统约定，一级分类作为顶级分类，可以拥有多个子分类，子分类包括其二级分类和三级分类等，考虑分类的可扩展性" +
        "和节约字段资源因素，故一级分类拥有的id字段资源为200个，且最低级子分类同时只能属于一个顶级分类。在新增和修改分类时，用户应当遵守本约定，以方便分类信息的管理。" +
        "";
    let ruleElement = document.getElementById("rule-tips");
    ruleElement.innerText = text;
}
/*加载分类规则end*/

/*全选*/
function selectAllCategory() {
    selectAll("select-all","ids-to-do");
}
/*单选*/
function selectThis(obj) {
    selectByOne(obj,"ids-to-do");
}

/*删除分类操作start*/
function deleteThis(obj) {
    let text = "确定要删除id为  " + obj.id + "  的分类吗？";
    operation(obj,text,deleteStart);
}
function deleteStart() {
    let url = "/category/delete";
    startOperation(url,deleteStart);
}
//确认操作
function operation(obj,text,addMethod) {
    let element = document.getElementById("id");
    element.value = "";
    eject(obj);
    showMsg(text);
    document.getElementById("confirm").addEventListener('click',eval(addMethod));
    document.getElementById("confirm").type = "button";
    element.value = obj.id;
}
//进行操作
function startOperation(url,removeMethod) {
    let value = document.getElementById("id").value;
    let id = '{' + '\"deleteId\":' + '"' + value + '"' + '}';
    let data = JSON.parse(id);
    $('#confirm').on('click',sendData(url,data,pageCategory,true));
    document.getElementById("confirm").type = "hidden";//隐藏确认按钮
    document.getElementById("confirm").removeEventListener('click',eval(removeMethod));//执行完函数移除事件
}
/*删除分类操作end*/

/*新增分类start*/
function recovery() {//修复功能
    document.getElementById("put-btn").type = "button";
    document.getElementById("edit-btn").type = "hidden";
    reset();
}
function increaseThis(obj) {
    initResult();//初始化结果信息
    if (!isInfoEmpty()){
        let text = "确定要新增分类吗？";
        InOperation(obj,text,increase,resetPart);
    }else {
        let text = "所有字段均不能为空！请检查！"
        eject(obj);
        showMsg(text);
        document.getElementById("confirm").type = "hidden";
    }
}
function increase() {
    let url = "/category/increase";
    putOperation(url,increase,resetPart);
}
//确认操作
function InOperation(obj,text,addMethod,removeMethod) {
    eject(obj);
    showMsg(text);
    document.getElementById("confirm").addEventListener('click',eval(addMethod));
    document.getElementById("confirm").type = "button";
    document.getElementById("window-close").removeEventListener('click',eval(removeMethod));//移除上次的重置事件
}
//提交分类
function putOperation(url,removeMethod,addMethod) {
    let categoryId = document.getElementById("category-id").value;
    let category1 = document.getElementById("category1").value;
    let category2 = document.getElementById("category2").value;
    let category3 = document.getElementById("category3").value;
    let category = '{' + '"categoryId":' + categoryId + ',"categoryFirstName":"'
        + category1 + '","categorySecondName":"' + category2 + '","categoryThirdName":"' + category3 + '"}';
    let data = JSON.parse(category);
    $('#confirm').on('click',sendDataHandle(url,data,pageCategory,false,addMethod));
    document.getElementById("confirm").type = "hidden";//隐藏确认按钮
    document.getElementById("confirm").removeEventListener('click',eval(removeMethod));//执行完函数移除事件
}
//有对返回数据进行处理的发送请求操作
function sendDataHandle(url,data,methodName,isEnable,addMethod) {
    //url：请求路径，data：要发送的数据,methodName：要调用函数的函数名称，isEnable：是否启用函数
    $.ajax({ //发送
        type : "get",
        url : url,
        contentType :  'application/json;charset=UTF-8',
        dataType : "json",
        data : data,
        success : function(result) {//后台处理结果信息回显，result：处理结果
            showMsgAll(result.msgTitle,result.msgContent);
            if (isEnable){
                eval(methodName());//使用eval把字符串转换为函数名
            }
            //将处理结果详情显示
            let element = document.getElementById("result-details");
            element.innerText = result.msgContent;
            if (result.msgType == "success"){
                element.style.color = "yellowgreen";
                eval(methodName());//重新分页
                document.getElementById("window-close").addEventListener('click',eval(addMethod));
            }else {
                element.style.color = "red";
            }
        }
    });
}
/*新增分类end*/

/*编辑分类start*/
//准备编辑工作
function editThis(obj) {
    let element = document.getElementById(obj.id);
    createAttribute(element);
    element.addEventListener('click',setContent);//为编辑添加设置内容事件
    setContent(obj.id);
}
//为元素增添属性
function createAttribute(element) {//element：要设置属性的元素
    element.setAttribute("data-toggle","collapse");
    element.setAttribute("data-target","#category-info");
    element.setAttribute("aria-expanded","false");
    element.setAttribute("aria-controls","category-info");
}
//移除元素属性
function removeAttribute(value) {//value：可以定位元素的title属性的值
    let linkElements = document.getElementsByTagName("a");
    for (let i = 0 ; i < linkElements.length ; i ++){
        if (linkElements[i].title == value){
            linkElements[i].removeAttribute("data-toggle");
            linkElements[i].removeAttribute("data-target");
            linkElements[i].removeAttribute("aria-expanded");
            linkElements[i].removeAttribute("aria-controls");
        }
    }
}
//设置内容
function setContent(cid) {
    let id = '{' + '\"categoryId\":"' + cid + '"}';
    let url = "/category/query";
    let data = JSON.parse(id);//一定要转json对象后再发送请求！
    let element = document.getElementById(cid);
    sendEditData(url,data);
}
function setResultContent(cid,cate1,cate2,cate3) {//cid:分类id,cate1：一级分类名,cate2：二级分类名,cate3：三级分类名
    document.getElementById("category-id").value = cid;
    document.getElementById("category1").value = cate1;
    document.getElementById("category2").value = cate2;
    document.getElementById("category3").value = cate3;
    let element = document.getElementById(cid);
    if (element != null){
        element.removeEventListener('click',setContent);//设置内容完毕后移除设置内容事件
    }
    document.getElementById("put-btn").type = "hidden";
    document.getElementById("edit-btn").type = "button";
}
//请求数据内容
function sendEditData(url,data) {//url：请求路径，data：要发送的数据
    $.ajax({ //发送
        type : "get",
        url : url,
        // contentType :  'application/json;charset=UTF-8',
        dataType : "json",
        data : data,
        success : function(result) {//后台处理结果信息回显，result：处理结果
            setResultContent(result.categoryId,result.categoryFirstName,result.categorySecondName,result.categoryThirdName);
            idTips();
            category1Verify();
            category2Verify();
            category3Verify();
            initResult();
        }
    });
}
//进入编辑
function edit(obj) {
    initResult();//初始化结果信息
    if (!isInfoEmpty()){
        let text = "确定要修改分类吗？";
        InOperation(obj,text,editStart,reset);
    }else {
        let text = "所有字段均不能为空！请检查！"
        eject(obj);
        showMsg(text);
        document.getElementById("confirm").type = "hidden";
    }
}
//开始编辑
function editStart() {
    let url = "/category/update";
    putOperation(url,editStart,reset);
}
/*编辑分类end*/
//搜索分类
//分页函数
function pageSearchSplit(url,key) {
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
                        }
                    });
                }
            };
            $('#product-split').bootstrapPaginator(options);//设置分页
        }
    });
}
//搜索
function searchCategory() {
    let url = "/category/search";
    let key = document.getElementById("search-key").value;
    if (key.length > 0){
        search(url,pageSearchSplit,"search-key");
    }else {
        pageCategory();
    }
}
//初始化检索提示
function initToOriTips() {
    initToOri("error-msg");
}

/*自定义格式控制start*/
//清空输入，初始化样式
function reset() {
    let inputElements = document.getElementsByTagName("input");
    for (let i = 0 ; i < inputElements.length ; i ++){
        if ((inputElements[i].attributes["type"].value) == ("text")){
            inputElements[i].value = "";
        }
    }
    idTips();
    category1Verify();
    category2Verify();
    category3Verify();
    initResult();
}
//关键内容部分重置
function resetPart() {
    let id = document.getElementById("category-id");
    let category3 = document.getElementById("category3");
    id.value = "";
    category3.value = "";
    idTips();
    category3Verify();
    initResult();
}
function initResult() {
    let element = document.getElementById("result-details");
    element.innerText = "你还没选择操作！";
    element.style.color = "black"
}
//验证分类id是否合法
function idTips() {
    let obj = document.getElementById("category-id");
    let msg = document.getElementById("id-msg");
    let regex = /^[1-9](\d+)?$/;
    if (obj.value.length != 0){
        if(regex.test(obj.value)){//验证通过
            if(obj.value > 9999){
                msg.innerText = "id不宜过大！";
                msg.style.color = "red";
                return false;
            }else {
                msg.innerHTML = "<span class=\"glyphicon glyphicon-ok\" aria-hidden=\"true\"></span>";
                msg.style.color = "lightgreen";
                return true;
            }
        }else {//验证不通过
            msg.innerText = "请确认输入的是数字且符合要求！"
            msg.style.color = "red";
            return false;
        }
    }else {
        msg.innerText = "可以输入1~9999之间的任何数字";
        msg.style.color = "#999999";
        return false;
    }
}
//验证一级是否合法
function category1Verify() {
    nameTips("category1",20,"","输入内容过大！","1~20个中文、英文或数字字符")
}
//验证二级是否合法
function category2Verify() {
    nameTips("category2",20,"","输入内容过大！","1~20个中文、英文或数字字符")
}
//验证三级是否合法
function category3Verify() {
    nameTips("category3",20,"","输入内容过大！","1~20个中文、英文或数字字符")
}
//验证名称是否合法
function nameTips(elementId,limitNum,msgRight,msgWrong,msgInit) {//elementId：需要验证的内容的id,limitNum：限制数量,msgRight：正确提示,msgWrong：错误提示,msgInit：初始化提示
    let text = document.getElementById(elementId).value;
    let msg = document.getElementById(elementId + "-msg");
    let len = text.length;
    if(len <= limitNum && len > 0){
        msg.innerText = (limitNum-len) + "/" + limitNum;
        msg.style.color = "#999999";

    }else if(len > limitNum) {
        msg.innerText = msgWrong;
        msg.style.color = "red";
        return false;
    }else {
        msg.innerText = msgInit;
        return false;
    }
}
//整体验证
function isInfoEmpty() {
    let id = document.getElementById("category-id").value;
    let cate1 = document.getElementById("category1").value;
    let cate2 = document.getElementById("category2").value;
    let cate3 = document.getElementById("category3").value;
    if (id.length == 0 | cate1.length == 0 | cate2.length == 0 | cate3.length == 0){
        return true;
    }else {
        return false;
    }
}
//清空全选框
function initCheckboxCategory() {
    initCheckbox("select-all");
}
/*自定义格式控制end*/