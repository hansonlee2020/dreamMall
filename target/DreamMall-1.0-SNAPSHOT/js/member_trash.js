/*用户回收站业务处理js文件*/

$(function () {
    pageMemberTrash();
    document.getElementById("page-select-trash").addEventListener("change",initMemberSelectTrash);
    document.getElementById("select-all-trash").addEventListener("click",selectAllTrash);
});
//分页查询用户
function pageMemberTrash() {
    let url = "/member/trash";
    pageSplitTrash(url,"null");
}
//分页函数
function pageSplitTrash(url,key) {
    let pageSize = document.getElementById("page-select-trash").value;
    $.ajax({ //去后台查询第一页数据
        type : "get",
        url : url,
        contentType :  'application/json;charset=UTF-8',
        dataType : "json",
        data : {pageSize : pageSize,currentPage : 1,searchKeyTrash : key}, //参数：当前页为1
        success : function(result) {

            appendHtmlTrash(result.list)//处理第一页的数据
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
            initCheckBoxTrash();
            if (url == "/member/trash"){
                initStateTrash();//初始化状态
            }

            let options = {//根据后台返回的分页相关信息，设置插件参数
                size:"small",//分页模型选择小
                bootstrapMajorVersion : 3, //如果是bootstrap3版本需要加此标识，并且设置包含分页内容的DOM元素为UL,如果是bootstrap2版本，则DOM包含元素是DIV
                currentPage : result.currentPage, //当前页数
                totalPages : result.totalPage, //总页数
                numberOfPages : result.size,//每页记录数
                onPageClicked : function(event, originalEvent, type, page) {//分页按钮点击事件
                    loading();
                    $.ajax({//根据page去后台加载数据
                        url : url,
                        type : "get",
                        dataType : "json",
                        data : {"currentPage" : page,"pageSize" : pageSize,"searchKeyTrash" : key},
                        success : function(result) {
                            appendHtmlTrash(result.list);//处理数据
                            let start = (result.currentPage - 1) * result.pageSize + 1;
                            let end = start + result.size - 1;
                            tipsChange(start,end,result.total);
                            loaded();//恢复提示样式
                            initCheckBoxTrash();
                            if (url == "/member/trash"){
                                initStateTrash();//初始化状态
                            }
                        }
                    });
                }
            };
            $('#member-split-trash').bootstrapPaginator(options);//设置分页
        }
    });
}
//权限数据展示页面渲染
function appendHtmlTrash(list) {
    let tableTbody = '';
    for (let i = 0; i < list.length; i++) {
        tableTbody += '<tr style="word-wrap: break-word">';
        tableTbody += '<td><input type="checkbox" id="select-' + i +'" style="width: 20px" value="' + list[i].memberId + '" onclick="selectTrashThis(this)"></td>';
        tableTbody += '<td><span style="width: 300px;">' + list[i].memberId + '</span></td>';
        tableTbody += '<td><span style="width: 300px;">' + list[i].memberName + '</span></td>';
        tableTbody += '<td><span style="width: 50px;">' + list[i].sex + '</span></td>';
        tableTbody += '<td><span style="width: 300px;">'+ list[i].createTime + '</span></td>';
        tableTbody += '<td><span style="width: 40px;" class="state-trash">'+ list[i].memberState + '</span></td>';
        tableTbody += '<td><div style="padding-top: 0">';
        tableTbody += '<a href="#" aria-label="Save" style="color: greenyellow;text-decoration: none" data-toggle="tooltip" data-placement="bottom" title="恢复"'
                + ' data-modal="message-window" onclick="saveThis(this)" id="save-' + list[i].memberId + '">'
                + '<span class="glyphicon glyphicon-save" aria-hidden="true"></span>&nbsp;|&nbsp;</a>'
                + '<a class="md-trigger" href="#" aria-label="Trash" style="color: red" data-toggle="tooltip" data-placement="bottom" title="彻底删除"'
                + ' data-modal="message-window" onclick="deleteThis(this)" id="delete-' + list[i].memberId + '">'
                + '<span class="glyphicon glyphicon-trash" aria-hidden="true"></span></a></div></td>';
        tableTbody += '</tr>';
    }
    $('#member-table-trash tbody').html(tableTbody);
}
/*选择列表内容改变时执行start*/
function initMemberSelectTrash() {
    initSelect(pageMemberTrash);//重新请求分页
    initCheckBoxTrash();
}
/*选择列表内容改变时执行end*/

/*初始化状态栏start*/
function initStateTrash() {
    let stateElements = document.getElementsByClassName("state-trash");
    for(let i = 0; i < stateElements.length ; i ++){
        stateElements[i].style.color = "orange";
    }
}
/*初始化状态栏end*/
/*全选start*/
function selectAllTrash() {
    selectAll("select-all-trash","ids-to-delete-trash");
}
/*全选end*/
/*单选start*/
function selectTrashThis(obj) {
    selectByOne(obj,"ids-to-delete-trash");
}
/*单选end*/
/*操作start*/
//恢复用户
function saveThis(obj) {
    //id形式为save-id
    let memberId = obj.id;
    let id = memberId.substring(5);
    let text = "确定要恢复id：" + id + "的用户吗？";
    operationTransfer(obj,id,text,saveMember);
}
function saveMember() {
    let id = document.getElementById("id").value;
    let url = "/member/transfer";
    let info = '{"memberId":"' + id + '","state":' + 20 + '}';
    let data = JSON.parse(info);
    startOperationTransfer(url,data,saveMember,pageMemberTrash);
}
//彻底删除用户
function deleteThis(obj) {
    //元素id形式为delete-id
    let elementId = obj.id;
    let memberId = elementId.substring(7);//截取id信息
    let text = "删除用户是非常危险的,请谨慎操作！确定要删除id：" + memberId + "的用户吗？";
    operation(obj,memberId,text,deleteTrash);
}
function deleteTrash() {
    let value = document.getElementById("id").value;
    let url = "/member/delete";
    let info = '{' + '"deleteId":"' + value + '"}';
    let data = JSON.parse(info);
    startOperation(url,data,deleteTrash,pageMemberTrash);
}
//批量删除用户
function batchDeleteThis(obj) {
    let rightText = "彻底删除后数据不可恢复，确定要彻底删除这些用户吗？";
    let wrongText = "你还没勾选任何数据！";
    batch(obj,"ids-to-delete-trash",rightText,wrongText,batchDeleteMember);
}
function batchDeleteMember() {
    let url = "/member/deletes";
    let ids = document.getElementById("ids-to-delete-trash").value;
    batchConfirm(url,ids,batchDeleteMember,pageMemberTrash);
}
//批量恢复用户
function batchRecoveryThis(obj) {
    let rightText = "用户将恢复为禁用账户，确定要恢复这些用户吗？";
    let wrongText = "你还没勾选任何数据！";
    batch(obj,"ids-to-delete-trash",rightText,wrongText,batchRecoveryMember);
}
function batchRecoveryMember() {
    let url = "/member/recoveries";
    let ids = document.getElementById("ids-to-delete-trash").value;
    batchConfirm(url,ids,batchRecoveryMember,pageMemberTrash);
}
//搜索回收站用户
function searchMember() {
    let url = "/member/trash";
    search(url,pageSplitTrash,"search-key");
}
//初始化检索提示
function initToOriTips() {
    initToOri("error-msg");
}
/*操作end*/
/*样式控制start*/
function initCheckBoxTrash() {
    initCheckbox("select-all-trash");
}
/*样式控制end*/