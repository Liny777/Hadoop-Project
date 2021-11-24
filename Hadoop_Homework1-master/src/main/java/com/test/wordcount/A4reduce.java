package com.test.wordcount;

import java.io.IOException;
import java.util.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

public class A4reduce extends MapReduceBase implements Reducer<Text, Text, Text, Text> {
    Map<String, List<String>> map =new HashMap<String,List<String>>();
    OutputCollector<Text, Text> output_new;
    @Override
    public void reduce(Text key, Iterator<Text> values,
                       OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
        List<String> arr = new ArrayList<String>();
        while (values.hasNext()) {
            arr.add(values.next().toString());
        }
        map.put(key.toString(),arr);
        output_new = output;
    }

    @Override
    public void close()throws IOException{
        int sim = 0;
        String followee = "";
        Text text = new Text();
        Text text1 = new Text();
        for (Map.Entry<String, List<String>> entry1 : map.entrySet())
        {

            for (Map.Entry<String, List<String>> entry2 : map.entrySet())
            {
                if(entry2.getKey()!= entry1.getKey())
                {
                    List<String> ls1 = new ArrayList<String>();
                    ls1.addAll(entry1.getValue());
                    ls1.retainAll(entry2.getValue());
                    if (ls1.size()>sim)
                    {
                        followee = entry2.getKey();
                        sim = ls1.size();
                    }
                }
            }

            text.set(entry1.getKey());
            text1.set(followee);
            output_new.collect(text, text1);
        }
    }
}