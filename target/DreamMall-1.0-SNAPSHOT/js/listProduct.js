/*本js文件用于处理商品列表业务*/

$(function() {
    //为select列表添加监听
    document.getElementById("page-select").addEventListener("change",initProductSelect);
    //为checkbox添加监听
    document.getElementById("select-all").addEventListener("click",selectAllProducts);
    pageProduct();//开始分页
    // pageStart();
});
//商品数据分页
function pageProduct() {
    let url="/product/goods/list";
    pageSplit(url);
}
//此函数用于处理后台返回的数据，来实现页面拼接
function appendHtml(list) {
    let tableTbody = '';
    for (let i = 0; i < list.length; i++) {
        tableTbody += '<tr style="word-wrap: break-word">';
        tableTbody += '<td><input type="checkbox" id="select-' + i +'" style="width: 20px" value="' + list[i].productId + '" onclick="selectThis(this)"></td>';
        tableTbody += '<td><span style="width: 75px;">' + list[i].productId + '</span></td>';
        tableTbody += '<td><span style="width: 50px;">' + list[i].productName + '</span></td>';
        tableTbody += '<td><span style="width: 205px;overflow: hidden; height: 60px">' + list[i].briefInfo + '</span></td>';
        tableTbody += '<td><span style="padding-top: 20px">￥'+ list[i].price + '</span></td>';
        tableTbody += '<td><span style="padding-top: 20px">'+ list[i].stock + '件</span></td>';
        tableTbody += '<td><span style="padding-top: 20px">'+ list[i].limitNum + '件</span></td>';
        tableTbody += '<td><span style="padding-top: 20px">'+ list[i].thirdCategory + '</span></td>';
        tableTbody += '<td><span style="padding-top: 20px">'+ list[i].imageSrc + '</span></td>';
        tableTbody += '<td><span style="padding-top: 20px" class="state">'+ list[i].releaseState + '</span></td>';
        tableTbody += '<td><div>';
        if(list[i].releaseState == '审核中'){
            tableTbody += '<a href="#" aria-label="Send" style="color: greenyellow;text-decoration: none" data-toggle="tooltip" data-placement="bottom" title="发布" data-modal="message-window" onclick="releaseThis(this)" id="' + list[i].productId + '">'
                + '<span class="glyphicon glyphicon-send" aria-hidden="true"></span>&nbsp;|&nbsp;</a>';
        }else if (list[i].releaseState == '已发布'){
            tableTbody += '<a href="#" aria-label="Remove-sign" style="color: orange;text-decoration: none" data-toggle="tooltip" data-placement="bottom" title="下架" data-modal="message-window" onclick="offShelfThis(this)" id="' + list[i].productId + '">'
                + '<span class="glyphicon glyphicon-remove-sign" aria-hidden="true"></span>&nbsp;|&nbsp;</a>';
        }else if (list[i].releaseState == '已下架'){
            tableTbody += '<a href="#" aria-label="Ok" style="color: #7199ff;text-decoration: none" data-toggle="tooltip" data-placement="bottom" title="重新审核" data-modal="message-window" onclick="examineThis(this)" id="' + list[i].productId + '">'
                + '<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>&nbsp;|&nbsp;</a>';
        }else {
            tableTbody += '<a href="#" aria-label="ban" style="color: red;text-decoration: none" data-toggle="tooltip" data-placement="bottom" title="不可用" data-modal="message-window" onclick="banThis(this)" id="' + list[i].productId + '">'
                + '<span class="glyphicon glyphicon-ban-circle" aria-hidden="true"></span>&nbsp;|&nbsp;</a>';
        }
        tableTbody += '<a href="#" aria-label="Edit" style="color: mediumpurple;text-decoration: none" data-toggle="tooltip" data-placement="bottom" title="编辑" id="' + list[i].productId + '">'
            + '<span class="glyphicon glyphicon-edit" aria-hidden="true"></span>&nbsp;|&nbsp;</a>'
            + '<a class="md-trigger" href="#" aria-label="Trash" style="color: red" data-toggle="tooltip" data-placement="bottom" title="删除" data-modal="message-window" onclick="deleteThis(this)" id="' + list[i].productId + '">'
            + '<span class="glyphicon glyphicon-trash" aria-hidden="true"></span></a></div></td>';
        tableTbody += '</tr>';
    }
    $('#product-table tbody').html(tableTbody);
}

/*初始化发布状态栏start*/
function initRelease() {
    let stateElements = document.getElementsByClassName("state");
    for(let i = 0; i < stateElements.length ; i ++){
        if (stateElements[i].innerText == "审核中"){
            stateElements[i].style.color = "black";
        }else if (stateElements[i].innerText == "已发布"){
            stateElements[i].style.color = "yellowgreen";
        }else {
            stateElements[i].style.color = "red";
        }
    }
}
/*初始化发布状态栏end*/

