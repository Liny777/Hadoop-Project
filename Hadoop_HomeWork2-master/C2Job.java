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

public class C2Job extends Configured implements Tool {
    @Override
    public int run(String[] strings) throws Exception {
        Job job1 = Job.getInstance(super.getConf(), "mapreduce2_c2");
        job1.setInputFormatClass(TextInputFormat.class);
        TextInputFormat.addInputPath(job1, new Path("/Users/linyouguang/IdeaProjects/A-priori/src/dataset"));
        job1.addCacheFile(new URI("/Users/linyouguang/IdeaProjects/A-priori/src/output4/part-r-00000"));
        job1.setJarByClass(C2Job.class);
        job1.setMapperClass(C2M.class);
        job1.setMapOutputKeyClass(Text.class);
        job1.setMapOutputValueClass(DoubleWritable.class);
        job1.setReducerClass(C2R.class);
        job1.setOutputKeyClass(Text.class);
        job1.setOutputValueClass(NullWritable.class);
        job1.setOutputFormatClass(TextOutputFormat.class);
        TextOutputFormat.setOutputPath(job1, new Path("/Users/linyouguang/IdeaProjects/A-priori/src/output5"));
        boolean b1 = job1.waitForCompletion(true);
        return b1 ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration();
        int run = ToolRunner.run(configuration, new C2Job(), args);
        System.exit(run);

    }
}
