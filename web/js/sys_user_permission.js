/*管理员业务处理js文件*/

$(function () {
    pageSystemUser();//分页显示管理员数据
    //绑定事件
    document.getElementById("page-select").addEventListener('change',initSystemUserSelect);
    document.getElementById("system-user-name").addEventListener('keyup',verifyName);
    document.getElementById("system-user-state").addEventListener('keyup',verifyState);
    document.getElementById("system-user-pwd").addEventListener('keyup',verifyPwd);
    document.getElementById("confirm-pwd").addEventListener('keyup',verifyConfirmPwd);
    document.getElementById("confirm-pwd").addEventListener('blur',tipsConfirmPwd);
    document.getElementById("select-all-authority").addEventListener('click',selectAllAuthorities);
});
//管理员数据分页
function pageSystemUser() {
    let url = "/sys/user/list";
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
                            if (url == "/member/list"){
                                initState();//初始化状态
                            }
                        }
                    });
                }
            };
            $('#split').bootstrapPaginator(options);//设置分页
        }
    });
}
//展示页面渲染
function appendHtml(list) {
    let tableTbody = '';
    for (let i = 0; i < list.length; i++) {
        tableTbody += '<tr style="word-wrap: break-word">';
        tableTbody += '<td><input type="checkbox" id="select-' + i +'" style="width: 20px" value="' + list[i].userId + '" onclick="selectThis(this)" disabled></td>';
        tableTbody += '<td><span style="width: 150px;">' + list[i].userId + '</span></td>';
        tableTbody += '<td><span style="width: 300px;">' + list[i].userName + '</span></td>';
        tableTbody += '<td><span style="width: 300px;word-wrap: break-word">'+ list[i].userPermission + '</span></td>';
        tableTbody += '<td><span style="width: 70px;">' + list[i].userState + '</span></td>';
        tableTbody += '<td><div style="padding-top: 0px">';
        if (list[i].userId == 1){
            tableTbody +='<a href="#" aria-label="power" style="color: yellowgreen;text-decoration: none" data-toggle="tooltip" data-placement="bottom" title="授权"'
                + ' onclick="powerThis(this)" id="power-' +list[i].userId +  '">'
                + '<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>&nbsp;|&nbsp;</a>'
                + '<a href="#" aria-label="Edit" style="color: mediumpurple;text-decoration: none" data-toggle="tooltip" data-placement="bottom" title="编辑"'
                + ' onclick="editThis(this)" id="edit-' +list[i].userId +  '">'
                + '<span class="glyphicon glyphicon-edit" aria-hidden="true"></span></a>';
        }else {
            tableTbody += '<a href="#" aria-label="power" style="color: yellowgreen;text-decoration: none" data-toggle="tooltip" data-placement="bottom" title="授权"'
                + ' onclick="powerThis(this)" id="power-' +list[i].userId +  '">'
                + '<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>&nbsp;|&nbsp;</a>'
                + '<a href="#" aria-label="Edit" style="color: mediumpurple;text-decoration: none" data-toggle="tooltip" data-placement="bottom" title="编辑"'
                + ' onclick="editThis(this)" id="edit-' +list[i].userId +  '">'
                + '<span class="glyphicon glyphicon-edit" aria-hidden="true"></span>&nbsp;|&nbsp;</a>'
                + '<a class="md-trigger" href="#" aria-label="Trash" style="color: red" data-toggle="tooltip" data-placement="bottom" title="删除"'
                + ' data-modal="message-window" onclick="deleteThis(this)" id="delete-' + list[i].userId + '">'
                + '<span class="glyphicon glyphicon-trash" aria-hidden="true"></span></a></div></td>';
        }
        tableTbody += '</tr>';
    }
    $('#system-user-table tbody').html(tableTbody);
}
/*选择列表内容改变时执行start*/
function initSystemUserSelect() {
    initSelect(pageSystemUser);//重新请求分页
}
/*选择列表内容改变时执行end*/

