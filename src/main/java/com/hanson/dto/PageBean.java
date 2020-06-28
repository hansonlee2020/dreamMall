package com.hanson.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/**
 * @program: DreamMall
 * @description: 存储分页的对象，用于系统返回分页信息，实现了序列化接口
 * @param:
 * @author: Hanson
 * @create: 2020-04-11 20:01
 **/
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageBean<T> implements Serializable {
    private Long total;         //总数据量
    private Integer currentPage;//当前的页数(currentPage)
    private Integer pageSize;   //每页的数据量(pageSize)
    private Integer totalPage;  //页面的总数(total/pageSize)
    private Integer size;       //当前页面数据量 (直接=list.size())
    private List<T> list;       //每个分页结果集
}
