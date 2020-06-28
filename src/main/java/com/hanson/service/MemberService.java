package com.hanson.service;

import com.hanson.dto.MemberTable;
import com.hanson.dto.Message;
import com.hanson.dto.PageBean;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @program: DreamMall
 * @description: 用户service层接口
 * @param:
 * @author: Hanson
 * @create: 2020-04-21 13:55
 **/
@Service
public interface MemberService {

    /*
    * @description: 分页模糊查询用户
    * @params: [pageSize, currentPage, key] pageSize：分页大小, currentPage：当前页数, key：搜索关键字
    * @return: com.hanson.dto.PageBean<com.hanson.dto.MemberTable> 用户数据，封装为pageBean对象
    * @Date: 2020/4/21
    */
    public PageBean<MemberTable> splitMember(Integer pageSize, Integer currentPage, String key);


    /*
     * @description: 分页模糊查询回收站用户
     * @params: [pageSize, currentPage, key] pageSize：分页大小, currentPage：当前页数, key：搜索关键字
     * @return: com.hanson.dto.PageBean<com.hanson.dto.MemberTable> 用户数据，封装为pageBean对象
     * @Date: 2020/4/21
     */
    public PageBean<MemberTable> splitMemberTrash(Integer pageSize, Integer currentPage, String key);


    /*
    * @description: 根据用户id更新用户状态
    * @params: [memberId, state] memberId：用户id，state：用户状态
    * @return: com.hanson.dto.Message 返回处理消息
    * @Date: 2020/4/21
    */
    public Message transferMemberState(String memberId,Integer state);


    /*
    * @description: 根据用户id删除用户
    * @params: [memberId] 用户id
    * @return: com.hanson.dto.Message 返回处理消息
    * @Date: 2020/4/22
    */
    public Message deleteMember(String memberId);


    /*
    * @description: 批量删除用户
    * @params: [ids] 要删除的用户id，字符串格式，需要拆分处理
    * @return: com.hanson.dto.Message 返回删除处理信息
    * @Date: 2020/4/22
    */
    public Message batchDeleteMembers(String ids);



    /*
     * @description: 批量恢复用户
     * @params: [ids] 要恢复的用户id，字符串格式，需要拆分处理
     * @return: com.hanson.dto.Message 返回恢复处理信息
     * @Date: 2020/4/22
     */
    public Message batchRecoveryMembers(String ids);


    /*
     * @description: 批量启用用户
     * @params: [ids] 要启用的用户id，字符串格式，需要拆分处理
     * @return: com.hanson.dto.Message 返回启用处理信息
     * @Date: 2020/4/22
     */
    public Message batchOnMembers(String ids);


    /*
     * @description: 批量禁用用户
     * @params: [ids] 要禁用的用户id，字符串格式，需要拆分处理
     * @return: com.hanson.dto.Message 返回禁用处理信息
     * @Date: 2020/4/22
     */
    public Message batchOffMembers(String ids);


    /*
     * @description: 批量回收用户
     * @params: [ids] 要回收的用户id，字符串格式，需要拆分处理
     * @return: com.hanson.dto.Message 返回回收处理信息
     * @Date: 2020/4/22
     */
    public Message batchRecycleMembers(String ids);
}
