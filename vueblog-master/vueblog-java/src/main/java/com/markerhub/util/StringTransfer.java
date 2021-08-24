package com.markerhub.util;

/**
 * @ClassName : StringTransfer
 * @Author : Administrator
 * @Date: 2021/8/20 9:42
 * @Description :
 */
public class StringTransfer {
    public static String stringToLegal(String substring){
        return substring.replace('/','-')
                //.replace('(','-')
                .replace(')','-')
                ;
    }
}
