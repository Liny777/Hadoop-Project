package com.test.wordcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueLineRecordReader;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
public class A3job {
    public static void main(String[] args) throws Exception {
        Configuration conf= new Configuration();
        conf.set(KeyValueLineRecordReader.KEY_VALUE_SEPERATOR," ");
        Job job = Job.getInstance(conf,"A3");
        job.setJobName("169171-A3job-"+System.currentTimeMillis());
        job.setJarByClass(A3job.class);
        job.setMapperClass(A3map.class);
        job.setReducerClass(A3reduce.class);
        job.setInputFormatClass(KeyValueTextInputFormat.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
//        FileInputFormat.addInputPath(job,new Path("/Users/linyouguang/Desktop/medium"));
//        FileOutputFormat.setOutputPath(job, new Path("/Users/linyouguang/Desktop/output"));
        FileInputFormat.addInputPath(job,new Path("/user/s1155169171/medium"));
        FileOutputFormat.setOutputPath(job, new Path("/user/s1155169171/output-A3-"+System.currentTimeMillis()));
        System.exit(job.waitForCompletion(true)?0:1);
    }
}