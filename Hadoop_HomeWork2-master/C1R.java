package HK2_C;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.*;

public class C1R extends Reducer<Text, DoubleWritable, Text, NullWritable> {
    private Integer baskets = 0;
    private Map<Set<String>, Double> map = new HashMap<>();
    private Double min_support = 0.0025;
    private Integer limited = 20;
    @Override
    protected void reduce(Text key, Iterable<DoubleWritable> values, Reducer<Text, DoubleWritable, Text, NullWritable>.Context context) throws IOException, InterruptedException {
        String[] arrs = null;
        arrs = key.toString().split(" ");
        context.write(new Text(arrs[0]+" "+arrs[1]),NullWritable.get());
    }
}