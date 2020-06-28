/*本文件是处理添加商品页面业务的js文件*/

/*创建ajax对象*/
let xmlHttpRequest;
function createXmlHttpRequest(){
    if(window.XMLHttpRequest){
        xmlHttpRequest = new XMLHttpRequest();//非IE浏览器直接实例化对象
    }else {
        xmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
    }
}
/*页面加载时动态绑定事件*/
window.onload=function () {
    loadFirstCategory();
    document.getElementById("high-level-category").addEventListener("change",loadSecondCategory);
    document.getElementById("lower-level-category").addEventListener("change",loadThirdCategory);
    document.getElementById("product-name").addEventListener("keyup",nameTips);
    document.getElementById("brief-info").addEventListener("keyup",tips);
    document.getElementById("product-details").addEventListener("keyup",note);
    document.getElementById("price").addEventListener("blur",verifyPrice);
    document.getElementById("stock").addEventListener("blur",verifyStock);
    document.getElementById("limit-num").addEventListener("blur",verifyLimitNum);
    document.getElementById("put").addEventListener("click",addProduct);
}
/*加载分类信息*/
function loadFirstCategory() {
    createXmlHttpRequest();
    xmlHttpRequest.open("post","/product/goods/category/1" ,true);
    xmlHttpRequest.onreadystatechange=function () {
        if(xmlHttpRequest.readyState==4 && xmlHttpRequest.status==200){
            let data = eval(xmlHttpRequest.responseText);
            let selectElement = document.getElementById("high-level-category");
            selectElement.length = 1;
            for(let i = 0 ; i < data.length ; i++){
                let option = document.createElement("option");
                option.setAttribute("value",data[i]);
                option.appendChild(document.createTextNode(data[i]));
                selectElement.appendChild(option);
            }
        }
    }
    xmlHttpRequest.send();
}
/*触发ajax请求二级分类信息*/
function loadSecondCategory() {
    createXmlHttpRequest();
    let data = document.getElementById("high-level-category").value;
    let choice = data.replace(/\//g,":");
    let selectNexts = document.getElementById("low-level-category");//清空三级列表旧数据
    selectNexts.length = 1;
    if (choice == 0) {//选择框没有选择，则不发请求，并把上次请求回来放在二级列表的数据清空
        let selectNext = document.getElementById("lower-level-category");
        selectNext.length = 1;
    } else {    //已经选择，发请求
        xmlHttpRequest.open("post", "/product/goods/category/" + choice, true);
        xmlHttpRequest.onreadystatechange = function () {
            if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
                let secondCategory = eval(xmlHttpRequest.responseText);
                let selectElement = document.getElementById("lower-level-category");
                selectElement.length = 1;
                for (let i = 0; i < secondCategory.length; i++) {
                    let option = document.createElement("option");
                    option.setAttribute("value", secondCategory[i]);
                    option.appendChild(document.createTextNode(secondCategory[i]));
                    selectElement.appendChild(option);
                }
            }
        }
        xmlHttpRequest.send();
    }
}
//ajax请求三级分类信息
function loadThirdCategory() {
    createXmlHttpRequest();
    let data = document.getElementById("lower-level-category").value;
    let choice = data.replace(/\//g,":");
    if (choice == 0) {//选择框没有选择，则不发请求，并把上次请求回来放在三级列表的数据清空
        let selectNext = document.getElementById("low-level-category");
        selectNext.length = 1;
    }else {     //已经选择，发请求
        xmlHttpRequest.open("post","/product/goods/category/" + choice + "3" ,true);
        xmlHttpRequest.onreadystatechange=function () {
            if(xmlHttpRequest.readyState==4 && xmlHttpRequest.status==200){
                let thirdCategory = eval(xmlHttpRequest.responseText);
                let selectElement = document.getElementById("low-level-category");
                selectElement.length = 1;
                for (let i = 0 ; i < thirdCategory.length ; i ++){
                    let option = document.createElement("option");
                    option.setAttribute("value",thirdCategory[i]);
                    option.appendChild(document.createTextNode(thirdCategory[i]));
                    selectElement.appendChild(option);
                }
            }
        }
        xmlHttpRequest.send();
    }
}

/*商品添加*/
function addProduct() { //商品添加ajax请求
    document.getElementById("header").innerText = "系统信息";
    document.getElementById("msg").innerText = "";
    document.getElementById("window-close").removeEventListener("click",reset);
    document.getElementById("confirm").removeEventListener("click",reset);
    if(!isEmpty()){//表单验证通过
        createXmlHttpRequest();//调用ajax对象创建函数
        //拼接访问json字符串
        let data = "{"
            + "\"productName\":" + "\""+ document.getElementById("product-name").value + "\""
            + ",\"briefInfo\":" + "\"" + document.getElementById("brief-info").value + "\""
            + ",\"price\":" + document.getElementById("price").value
            + ",\"stock\":" + document.getElementById("stock").value
            + ",\"limitNum\":" + document.getElementById("limit-num").value
            + ",\"thirdCategory\":" + "\"" + document.getElementById("low-level-category").value + "\""
            + ",\"releaseState\":" + document.getElementById("release").value
            + ",\"imageSrc\":" + "\"" + document.getElementById("show-image").value + "\""
            + ",\"productDetails\":" + "\"" + document.getElementById("product-details").value + "\""
            + "}";
        xmlHttpRequest.open("get","/product/goods/add/form/" + data + "/",true);
        xmlHttpRequest.setRequestHeader("X-Requested-With","XMLHttpRequest");
        // alert(xmlHttpRequest.status);
        xmlHttpRequest.onreadystatechange=function () {
            if(xmlHttpRequest.readyState==4 && xmlHttpRequest.status==200){
                //处理访问返回结果
                let message = JSON.parse(xmlHttpRequest.responseText);
                document.getElementById("header").innerText = message.msgTitle;
                document.getElementById("msg").innerText = message.msgContent;
                if (message.msgType == "success"){
                    document.getElementById("window-close").addEventListener("click",reset);
                    document.getElementById("confirm").addEventListener("click",reset);
                }
            }
        }
        xmlHttpRequest.send();
    }else {
        document.getElementById("header").innerText = "错误信息";
        document.getElementById("msg").innerText = "必要字段数据不能为空，请检查输入！";
        return false;//表单验证不通过
    }
}
function reset() {
    let inputElements = document.getElementsByTagName("input");
    for (let i = 0 ; i < inputElements.length ; i ++){
        if((inputElements[i].attributes["type"].value) == ("text")){
            inputElements[i].value = "";
        }
    }
    let selectElements = document.getElementsByTagName("select");
    for (let i = 0 ; i < selectElements.length ; i ++){
        if(selectElements[i].id != "high-level-category"){
            selectElements[i].length=1;
        }else {
            selectElements[i].selectedIndex=0;
        }
    }
    let textAreaElements = document.getElementsByTagName("textarea");
    for (let i = 0 ; i < textAreaElements.length ; i ++){
        textAreaElements[i].value = "";
    }
    let check = document.getElementById("release-checkbox");
    check.checked=false;
    document.getElementById("release").value = 0;
    /*提示初始化*/
    nameTips();
    tips();
    note();
    verifyPrice();
    verifyStock();
    verifyLimitNum();
}
