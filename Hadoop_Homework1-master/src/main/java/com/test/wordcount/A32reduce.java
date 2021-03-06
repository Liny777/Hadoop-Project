package com.test.wordcount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;

public class A32reduce extends Reducer<Text, IntWritable,Text, IntWritable> {

    public void reduce(Text key, Iterable<IntWritable> values,Context context) throws IOException,InterruptedException {
        IntWritable result = new IntWritable();
        int sum = 0;
        for (IntWritable val : values)
        {
            sum += val.get();
        }
        result.set(sum);
        context.write(key, result);
    }

}
