package com.knightboost.moonlight.service;

/*
 * Created by Knight-ZXW on 2022/5/6
 * email: nimdanoob@163.com
 */
public class Test {

    public static final int PROC_SPACE_TERM = (int)' ';

    public static final int PROC_TAB_TERM = (int)'\t';

    public static final int PROC_NEWLINE_TERM = (int) '\n';

    public static final int PROC_OUT_STRING = 0x1000;

    public static final int PROC_OUT_LONG = 0x2000;

    public static final int PROC_OUT_FLOAT = 0x4000;

    public static void main(String[] args) {
        System.out.println(" PROC_SPACE_TERM is "+PROC_SPACE_TERM);
        System.out.println(" PROC_TAB_TERM is "+PROC_TAB_TERM);
        System.out.println(" PROC_NEWLINE_TERM is "+PROC_NEWLINE_TERM);
        System.out.println(" and  is "+ (((PROC_SPACE_TERM|PROC_OUT_LONG)&PROC_SPACE_TERM) == PROC_SPACE_TERM));



    }
}
