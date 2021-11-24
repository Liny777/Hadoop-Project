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

public class C1Job extends Configured implements Tool {
    @Override
    public int run(String[] strings) throws Exception {
        Job job1 = Job.getInstance(getConf(), "mapreduce2_c1");
        job1.setInputFormatClass(TextInputFormat.class);
        TextInputFormat.addInputPath(job1, new Path("/Users/linyouguang/IdeaProjects/A-priori/src/dataset"));
        TextInputFormat.setMaxInputSplitSize(job1, 11090571);
        job1.setJarByClass(C1Job.class);
        //job.addCacheFile(new URI("/Users/xiexiaohao/Desktop/end1c2/part-r-00000"));
        job1.setMapperClass(C1M.class);
        job1.setMapOutputKeyClass(Text.class);
        job1.setMapOutputValueClass(DoubleWritable.class);

        job1.setReducerClass(C1R.class);
        job1.setOutputKeyClass(Text.class);
        job1.setOutputValueClass(NullWritable.class);

        job1.setOutputFormatClass(TextOutputFormat.class);
        TextOutputFormat.setOutputPath(job1, new Path("/Users/linyouguang/IdeaProjects/A-priori/src/output4"));

        boolean b1 = job1.waitForCompletion(true);
        return b1 ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration();
        int run = ToolRunner.run(configuration, new C1Job(), args);
        System.exit(run);

    }
}
