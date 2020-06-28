/*用户列表业务处理js文件*/

$(function () {
    pageMember();
    document.getElementById("page-select").addEventListener("change",initMemberSelect);
    document.getElementById("select-all").addEventListener("click",selectAllMembers);
});
//分页查询用户
function pageMember() {
    let url = "/member/list";
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
            if (url == "/member/list"){
                initState();//初始化状态
            }
            initCheckboxMember();

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
                            initCheckboxMember();
                            if (url == "/member/list"){
                                initState();//初始化状态
                            }
                        }
                    });
                }
            };
            $('#member-split').bootstrapPaginator(options);//设置分页
        }
    });
}
//数据展示页面渲染
function appendHtml(list) {
    let tableTbody = '';
    for (let i = 0; i < list.length; i++) {
        tableTbody += '<tr style="word-wrap: break-word">';
        tableTbody += '<td><input type="checkbox" id="select-' + i +'" style="width: 20px" value="' + list[i].memberId + '" onclick="selectThis(this)"></td>';
        tableTbody += '<td><span style="width: 300px;">' + list[i].memberId + '</span></td>';
        tableTbody += '<td><span style="width: 300px;">' + list[i].memberName + '</span></td>';
        tableTbody += '<td><span style="width: 50px;">' + list[i].sex + '</span></td>';
        tableTbody += '<td><span style="width: 300px;">'+ list[i].createTime + '</span></td>';
        tableTbody += '<td><span style="width: 40px;" class="state">'+ list[i].memberState + '</span></td>';
        tableTbody += '<td><div style="padding-top: 0">';
        if(list[i].memberState == '已启用'){
            tableTbody += '<a href="#" aria-label="Transfer" style="color: orange;text-decoration: none" data-toggle="tooltip" data-placement="bottom" title="禁用" data-modal="message-window" onclick="offThis(this)" id="off-' + list[i].memberId + '">'
                + '<span class="glyphicon glyphicon-transfer" aria-hidden="true"></span>&nbsp;|&nbsp;</a>';
        }else {
            tableTbody += '<a href="#" aria-label="Transfer" style="color: greenyellow;text-decoration: none" data-toggle="tooltip" data-placement="bottom" title="启用" data-modal="message-window" onclick="onThis(this)" id="on-' + list[i].memberId + '">'
                + '<span class="glyphicon glyphicon-transfer" aria-hidden="true"></span>&nbsp;|&nbsp;</a>';
        }
        tableTbody += '<a class="md-trigger" href="#" aria-label="Trash" style="color: red" data-toggle="tooltip" data-placement="bottom" title="回收"'
            + ' data-modal="message-window" onclick="recycleThis(this)" id="recycle-' + list[i].memberId + '">'
            + '<span class="glyphicon glyphicon-trash" aria-hidden="true"></span></a></div></td>';
        tableTbody += '</tr>';
    }
    $('#member-table tbody').html(tableTbody);
}
/*选择列表内容改变时执行start*/
function initMemberSelect() {
    initSelect(pageMember);//重新请求分页
    initCheckboxMember();
}
/*选择列表内容改变时执行end*/

/*初始化状态栏start*/
function initState() {
    let stateElements = document.getElementsByClassName("state");
    for(let i = 0; i < stateElements.length ; i ++){
        if (stateElements[i].innerText == "已回收"){
            stateElements[i].style.color = "orange";
        }else if (stateElements[i].innerText == "已启用"){
            stateElements[i].style.color = "yellowgreen";
        }else {
            stateElements[i].style.color = "red";
        }
    }
}
/*初始化状态栏end*/
/*全选*/
function selectAllMembers() {
    selectAll("select-all","ids-to-do");
}
/*单选*/
function selectThis(obj) {
    selectByOne(obj,"ids-to-do");
}
/*操作start*/
//启用用户
function onThis(obj) {
    //id形式为on-id
    let memberId = obj.id;
    let id = memberId.substring(3);
    let text = "确定要启用id：" + id + "的用户吗？";
    operationTransfer(obj,id,text,onMember);
}
function onMember() {
    let id = document.getElementById("id").value;
    let url = "/member/transfer";
    let info = '{"memberId":"' + id + '","state":' + 1 + '}';
    let data = JSON.parse(info);
    startOperationTransfer(url,data,onMember,pageMember);
}
//禁用用户
function offThis(obj) {
    //id形式为：off-id
    let memberId = obj.id;
    let id = memberId.substring(4);
    let text = "确定要禁用id：" + id + "的用户吗？";
    operationTransfer(obj,id,text,offMember);
}
function offMember() {
    let id = document.getElementById("id").value;
    let url = "/member/transfer";
    let info = '{"memberId":"' + id + '","state":' + 0 + '}';
    let data = JSON.parse(info);
    startOperationTransfer(url,data,offMember,pageMember);
}
//回收用户
function recycleThis(obj) {
    //id形式为：recycle-
    let memberId = obj.id;
    let id = memberId.substring(8);
    let text = "确定回收id：" + id + "的用户吗？";
    operationTransfer(obj,id,text,recycle);
}
function recycle() {
    let id = document.getElementById("id").value;
    let url = "/member/transfer";
    let info = '{"memberId":"' + id + '","state":' + 2 + '}';
    let data = JSON.parse(info);
    startOperationTransfer(url,data,recycle,pageMember);
}
//搜索用户
function searchMember() {
    let url = "/member/list";
    search(url,pageSplit,"search-key");
}
//初始化检索提示
function initToOriTips() {
    initToOri("error-msg");
}
//批量启用用户
function batchOnThis(obj) {
    let rightText = "确定要启用这些用户吗？";
    let wrongText = "你还没勾选任何数据！";
    batch(obj,"ids-to-do",rightText,wrongText,batchOnMember);
}
function batchOnMember() {
    let url = "/member/ons";
    let ids = document.getElementById("ids-to-do").value;
    batchConfirm(url,ids,batchOnMember,pageMember);
}
//批量禁用用户
function batchOffThis(obj) {
    let rightText = "确定要禁用这些用户吗？";
    let wrongText = "你还没勾选任何数据！";
    batch(obj,"ids-to-do",rightText,wrongText,batchOffMember);
}
function batchOffMember() {
    let url = "/member/offs";
    let ids = document.getElementById("ids-to-do").value;
    batchConfirm(url,ids,batchOffMember,pageMember);
}
//批量回收用户
function batchRecycleThis(obj) {
    let rightText = "确定要回收这些用户吗？";
    let wrongText = "你还没勾选任何数据！";
    batch(obj,"ids-to-do",rightText,wrongText,batchRecycleMember);
}
function batchRecycleMember() {
    let url = "/member/recycles";
    let ids = document.getElementById("ids-to-do").value;
    batchConfirm(url,ids,batchRecycleMember,pageMember);
}
/*操作end*/
/*样式控制start*/
function initCheckboxMember() {
    initCheckbox("select-all");
}
/*样式控制end*/

