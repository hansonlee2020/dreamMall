package com.hanson.service.impl;

import com.hanson.dao.GoodsMapper;
import com.hanson.dao.ProductCategoryMapper;
import com.hanson.dto.Message;
import com.hanson.dto.PageBean;
import com.hanson.pojo.Goods;
import com.hanson.pojo.ProductCategory;
import com.hanson.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/**
 * @program: DreamMall
 * @description: 商品分类信息的service层接口实现类
 * @param:
 * @author: Hanson
 * @create: 2020-04-04 10:06
 **/
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    private ProductCategoryMapper productCategoryMapper;
    @Autowired
    public void setProductCategoryMapper(ProductCategoryMapper productCategoryMapper) {
        this.productCategoryMapper = productCategoryMapper;
    }
    private GoodsMapper goodsMapper;
    @Autowired
    public void setGoodsMapper(GoodsMapper goodsMapper) {
        this.goodsMapper = goodsMapper;
    }

    @Override
    public List<ProductCategory> getCategoriesByName(String firstCategory) {
        return productCategoryMapper.doGetCategoriesByName(firstCategory.replaceAll(":","\\/"));
    }

    @Override
    public List<ProductCategory> getAllCategories() {
        return productCategoryMapper.doGetAllCategories();
    }

    @Override
    public List<String> getCategoriesFirstName() {
        return productCategoryMapper.doGetCategoriesFirstName();
    }

    @Override
    public List<String> getCategoriesSecondName(String firstCategory) {
        return productCategoryMapper.doGetCategoriesSecondName(firstCategory.replaceAll(":","\\/"));
    }

    @Override
    public List<String> getCategoriesThirdName(String secondCategory) {
        return productCategoryMapper.doGetCategoriesThirdName(secondCategory.replaceAll(":","\\/"));
    }

    @Override
    public ProductCategory getCategory(String thirdName) {
        return productCategoryMapper.doGetCategory(thirdName.replaceAll(":","\\/"));
    }

    @Override
    public ProductCategory getCategoryById(Integer id) {
        return productCategoryMapper.doGetCategoryById(id);
    }

    @Override
    public PageBean<ProductCategory> getCategorySplit(Integer pageSize, Integer currentPage) {
        try{
            Integer startIndex = (currentPage - 1) * pageSize;//分页查询开始索引
            Long total =(long) productCategoryMapper.doCounts();//总数据量
            Integer pages;
            if (total%pageSize != 0){
                pages = (int)(((double)total/pageSize) + 1);//总页数,有余数，+1再取整
            }else {
                pages = (int)(((double)total/pageSize));//总页数,没有余数，直接取整
            }
            List<ProductCategory> categoryList = productCategoryMapper.doGetCategorySplit(startIndex, pageSize);//分页查询结果集
            Integer size = categoryList.size();//当前页数据量
            PageBean<ProductCategory> pageBean = new PageBean<>(total,currentPage,pageSize,pages,size,categoryList);
            return pageBean;
        }catch (Exception e){
            System.err.println("error:" + e);
            return new PageBean<>();
        }
    }

    @Override
    public Message deleteCategoryById(Integer id){
        try {
            Integer result = productCategoryMapper.doDeleteCategory(id);
            Message message;
            if (result == 0){
                message = new Message("错误信息","删除分类信息失败！","error");
            }else {
                message = new Message("系统信息","删除分类信息成功！","success");
            }
            return message;
        }catch (Exception e){
            System.err.println("error:" + e);
            //待写异常处理
            List<Goods> goodsList = goodsMapper.doGetGoodsByCategoryId(id);
            int size = goodsList.size();//使用该分类的商品总数
            String text = "目前使用该分类的有" + size + "个商品，不能进行删除该分类操作！";
            return new Message("警告",text,"warnming");
        }
    }

    @Override
    public Message createCategory(ProductCategory productCategory) {
        Message message;//操作返回的信息结果
        ProductCategory newCategory;//保存前端递来的分类信息数据处理后的结果
        if (productCategory.getCategoryId() == null | productCategory.getCategoryFirstName() == null |
                productCategory.getCategorySecondName() == null | productCategory.getCategoryThirdName() == null){
            message = new Message("系统信息","新增分类失败，分类内容有空字段，请检查！","error");
            return message;
        }else {
            newCategory = new ProductCategory(
                    productCategory.getCategoryId(),
                    productCategory.getCategoryFirstName().replaceAll(" " ,""),
                    productCategory.getCategorySecondName().replaceAll(" " ,""),
                    productCategory.getCategoryThirdName().replaceAll(" " ,"")
            );
        }
        try {
            Integer result = productCategoryMapper.doCreateCategory(newCategory);
            if (result == 1){
                message = new Message("系统信息","新增分类信息成功！","success");
            }else {
                message = new Message("警告","未知错误！","warnming");
            }
            return message;
        }catch (Exception e){
            System.err.println("error:" + e);
            Message warnming = new Message();
            ProductCategory category = productCategoryMapper.doGetCategoryById(newCategory.getCategoryId());
            List<String> nameList = productCategoryMapper.doGetCategoryThirdName();
            if (category != null){
                warnming = new Message("警告","新增分类失败，分类id：" + productCategory.getCategoryId() + "已存在！","warnming");
            }else {
                Iterator<String> iter = nameList.iterator();
                while (iter.hasNext()){
                    String next = iter.next();
                    String thirdName = newCategory.getCategoryThirdName();
                    if (thirdName.equals(next)){
                        warnming = new Message("警告","新增分类失败，三级分类名称：" + thirdName + "已存在！","warnming");
                    }
                }
            }
            return warnming;
        }
    }

    @Override
    public Message updateCategory(ProductCategory productCategory) {
        Message message;//操作返回的信息结果
        ProductCategory newCategory;//保存前端递来的分类信息数据处理后的结果
        if (productCategory.getCategoryId() == null | productCategory.getCategoryFirstName() == null |
                productCategory.getCategorySecondName() == null | productCategory.getCategoryThirdName() == null){
            message = new Message("系统信息","修改分类失败，分类内容有空字段，请检查！","error");
            return message;
        }else {
            newCategory = new ProductCategory(
                    productCategory.getCategoryId(),
                    productCategory.getCategoryFirstName().replaceAll(" ", ""),
                    productCategory.getCategorySecondName().replaceAll(" ", ""),
                    productCategory.getCategoryThirdName().replaceAll(" ", "")
            );
        }
        try {
            Integer result = productCategoryMapper.doUpdateCategory(newCategory);
            if (result == 1){
                message = new Message("系统信息","修改分类信息成功！","success");
            }else {
                message = new Message("警告","修改失败，未找到分类id:" + newCategory.getCategoryId() + "！","warnming");
            }
            return message;
        }catch (Exception e){
            System.err.println("error:" + e);
            Message warnming = new Message();
            ProductCategory category = productCategoryMapper.doGetCategoryById(newCategory.getCategoryId());
            List<String> nameList = productCategoryMapper.doGetCategoryThirdName();
            Iterator<String> iter = nameList.iterator();
            while (iter.hasNext()){
                String next = iter.next();
                String thirdName = newCategory.getCategoryThirdName();
                if (thirdName.equals(next)){
                    warnming = new Message("警告","修改分类失败，三级分类名称：" + thirdName + "已存在！","warnming");
                }
            }
            return warnming;
        }
    }

    @Override
    public PageBean<ProductCategory> searchCategory(Integer pageSize, Integer currentPage, String key) {
        try{
            Integer startIndex = (currentPage - 1) * pageSize;//分页查询开始索引
            Long total =(long) productCategoryMapper.doSearchCategory(key).size();//总数据量
            Integer pages;
            if (total%pageSize != 0){
                pages = (int)(((double)total/pageSize) + 1);//总页数,有余数，+1再取整
            }else {
                pages = (int)(((double)total/pageSize));//总页数,没有余数，直接取整
            }
            List<ProductCategory> categoryList = productCategoryMapper.doSearchCategorySplit(startIndex, pageSize,key);//分页查询结果集
            Integer size = categoryList.size();//当前页数据量
            PageBean<ProductCategory> pageBean = new PageBean<>(total,currentPage,pageSize,pages,size,categoryList);
            return pageBean;
        }catch (Exception e){
            System.err.println("error:" + e);
            return new PageBean<>();
        }
    }
}
