package com.test.wordcount;

import com.test.similar.SimilarBean;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

// WritableComparator是一个类
public class A22GroupComparator extends WritableComparator {
    /*

    1：继承WritableComparator
    2：调用父类的有参构造
    3：指定分组的规则（重写方法）

     */

    // 1.继承WriteableComparator
    public A22GroupComparator(){
        // 2.调用父类的有参构造
        super(SimilarBean.class,true);
    }

    // 3：指定分组的规则（重写方法）
    @Override
    // WritableComparable是一个接口
    public int compare(WritableComparable a,WritableComparable b){
        //3.1 对形参做强制类型转换
        SimilarBean first = (SimilarBean)a;
        SimilarBean second = (SimilarBean)b;
        //3.2 指定分组规则
//        return super.compare(a,b);
        return first.getFollower().compareTo(second.getFollower());
    }



}
