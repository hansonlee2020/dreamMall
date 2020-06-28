package com.hanson.util;

import org.apache.shiro.util.ByteSource;
import org.w3c.dom.ls.LSOutput;

/**
 * @program: DreamMall
 * @description: 测试
 * @param:
 * @author: Hanson
 * @create: 2020-05-14 18:49
 **/
public class Test  {
    public static void main(String[] args) {
        ByteSource byteSource = ByteSource.Util.bytes("1235554");
        System.out.println(byteSource);
    }
}
