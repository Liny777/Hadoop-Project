package com.test.wordcount;

import com.test.similar.SimilarBean;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.io.IOException;
import java.util.StringTokenizer;

public class A5map extends Mapper<Text,Text,Text, Text> {
    //    private IntWritable out = new IntWritable(1);
    public void map(Text key, Text value, Context context) throws IOException,InterruptedException {

        context.write(value,key);
    }
}