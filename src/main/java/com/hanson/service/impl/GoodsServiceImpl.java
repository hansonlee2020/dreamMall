package com.hanson.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hanson.dao.GoodsMapper;
import com.hanson.dao.ProductCategoryMapper;
import com.hanson.dto.GoodsEditTable;
import com.hanson.dto.GoodsTable;
import com.hanson.dto.Message;
import com.hanson.dto.PageBean;
import com.hanson.pojo.Goods;
import com.hanson.pojo.ProductCategory;
import com.hanson.service.GoodsService;
import com.hanson.util.HandleStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @program: DreamMall
 * @description: 商品实现类
 * @param:
 * @author: Hanson
 * @create: 2020-03-27 19:30
 **/
@Service
public class GoodsServiceImpl implements GoodsService {
    private GoodsMapper goodsMapper;
    @Autowired
    public void setGoodsMapper(GoodsMapper goodsMapper) {
        this.goodsMapper = goodsMapper;
    }

    private ProductCategoryMapper productCategoryMapper;
    @Autowired
    public void setProductCategoryMapper(ProductCategoryMapper productCategoryMapper) {
        this.productCategoryMapper = productCategoryMapper;
    }

    @Override
    public List<Goods> getGoods() {
        return goodsMapper.doGetGoods();
    }

    @Override
    public Message addGoods(String data) {
        String price = HandleStringUtils.fetchValue(data, "price", false);
        String stock = HandleStringUtils.fetchValue(data, "stock", false);
        String limitNum = HandleStringUtils.fetchValue(data, "limitNum", false);
        if (!checkNum(price)){
            return new Message("系统消息","价格输入有误，请输入正确的整数或小数！","error");
        }
        if (!checkInteger(stock)){
            return new Message("系统消息","库存输入有误，请输入正确的整数！","error");
        }
        if (!checkInteger(limitNum)){
            return new Message("系统消息","限购数量输入有误，请输入正确的整数！","error");
        }
        String value = HandleStringUtils.fetchValue(data, "thirdCategory",true);//获取前端递进来的数据的三级分类名
        String categoryId = productCategoryMapper.doGetCategory(value).getCategoryId().toString();//查询对应的分类id
        String format = HandleStringUtils.formatString(data, "categoryId", categoryId, 5,false);//替换分类数据
        UUID uuid = UUID.randomUUID();//生成商品id
        String id = HandleStringUtils.formatUUID(uuid);//格式id
        String goods = HandleStringUtils.increaseString(format, "productId", id);//添加商品id
        JSONObject dataNew = JSON.parseObject(goods);//转json对象
        Goods item = dataNew.toJavaObject(Goods.class);//转java对象
        Message message;//保存添加消息结果
        Integer result = goodsMapper.doAddGoods(item);//出现了无限添加的问题，需要进行内容相似度匹配或者当提交成功后清空原有内容
        if (result == 1){
            message = new Message("系统消息", "商品" + item.getProductName() + "添加成功！", "success");
        }else {
            message = new Message("系统消息", "商品" + item.getProductName() + "添加失败！", "error");
        }
        return message;
    }

    @Override
    public List<Goods> getGoodsByName(String name) {
        return goodsMapper.doGetGoodsByName(name);
    }

    @Override
    public GoodsEditTable getGoodsById(String id) {
        try {
            Goods goods = goodsMapper.doGetGoodsById(id);//获取商品信息
            Integer categoryId = goods.getCategoryId();//获取商品分类id
            ProductCategory productCategory = productCategoryMapper.doGetCategoryById(categoryId);//查询商品分类信息
            String firstName = productCategory.getCategoryFirstName();//获取各级分类名称
            String secondName = productCategory.getCategorySecondName();
            String thirdName = productCategory.getCategoryThirdName();
            String src = new String();
            if (goods.getImageSrc() == null){
                src = "";
            }else {
                src = goods.getImageSrc();
            }
            //商品封装为GoodsEditTable
            GoodsEditTable editTable = new GoodsEditTable(
                    goods.getProductId(),
                    goods.getProductName(),
                    goods.getBriefInfo(),
                    goods.getPrice(),
                    goods.getStock(),
                    goods.getLimitNum(),
                    firstName,
                    secondName,
                    thirdName,
                    goods.getReleaseState(),
                    src,
                    goods.getProductDetails()
            );
            return editTable;
        }catch (Exception e){
            System.out.println("error" + e);
            return new GoodsEditTable();
        }
    }

