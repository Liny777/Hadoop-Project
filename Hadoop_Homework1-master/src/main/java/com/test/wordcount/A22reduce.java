package com.test.wordcount;

import com.test.similar.SimilarBean;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;

public class A22reduce extends Reducer<SimilarBean, Text,Text, NullWritable> {

    Text t = new Text();
    Text t1 = new Text();
    public void reduce(SimilarBean key, Iterable<Text> values,Context context) throws IOException,InterruptedException{
        int count = 0;
        for(Text val:values){
            if(count<3){
                context.write(val,NullWritable.get());
            }
            count++;
        }
    }
}
