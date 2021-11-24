package com.test.wordcount;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.io.IOException;
public class A1map extends Mapper<Text,Text,Text, Text> {
    public void map(Text key, Text value, Context context) throws IOException,InterruptedException {
        context.write(value,key);
    }
}