/*操作start*/
/*编辑start*/
function editThis(obj) {
    //id为edit-id的形式，需要字符串截取
    let element = document.getElementById(obj.id);
    let userId = obj.id;
    let value = "system-user-info";
    createAttribute(element,value);//创建属性
    document.getElementById(obj.id).addEventListener('click',setSystemUserContent);//添加设置内容事件
    setSystemUserContent(userId.substring(5));
    backToTop();
}
//设置内容
function setSystemUserContent(userId) {
    let url = "/sys/user/query";
    let id = '{"userId":"' + userId +  '"}';
    let data = JSON.parse(id);
    sendEditData(url,data);//调用请求管理员信息
}
function setResultUserContent(uid,userName,userState) {
    document.getElementById("system-user-id").value = uid;
    document.getElementById("system-user-name").value = userName;
    document.getElementById("system-user-state").value = userState;
    let element = document.getElementById("edit-" + uid);
    if(element != null){
        element.removeEventListener('click',setSystemUserContent);//移除设置内容事件
    }
    document.getElementById("put-btn").type = "hidden";
    document.getElementById("edit-btn").type = "button";
    removeAttribute("编辑");//移除属性
    verifyName();
    verifyState();
    verifyPwd();
    verifyConfirmPwd();
}
//请求用户信息
function sendEditData(url,data) {
    $.ajax({
        type : "get",
        url : url,
        dataType : "json",
        data : data,
        success(result){
            setResultUserContent(result.userId,result.userName,result.userState);
        }
    });
}
//进入编辑
function edit(obj) {
    initResult();//初始化结果信息
    initMessage();//初始化通知消息
    if (!isInfoEmpty() && confirmPwd()){
        initMessage();
        let text = "确定要修改该管理员账号吗？";
        inOperation(obj,text,editStart,reset);
    }else {
        let title = "警告";
        let text;
        if (!confirmPwd()){
            text = "密码不一致！"
        }else {
            text= "必要字段均不能为空！请检查！"
        }
        eject(obj);
        showMsgAll(title,text);
        document.getElementById("confirm").type = "hidden";
    }
}
//开始编辑
function editStart() {
    let url = "/sys/user/update";
    let id = document.getElementById("system-user-id").value;
    let name = document.getElementById("system-user-name").value;
    let state = document.getElementById("system-user-state").value;
    let pwd = document.getElementById("system-user-pwd").value;
    //加密
    let afterPwd = md5(pwd);
    let info = '{' + '"userId":"' + id + '","userName":"' + name + '","userState":"' + state
        + '","userPwd":"' + afterPwd + '"}';
    let data = JSON.parse(info);
    putOperation(url,data,editStart,reset,pageSystemUser);
}
//删除管理员
function deleteThis(obj) {
    //元素id形式为delete-id
    let elementId = obj.id;
    let userId = elementId.substring(7);//截取id信息
    let text = "删除管理员是非常危险的,请谨慎操作！确定要删除id：" + userId + "的管理员吗？";
    operation(obj,userId,text,deleteStart);
}
function deleteStart() {
    let value = document.getElementById("id").value;
    let url = "/sys/user/delete";
    let info = '{' + '"userId":"' + value + '"}';
    let data = JSON.parse(info);
    startOperation(url,data,deleteStart,pageSystemUser);
}
//添加管理员
function recovery() {
    document.getElementById("put-btn").type = "button";
    document.getElementById("edit-btn").type = "hidden";
    reset();
    verifyName();
    verifyState();
    verifyPwd();
    verifyConfirmPwd();
}
function increaseThis(obj) {
    initResult();//初始化结果
    if (!isInfoEmpty() && confirmPwd()){
        let text = "确定新增管理员吗？"
        inOperation(obj,text,increase,reset);
    }else {
        let title = "警告";
        let text;
        if (!confirmPwd()){
            text = "密码不一致！"
        }else {
            text= "必要字段均不能为空！请检查！"
        }
        eject(obj);
        showMsgAll(title,text);
        document.getElementById("confirm").type = "hidden";
    }
}
function increase() {
    let url = "/sys/user/increase";
    let name = document.getElementById("system-user-name").value;
    let state = document.getElementById("system-user-state").value;
    let pwd = document.getElementById("system-user-pwd").value;
    //加密
    let afterPwd = md5(pwd);
    let info = '{' + '"userId":"' + id + '","userName":"' + name + '","userState":"' + state
        + '","userPwd":"' + afterPwd + '"}';
    let data = JSON.parse(info);
    putOperation(url,data,increase,reset,pageSystemUser);

}
//授权
function powerThis(obj) {
    //id为power-id的形式，需要字符串截取
    let element = document.getElementById(obj.id);
    let userId = obj.id;
    let value = "auth-permission";
    createAttribute(element,value);//创建属性
    document.getElementById(obj.id).addEventListener('click',setPowerContent);//添加设置内容事件
    setPowerContent(userId.substring(6));
}
//设置内容
function setPowerContent(userId) {
    let url = "/sys/user/query";
    let id = '{"userId":"' + userId +  '"}';
    let data = JSON.parse(id);
    sendAuthData(url,data);//调用请求管理员信息
}
function setResultAuthContent(uid,userName,userState) {
    document.getElementById("user-id").value = uid;
    document.getElementById("user-name").value = userName;
    document.getElementById("user-state").value = userState;
    let element = document.getElementById("power-" + uid);
    if(element != null){
        element.removeEventListener('click',setPowerContent);//移除设置内容事件
    }
    removeAttribute("授权");//移除属性
}
//请求用户信息
function sendAuthData(url,data) {
    $.ajax({
        type : "get",
        url : url,
        dataType : "json",
        data : data,
        success(result){
            //结果回显,待写
            setResultAuthContent(result.userId,result.userName,result.userState);
            //获取权限列表
            $.ajax({
                type : "get",
                url : "/sys/user/authority",
                success(result){
                    //渲染权限选择html
                    appendHtmlAuth(result);
                }
            });
        }
    });
}
//渲染权限选择
function appendHtmlAuth(list) {
    let div = '';
    for (let i = 0; i < list.length; i++) {
        div += '';
        div += '<span>'
            + '<input type="checkbox" class="authority" id="select-auth-' + i +'" style="width: 20px" value="' + list[i].authorityId + '" onclick="selectAuth(this)">'
            + list[i].authorityDetails +'&nbsp;|&nbsp;</span>';
    }
    $('#choice').html(div);
}
//全选权限
function selectAllAuthorities() {//重写全选方法
    let flag = document.getElementById("select-all-authority").checked;//全选标记
    let inputElements = document.getElementsByClassName("authority");
    ids.length = 0;//先清空数组
    for (let i = 0; i < inputElements.length; i ++){
        if (inputElements[i].type == "checkbox" && inputElements[i].id != "select-all-authority"){
            inputElements[i].checked = flag;
            if (flag){
                ids.push(inputElements[i].value);//勾选全选，将id保存到数组
            }else {
                ids.length = 0;//取消全选，删除保存在数组的id
            }
        }
    }
    document.getElementById("ids-to-do").value = ids;
}
//单选权限
function selectAuth(obj) {
    selectByOne(obj,"ids-to-do");
}
//授权确认
function batchAuthThis(obj) {
    let idToPer = document.getElementById("user-id").value;
    let rightText = "确定要授予权限给id：" + idToPer + "用户吗？";
    let wrongText = "你还没勾选任何数据！";
    batch(obj,"ids-to-do",rightText,wrongText,batchAuth);
}
function batchAuth() {
    let url = "/sys/user/permission";
    let idToPer = document.getElementById("user-id").value;
    let ids = document.getElementById("ids-to-do").value;
    batchConfirmAuth(url,ids,idToPer,batchAuth,pageSystemUser);
}
//提交批量操作
function batchConfirmAuth(url,ids,id,removeMethod,resultMethod) {
    //url:请求地址,ids：发送的id数据,id：管理员id，removeMethod：移除监听事件,resultMethod：回调函数执行函数，一般为分页函数
    $.ajax({ //发送
        type : "get",
        url : url,
        contentType :  'application/json;charset=UTF-8',
        dataType : "json",
        data : {ids : ids,userId : id},
        success : function(data) {//后台处理结果信息回显
            document.getElementById("confirm").type = "hidden";
            showMsgAll(data.msgTitle,data.msgContent);
            eval(resultMethod());
            if (data.msgType == "success"){
                initCheckbox("select-all-authority");
                initCheckboxPerm();
            }
        }
    });
    document.getElementById("confirm").type = "hidden";//隐藏确认按钮
    document.getElementById("confirm").removeEventListener('click',eval(removeMethod));
}
//撤销授权
function revokeThis(obj) {
    let idToPer = document.getElementById("user-id").value;
    let rightText = "确定要撤销id：" + idToPer + "用户的权限吗？";
    let wrongText = "你还没勾选任何数据！";
    batch(obj,"ids-to-do",rightText,wrongText,revoke);
}
function revoke() {
    let url = "/sys/user/revoke/perm";
    let idToPer = document.getElementById("user-id").value;
    let ids = document.getElementById("ids-to-do").value;
    batchConfirmAuth(url,ids,idToPer,revoke,pageSystemUser);
}
/*操作end*/
/*上线恢复系统专用*/
function initSys(obj) {
    isRestart(obj);
}
//确认恢复系统操作
function isRestart(obj) {
    document.getElementById("confirm").type = "button";//显示确认操作按钮
    let text = "确定要恢复系统数据吗？"
    eject(obj);
    showMsg(text);
    document.getElementById("confirm").addEventListener("click",confirmRestart);
}
//提交恢复系统操作
function confirmRestart() {
    let url = "/init/start";
    $.ajax({
        url : url,
        type : "get",
        success : function(result) {
            document.getElementById("confirm").type = "hidden";
            showMsgAll(result.msgTitle,result.msgContent);
        }
    });
    document.getElementById("confirm").type = "hidden";//隐藏确认按钮
    document.getElementById("confirm").removeEventListener("click",confirmRestart);
}
//返回顶部
function backToTop() {
}
/*自定义格式控制start*/
//清空内容栏
function reset() {
    let inputElements = document.getElementsByTagName("input");
    for (let i = 0 ; i< inputElements.length ; i ++){
        if (inputElements[i].attributes["type"].value == "text" && inputElements[i].id != "authority-id"){
            inputElements[i].value = "";
        }
        if (inputElements[i].attributes["type"].value == "password"){
            inputElements[i].value = "";
        }
    }
    document.getElementById("system-user-id").value = "9999";
    verifyName();
    verifyState();
    verifyPwd();
    verifyConfirmPwd();
}
//检查必要内容是否为空
function isInfoEmpty() {
    let name = document.getElementById("system-user-name").value;
    let state = document.getElementById("system-user-state").value;
    let pwd = document.getElementById("system-user-pwd").value;
    let cPwd = document.getElementById("confirm-pwd").value;
    if (name.length == 0 | state.length == 0 | pwd.length == 0){
        return true;
    }else {
        return false;
    }
}
//确认密码
function confirmPwd() {
    let pwd = document.getElementById("system-user-pwd").value;
    let cPwd = document.getElementById("confirm-pwd").value;
    return pwd == cPwd;
}
//初始化处理结果
function initResult() {
    let element = document.getElementById("result-details");
    element.innerText = "你还没选择操作！";
    element.style.color = "black"
}
//检查管理员昵称是否合法
function verifyName() {
    nameTips("system-user-name",20,"","昵称过长！","1~20个字符");
}
//检查类别是否合法
function verifyState() {
    nameTips("system-user-state",10,"","内容过长！","1~10个字符");
}
//检查密码是否合法
function verifyPwd() {
    nameTips("system-user-pwd",20,"","密码过长","1~20个字符");
}
//检查确认密码是否合法
function verifyConfirmPwd() {
    nameTips("confirm-pwd",20,"","密码过长","1~20个字符");
}
//验证名称是否合法
function nameTips(elementId,limitNum,msgRight,msgWrong,msgInit) {
    //elementId：需要验证的内容的id,limitNum：限制数量,msgRight：正确提示,msgWrong：错误提示,msgInit：初始化提示
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
        msg.style.color = "#999999";
        return false;
    }
}
function tipsConfirmPwd() {
    let msg = document.getElementById("confirm-pwd-msg");
    if (confirmPwd()){
        msg.innerHTML = "<span style='font-size: 12px' class=\"glyphicon glyphicon-ok\" aria-hidden=\"true\"></span>";
        msg.style.color = "lightgreen";
    }else {
        msg.innerHTML = "<span style='font-size: 12px' class=\"glyphicon glyphicon-remove\" aria-hidden=\"true\">密码不一致</span>";
        msg.style.color = "red";
    }
}
//初始化选择框样式
function initCheckboxPerm() {
    let flag = document.getElementById("select-all-authority").checked;//全选标记
    let inputElements = document.getElementsByClassName("authority");
    for (let i = 0; i < inputElements.length; i ++){
        if (inputElements[i].type == "checkbox" && inputElements[i].id != "select-all-authority"){
            inputElements[i].checked = flag;
            ids.length = 0;
        }
    }
}
/*自定义格式控制end*/
