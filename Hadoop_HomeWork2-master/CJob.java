package HK2_C;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.filecache.DistributedCache;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.net.URI;

public class CJob extends Configured implements Tool {
    @Override
    public int run(String[] strings) throws Exception {
        // 1
        Job job1 = Job.getInstance(getConf(), "mapreduce2_c1");
        job1.setInputFormatClass(TextInputFormat.class);
        TextInputFormat.addInputPath(job1, new Path("hdfs://dicvmc2.ie.cuhk.edu.hk:8020/user/s1155169171/shakespeare_basket"));
        TextInputFormat.setMaxInputSplitSize(job1, 11090571);
        job1.setJarByClass(C1Job.class);
        job1.setMapperClass(C1M.class);
        job1.setMapOutputKeyClass(Text.class);
        job1.setMapOutputValueClass(DoubleWritable.class);

        job1.setReducerClass(C1R.class);
        job1.setOutputKeyClass(Text.class);
        job1.setOutputValueClass(NullWritable.class);

        job1.setOutputFormatClass(TextOutputFormat.class);
        TextOutputFormat.setOutputPath(job1, new Path("hdfs://dicvmc2.ie.cuhk.edu.hk:8020/user/s1155169171/output_c1"));
        boolean b1 = job1.waitForCompletion(true);
        // 2
        Configuration config = super.getConf();
        config.set("fs.defaultFS", "hdfs://dicvmc2.ie.cuhk.edu.hk:8020");
        config.set("mapreduce.framework.name", "yarn");
        config.set("yarn.resourcemanager.hostname", "dicvmc2.ie.cuhk.edu.hk");
//        Job job = Job.getInstance(config, "mapreduce_c2");
        String uri = "hdfs://dicvmc2.ie.cuhk.edu.hk:8020/user/s1155169171/output_c1/part-r-00000#test";

        Job job2 = Job.getInstance(config, "mapreduce2_c2");
        job2.setInputFormatClass(TextInputFormat.class);
        TextInputFormat.addInputPath(job2, new Path("hdfs://dicvmc2.ie.cuhk.edu.hk:8020/user/s1155169171/shakespeare_basket"));
        DistributedCache.addCacheFile(new URI(uri), config);
        TextInputFormat.setMaxInputSplitSize(job2, 11090571);
        job2.addCacheFile(new URI(uri));
        job2.setJarByClass(C2Job.class);
        job2.setMapperClass(C2M.class);
        job2.setMapOutputKeyClass(Text.class);
        job2.setMapOutputValueClass(DoubleWritable.class);
        job2.setReducerClass(C2R.class);
        job2.setOutputKeyClass(Text.class);
        job2.setOutputValueClass(NullWritable.class);
        job2.setOutputFormatClass(TextOutputFormat.class);
        TextOutputFormat.setOutputPath(job2, new Path("hdfs://dicvmc2.ie.cuhk.edu.hk:8020/user/s1155169171/output_c2"));
        boolean b2 = job2.waitForCompletion(true);
        // 3
        Configuration config1 = super.getConf();
        config1.set("fs.defaultFS", "hdfs://dicvmc2.ie.cuhk.edu.hk:8020");
        config1.set("mapreduce.framework.name", "yarn");
        config1.set("yarn.resourcemanager.hostname", "dicvmc2.ie.cuhk.edu.hk");
//        Job job = Job.getInstance(config, "mapreduce_c2");
//        String uri = "hdfs://dicvmc2.ie.cuhk.edu.hk:8020/user/s1155169171/output_c1/part-r-00000#test";
        Job job3 = Job.getInstance(config1, "mapreduce2_c3");
        job3.setInputFormatClass(TextInputFormat.class);
        TextInputFormat.addInputPath(job3, new Path("hdfs://dicvmc2.ie.cuhk.edu.hk:8020/user/s1155169171/shakespeare_basket"));
        String uri1 = "hdfs://dicvmc2.ie.cuhk.edu.hk:8020/user/s1155169171/output_c2/part-r-00000#test";
        DistributedCache.addCacheFile(new URI(uri1), config1);
        TextInputFormat.setMaxInputSplitSize(job3, 11090571);
        job3.setJarByClass(C3Job.class);
        job3.setMapperClass(C3M.class);
        job3.setMapOutputKeyClass(Text.class);
        job3.setMapOutputValueClass(DoubleWritable.class);
        job3.setReducerClass(C3R.class);
        job3.setOutputKeyClass(Text.class);
        job3.setOutputValueClass(DoubleWritable.class);
        job3.setOutputFormatClass(TextOutputFormat.class);
        TextOutputFormat.setOutputPath(job3, new Path("hdfs://dicvmc2.ie.cuhk.edu.hk:8020/user/s1155169171/output_c3"));
        boolean b3 = job3.waitForCompletion(true);

        return b3 ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration();
        int run = ToolRunner.run(configuration, new CJob(), args);
        System.exit(run);

    }
}
