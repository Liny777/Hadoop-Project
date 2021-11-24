package com.test.similar;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class SimilarBean implements WritableComparable<SimilarBean> {

    Double similarity;
    String follower;

    public SimilarBean(){
        super();
    }
    public SimilarBean(Double similarity, String follower){
        super();
        this.similarity = similarity;
        this.follower = follower;
    }

    public Double getSimilarity() {
        return similarity;
    }

    public void setSimilarity(Double similarity) {
        this.similarity = similarity;
    }

    public String getFollower() {
        return follower;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }

    // 序列化方法
    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeDouble(similarity);
        dataOutput.writeUTF(follower);
    }

    // 反序列化方法
    @Override
    public void readFields(DataInput dataInput) throws IOException {
    // 必须要去和反序列化方法顺序一致
        similarity = dataInput.readDouble();
        follower = dataInput.readUTF();
    }
    public String toString(){
        return this.follower+"\t"+this.similarity;
    }

    // 指定排序的规则,先比较订单ID，如果订单一致，则排序订单金额（降序）
    @Override
    public int compareTo(SimilarBean similarBean){
//        return this.similarity.compareTo(this.getSimilarity());
        int i = this.follower.compareTo(similarBean.follower);
        if(i==0){ // i==0意味着id是一样的
            i = this.similarity.compareTo(similarBean.similarity)* -1;
        }
        return i;
//        return 0;
    }
}
