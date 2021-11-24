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

public class CTestJob extends Configured implements Tool {
    @Override
    public int run(String[] strings) throws Exception {
        // 1
        Job job1 = Job.getInstance(getConf(), "mapreduce2_c1");
        job1.setInputFormatClass(TextInputFormat.class);
        TextInputFormat.addInputPath(job1, new Path("/Users/linyouguang/IdeaProjects/A-priori/src/dataset"));
        TextInputFormat.setMaxInputSplitSize(job1, 11090571);
        job1.setJarByClass(C1Job.class);
        job1.setMapperClass(C1M.class);
        job1.setMapOutputKeyClass(Text.class);
        job1.setMapOutputValueClass(DoubleWritable.class);

        job1.setReducerClass(C1R.class);
        job1.setOutputKeyClass(Text.class);
        job1.setOutputValueClass(NullWritable.class);

        job1.setOutputFormatClass(TextOutputFormat.class);
        TextOutputFormat.setOutputPath(job1, new Path("/Users/linyouguang/IdeaProjects/A-priori/src/output3_1"));
        boolean b1 = job1.waitForCompletion(true);
        // 2

        Job job2 = Job.getInstance(super.getConf(), "mapreduce2_c2");
        job2.setInputFormatClass(TextInputFormat.class);
        TextInputFormat.addInputPath(job2, new Path("/Users/linyouguang/IdeaProjects/A-priori/src/dataset"));
        job2.addCacheFile(new URI("/Users/linyouguang/IdeaProjects/A-priori/src/output3_1/part-r-00000"));
        job2.setJarByClass(C2Job.class);
        job2.setMapperClass(C2M.class);
        job2.setMapOutputKeyClass(Text.class);
        job2.setMapOutputValueClass(DoubleWritable.class);
        job2.setReducerClass(C2R.class);
        job2.setOutputKeyClass(Text.class);
        job2.setOutputValueClass(NullWritable.class);
        job2.setOutputFormatClass(TextOutputFormat.class);
        TextOutputFormat.setOutputPath(job2, new Path("/Users/linyouguang/IdeaProjects/A-priori/src/output3_2"));
        boolean b2 = job2.waitForCompletion(true);
        // 3
        Job job3 = Job.getInstance(super.getConf(), "mapreduce2_c3");
        job3.setInputFormatClass(TextInputFormat.class);
        TextInputFormat.addInputPath(job3, new Path("/Users/linyouguang/IdeaProjects/A-priori/src/dataset"));
        job3.addCacheFile(new URI("/Users/linyouguang/IdeaProjects/A-priori/src/output3_2/part-r-00000"));
        TextInputFormat.setMaxInputSplitSize(job3, 11090571);
        job3.setJarByClass(C3Job.class);
        job3.setMapperClass(C3M.class);
        job3.setMapOutputKeyClass(Text.class);
        job3.setMapOutputValueClass(DoubleWritable.class);
        job3.setReducerClass(C3R.class);
        job3.setOutputKeyClass(Text.class);
        job3.setOutputValueClass(DoubleWritable.class);
        job3.setOutputFormatClass(TextOutputFormat.class);
        TextOutputFormat.setOutputPath(job3, new Path("/Users/linyouguang/IdeaProjects/A-priori/src/output3_3"));
        boolean b3 = job3.waitForCompletion(true);

        return b3 ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration();
        int run = ToolRunner.run(configuration, new CTestJob(), args);
        System.exit(run);

    }
}