    @Override
    public PageBean<GoodsTable> getGoodsSplit(Integer pageSize, Integer currentPage) {
            Integer startIndex = (currentPage - 1) * pageSize;//分页查询开始索引
            Long total =(long) goodsMapper.doGetCounts();//总数据量
            Integer pages;
            if (total % pageSize != 0){
                pages = (int)(((double)total/pageSize) + 1);//总页数,有余数，+1再取整
            }else {
                pages = (int)(((double)total/pageSize));//总页数,没有余数，直接取整
            }
        try{
            List<Goods> goodsList = goodsMapper.doGetGoodsSplit(startIndex, pageSize);//分页查询结果集
            Integer size = goodsList.size();//当前页数据量
            //转换商品对象Goods为GoodsTable
            List<GoodsTable> table = new ArrayList<>();
            Iterator<Goods> iter = goodsList.iterator();
            String src;
            while (iter.hasNext()){
                Goods goods = iter.next();
                Integer categoryId = goods.getCategoryId();//获得商品分类id
                ProductCategory productCategory = productCategoryMapper.doGetCategoryById(categoryId);//根据id查询所在分类
                String thirdName = productCategory.getCategoryThirdName();//获取商品的三级分类名称
                if (goods.getImageSrc() == null){
                    src = "";
                }else {
                    src = goods.getImageSrc();
                }
                GoodsTable goodsTable = new GoodsTable(
                        goods.getProductId(),
                        goods.getProductName(),
                        goods.getBriefInfo(),
                        goods.getPrice(),
                        goods.getStock(),
                        goods.getLimitNum(),
                        thirdName,
                        goods.getReleaseState(),
                        src,
                        goods.getProductDetails()
                );
                table.add(goodsTable);
            }
            return new PageBean<>(total,currentPage,pageSize,pages,size,table);
        }catch (Exception e){
            System.out.println("error:" + e);
            return new PageBean<>();
        }
    }

    @Override
    public Message batchRemoveGoods(String ids) {
        Message message;
        Set<String> newIds = HandleStringUtils.formatToSet(ids);//处理字符串格式
        Integer num = goodsMapper.doBatchDeleteGoods(newIds);//执行批量删除
        if (num != 0){
            message = new Message("系统消息","删除商品成功，已删除 " + num + " 条数据！","success");
            return message;
        }
        message = new Message("系统消息","商品数据不存在，删除失败！","error");
        return message;
    }

    @Override
    public Message updateGoods(GoodsEditTable editTable) {
        try {
            Message message;
            String category3 = editTable.getCategory3();
            ProductCategory productCategory = productCategoryMapper.doGetCategory(category3);//获取商品分类信息
            //将GoodsEditTable转为Goods对象
            Goods goods = new Goods(
                    editTable.getProductId(),
                    editTable.getProductName(),
                    editTable.getBriefInfo(),
                    editTable.getPrice(),
                    editTable.getStock(),
                    editTable.getLimitNum(),
                    productCategory.getCategoryId(),
                    editTable.getReleaseState(),
                    editTable.getImageSrc(),
                    editTable.getProductDetails()
            );
            Integer num = goodsMapper.doUpdateGoods(goods);
            if (num == 0){
                message = new Message("错误信息","修改商品信息失败！","error");
            }else {
                message = new Message("系统信息","修改商品信息成功！","success");
            }
            return message;
        }catch (Exception e){
            System.err.println("error:" + e);
            return new Message("异常信息","系统处理异常","error");
        }
    }

