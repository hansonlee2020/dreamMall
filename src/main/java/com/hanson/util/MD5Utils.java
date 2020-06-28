package com.hanson.util;




import com.ndktools.javamd5.Mademd5;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @program: DreamMall
 * @description: MD5加密工具类
 * @param:
 * @author: Hanson
 * @create: 2020-03-18 23:05
 **/
public class MD5Utils {
    private final static String FACTOR = "saltValue531";

    private MD5Utils(){};//构造方法私有化
    /*
    * @description: 简单MD5加密
    * @params: [source] 需要加密的数据
    * @return: java.lang.String 返回32位md5加密字符串
    * @Date: 2020/3/19
    */
        public static String getMD5(byte[] source) {
            String s = null;
            char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'a', 'b', 'c', 'd', 'e', 'f' };// 用来将字节转换成16进制表示的字符
            try {
                java.security.MessageDigest md = java.security.MessageDigest
                        .getInstance("MD5");
                md.update(source);
                byte tmp[] = md.digest();// MD5 的计算结果是一个 128 位的长整数，
                // 用字节表示就是 16 个字节
                char str[] = new char[16 * 2];// 每个字节用 16 进制表示的话，使用两个字符， 所以表示成 16
                // 进制需要 32 个字符
                int k = 0;// 表示转换结果中对应的字符位置
                for (int i = 0; i < 16; i++) {// 从第一个字节开始，对 MD5 的每一个字节// 转换成 16
                    // 进制字符的转换
                    byte byte0 = tmp[i];// 取第 i 个字节
                    str[k++] = hexDigits[byte0 >>> 4 & 0xf];// 取字节中高 4 位的数字转换,// >>>
                    // 为逻辑右移，将符号位一起右移
                    str[k++] = hexDigits[byte0 & 0xf];// 取字节中低 4 位的数字转换

                }
                s = new String(str);// 换后的结果转换为字符串

            } catch (NoSuchAlgorithmException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return s;
        }

        /*
        * @description: 加盐md5加密
        * @params: [str] 需要加密数据
        * @return: java.util.Map<java.lang.String,java.lang.String> Map集合返回，key："userPwd","encryptionSalt"；value：属性对应的值
        * @Date: 2020/3/19
        */
        public static Map<String, String> encryptionToMD5(String str){
            String s = FACTOR + str;//拼接初始盐值
            Random random = new Random();
            Set<Integer> set = new HashSet<>();
            StringBuffer salt = new StringBuffer();                             //保存盐值
            while (set.size()<6){
                set.add(random.nextInt(s.length()));                            //生成6个不重复的索引
            }
            Iterator<Integer> iter = set.iterator();
            while (iter.hasNext()){
                Integer index = iter.next();
                salt.append(s.charAt(index));             //从初始盐值中取出对应索引里的6个字符，拼接新盐值
            }
            String pwdToMD5 = MD5(str, salt.toString());
            Map<String, String> map = new HashMap<>();
            map.put("userPwd",pwdToMD5);
            map.put("encryptionSalt",salt.toString());
            return map;
        }
        /*
        * @description: 加密方法
        * @params: [str, salt] str：需加密数据，salt：盐值
        * @return: java.lang.String 返回加密结果
        * @Date: 2020/3/19
        */
        public static String MD5(String str,String salt){
            return new SimpleHash("MD5", str, ByteSource.Util.bytes(salt), 2).toString();    //加密后数据
        }
        /*
        * @description: 加密验证
        * @params: [originalPwd, encryptedPwd] originalPwd：原始密码，encryptedPwd加密后密码
        * @return: boolean 验证通过返回true，验证失败返回false
        * @Date: 2020/3/21
        */
        public static boolean isRightPwd(String originalPwd, String encryptedPwd){
            if(originalPwd != null){
                return originalPwd.equals(encryptedPwd);
            }
            return false;
        }
}
