/*公共js资源文件，用于搜索业务处理*/

//检索数据
function search(url,pageMethod,searchElement) {
    //url：检索路径,pageMethod:分页函数名,searchElement：搜索框元素
    let key = document.getElementById(searchElement).value;
    if (key.length > 0){
        //开始检索
        eval(pageMethod(url,key));
    }else {
        let key = "null";
        initSearchTips(key,"error-msg");
        document.getElementById("error-msg").innerText = "未输入检索字";
        document.getElementById("error-msg").style.color = "black";
        eval(pageMethod(url,key));
    }
}
/*样式控制start*/
function initSearchTips(key,tipsElement) {
    let element = document.getElementById(tipsElement);
    if (key.length > 0){
        element.innerText = "";
        element.style.color = "none";
    }
}
function initToOri(tipsElement) {
    let element = document.getElementById(tipsElement);
    element.innerText = "";
    element.style.color = "none";
}
/*样式控制end*/