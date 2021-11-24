package com.test.wordcount;

import com.jcraft.jsch.IO;
import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

/*
    K1:  Text   blog
    V1:  Text   label

    K2:  Text   blog
    V2:  Text   followee
 */

public class A3map extends Mapper<Text,Text,Text, Text> {
    Text t = new Text();

    /*
            followee 博主
            follower 粉丝
     */
    /*
     relation       K1          V1
                    followee    follower
      label         K1          V1
                    followee    label
                    K2          V2
                    blog        [fans] label
     */
    //----------------------------------------------
    /*

                 K2          V2
                    followee    label
     */
    @Override
    protected void map(Text key,Text value,Context context) throws IOException,InterruptedException{
        // 1 判断数据来自哪个文件
            FileSplit fileSplit =  (FileSplit) context.getInputSplit();
            String fileName = fileSplit.getPath().getName();
            if(fileName.equals("medium_relation")){
                context.write(value,key);
                //1数据来自关系表
                //2将K1和V1转为K2和V2，写入上下文中
//                String[] split = value.toString()
            }else{
                //数据来自标签表
                String nvs = "L: "+value;
                t.set(nvs);
                context.write(key,t);
            }
        // 2 将K1和V1转为K2和V2，写入上下文中

    }
}
