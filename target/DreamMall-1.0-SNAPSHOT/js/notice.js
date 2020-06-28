/*消息弹框业务处理js文件*/

/*通用弹框start*/
function eject(obj){//obj表示当前对象
    initMessage();
    let modal = $(obj).data('modal');
    $("#" + modal).niftyModal();
}
/*通用弹框end*/

/*通用分页发送ajax请求函数start*/
function sendData(url,data,methodName,isEnable) {//url：请求路径，data：要发送的数据,methodName：要调用函数的函数名称，isEnable：是否启用函数
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
            //重新请求分页数据
        }
    });
}
/*通用分页发送ajax请求函数end*/

/*通用发送ajax请求函数start*/
function sendDataNormal(url,data) {//url：请求路径，data：要发送的数据
    $.ajax({ //发送
        type : "get",
        url : url,
        contentType :  'application/json;charset=UTF-8',
        dataType : "json",
        data : data,
        success : function(result) {//后台处理结果信息回显，result：处理结果
            document.getElementById("confirm").type = "hidden";
            showMsgAll(result.msgTitle,result.msgContent);
        }
    });
}
/*通用发送ajax请求函数end*/

/*通用消息显示start*/
function showMsgAll(title,msg) {
    $('#header').text(title);
    $('#msg').text(msg);
}
function showMsg(msg) {
    $('#msg').text(msg);
}
/*通用消息显示end*/

//确定按钮变化样式
function changeBtn(obj) {
    obj.style.backgroundColor = "#c9ffd1";
}
function initBtn(obj) {
    obj.style.backgroundColor = "";
}
function initMessage() {
    document.getElementById("header").innerText = "系统消息";
}