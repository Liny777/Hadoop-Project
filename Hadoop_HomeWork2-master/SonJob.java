package Homework2_B;
import Homework2_C.Job3_1;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.filecache.DistributedCache;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.net.URI;
//import java.net.URL;

public class SonJob extends Configured implements Tool {

    @Override
    public int run(String[] strings) throws Exception {
        Job job = Job.getInstance(super.getConf(), "mapreduce2_b1");
        job.setInputFormatClass(TextInputFormat.class);
//        TextInputFormat.addInputPath(job, new Path("hdfs://dicvmc2.ie.cuhk.edu.hk:8020/user/s1155169171/shakespeare_basket"));
        TextInputFormat.addInputPath(job, new Path("/user/s1155169171/shakespeare_basket"));
        job.setJarByClass(SonJob.class);
        job.setMapperClass(SonMapper1.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        job.setReducerClass(SonReducer1.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        job.setOutputFormatClass(TextOutputFormat.class);
//        TextOutputFormat.setOutputPath(job, new Path("hdfs://dicvmc2.ie.cuhk.edu.hk:8020/user/s1155169171/output_b1"));
        TextOutputFormat.setOutputPath(job, new Path("/user/s1155169171/output_b1"));
        boolean b = job.waitForCompletion(true);

//        Configuration config = super.getConf();
//        config.set("fs.defaultFS", "hdfs://dicvmc2.ie.cuhk.edu.hk:8020");
//        config.set("mapreduce.framework.name", "yarn");
//        config.set("yarn.resourcemanager.hostname", "dicvmc2.ie.cuhk.edu.hk");
//        Job job1 = Job.getInstance(config, "mapreduce_b2");
//        Configuration conf= new Configuration();
        Job job1 = Job.getInstance(super.getConf(),"mapreduce_b2");
        job1.setInputFormatClass(TextInputFormat.class);
//        TextInputFormat.addInputPath(job1, new Path("hdfs://dicvmc2.ie.cuhk.edu.hk:8020/user/s1155169171/shakespeare_basket"));
//        String url = "hdfs://dicvmc2.ie.cuhk.edu.hk:8020/user/s1155169171/output_b1/part-r-00000#liny";
        TextInputFormat.addInputPath(job1, new Path("/user/s1155169171/shakespeare_basket"));
        String url = "/user/s1155169171/output_b1/part-r-00000";
        job.addCacheFile(new URI(url));
//        DistributedCache.addCacheFile(new URI(url), config);
//        job1.addCacheFile(new URI(url));
        job1.setJarByClass(SonJob.class);
        job1.setMapperClass(SonMapper2.class);
        job1.setMapOutputKeyClass(Text.class);
        job1.setMapOutputValueClass(DoubleWritable.class);
        job1.setReducerClass(SonReducer2.class);
        job1.setOutputKeyClass(Text.class);
        job1.setOutputValueClass(DoubleWritable.class);
        job1.setOutputFormatClass(TextOutputFormat.class);
        TextOutputFormat.setOutputPath(job1,  new Path("/user/s1155169171/output_b2"));
        boolean b1 = job1.waitForCompletion(true);
        return b1 ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration();
        int run = ToolRunner.run(configuration, new SonJob(), args);
        System.exit(run);
    }
}