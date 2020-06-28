package com.hanson.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hanson.dto.GoodsTable;
import com.hanson.dto.Message;
import com.hanson.dto.PageBean;
import com.hanson.pojo.ProductCategory;
import com.hanson.service.impl.ProductCategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: DreamMall
 * @description: 分类信息，处理分类信息业务。可以处理分类信息的查看、搜索、新增、更改、删除等业务
 * @param:
 * @author: Hanson
 * @create: 2020-04-17 15:44
 **/
@Controller
@RequestMapping("/category")
public class CategoryController {

    private ProductCategoryServiceImpl categoryService;
    @Autowired
    public void setCategoryService(ProductCategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }

    //跳转到分类列表页面
    @RequestMapping("/toList")
    public String toCategoryList(){
        return "category_list";
    }

    //接收商品分页查询请求，返回商品分页查询结果
    @RequestMapping("/list")
    @ResponseBody
    public JSONObject categorySplit(@RequestParam("pageSize") Integer pageSize, @RequestParam("currentPage") Integer currentPage){
        PageBean<ProductCategory> categorySplit = categoryService.getCategorySplit(pageSize, currentPage);
        JSONObject json;
        if (categorySplit != null){
            json =(JSONObject) JSON.toJSON(categorySplit);
        }else {
            Message message = new Message("系统错误", "分页查询失败！", "error");
            json = (JSONObject) JSON.toJSON(message);
        }
        return json;
    }
    //分类删除
    @RequestMapping("/delete")
    @ResponseBody
    public JSONObject categoryDelete(@RequestParam("deleteId") Integer id){
        //问题：service层应在删除分类前检查商品是否正在使用或者捕获异常处理，分类正使用返回删除失败具体错误信息
        Message message = categoryService.deleteCategoryById(id);
        JSONObject json;
        json = (JSONObject) JSON.toJSON(message);
        return json;
    }

    //新增分类
    @RequestMapping("/increase")
    @ResponseBody
    public JSONObject categoryIncrease(ProductCategory category){
//        Message message = new Message("测试消息", "测试成功", "success");//test
        Message message = categoryService.createCategory(category);
        JSONObject json;
        json = (JSONObject) JSON.toJSON(message);
        return json;
    }
    /*编辑分类start*/
        //获取要编辑的分类信息
    @RequestMapping("/query")
    @ResponseBody
    public JSONObject getCategoryById(@RequestParam("categoryId")Integer cid){
        ProductCategory category = categoryService.getCategoryById(cid);
        JSONObject json;
        json = (JSONObject) JSON.toJSON(category);
        return json;
    }
        //编辑
    @RequestMapping("/update")
    @ResponseBody
    public JSONObject updateCategory(ProductCategory category){
//        Message message = new Message("测试消息", "测试成功", "success");//test
        Message message = categoryService.updateCategory(category);
        JSONObject json;
        json = (JSONObject) JSON.toJSON(message);
        return json;
    }
    /*编辑分类end*/

    //分页模糊查询分类信息
    @RequestMapping("/search")
    @ResponseBody
    public JSONObject categorySearch(@RequestParam("pageSize") Integer pageSize, @RequestParam("currentPage") Integer currentPage,@RequestParam("searchKey")String key){
        PageBean<ProductCategory> pageBean = categoryService.searchCategory(pageSize, currentPage, key);
        return (JSONObject) JSON.toJSON(pageBean);
    }
}
