/*本文件是处理权限信息页面业务的js文件*/

/*页面加载时运行*/
$(function () {
    pageAuthority();//获取权限信息
    loadTips();//加载提示
    //事件绑定
    document.getElementById("authority-groupId").addEventListener('blur',verifyGroupId);
    document.getElementById("authority-name").addEventListener('keyup',verifyName);
    document.getElementById("authority-field").addEventListener('keyup',verifyField);
    document.getElementById("authority-details").addEventListener('keyup',verifyDetails);
    document.getElementById("page-select").addEventListener('change',initAuthoritySelect);
    document.getElementById("select-all").addEventListener("click",selectAllAuthorities);
});

//权限数据分页
function pageAuthority() {
    let url = "/authority/list";
    pageSplit(url);
}
//权限数据展示页面渲染
function appendHtml(list) {
    let tableTbody = '';
    for (let i = 0; i < list.length; i++) {
        tableTbody += '<tr style="word-wrap: break-word">';
        tableTbody += '<td><input type="checkbox" id="select-' + i +'" style="width: 20px" value="' + list[i].authorityId + '" onclick="selectThis(this)"></td>';
        tableTbody += '<td><span style="width: 70px;">' + list[i].authorityId + '</span></td>';
        tableTbody += '<td><span style="width: 300px;">' + list[i].authorityField + '</span></td>';
        tableTbody += '<td><span style="width: 300px;">' + list[i].authorityName + '</span></td>';
        tableTbody += '<td><span style="width: 150px;">' + list[i].authorityDetails + '</span></td>';
        tableTbody += '<td><span style="width: 70px;">'+ list[i].authorityGroupId + '</span></td>';
        tableTbody += '<td><div style="padding-top: 0px">';
        tableTbody += '<a href="#" aria-label="Edit" style="color: mediumpurple;text-decoration: none" data-toggle="tooltip" data-placement="bottom" title="编辑"'
            + ' onclick="editThis(this)" id="edit-' +list[i].authorityId +  '">'
            + '<span class="glyphicon glyphicon-edit" aria-hidden="true"></span>&nbsp;|&nbsp;</a>'
            + '<a class="md-trigger" href="#" aria-label="Trash" style="color: red" data-toggle="tooltip" data-placement="bottom" title="删除"'
            + ' data-modal="message-window" onclick="deleteThis(this)" id="delete-' + list[i].authorityId + '">'
            + '<span class="glyphicon glyphicon-trash" aria-hidden="true"></span></a></div></td>';
        tableTbody += '</tr>';
    }
    $('#authority-table tbody').html(tableTbody);
}
/*选择列表内容改变时执行start*/
function initAuthoritySelect() {
    initSelect(pageAuthority);//重新请求权限分页
}
/*选择列表内容改变时执行end*/

/*全选*/
function selectAllAuthorities() {
    selectAll("select-all","ids-to-do");
}
/*单选*/
function selectThis(obj) {
    selectByOne(obj,"ids-to-do");
}

/*加载提示start*/
function loadTips() {
    let text = "anon 表示不需要认证以及授权，authc 表示需要认证 没有登录是不能进行访问的，" +
        "表示需要该权限才能访问的页面，例如 /user/* = perms[*]" +
        "，权限名标准格式：perms[test]、perms[test:test2]，资源字段名标准格式：/*，例如：/test/test2";
    document.getElementById("help-tips").innerText = text;
}
/*加载提示end*/
/*新增权限start*/
function recovery() {
    document.getElementById("put-btn").type = "button";
    document.getElementById("edit-btn").type = "hidden";
    reset();
    verifyName();
    verifyField();
    verifyDetails();
    verifyGroupId();
    initResult();
}
function increaseThis(obj) {
    if (!isInfoEmpty()){
        initMessage();
        let text = "确定新增权限数据吗？"
        inOperation(obj,text,increase,reset);
    }else {
        let title = "警告";
        let text = "必要字段不能为空！";
        eject(obj);
        showMsgAll(title,text);
        document.getElementById("confirm").type = "hidden";
    }
}
function increase() {
    let url = "/authority/increase";
    let authid = document.getElementById("authority-id").value;
    let authf = document.getElementById("authority-field").value;
    let authn = document.getElementById("authority-name").value;
    let groupId = document.getElementById("authority-groupId").value;
    let details = document.getElementById("authority-details").value;
    if (groupId.length === 0){//内容为空则设置默认值
        groupId = "0";
    }
    if (details.length === 0){
        details = "0";
    }
    let info = '{' + '"authorityId":' + authid + ',"authorityName":' + '"' + authn + '"'
        + ',"authorityField":' + '"' + authf + '"' + ',"authorityGroupId":' + groupId
        + ',"authorityDetails":' + '"' + details + '"' + '}';
    let data = JSON.parse(info);
    putOperation(url,data,increase,reset,pageAuthority);

}
/*新增权限end*/

