/*公共js资源文件，新增业务处理逻辑*/

//确认操作
function inOperation(obj,text,addMethod,removeMethod) {
    //obj:触发事件的对象,text:操作提醒内容,addMethod：新增事件监听,removeMethod：移除上次监听
    eject(obj);
    showMsg(text);
    document.getElementById("confirm").addEventListener('click',eval(addMethod));
    document.getElementById("confirm").type = "button";
    document.getElementById("window-close").removeEventListener('click',eval(removeMethod));//移除上次事件
}
//提交操作
function putOperation(url,data,removeMethod,addMethod,resultMethod) {
    //url:请求地址,data:需要发送的数据,removeMethod：移除旧监听事件,addMethod：添加新监听事件,resultMethod:回调函数需要处理的分页函数
    $('#confirm').on('click',sendDataHandle(url,data,resultMethod,false,addMethod));
    document.getElementById("confirm").type = "hidden";//隐藏确认按钮
    document.getElementById("confirm").removeEventListener('click',eval(removeMethod));//执行完函数移除事件
}
//有对返回数据进行处理的发送请求操作
function sendDataHandle(url,data,resultMethod,isEnable,addMethod) {
    //url：请求路径，data：要发送的数据,methodName：要调用函数的函数名称，isEnable：是否启用函数，addMethod：设置新监听事件
    $.ajax({ //发送
        type : "get",
        url : url,
        contentType :  'application/json;charset=UTF-8',
        dataType : "json",
        data : data,
        success : function(result) {//后台处理结果信息回显，result：处理结果
            showMsgAll(result.msgTitle,result.msgContent);
            if (isEnable){
                eval(resultMethod());//使用eval把字符串转换为函数名
            }
            //将处理结果详情显示
            let element = document.getElementById("result-details");
            element.innerText = result.msgContent;
            if (result.msgType == "success"){
                element.style.color = "yellowgreen";
                eval(resultMethod());//重新分页
                document.getElementById("window-close").addEventListener('click',eval(addMethod));
            }else {
                element.style.color = "red";
            }
        }
    });
}