package com.test.wordcount;

import com.test.similar.SimilarBean;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class A22Partition extends Partitioner<SimilarBean, Text> {
    // 分区规则：根据订单的ID实现分区
    /**
     *
     * @param similarBean K2
     * @param text V2
     * @param i ReduceTask的个数
     * @return 返回分区的编号
     */
    @Override
    public int getPartition(SimilarBean similarBean,Text text,int i){
        return (similarBean.getFollower().hashCode() & 2147483647) % i;
    }

}
