package com.test.wordcount;

import com.test.similar.SimilarBean;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.KeyValueLineRecordReader;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class A52job {
    public static void main(String[] args) throws Exception {

        Configuration conf= new Configuration();
        conf.set(KeyValueLineRecordReader.KEY_VALUE_SEPERATOR,"\t");
        Job job = Job.getInstance(conf,"A52");
        job.setJobName("169171-A52job-"+System.currentTimeMillis());
        job.setJarByClass(A52job.class);
//2. 设置job任务
        // 第一步：设置输入类和输入路径和输出
        job.setInputFormatClass(KeyValueTextInputFormat.class);
//        FileInputFormat.addInputPath(job,new Path("/Users/linyouguang/Desktop/input/part-r-00000"));
//        FileOutputFormat.setOutputPath(job, new Path("/Users/linyouguang/Desktop/output"));
        FileInputFormat.addInputPath(job,new Path("/user/s1155169171/output-A5-1633783984792/part-r-00000"));
        FileOutputFormat.setOutputPath(job, new Path("/user/s1155169171/output-A52-"+System.currentTimeMillis()));
        // 第二步：设置Mapper类和数据类型
        job.setMapperClass(A52map.class);
        job.setMapOutputKeyClass(SimilarBean.class);
        job.setMapOutputValueClass(Text.class);
        // 第三，四，五，六
        //设置分区
        job.setPartitionerClass(A22Partition.class);
        //设置分组
        job.setGroupingComparatorClass(A22GroupComparator.class);

        //第七步：设置Reducer类和数据类型
        job.setReducerClass(A22reduce.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        //设置mapreduce，不设置，默认是一个，就输出到一个文件。



        System.exit(job.waitForCompletion(true)?0:1);
    }
}
