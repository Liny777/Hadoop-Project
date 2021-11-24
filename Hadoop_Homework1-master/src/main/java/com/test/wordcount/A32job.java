package com.test.wordcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueLineRecordReader;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class A32job {
    public static void main(String[] args) throws Exception {
        Configuration conf= new Configuration();
        conf.set(KeyValueLineRecordReader.KEY_VALUE_SEPERATOR,"\t");
        Job job = Job.getInstance(conf,"A32");
        job.setJobName("169171-A32job-"+System.currentTimeMillis());
        job.setJarByClass(A32job.class);
        job.setMapperClass(A32map.class);
        job.setReducerClass(A32reduce.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setInputFormatClass(KeyValueTextInputFormat.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
//        FileInputFormat.addInputPath(job,new Path("/Users/linyouguang/Desktop/A33/part-r-00000"));
//        FileOutputFormat.setOutputPath(job, new Path("/Users/linyouguang/Desktop/output"));
        FileInputFormat.addInputPath(job,new Path("/user/s1155169171/output-A3-1633658576130/part-r-00000"));
        FileOutputFormat.setOutputPath(job, new Path("/user/s1155169171/output-A32-"+System.currentTimeMillis()));
        System.exit(job.waitForCompletion(true)?0:1);
    }
}