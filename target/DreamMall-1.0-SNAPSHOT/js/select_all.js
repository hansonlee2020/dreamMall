/*公共js资源文件，用于全选业务处理*/

/*全选start*/
let ids = new Array();//保存所有选中的商品id
function selectAll(flagElementId,valueElementId) {
    //flagElementId：全选框id,valueElementId：ids保存所在元素的id
    let flag = document.getElementById(flagElementId).checked;//全选标记
    let inputElements = document.getElementsByTagName("input");
    ids.length = 0;//先清空数组
    for (let i = 0; i < inputElements.length; i ++){
        if (inputElements[i].type == "checkbox" && inputElements[i].id != flagElementId){
            inputElements[i].checked = flag;
            if (flag){
                ids.push(inputElements[i].value);//勾选全选，将id保存到数组
            }else {
                ids.length = 0;//取消全选，删除保存在数组的id
            }
        }
    }
    document.getElementById(valueElementId).value = ids;//把数组保存到一个id为'ids-to-do'的input的value里,方便把数据递给后台
}
/*单选、逐选*/
function selectByOne(obj,valueElementId) {
    //在Array原型上添加方法来删除元素
    Array.prototype.indexOf = function(val) {//获取元素下标
        for (let i = 0; i < this.length; i++) {
            if (this[i] == val) return i;
        }
        return -1;
    };
    Array.prototype.remove = function(val) {//移除元素
        let index = this.indexOf(val);
        if (index > -1) {
            this.splice(index, 1);
        }
    };
    let doId;//用于保存批量操作的id数组
    if (obj.checked){//勾选
        if (ids.indexOf(obj.value) < 0){//数组里没有该元素
            ids.push(obj.value);
        }
        //数组含有该元素，不进行任何操作
    }else {//取消勾选
        ids.remove(obj.value);//调用数组元素删除方法
    }
    doId = ids ;//保存全部要批量操作的id
    document.getElementById(valueElementId).value = doId;//将要操作的数据id保存到隐藏的input的value里，用于提交给后台
}
/*全选框初始化*/
function initCheckbox(elementId) {
    document.getElementById(elementId).checked = false;
    ids.length = 0;//清空数组缓存
}
/*全选end*/
