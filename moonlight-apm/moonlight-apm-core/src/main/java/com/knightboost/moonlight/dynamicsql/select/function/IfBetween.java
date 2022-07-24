package com.knightboost.moonlight.dynamicsql.select.function;

/*
 * Created by Knight-ZXW on 2022/5/12
 * email: nimdanoob@163.com
 */
public class IfBetween implements IfCondition{
    private Object leftValue;
    private Object rightValue;

    protected IfBetween(Object leftValue,Object rightValue){
        this.leftValue = leftValue;
        this.rightValue = rightValue;
    }

    public static IfBetween of(Object leftValue,Object rightValue){
        return  new IfBetween(leftValue,rightValue);
    }

    @Override
    public String render(String column) {

        return column+" >= "+leftValue+" and "+column+" < "+rightValue;
    }
}
