package com.hanson.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hanson.dto.GoodsEditTable;
import com.hanson.dto.GoodsTable;
import com.hanson.dto.Message;
import com.hanson.dto.PageBean;
import com.hanson.service.impl.GoodsServiceImpl;
import com.hanson.service.impl.ProductCategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

/**
 * @program: DreamMall
 * @description: 处理商品业务,负责接收前端的商品请求，实现数据操作，返回对应商品数据
 * 可以处理商品信息的查看、检索、新增、修改、删除等业务
 * @param:
 * @author: Hanson
 * @create: 2020-03-27 19:32
 **/
@Controller
@RequestMapping("/product/goods")
public class GoodsController {
    //注入商品service层接口实现类对象
    private GoodsServiceImpl goodsService;
    @Autowired
    public void setGoodsService(GoodsServiceImpl goodsService) {
        this.goodsService = goodsService;
    }
    //注入分类service层接口实现类对象
    private ProductCategoryServiceImpl categoryService;
    @Autowired
    public void setCategoryService(ProductCategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }


    /*添加商品Start*/
    //自动跳转到添加商品页面
    @GetMapping("/toAdd")
    public String toAddProductPage(){
        return "addProduct";
    }

    //接收前端添加商品信息请求，返回添加处理结果
    @RequestMapping("/add/form/{data}")
    @ResponseBody//不走视图
    public JSONObject addGoods(@PathVariable("data") String data){
        /* 这里暂时没有好的处理方法，先通过按钮触发异步请求处理，
        而不是通过submit提交表单跳转，因为会405错误：请求行中接收的方法由源服务器知道，但目标资源不支持*/
        Message message = goodsService.addGoods(data);
        JSONObject jsonObject;//返回消息结果给前端
        jsonObject = (JSONObject) JSON.toJSON(message);
        return jsonObject;
    }

    //接收前端分类请求，返回一二级分类信息，为json数组对象，不走视图解析器
    @RequestMapping("/category/{choice}")
    @ResponseBody
    public JSONArray getCategoriesFirstName(@PathVariable("choice") String choice){
        JSONArray jsonArray = new JSONArray();
        List<String> categories;
        if(choice == null){
            return null;
        }
        if("1".equals(choice)){//一级分类请求
            categories = categoryService.getCategoriesFirstName();
        }else if(!choice.contains("3")) {//二级分类请求
            categories = categoryService.getCategoriesSecondName(choice);
        }else {//三级分类请求
            String realChoice = choice.substring(0, choice.length() - 1);
            categories = categoryService.getCategoriesThirdName(realChoice);
        }
        Iterator<String> iterator = categories.iterator();
        while (iterator.hasNext()){
            String name = iterator.next();
            jsonArray.add(name);
        }
        return jsonArray;   //返回json数据对象
    }
    /*添加商品end*/


    /*商品列表start*/
    //跳转到商品列表页面
    @GetMapping("/toList")
    public String toListProductPage(){
        return "listProduct";
    }

    //接收商品分页查询请求，返回商品分页查询结果
    @RequestMapping("/list")
    @ResponseBody
    public JSONObject goodsSplit(@RequestParam("pageSize") Integer pageSize, @RequestParam("currentPage") Integer currentPage){
        PageBean<GoodsTable> goodsSplit = goodsService.getGoodsSplit(pageSize, currentPage);
        JSONObject json;
        if (goodsSplit != null){
            json =(JSONObject) JSON.toJSON(goodsSplit);
        }else {
            Message message = new Message("系统错误", "分页查询失败！", "error");
            json = (JSONObject) JSON.toJSON(message);
        }
        return json;
    }

    //批量删除商品
    @RequestMapping("/delete")
    @ResponseBody
    public JSONObject goodsDelete(@RequestParam("deleteIds") String ids){
        System.out.println(ids);
        Message message = goodsService.batchRemoveGoods(ids);
        JSONObject json;
        json = (JSONObject) JSON.toJSON(message);
        return json;
/*        Message message = new Message("测试信息", "111111111111", "success");
        JSONObject json;
        json = (JSONObject) JSON.toJSON(message);
        return json;*/
    }

    //单商品删除
    @RequestMapping("/delete1")
    @ResponseBody
    public JSONObject goodsDelete1(@RequestParam("deleteId") String id){
        Message message = goodsService.batchRemoveGoods(id);
        JSONObject json;
        json = (JSONObject) JSON.toJSON(message);
        return json;
    }
    /*商品列表end*/

    /*商品编辑start*/
    //跳转到编辑页面
    @RequestMapping("/toEdit/{id}")
    public String toGoodsEdit(@PathVariable("id") String id , Model model){
        GoodsEditTable goods = goodsService.getGoodsById(id);
        model.addAttribute("goods",goods);
        return "editProduct";
    }
    //修改商品信息
    @RequestMapping("/edit")
    @ResponseBody
    public JSONObject goodsEdit(GoodsEditTable editTable){
        Message message = goodsService.updateGoods(editTable);
        JSONObject json;
        json = (JSONObject) JSON.toJSON(message);
        return json;
    }
    /*商品编辑end*/

    /*商品发布start*/
    @RequestMapping("/release")
    @ResponseBody
    public JSONObject goodsRelease(@RequestParam("productId")String id ,@RequestParam("state")Integer state){
        Message message = goodsService.updateGoodsState(id, state);
        JSONObject json;
        json = (JSONObject) JSON.toJSON(message);
        return json;
    }
    /*商品发布end*/

    /*商品下架处理start*/
    @RequestMapping("/off")
    @ResponseBody
    public JSONObject goodsOffShelf(@RequestParam("productId")String id ,@RequestParam("state")Integer state){
        Message message = goodsService.updateGoodsState(id, state);
        JSONObject json;
        json = (JSONObject) JSON.toJSON(message);
        return json;
    }
    /*商品下架处理end*/

    /*商品重新审核start*/
    @RequestMapping("/examine")
    @ResponseBody
    public JSONObject goodsExamine(@RequestParam("productId")String id ,@RequestParam("state")Integer state){
        Message message = goodsService.updateGoodsState(id, state);
        JSONObject json;
        json = (JSONObject) JSON.toJSON(message);
        return json;
    }
    /*商品重新审核end*/
    /*商品搜索start*/
    @RequestMapping("search")
    @ResponseBody
    public JSONObject goodsSplitWithSearch(@RequestParam("pageSize") Integer pageSize,
                                           @RequestParam("currentPage") Integer currentPage,
                                           @RequestParam("searchKey") String key){
        return (JSONObject) JSON.toJSON(goodsService.getGoodsSplitWithSearch(pageSize, currentPage, key));
    }
    /*商品搜索end*/
}