/*选择列表内容改变时执行start*/
function initProductSelect() {
    initSelect(pageProduct);
    // initCheckboxProduct();
}
/*选择列表内容改变时执行end*/
/*全选*/
function selectAllProducts() {
    selectAll("select-all","ids-to-do");
}
/*单选*/
function selectThis(obj) {
    selectByOne(obj,"ids-to-do");
}

/*批量删除start*/
function confirmOperation(obj) {
    eject(obj);
    let data = document.getElementById("ids-to-do").value;
    let noticeElement = document.getElementById("msg");
    let confirmBtn = document.getElementById("confirm");
    noticeElement.innerText = "";
    if (data.length > 0){
        noticeElement.innerText = "确定要删除这些商品吗？"
        confirmBtn.type = "button";
        confirmBtn.addEventListener("click",batchDelete);
    }else {
        noticeElement.innerText = "你还没勾选任何数据！";
        confirmBtn.type = "hidden";
    }

}
//发送删除请求
function batchDelete() {
    let deleteIds = document.getElementById("ids-to-do").value;
    $.ajax({ //发送
        type : "get",
        url : "/product/goods/delete",
        contentType :  'application/json;charset=UTF-8',
        dataType : "json",
        data : {deleteIds : deleteIds},
        success : function(data) {//后台处理结果信息回显
            document.getElementById("confirm").type = "hidden";
            showMsgAll(data.msgTitle,data.msgContent);
            pageProduct();
        }
    });
    document.getElementById("confirm").removeEventListener('click',batchDelete);
}

/*批量删除end*/

/*单个数据删除start*/
function deleteThis(obj) {
    let text = "确定要删除id为："+ obj.id + "的数据吗？";
    let element = document.getElementById("id");
    element.value = "";
    eject(obj);
    showMsg(text);
    document.getElementById("confirm").addEventListener('click',deleteStart);
    document.getElementById("confirm").type = "button";
    element.value = obj.id;
}
function deleteStart() {
    let url = "/product/goods/delete1";
    let value = document.getElementById("id").value;
    let id = '{' + '\"deleteId\":' + '"' + value + '"' + '}';
    let data = JSON.parse(id);
    $('#confirm').on('click',sendData(url,data,pageProduct,true));
    document.getElementById("confirm").type = "hidden";//隐藏确认按钮
    document.getElementById("confirm").removeEventListener('click',deleteStart);//执行完函数移除事件
}
/*单个数据删除end*/

/*编辑商品start*/
function initEditLink() {
    let linkElements = document.getElementsByTagName("a");
    for (let i = 0 ; i < linkElements.length ; i ++){
        if (linkElements[i].title == "编辑"){
            let editId = linkElements[i].id;
            linkElements[i].href = "/product/goods/toEdit/" + editId;
        }
    }
}
/*编辑商品end*/
/*商品搜索start*/
//分页函数
function pageGoodsSplit(url,key) {
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
            if (url == "/product/goods/search"){
                initRelease();//初始化发布状态
                initEditLink();//初始化链接
            }
            initCheckbox("select-all");
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
                            initCheckbox("select-all");
                            if (url == "/product/goods/search"){
                                initRelease();//初始化发布状态
                                initEditLink();//初始化链接
                            }
                        }
                    });
                }
            };
            $('#product-split').bootstrapPaginator(options);//设置分页
        }
    });
}
//搜索商品
function searchGoods() {
    let url = "/product/goods/search";
    search(url,pageGoodsSplit,"search-key")
}
//初始化检索提示
function initSearchTips() {
    initToOri("error-msg");
}
/*商品搜索end*/

/*单个发布、重新审核、下架或禁用商品操作start*/
//发布
function releaseThis(obj) {
    let text = "确定要发布id为  " + obj.id + "  的商品吗？";
    operation(obj,text,release);
}
function release() {
    let url = "/product/goods/release";
    startOperation(url,1,release);
}
//下架
function offShelfThis(obj) {
    let text = "确定要下架id为  " + obj.id + "  的商品吗？";
    operation(obj,text,offShelf);
}
function offShelf() {
    let url = "/product/goods/off";
    startOperation(url,2,offShelf);
}
//重新审核
function examineThis(obj) {
    let text = "确定重新审核上架id为  " + obj.id + "  的商品吗？";
    operation(obj,text,examine);
}
function examine() {
    let url = "/product/goods/examine";
    startOperation(url,0,examine);
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
function startOperation(url,state,removeMethod) {
    let value = document.getElementById("id").value;
    let id = '{' + '\"productId\":' + '"' + value + '"' + ',"state":' + state + '}';
    let data = JSON.parse(id);
    $('#confirm').on('click',sendData(url,data,pageProduct,true));
    document.getElementById("confirm").type = "hidden";//隐藏确认按钮
    document.getElementById("confirm").removeEventListener('click',eval(removeMethod));//执行完函数移除事件
}
/*单个发布、重新审核、下架或禁用商品操作end*/
/*样式控制start*/
function initCheckboxProduct() {
    initCheckbox("select-all");
}
/*样式控制end*/





