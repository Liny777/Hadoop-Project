package Homework2_B;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.net.URI;

public class SonJob2 extends Configured implements Tool {

    @Override
    public int run(String[] strings) throws Exception {
        Job job = Job.getInstance(super.getConf(), "mapreduce2_b1");
        job.setInputFormatClass(TextInputFormat.class);
        TextInputFormat.addInputPath(job, new Path("/Users/linyouguang/IdeaProjects/A-priori/src/dataset"));
        job.setJarByClass(SonJob2.class);
        job.setMapperClass(SonMapper1.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        job.setReducerClass(SonReducer1.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        TextOutputFormat.setOutputPath(job, new Path("/Users/linyouguang/IdeaProjects/A-priori/src/output2"));
        boolean b = job.waitForCompletion(true);

        Job job1 = Job.getInstance(super.getConf(), "mapreduce2_b2");
        job1.setInputFormatClass(TextInputFormat.class);
        TextInputFormat.addInputPath(job1, new Path("/Users/linyouguang/IdeaProjects/A-priori/src/dataset"));
        job1.addCacheFile(new URI("/Users/linyouguang/IdeaProjects/A-priori/src/output2/part-r-00000"));
        job1.setJarByClass(SonJob2.class);
        job1.setMapperClass(SonMapper2.class);
        job1.setMapOutputKeyClass(Text.class);
        job1.setMapOutputValueClass(DoubleWritable.class);
        job1.setReducerClass(SonReducer2.class);
        job1.setOutputKeyClass(Text.class);
        job1.setOutputValueClass(DoubleWritable.class);
        job1.setOutputFormatClass(TextOutputFormat.class);
        TextOutputFormat.setOutputPath(job1, new Path("/Users/linyouguang/IdeaProjects/A-priori/src/output3"));
        boolean b1 = job1.waitForCompletion(true);
        return b1 ? 0 : 1;
//        boolean b = job.waitForCompletion(true);
//        return b ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration();
        int run = ToolRunner.run(configuration, new SonJob2(), args);
        System.exit(run);
    }
}
