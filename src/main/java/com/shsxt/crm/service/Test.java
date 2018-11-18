package com.shsxt.crm.service;

/**
 * Created by xlf on 2018/7/27.
 */
public class Test {
    public static void main(String[] args) {
        String code1 = "1010";
        String code2 = "111010";
        boolean b = code2.indexOf(code1) == 0;
        System.out.println(b);
    }
}
