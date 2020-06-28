/*公共js资源文件，用于发送分页请求*/

//分页函数
function pageSplit(url) {
    let pageSize = document.getElementById("page-select").value;
    $.ajax({ //去后台查询第一页数据
        type : "get",
        url : url,
        contentType :  'application/json;charset=UTF-8',
        dataType : "json",
        data : {pageSize : pageSize,currentPage : 1}, //参数：当前页为1
        success : function(result) {

            appendHtml(result.list)//处理第一页的数据
            let start;
            let end;
            if (result.total == 0){
                start = 0;
                end = 0;
            }else {
                start = (result.currentPage - 1) * result.pageSize + 1;
                end = start + result.size - 1;
            }
            tipsChange(start,end,result.total);//数据提示信息
            if (url == "/product/goods/list"){
                initRelease();//初始化发布状态
                initEditLink();//初始化链接
            }
            initCheckbox("select-all");

            let options = {//根据后台返回的分页相关信息，设置插件参数
                size:"small",//分页模型选择小
                bootstrapMajorVersion : 3, //如果是bootstrap3版本需要加此标识，并且设置包含分页内容的DOM元素为UL,如果是bootstrap2版本，则DOM包含元素是DIV
                currentPage : result.currentPage, //当前页数
                totalPages : result.totalPage, //总页数
                numberOfPages : result.size,//每页记录数
                onPageClicked : function(event, originalEvent, type, page) {//分页按钮点击事件
                    loading();//加载时提示样式
                    $.ajax({//根据page去后台加载数据
                        url : url,
                        type : "get",
                        dataType : "json",
                        data : {"currentPage" : page,"pageSize" : pageSize},
                        success : function(result) {
                            appendHtml(result.list);//处理数据
                            let start = (result.currentPage - 1) * result.pageSize + 1;
                            let end = start + result.size - 1;
                            tipsChange(start,end,result.total);
                            loaded();//恢复提示样式
                            initCheckbox("select-all");
                            if (url == "/product/goods/list"){
                                initRelease();//初始化发布状态
                                initEditLink();//初始化链接
                            }
                        }
                    });
                }
            };
            $('#product-split').bootstrapPaginator(options);//设置分页
        }
    });
}
//分页个性化定制
function loading() {//加载时样式
    document.getElementById("loading-data-tips").style.display = 'inline-block';
}
function loaded() {//加载完样式
    setTimeout(function () {
        document.getElementById("loading-data-tips").style.display = 'none';
    },200);
}
function tipsChange(startIndex,endIndex,total) {//统计信息提示
    let span = '';
    span += '<span id="data-tips" style="line-height: 15px;font-size: 15px">' + '显示&nbsp;&nbsp;' + startIndex + '&nbsp;&nbsp;到&nbsp;&nbsp;' +
        endIndex + '&nbsp;&nbsp;，共&nbsp;&nbsp;' + total + '&nbsp;&nbsp;条' + '</span>';
    $('#data-bottom-tips').html(span);
}
/*选择列表内容改变时执行start*/
function initSelect(method) {
    loading();
    eval(method());//重新请求分页
    loaded();
}
/*选择列表内容改变时执行end*/

