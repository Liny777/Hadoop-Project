package Homework2_B;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;

public class SonReducer1 extends Reducer<Text, Text, Text, NullWritable> {
    @Override
    protected void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, NullWritable>.Context context) throws IOException, InterruptedException {
        String[] arrs = null;
        arrs = key.toString().split(" ");
        context.write(new Text(arrs[0]+" "+arrs[1]),NullWritable.get());
    }
}
