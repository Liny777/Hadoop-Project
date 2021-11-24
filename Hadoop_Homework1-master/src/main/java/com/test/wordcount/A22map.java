package com.test.wordcount;

import com.test.similar.SimilarBean;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class A22map extends Mapper<Text,Text, SimilarBean, Text> {
    Text t = new Text();
    Text t1 = new Text();

    public void map(Text key, Text value, Context context) throws IOException,InterruptedException {
        // 1.以冒号分割字符串
        String[] s1 = key.toString().split(":");
        String[] s2 = value.toString().split(":");
        // 2.封装SimilarBean
        SimilarBean sb = new SimilarBean();
        int length = s1[0].length();
        if(length>=4){
            if(s1[0].substring(length-4,length).equals("4941")){
                sb.setFollower(s1[0]);
                sb.setSimilarity(Double.valueOf(s2[1]));
                // 3.改变value
                String newvalue = key.toString() + value.toString();
                t.set(newvalue);
                // 4.将K2和V2写入上下文
                context.write(sb,t);
            }
        }

    }
}
