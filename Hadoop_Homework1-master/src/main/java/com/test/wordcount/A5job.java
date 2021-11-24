package com.test.wordcount;

import com.test.similar.SimilarBean;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.KeyValueLineRecordReader;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class A5job {
    public static void main(String[] args) throws Exception {
        Configuration conf= new Configuration();
        conf.set(KeyValueLineRecordReader.KEY_VALUE_SEPERATOR," ");
        Job job = Job.getInstance(conf,"A5");
        job.setJobName("169171-A5job-"+System.currentTimeMillis());
        job.setJarByClass(A5job.class);
        job.setMapperClass(A5map.class);
        job.setReducerClass(A5reduce.class);
        job.setInputFormatClass(KeyValueTextInputFormat.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
//        FileInputFormat.addInputPath(job,new Path("/Users/linyouguang/Desktop/input/small_relation"));
//        FileOutputFormat.setOutputPath(job, new Path("/Users/linyouguang/Desktop/output"));
//        FileInputFormat.addInputPath(job,new Path("/user/s1155169171/large/large_relation"));
        FileInputFormat.addInputPath(job,new Path("/user/s1155169171/large/large_relation"));
        FileOutputFormat.setOutputPath(job, new Path("/user/s1155169171/output-A5-"+System.currentTimeMillis()));
        System.exit(job.waitForCompletion(true)?0:1);
    }
}
