package com.hanson.dao;

import com.hanson.pojo.ProductCategory;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @program: DreamMall
 * @description: 商品分类dao层接口，进行商品分类级别节点的添加修改删除操作
 * @param:
 * @author: Hanson
 * @create: 2020-04-04 09:42
 **/
@Repository
public interface ProductCategoryMapper {
    /*
    * @description: 根据一级分类名称查询全部一级分类下的所有分类信息
     * @params: [fistCategory] 一级分类名称
    * @return: java.util.List<com.hanson.pojo.ProductCategory> 所有该一级分类下的分类信息，返回为ProductCategory对象的list集合
    * @Date: 2020/4/4
    */
    public List<ProductCategory> doGetCategoriesByName(@Param("firstName") String fistCategory);


    /*
    * @description: 查询全部分类信息
    * @params: []
    * @return: java.util.List<com.hanson.pojo.ProductCategory> 返回所有分类信息
    * @Date: 2020/4/4
    */
    public List<ProductCategory> doGetAllCategories();


    /*
    * @description: 查询所有一级分类名称
    * @params: []
    * @return: java.util.List<java.lang.String> 所有一级分类名称，返回为String类型的list集合
    * @Date: 2020/4/4
    */
    public List<String> doGetCategoriesFirstName();


    /*
    * @description: 根据一级分类名称查询对应的所有二级分类名称
    * @params: [firstCategory] 一级分类名称
    * @return: java.util.List<java.lang.String> 所有二级分类名称，返回为String类型的list集合
    * @Date: 2020/4/4
    */
    public List<String> doGetCategoriesSecondName(@Param("firstName") String firstCategory);


    /*
    * @description: 根据二级分类名称查询对应的所有三级分类名称
    * @params: [secondCategory] 二级分类名称
    * @return: java.util.List<java.lang.String> 所有三级分类名称，返回为String类型放list集合
    * @Date: 2020/4/4
    */
    public List<String> doGetCategoriesThirdName(@Param("secondName") String secondCategory);


    /*
    * @description: 根据三级分类名称查询分类信息，主要用于查询该三级分类名称所在的分类id
    * @params: [thirdCategory] 三级分类名
    * @return: java.util.List<com.hanson.pojo.ProductCategory> 所在分类的分类信息
    * @Date: 2020/4/6
    */
    public ProductCategory doGetCategory(@Param("thirdName") String thirdCategory);


    /*
    * @description: 根据分类id查询分类信息
    * @params: [id] 要查询的分类id
    * @return: com.hanson.pojo.ProductCategory 所在分类信息
    * @Date: 2020/4/12
    */
    public ProductCategory doGetCategoryById(@Param("categoryId") Integer id);



    /*
    * @description: 获取所有三级分类名
    * @params: []
    * @return: java.lang.String 所有三级分类名
    * @Date: 2020/4/18
    */
    public List<String> doGetCategoryThirdName();




    /*
    * @description: 新增分类信息
    * @params: [category] 需要新增的分类信息
    * @return: java.lang.Integer 增加成功返回1，否则抛出异常信息
    * @throws: Exception SQL执行异常：违反主键唯一约束或者违反唯一约束异常
    * @Date: 2020/4/17
    */
    public Integer doCreateCategory(ProductCategory category)throws Exception;



    /*
    * @description: 根据分类id删除分类信息
    * @params: [id] 需要删除的分类的分类id
    * @return: java.lang.Integer 删除成功返回1，否则抛出异常信息
    * @throws: Exception SQL执行异常：外键被使用异常
    * @Date: 2020/4/17
    */
    public Integer doDeleteCategory(@Param("categoryId") Integer id)throws Exception;



    /*
    * @description: 修改分类信息
    * @params: [category] 需要修改的分类
    * @return: java.lang.Integer 修改成功返回1，否则返回0或抛出异常信息
    * @throws: Exception SQL执行异常：违反主键唯一约束或者违反唯一约束异常
    * @Date: 2020/4/17
    */
    public Integer doUpdateCategory(ProductCategory category)throws Exception;


    /*
    * @description: 统计分类信息数据量
    * @params: []
    * @return: java.lang.Integer 返回统计结果
    * @Date: 2020/4/17
    */
    public Integer doCounts();


    /*
    * @description: 分页查询所有分类
    * @params: [index, pageSize] index:开始索引，pageSize:页大小
    * @return: java.util.List<com.hanson.pojo.ProductCategory> 查询到的分类对象集合list
    * @Date: 2020/4/17
    */
    public List<ProductCategory> doGetCategorySplit(@Param("startIndex") Integer index,@Param("pageSize") Integer pageSize);



    /*
    * @description: 分页模糊查询分类信息
    * @params: [index, pageSize, key] index：开始索引, pageSize：分页大小, key：关键字
    * @return: java.util.List<com.hanson.pojo.ProductCategory>
    * @Date: 2020/5/12
    */
    public List<ProductCategory> doSearchCategorySplit(@Param("startIndex")Integer index,@Param("pageSize")Integer pageSize,@Param("searchKey")String key);



    /*
    * @description: 模糊查询分类信息
    * @params: [key] key：搜索关键字
    * @return: java.util.List<com.hanson.pojo.ProductCategory>
    * @Date: 2020/5/12
    */
    public List<ProductCategory> doSearchCategory(@Param("searchKey")String key);
}