    @Override
    public Message updateGoodsState(String id, Integer state) {
        try {
            Message message;
            Integer num = goodsMapper.doUpdateGoodsState(id, state);
            String content = new String();
            if (state == 0){
                content = "重新审核";
            }else if (state == 1){
                content = "发布";
            }else if (state == 2){
                content = "下架";
            }else {
                content = "操作";
            }
            if (num == 0){
                message = new Message("错误信息","商品" + content + "失败！","error");
            }else {
                message = new Message("系统信息","商品" + content + "成功！","success");
            }
            return message;
        }catch (Exception e){
            System.err.println("error:" + e);
            return new Message("异常信息","系统处理异常","error");
        }
    }

    @Override
    public PageBean<GoodsTable> getGoodsSplitWithSearch(Integer pageSize, Integer currentPage, String key) {
        String newKey;
        if ("null".equals(key)){
            newKey = "";
        }else {
            newKey = key.replaceAll(" ","");
        }
        Integer startIndex = (currentPage - 1) * pageSize;//分页查询开始索引
        Long total =(long) goodsMapper.doGetGoodsWithSearch(newKey).size();//模糊查询总数据量
        Integer pages;
        if (total % pageSize != 0){
            pages = (int)(((double)total/pageSize) + 1);//总页数,有余数，+1再取整
        }else {
            pages = (int)(((double)total/pageSize));//总页数,没有余数，直接取整
        }
        try{
            List<Goods> goodsList = goodsMapper.doGetGoodsSplitWithSearch(startIndex, pageSize,newKey);//分页查询结果集
            Integer size = goodsList.size();//当前页数据量
            //转换商品对象Goods为GoodsTable
            List<GoodsTable> table = new ArrayList<>();
            Iterator<Goods> iter = goodsList.iterator();
            String src;
            while (iter.hasNext()){
                Goods goods = iter.next();
                Integer categoryId = goods.getCategoryId();//获得商品分类id
                ProductCategory productCategory = productCategoryMapper.doGetCategoryById(categoryId);//根据id查询所在分类
                String thirdName = productCategory.getCategoryThirdName();//获取商品的三级分类名称
                if (goods.getImageSrc() == null){
                    src = "";
                }else {
                    src = goods.getImageSrc();
                }
                GoodsTable goodsTable = new GoodsTable(
                        goods.getProductId(),
                        goods.getProductName(),
                        goods.getBriefInfo(),
                        goods.getPrice(),
                        goods.getStock(),
                        goods.getLimitNum(),
                        thirdName,
                        goods.getReleaseState(),
                        src,
                        goods.getProductDetails()
                );
                table.add(goodsTable);
            }
            return new PageBean<>(total,currentPage,pageSize,pages,size,table);
        }catch (Exception e){
            System.out.println("error:" + e);
            return new PageBean<>((long)0,1,0,0,0,new ArrayList<GoodsTable>());
        }
    }


    /*
    * @description: 验证是否整数或小数
    * @params: [s] 需要验证的字符串
    * @return: boolean
    * @Date: 2020/5/9
    */
    public boolean checkNum(String s){
        String regex = "\\d+(.\\d+)?" ;
        if (s.matches(regex)){
            if (s.length() == 1){
                return true;
            }
            if ("0".equals(s.substring(0,1))){
                if (s.contains(".")){
                    if (s.indexOf(".") > 1){
                        return false;
                    }
                    return s.matches(regex);
                }
                return false;
            }
            return s.matches(regex);
        }else {
            return false;
        }
    }

    public boolean checkInteger(String s){
        String regex = "\\d+";
        if (s.matches(regex)){
            if (s.length() == 1){
                return true;
            }
            if ("0".equals(s.substring(0,1))){
                return false;
            }
            return true;
        }
        return false;
    }
}
