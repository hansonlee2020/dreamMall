/*本文件是处理编辑商品业务的js文件*/

window.onload = function () {
    loadCategory1();//加载商品一级分类
    loadCategory2();//加载商品二级分类
    loadCategory3();//加载商品三级分类
    setBriefInfo();//设置商品简介信息
    setDetails();//设置商品详细信息
    init();
    //绑定事件
    document.getElementById("high-level-category").addEventListener("change",loadSecondCategory);
    document.getElementById("lower-level-category").addEventListener("change",loadThirdCategory);
    document.getElementById("product-name").addEventListener("keyup",nameTips);
    document.getElementById("brief-info").addEventListener("keyup",tips);
    document.getElementById("product-details").addEventListener("keyup",note);
    document.getElementById("price").addEventListener("blur",verifyPrice);
    document.getElementById("stock").addEventListener("blur",verifyStock);
    document.getElementById("limit-num").addEventListener("blur",verifyLimitNum);
    // document.getElementById("put").addEventListener("click",submitEdit);
}
/*加载分类start*/
//加载一级分类
function loadCategory1() {
    let url = "/product/goods/category/1";
    let elementId = "high-level-category";
    let valueId = "category-1";
    let data = "";
    category(url,data,elementId,valueId);
}

//加载二级分类
function loadCategory2() {
    let c1 = document.getElementById("category-1").value;
    let choice = c1.replace(/\//g,":");//替换斜杠为冒号字符，防止地址栏符号识别冲突
    let url = "/product/goods/category/" + choice;
    let elementId = "lower-level-category";
    let valueId = "category-2";
    let data = "";
    category(url,data,elementId,valueId);

}
//加载三级分类
function loadCategory3() {
    let c2 = document.getElementById("category-2").value;
    let choice = c2.replace(/\//g,":");
    let url = "/product/goods/category/" + choice + "3";
    let elementId = "low-level-category";
    let valueId = "category-3";
    let data = "";
    category(url,data,elementId,valueId);

}
//选择触发二级分类请求
function loadSecondCategory() {
    let isChoice = document.getElementById("high-level-category").value;
    //把上次请求回来放在子级列表的数据清空
    let select2 = document.getElementById("lower-level-category");
    select2.length = 0;
    let select3 = document.getElementById("low-level-category");
    select3.length = 0;
    if (isChoice == 0) {//选择框没有选择，则不发请求
        return;
    } else {    //已经选择，发请求
        let choice = isChoice.replace(/\//g,":");
        let url = "/product/goods/category/" + choice;
        let elementId = "lower-level-category";
        let valueId = "0";
        let data = "";
        category(url,data,elementId,valueId);
    }
}
//选择触发三级分类请求
function loadThirdCategory() {
    let isChoice = document.getElementById("lower-level-category").value;
    //把上次请求回来放在子级列表的数据清空
    let select3 = document.getElementById("low-level-category");
    select3.length = 0;
    if (isChoice == 0) {//选择框没有选择，则不发请求
        return;
    } else {    //已经选择，发请求
        let choice = isChoice.replace(/\//g,":");
        let url = "/product/goods/category/" + choice + "3";
        let elementId = "low-level-category";
        let valueId = "0";
        let data = "";
        category(url,data,elementId,valueId);
    }
}
//分类请求
function category(url,data,elementId,valueId) {//url：请求地址,data：递交的数据,elementId：分类结果渲染所在的元素id,valueId：分类名称的值所在的元素的id
    $.ajax({//去后台加载分类数据
        url : url,
        type : "post",
        dataType : "json",
        data : data,
        success : function(result) {
            let selectElement = document.getElementById(elementId);
            selectElement.length = 1;
            let category;
            if (valueId == "0"){
                category = "0";
            }else {
                category = document.getElementById(valueId).value;
            }
            for(let i = 0 ; i < result.length ; i++){
                let option = document.createElement("option");
                option.setAttribute("value",result[i]);
                option.appendChild(document.createTextNode(result[i]));
                if (option.value == category){
                    option.selected = true;
                }
                selectElement.appendChild(option);
            }
        }
    });
}
/*加载分类end*/

/*设置textarea内容start*/
function setTextArea(element,valueElement) {
    let textArea = document.getElementById(element);
    let value = document.getElementById(valueElement).value;
    textArea.value = value;
}
//设置简介
function setBriefInfo() {
    setTextArea("brief-info","brief");
}
//设置详情
function setDetails() {
    setTextArea("product-details","details")
}
/*设置textarea内容end*/

/*初始化提交按钮状态*/
function init() {
    document.getElementById("put").disabled = false;
}

/*提交编辑start*/
//确认编辑
function confirmEdit(obj) {
    document.getElementById("window-close").removeEventListener('click',reset);
    eject(obj);
    if (isEmpty()){//数据验证不通过
        document.getElementById("header").innerText = "错误信息";
        document.getElementById("msg").innerText = "必要字段数据不能为空，请检查输入！";
        document.getElementById("confirm").type = "hidden";
        return false;
    }else {//数据通过验证
        let id = document.getElementById("product-id").value;
        let text = "确定要修改id为  " + id + "  的商品信息吗？";
        showMsg(text);
        let confirmBtn = document.getElementById("confirm");
        confirmBtn.type = "button";
        confirmBtn.addEventListener("click",submitEdit);
    }
}
//提交
function submitEdit() {
    let url = "/product/goods/edit";
    let info = "{"
        + "\"productId\":" + "\"" + document.getElementById("product-id").value + "\""
        + ",\"productName\":" + "\"" + document.getElementById("product-name").value + "\""
        + ",\"briefInfo\":" + "\"" + document.getElementById("brief-info").value + "\""
        + ",\"price\":" + document.getElementById("price").value
        + ",\"stock\":" + document.getElementById("stock").value
        + ",\"limitNum\":" + document.getElementById("limit-num").value
        + ",\"category1\":" + "\"" + document.getElementById("high-level-category").value + "\""
        + ",\"category2\":" + "\"" + document.getElementById("lower-level-category").value + "\""
        + ",\"category3\":" + "\"" + document.getElementById("low-level-category").value + "\""
        + ",\"releaseState\":" + document.getElementById("release").value
        + ",\"imageSrc\":" + "\"" + document.getElementById("show-image").value + "\""
        + ",\"productDetails\":" + "\"" + document.getElementById("product-details").value + "\""
        + "}";
    let data = JSON.parse(info);
    $('#confirm').on('click',sendDataNormal(url,data));
    document.getElementById("confirm").type = "hidden";//隐藏确认按钮
    document.getElementById("window-close").addEventListener('click',reset);
    document.getElementById("confirm").removeEventListener('click',submitEdit);//执行完函数移除事件
}
/*提交编辑end*/
/*弹框*/
function changeBtn(obj) {
    obj.style.backgroundColor = "#c9ffd1";
}
function initBtn(obj) {
    obj.style.backgroundColor = "";
}
//清空页面
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
    redirect();
}
//返回上一页
function redirect() {
    document.getElementById("back-link").innerText = "返回商品列表";
}
/*弹框end*/