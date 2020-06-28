/*公共js资源文件，编辑业务处理逻辑*/

//为元素增添属性
function createAttribute(element,targetValue) {//element：要设置属性的元素
    element.setAttribute("data-toggle","collapse");
    element.setAttribute("data-target","#" + targetValue);
    element.setAttribute("aria-expanded","false");
    element.setAttribute("aria-controls",targetValue);
}
//移除元素属性
function removeAttribute(value) {//value：可以定位元素的title属性的值
    let linkElements = document.getElementsByTagName("a");
    for (let i = 0 ; i < linkElements.length ; i ++){
        if (linkElements[i].title == value){
            linkElements[i].removeAttribute("data-toggle");
            linkElements[i].removeAttribute("data-target");
            linkElements[i].removeAttribute("aria-expanded");
            linkElements[i].removeAttribute("aria-controls");
        }
    }
}