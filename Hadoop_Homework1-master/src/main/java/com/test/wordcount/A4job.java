package com.test.wordcount;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;
public class A4job {
    public static void main(String[] args) throws Exception {

        JobConf conf = new JobConf(A4job.class);
        conf.setNumMapTasks(8);
        conf.setNumReduceTasks(4);
        conf.setJobName("A4");

        conf.setMapOutputKeyClass(Text.class);
        conf.setMapOutputValueClass(Text.class);

        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(Text.class);

        conf.setMapperClass(A4map.class);
        conf.setReducerClass(A4reduce.class);

        // KeyValueTextInputFormat treats each line as an input record,
        // and splits the line by the tab character to separate it into key and value
        conf.setInputFormat(KeyValueTextInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);
//        FileInputFormat.setInputPaths(conf, new Path("/Users/linyouguang/Desktop/medium/medium_relation"));
//        FileOutputFormat.setOutputPath(conf, new Path("/Users/linyouguang/Desktop/output"));
        FileInputFormat.setInputPaths(conf, new Path("/user/s1155169171/medium/medium_relation"));
        FileOutputFormat.setOutputPath(conf, new Path("/user/s1155169171/output-A44-"+System.currentTimeMillis()));
        JobClient.runJob(conf);
    }
}
