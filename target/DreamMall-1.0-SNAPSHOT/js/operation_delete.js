/*公共js资源文件，删除业务处理逻辑*/

//确认操作
function operation(obj,id,text,addMethod) {
    //obj:触发事件的对象,id：删除的id,text：提醒内容,addMethod：添加监听事件
    let element = document.getElementById("id");//保存要删除的id的元素
    element.value = "";
    eject(obj);
    showMsg(text);
    document.getElementById("confirm").addEventListener('click',eval(addMethod));
    document.getElementById("confirm").type = "button";
    element.value = id;
}
//进行操作
function startOperation(url,data,removeMethod,resultMethod) {
    //url:请求地址,data：发送的数据,removeMethod：移除监听事件,resultMethod：回调函数执行函数，一般为分页函数
    $('#confirm').on('click',sendData(url,data,resultMethod,true));//该函数为notice.js的统一发请求方法
    document.getElementById("confirm").type = "hidden";//隐藏确认按钮
    document.getElementById("confirm").removeEventListener('click',eval(removeMethod));//执行完函数移除事件
}