/*编辑权限start*/
function editThis(obj) {
    //id为edit-id的形式，需要字符串截取
    let element = document.getElementById(obj.id);
    let authId = obj.id;
    let value = "authority-info";
    createAttribute(element,value);//创建属性
    document.getElementById(obj.id).addEventListener('click',setAuthorityContent);//添加设置内容事件
    setAuthorityContent(authId.substring(5));
}
//设置权限内容
function setAuthorityContent(authId) {
    let url = "/authority/query";
    let id = '{"authorityId":' +authId +  '}';
    let data = JSON.parse(id);
    sendEditData(url,data);//调用请求权限信息
}
function setResultContent(aid,athName,athField,athGroupId,athDetails) {
    document.getElementById("authority-id").value = aid;
    document.getElementById("authority-name").value = athName;
    document.getElementById("authority-field").value = athField;
    document.getElementById("authority-groupId").value = athGroupId;
    document.getElementById("authority-details").value = athDetails;
    let element = document.getElementById("edit-" + aid);
    if(element != null){
    element.removeEventListener('click',setAuthorityContent);//移除设置内容事件
    }
    document.getElementById("put-btn").type = "hidden";
    document.getElementById("edit-btn").type = "button";
    removeAttribute("编辑");//移除属性
}
//请求权限信息
function sendEditData(url,data) {
    $.ajax({
        type : "get",
        url : url,
        dataType : "json",
        data : data,
        success(result){
            setResultContent(result.authorityId,result.authorityName,result.authorityField,result.authorityGroupId,result.authorityDetails);
            verifyName();
            verifyField();
            verifyGroupId();
            verifyDetails();
            initResult();
        }
    });
}
//进入编辑
function edit(obj) {
    initResult();//初始化结果信息
    if (!isInfoEmpty()){
        initMessage();
        let text = "确定要修改该权限吗？";
        inOperation(obj,text,editStart,reset);
    }else {
        let title = "警告";
        let text = "必要字段均不能为空！请检查！"
        eject(obj);
        showMsgAll(title,text);
        document.getElementById("confirm").type = "hidden";
    }
}
//开始编辑
function editStart() {
    let url = "/authority/update";
    let id = document.getElementById("authority-id").value;
    let name = document.getElementById("authority-name").value;
    let field = document.getElementById("authority-field").value;
    let groupId = document.getElementById("authority-groupId").value;
    let details = document.getElementById("authority-details").value;
    if (groupId.length === 0){//内容为空则设置默认值
        groupId = "0";
    }
    if (details.length === 0){
        details = "0";
    }
    let info = '{' + '"authorityId":' + id + ',"authorityName":"' + name + '","authorityField":"' + field
        + '","authorityGroupId":' + groupId + ',"authorityDetails":"' + details + '"}';
    let data = JSON.parse(info);
    putOperation(url,data,editStart,reset,pageAuthority);
}
/*编辑权限end*/

/*删除权限start*/
function deleteThis(obj) {
    //元素id形式为delete-id
    let elementId = obj.id;
    let authId = elementId.substring(7);//截取id信息
    let text = "删除权限是非常危险的,请谨慎操作！确定要删除id：" + authId + "的权限吗？";
    operation(obj,authId,text,deleteStart);
}
function deleteStart() {
    let value = document.getElementById("id").value;
    let url = "/authority/delete";
    let info = '{' + '"authorityId":' + value + '}';
    let data = JSON.parse(info);
    startOperation(url,data,deleteStart,pageAuthority);
}
/*删除权限end*/

//搜索权限
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
function searchAuthority() {
    let url = "/authority/search";
    let key = document.getElementById("search-key").value;
    if (key.length > 0){
        search(url,pageSearchSplit,"search-key");
    }else {
        pageAuthority();
    }
}
//初始化检索提示
function initToOriTips() {
    initToOri("error-msg");
}
/*自定义格式控制start*/
//清空内容栏
function reset() {
    let inputElements = document.getElementsByTagName("input");
    let textAreaElements = document.getElementsByTagName("textarea");
    for (let i = 0 ; i < inputElements.length ; i ++){//清空文本
        if (inputElements[i].attributes["type"].value == "text" && inputElements[i].id != "authority-id"){
            inputElements[i].value = "";
        }
    }
    for (let i = 0 ; i < textAreaElements.length ; i ++){//清空大文本
        if (inputElements[i].attributes["type"].value == "text"){
            textAreaElements[i].value = "";
        }
    }
    document.getElementById("authority-id").value = "9999";
    verifyGroupId();
    verifyName();
    verifyField();
    verifyDetails();
}
//检查必要内容是否为空
function isInfoEmpty() {
    let authf = document.getElementById("authority-field").value;
    let authn = document.getElementById("authority-name").value;
    let authd = document.getElementById("authority-details").value;
    if (authf.length == 0 | authn.length == 0 | authd.length == 0){
        return true;
    }else {
        return false;
    }
}
//检查权限组id是否合法
function verifyGroupId() {
    let groupId = document.getElementById("authority-groupId").value;
    let msg = document.getElementById("authority-groupId-msg");
    let regex = /^[1-9]\d*$/;
    if (groupId.length != 0){
        if (regex.test(groupId)){//验证通过
            if(groupId > 999){
                msg.innerText = "id过大！";
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
        msg.innerText = "1~3位数字,首位数字不能为0！";
        msg.style.color = "#999999";
        return false;
    }
}
//验证权限名
function verifyName() {
    nameTips("authority-name",50,"","名字过大！","1~50个英文字符");
}
//验证资源字段名
function verifyField() {
    nameTips("authority-field",50,"","名字过大！","1~50个英文字符");
}
//验证权限描述
function verifyDetails() {
    nameTips("authority-details",50,"","内容过大！","1~50个中文字符");
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
function initResult() {
    let element = document.getElementById("result-details");
    element.innerText = "你还没选择操作！";
    element.style.color = "black"
}
/*自定义格式控制end*/