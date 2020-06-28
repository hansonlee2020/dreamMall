/*公共js资源文件，批量操作*/

//确认批量操作
function batch(obj,valueElementId,rightText,wrongText,addMethod) {
    //obj：触发事件的对象,valueElementId：数据所在元素id,rightText：操作正确的提示内容,
    // wrongText：操作错误时的提示,addMethod：添加新监听事件
    eject(obj);
    let data = document.getElementById(valueElementId).value;
    let noticeElement = document.getElementById("msg");
    let confirmBtn = document.getElementById("confirm");
    noticeElement.innerText = "";
    if (data.length > 0){
        noticeElement.innerText = rightText;
        confirmBtn.type = "button";
        confirmBtn.addEventListener("click",eval(addMethod));
    }else {
        noticeElement.innerText = wrongText;
        confirmBtn.type = "hidden";
    }
}
//提交批量操作
function batchConfirm(url,ids,removeMethod,resultMethod) {
    //url:请求地址,ids：发送的id数据,removeMethod：移除监听事件,resultMethod：回调函数执行函数，一般为分页函数
    $.ajax({ //发送
        type : "get",
        url : url,
        contentType :  'application/json;charset=UTF-8',
        dataType : "json",
        data : {ids : ids},
        success : function(data) {//后台处理结果信息回显
            document.getElementById("confirm").type = "hidden";
            showMsgAll(data.msgTitle,data.msgContent);
            eval(resultMethod());
        }
    });
    document.getElementById("confirm").type = "hidden";//隐藏确认按钮
    document.getElementById("confirm").removeEventListener('click',eval(removeMethod));
}