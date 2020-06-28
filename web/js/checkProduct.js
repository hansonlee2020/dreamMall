/*表单内容合法性验证start*/
//验证名字是否符合长度标准
function nameTips() {
    let name = document.getElementById("product-name").value;
    let msg = document.getElementById("name-msg");
    let len = name.length;
    if(len <= 50 && len > 0){
        msg.innerText = "还可以输入"  +(50-len) + "个字";
        msg.style.color = "#999999";
        document.getElementById("put").disabled = false;
    }else if(len > 50) {
        msg.innerText = "名字过长！";
        msg.style.color = "red";
        document.getElementById("put").disabled = true;
    }else {
        msg.innerText = "可输入1~50个中文字符";
        document.getElementById("put").disabled = true;
    }
}
function tips() {   //验证简介内容是否符合标准
    let text = document.getElementById("brief-info").value;
    let msg = document.getElementById("info-msg");
    let len = text.length;
    if(len <= 50 && len > 0){
        msg.innerText = "还可以输入"  +(50-len) + "个字";
        msg.style.color = "#999999";
        document.getElementById("put").disabled = false;
    }else if(len > 50) {
        msg.innerText = "输入内容过大！";
        msg.style.color = "red";
        document.getElementById("put").disabled = true;
    }else {
        msg.innerText = "最多可输入50个字";
        document.getElementById("put").disabled = true;
    }
}
function note() { //验证详情内容是否符合标准
    let details = document.getElementById("product-details").value;
    let detailsMsg = document.getElementById("details-msg");
    let length = details.length;
    if(length <= 200 && length >0){
        detailsMsg.innerText = "还可以输入"  + (200-length) + "个字";
        detailsMsg.style.color = "#999999";
        document.getElementById("put").disabled = false;
    }else if (length > 200) {
        detailsMsg.innerText = "输入内容过大！";
        detailsMsg.style.color = "red";
        document.getElementById("put").disabled = true;
    }else {
        detailsMsg.innerText = "最多可输入200个字";
        document.getElementById("put").disabled = true;
    }
}
function verifyNumber(element) {
    //验证数字
    let regex = /^\d+$/
    let obj = document.getElementById(element);
    let msg = document.getElementById(element + "-msg");
    if(obj.value.length != 0){//有内容
        if(regex.test(obj.value)){//验证通过
            if(obj.value > 9999999999999){
                msg.innerText = "数字过大！";
                msg.style.color = "red";
                document.getElementById("put").disabled = true;
                return false;
            }else {
                msg.innerHTML = "<strong>√</strong>";
                msg.style.color = "lightgreen";
                document.getElementById("put").disabled = false;
                return true;
            }
        }else {//验证不通过
            msg.innerText = "请确认输入的是数字且符合要求！"
            msg.style.color = "red";
            document.getElementById("put").disabled = true;
            return false;
        }
    }else {
        msg.innerText = "最大可设置为：9,999,999,999,999";
        msg.style.color = "#999999";
        document.getElementById("put").disabled = true;
        return false;
    }
}
function verifyPrice() {
    let obj = document.getElementById("price");
    let msg = document.getElementById("price-msg");
    let regex = /^\d+(\.\d{1,2})?$/
    if(obj.value.length != 0){  //有内容
        if(regex.test(obj.value)){  //验证通过
            if(obj.value > 9999999999999.99){ //数字超出限制
                msg.innerText = "输入数字过大！";
                msg.style.color = "red";
                document.getElementById("put").disabled = true;
                return false;
            }else {
                msg.innerHTML = "<strong>√</strong>";
                msg.style.color = "lightgreen";
                document.getElementById("put").disabled = false;
                return true;
            }
        }else {//验证不通过
            msg.innerText = "请确认输入的是数字且符合要求！"
            msg.style.color = "red";
            document.getElementById("put").disabled = true;
            return false;
        }
    }else {
        msg.innerText = "最大可设置为：9,999,999,999,999.99";
        msg.style.color = "#999999";
        document.getElementById("put").disabled = true;
        return false;
    }
}
function verifyStock() {
    return verifyNumber("stock")
}
function verifyLimitNum() {
    return verifyNumber("limit-num")
}
function verifyImage() {//验证图片格式
    //暂时没实现该功能
}
function isEmpty() {
    let name = document.getElementById("product-name").value;
    let info = document.getElementById("brief-info").value;
    let price = document.getElementById("price").value;
    let stock = document.getElementById("stock").value;
    let limitNum = document.getElementById("limit-num").value;
    let category = document.getElementById("low-level-category").value;
    let details = document.getElementById("product-details").value;
    if(name.length == 0|info.length == 0|price.length == 0|
        stock.length == 0|limitNum.length == 0|category.length == 0|details.length == 0){
        // alert("必要字段不能为空，请检查输入！")
        return true;
    }else {
        return false;
    }
}
function verifyForm() {//验证整个表单
    return  !isEmpty() &&
        verifyPrice() &&
        verifyStock() &&
        verifyLimitNum();
    // verifyImage();
}
function selected() {//是否勾选立即发布
    let element = document.getElementById("release-checkbox");
    if(element.checked){
        document.getElementById("release").value = element.value;
    }else {
        document.getElementById("release").value = 0;
    }
}
/*表单内容合法性验证end*/