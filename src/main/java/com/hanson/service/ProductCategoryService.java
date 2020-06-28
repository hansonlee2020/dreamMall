package com.hanson.service;

import com.hanson.dto.Message;
import com.hanson.dto.PageBean;
import com.hanson.pojo.ProductCategory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @program: DreamMall
 * @description: 商品分类信息service层接口
 * @param:
 * @author: Hanson
 * @create: 2020-04-04 09:59
 **/
@Service
public interface ProductCategoryService {
    /*
    * @description: 根据一级分类名称查询该分类下的所有分类信息
    * @params: [firstCategory] 一级分类名称
    * @return: java.util.List<com.hanson.pojo.ProductCategory> 返回该一级分类信息ProductCategory对象集合
    * @Date: 2020/4/4
    */
    public List<ProductCategory> getCategoriesByName(String firstCategory);


    /*
    * @description: 查询全部分类信息
    * @params: []
    * @return: java.util.List<com.hanson.pojo.ProductCategory> 所有分类信息 返回为ProductCategory对象集合
     * @throws: Exception
    * @Date: 2020/4/4
    */
    public List<ProductCategory> getAllCategories();


    /*
    * @description: 查询所有的一级分类名称
    * @params: []
    * @return: java.util.List<java.lang.String> 所有一级分类名称，返回为String类型的list集合
    * @Date: 2020/4/4
    */
    public List<String> getCategoriesFirstName();


    /*
    * @description: 根据一级分类名称查询对应的所有二级分类名称
    * @params: [firstCategory] 一级分类名称
    * @return: java.util.List<java.lang.String> 所有的二级分类名称，返回为list集合
    * @Date: 2020/4/4
    */
    public List<String> getCategoriesSecondName(String firstCategory);


    /*
    * @description: 根据二级分类名称查询对应的所有三级分类名称
    * @params: [secondCategory] 二级分类名称
    * @return: java.util.List<java.lang.String>  所有的三级分类名称，返回为list集合
    * @Date: 2020/4/4
    */
    public List<String> getCategoriesThirdName(String secondCategory);


    /*
    * @description: 根据三级分类名称查询所在的分类的分类信息
    * @params: [thirdName] 要查询的三级分类名称
    * @return: com.hanson.pojo.ProductCategory 所在分类的分类信息
    * @Date: 2020/4/6
    */
    public ProductCategory getCategory(String thirdName);


    /*
    * @description: 根据分类id查询分类信息
    * @params: [id] 要查询的分类id
    * @return: com.hanson.pojo.ProductCategory 所在分类信息
    * @Date: 2020/4/12
    */
    public ProductCategory getCategoryById(Integer id);


    /*
    * @description: 根据每页显示数量和当前页分页查询分类信息
    * @params: [pageSize, currentPage] pageSize：每页显示数量, currentPage：当前页数
    * @return: com.hanson.dto.PageBean<com.hanson.pojo.ProductCategory> 分类信息对象封装在list集合里并用PageBean包装
    * @Date: 2020/4/17
    */
    public PageBean<ProductCategory> getCategorySplit(Integer pageSize, Integer currentPage);



    /*
    * @description: 根据分类id删除分类信息
    * @params: [id] 要删除的分类id
    * @return: com.hanson.dto.Message 删除成功返回处理成功信息，否则返回异常信息，需要捕获异常
    * @Date: 2020/4/17
    */
    public Message deleteCategoryById(Integer id);



    /*
    * @description: 新增分类信息
    * @params: [productCategory]  要新增的分类
    * @return: com.hanson.dto.Message 新增成功返回处理成功信息，否则返回异常信息，需要捕获异常
    * @Date: 2020/4/18
    */
    public Message createCategory(ProductCategory productCategory);


    /*
    * @description: 根据分类id修改分类信息
    * @params: [productCategory] 要修改的分类
    * @return: com.hanson.dto.Message 修改成功返回处理成功信息，否则返回异常信息，需要捕获异常
    * @Date: 2020/4/18
    */
    public Message updateCategory(ProductCategory productCategory);



    /*
    * @description: 分页模糊查询分类信息
    * @params: [pageSize, currentPage, key] pageSize：分页大小, currentPage：当前页, key：搜索关键字
    * @return: com.hanson.dto.PageBean<com.hanson.pojo.ProductCategory>
    * @Date: 2020/5/12
    */
    public PageBean<ProductCategory> searchCategory(Integer pageSize, Integer currentPage,String key);
}
