package com.test.wordcount;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.KeyValueLineRecordReader;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class A6job {
    public static void main(String[] args) throws Exception {
        Configuration conf= new Configuration();
//        long splitSize = Math.max(1, Math.min(4, ));
//        conf.setInt(JobContext.NUM_MAPS,4);
        conf.set(KeyValueLineRecordReader.KEY_VALUE_SEPERATOR," ");
        Job job = Job.getInstance(conf,"A1");
//        .setMAXinputsplitsize(26857);
        job.setJobName("wordcountjob-"+System.currentTimeMillis());
        job.setJarByClass(A6job.class);
        job.setMapperClass(A1map.class);
        job.setReducerClass(A1reduce.class);
        job.setInputFormatClass(KeyValueTextInputFormat.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(job,new Path("/Users/linyouguang/Desktop/input/small_relation"));
        FileOutputFormat.setOutputPath(job, new Path("/Users/linyouguang/Desktop/output"));
//        FileInputFormat.addInputPath(job,new Path("/user/s1155169171/medium/medium_relation"));
//        FileOutputFormat.setOutputPath(job, new Path("/user/s1155169171/output-"+System.currentTimeMillis()));
        FileInputFormat.setMaxInputSplitSize(job,26857);
//        FileInputFormat.getMaxSplitSize();
//        FileInputFormat.getMinSplitSize();
        job.setNumReduceTasks(4);
        System.exit(job.waitForCompletion(true)?0:1);
    }
}
