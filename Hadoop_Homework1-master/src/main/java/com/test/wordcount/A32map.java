package com.test.wordcount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class A32map extends Mapper<Text,Text,Text, IntWritable> {
    Text t = new Text();
    IntWritable w = new IntWritable(1);

    protected void map(Text key,Text value,Context context) throws IOException,InterruptedException{

        Integer fans_num = Integer.valueOf(value.toString());
        if(fans_num>=2){
//            w.set(fans_num);
            context.write(key,w);
        }
    }
}
