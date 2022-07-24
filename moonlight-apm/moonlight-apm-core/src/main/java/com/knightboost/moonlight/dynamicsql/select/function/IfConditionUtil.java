package com.knightboost.moonlight.dynamicsql.select.function;


/*
 * Created by Knight-ZXW on 2022/5/12
 * email: nimdanoob@163.com
 */
public class IfConditionUtil {
    public static String render(String column,Number left,Number right){
        if (left!=null && right!=null){
            return isInRange(column,left,right);
        }
        if (left!=null){
         return    isGreaterThan(column,left);
        }
        if (right!=null){
            return isLessThan(column,right);
        }
        throw new IllegalStateException("left or right illegal"+" left -> "+left+
                " right ->"+right);

    }


    public static String isGreaterThanOrEquals(String column,Number left){
        return  column+">="+left;
    }

    public static String isGreaterThan(String column,Number left){
        return  column+">"+left;
    }

    public static String isLessThan(String column,Number left){
        return  column+"<"+left;
    }
    public static String isLessThanOrEquals(String column,Number left){
        return  column+"<="+left;
    }

    public static  String isInRange(String column,Number left, Number right){
        return column+">= "+left+" and "+column+"< "+right;
    }

}
