/*公共js资源文件，修改/更新业务处理逻辑*/

//确认操作
function operationTransfer(obj,id,text,addMethod) {
    let element = document.getElementById("id");
    element.value = "";
    eject(obj);
    showMsg(text);
    element.value = id;
    document.getElementById("confirm").addEventListener('click',eval(addMethod));
    document.getElementById("confirm").type = "button";
}
//进行操作
function startOperationTransfer(url,data,removeMethod,resultMethod) {
    //url:请求地址,data：发送的数据,removeMethod：移除监听事件,resultMethod：回调函数执行函数，一般为分页函数
    $('#confirm').on('click',sendData(url,data,resultMethod,true));
    document.getElementById("confirm").type = "hidden";//隐藏确认按钮
    document.getElementById("confirm").removeEventListener('click',eval(removeMethod));//执行完函数移除事件
}