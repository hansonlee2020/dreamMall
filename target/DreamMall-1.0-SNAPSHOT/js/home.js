/*本文件是系统首页的js文件*/

/*页面加载时绑定事件*/
window.onload=function () {
    addListener();
    //点击折叠时运行该函数
   open_closeLabel();
}
//绑定事件
function addListener() {
    //绑定鼠标悬停事件，用于改变样式
    let elements = document.getElementsByClassName("style-col");
    for (let i = 0 ;i < elements.length ; i++){
        elements[i].addEventListener("mouseover",changeStyle);
        elements[i].addEventListener("mouseout",initStyle);
    }
    //绑定打开关闭标签事件,可以控制样式
    let labelElements = document.getElementsByClassName("menu-items");
    // for (let i = 0 ;i < labelElements.length ; i++){
    //     labelElements[i].addEventListener("click",openLabel);
    // }
}
//鼠标悬停
function changeStyle() {
    this.style.color = "dodgerblue";
}
//鼠标移除
function initStyle() {
    this.style.color = "black";
}
//打开标签和折叠标签
function open_closeLabel() {
    $('.panel').on('hide.bs.collapse show.bs.collapse', function (e) {
        let $this = $(this)
        $this.find("a").find("span").toggleClass("glyphicon-chevron-down");
        $this.find("a").find("span").toggleClass("glyphicon-chevron-up");
    });
}

